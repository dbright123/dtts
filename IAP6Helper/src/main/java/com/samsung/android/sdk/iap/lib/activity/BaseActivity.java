package com.samsung.android.sdk.iap.lib.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.samsung.android.sdk.iap.lib.R;
import com.samsung.android.sdk.iap.lib.dialog.BaseDialogFragment;
import com.samsung.android.sdk.iap.lib.helper.HelperDefine;
import com.samsung.android.sdk.iap.lib.helper.HelperUtil;
import com.samsung.android.sdk.iap.lib.helper.IapHelper;
import com.samsung.android.sdk.iap.lib.vo.ErrorVo;
import com.samsung.android.sdk.iap.lib.vo.PurchaseVo;


public abstract class BaseActivity extends Activity {
    private static final String TAG = BaseActivity.class.getSimpleName();

    protected ErrorVo mErrorVo = new ErrorVo();
    private Dialog mProgressDialog = null;
    protected PurchaseVo mPurchaseVo = null;

    /**
     * Helper Class between IAPService and 3rd Party Application
     */
    IapHelper mIapHelper = null;

    /**
     * Flag value to show successful pop-up. Error pop-up appears whenever it fails or not.
     */
    protected boolean mShowErrorDialog = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // 1. IapHelper Instance creation
        //    To test on development, set mode to test mode using
        //    use HelperDefine.OperationMode.OPERATION_MODE_TEST or
        //    HelperDefine.OperationMode.OPERATION_MODE_TEST_FAILURE constants.
        // ====================================================================
        mIapHelper = IapHelper.getInstance(this);
        // ====================================================================

        // 2. This activity is invisible excepting progress bar as default.
        // ====================================================================
        try {
            Toast.makeText(this,
                    R.string.dream_sapps_body_authenticating_ing,
                    Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
        // ====================================================================

        super.onCreate(savedInstanceState);
    }

    public void setErrorVo(ErrorVo _errorVo) {
        mErrorVo = _errorVo;
    }

    public boolean checkAppsPackage(Activity _activity) {
        // 1. If Galaxy Store is installed
        // ====================================================================
        if (HelperUtil.isInstalledAppsPackage(this)) {
            // 1) If Galaxy Store is enabled
            // ================================================================
            if (!HelperUtil.isEnabledAppsPackage(this)) {
                HelperUtil.showEnableGalaxyStoreDialog(_activity);
                // ================================================================
                // 2) If Galaxy Store is valid
                // ================================================================
            } else if (HelperUtil.isValidAppsPackage(this)) {
                return true;
            } else {
                // Set error to notify result to third-party application
                // ------------------------------------------------------------
                final String ERROR_ISSUER_IAP_CLIENT = "IC";
                final int ERROR_CODE_INVALID_GALAXY_STORE = 10002;
                String errorString = String.format(
                        getString(
                                R.string.dream_ph_body_contact_p1sscustomer_servicep2ss_for_more_information_n_nerror_code_c_p3ss),
                        "", "", ERROR_ISSUER_IAP_CLIENT + ERROR_CODE_INVALID_GALAXY_STORE);
                mErrorVo.setError(HelperDefine.IAP_PAYMENT_IS_CANCELED, errorString);
                HelperUtil.showInvalidGalaxyStoreDialog(this);
            }
            // ================================================================

            // ====================================================================
            // 2. If Galaxy Store is not installed
            // ====================================================================
        } else {
            HelperUtil.installAppsPackage(this);
        }
        // ====================================================================
        return false;
    }

    /**
     * dispose IapHelper {@link PaymentActivity} To do that, preDestory must be invoked at first in onDestory of each child activity
     */
    protected void preDestory() {
        // 1. Invoke dispose Method to unbind service and release inprogress flag
        // ====================================================================
        if (mIapHelper != null) {
            mIapHelper.dispose();
            mIapHelper = null;
        }
    }

    @Override
    protected void onDestroy() {
        // 1. dismiss ProgressDialog
        // ====================================================================
        try {
            if (mProgressDialog != null) {
                mProgressDialog.dismiss();
                mProgressDialog = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        // ====================================================================

        super.onDestroy();
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0, 0);
    }

    protected void finishPurchase(Intent _intent) {
        // 1. If there is bundle passed from IAP
        // ====================================================================
        if (_intent != null && _intent.getExtras() != null) {
            Bundle extras = _intent.getExtras();
            mErrorVo.setError(
                    extras.getInt(HelperDefine.KEY_NAME_STATUS_CODE),
                    extras.getString(HelperDefine.KEY_NAME_ERROR_STRING),
                    extras.getString(HelperDefine.KEY_NAME_ERROR_DETAILS, ""));

            // 1) If the purchase is successful,
            // ----------------------------------------------------------------
            if (mErrorVo.getErrorCode() == HelperDefine.IAP_ERROR_NONE) {
                mPurchaseVo = new PurchaseVo(extras.getString(
                        HelperDefine.KEY_NAME_RESULT_OBJECT));

                mErrorVo.setError(
                        HelperDefine.IAP_ERROR_NONE,
                        getString(R.string.dream_sapps_body_your_purchase_is_complete));

                finish();
            }
            // ----------------------------------------------------------------
            // 2) If the purchase is failed
            // ----------------------------------------------------------------
            else {
                Log.e(TAG, "finishPurchase: " + mErrorVo.dump());
                if (mShowErrorDialog) {
                    HelperUtil.showIapErrorDialog(
                            this,
                            getString(R.string.dream_ph_pheader_couldnt_complete_purchase),
                            mErrorVo.getErrorString(),
                            mErrorVo.getErrorDetailsString(),
                            new BaseDialogFragment.OnClickListener() {
                                @Override
                                public void onClick() {
                                    finish();
                                }
                            },
                            null);
                } else {
                    finish();
                }
            }
            // ----------------------------------------------------------------
        }
        // ====================================================================
        // 2. If there is no bundle passed from IAP
        // ====================================================================
        else {
            mErrorVo.setError(
                    HelperDefine.IAP_ERROR_COMMON,
                    getString(R.string.mids_sapps_pop_unknown_error_occurred));

            if (mShowErrorDialog) {
                HelperUtil.showIapErrorDialog(
                        this,
                        getString(R.string.dream_ph_pheader_couldnt_complete_purchase),
                        getString(R.string.mids_sapps_pop_unknown_error_occurred),
                        new BaseDialogFragment.OnClickListener() {
                            @Override
                            public void onClick() {
                                finish();
                            }
                        },
                        null);
            } else {
                finish();
            }

            return;
        }
        // ====================================================================
    }

    protected void cancelPurchase(Intent intent) {
        if (intent != null) {
            Bundle extras = intent.getExtras();
            if (extras != null) {
                mErrorVo.setError(
                        extras.getInt(
                                HelperDefine.KEY_NAME_STATUS_CODE,
                                HelperDefine.IAP_PAYMENT_IS_CANCELED),
                        extras.getString(
                                HelperDefine.KEY_NAME_ERROR_STRING,
                                getString(R.string.mids_sapps_pop_payment_canceled)),
                        extras.getString(HelperDefine.KEY_NAME_ERROR_DETAILS, ""));
                finish();
                return;
            }
        }

        mErrorVo.setError(
                HelperDefine.IAP_PAYMENT_IS_CANCELED,
                getString(R.string.mids_sapps_pop_payment_canceled));
        finish();
    }
}