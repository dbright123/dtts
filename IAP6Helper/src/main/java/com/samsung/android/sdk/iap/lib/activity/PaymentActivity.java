package com.samsung.android.sdk.iap.lib.activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.samsung.android.sdk.iap.lib.R;
import com.samsung.android.sdk.iap.lib.helper.HelperDefine;
import com.samsung.android.sdk.iap.lib.helper.HelperListenerManager;
import com.samsung.android.sdk.iap.lib.listener.OnPaymentListener;

public class PaymentActivity extends BaseActivity {
    private static final String TAG = PaymentActivity.class.getSimpleName();

    private String mItemId = null;
    private String mPassThroughParam = "";
    private int mMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        if (intent != null && intent.getExtras() != null
                && intent.getExtras().containsKey("ItemId")) {
            Bundle extras = intent.getExtras();
            mItemId = extras.getString("ItemId");
            mPassThroughParam = extras.getString("PassThroughParam");
            mShowErrorDialog = extras.getBoolean("ShowErrorDialog", true);
            mMode = extras.getInt("OperationMode", HelperDefine.OperationMode.OPERATION_MODE_PRODUCTION.getValue());
        } else {
            Toast.makeText(this,
                    R.string.mids_sapps_pop_an_invalid_value_has_been_provided_for_samsung_in_app_purchase,
                    Toast.LENGTH_LONG).show();

            // Set error to pass result to third-party application
            // ----------------------------------------------------------------
            mErrorVo.setError(HelperDefine.IAP_ERROR_COMMON,
                    getString(R.string.mids_sapps_pop_an_invalid_value_has_been_provided_for_samsung_in_app_purchase));
            // ----------------------------------------------------------------

            finish();
        }

        if (checkAppsPackage(this)) {
            startPaymentActivity();
        }
    }

    @Override
    protected void onDestroy() {
        super.preDestory();
        if (isFinishing()) {
            OnPaymentListener onPaymentListener =
                    HelperListenerManager.getInstance().getOnPaymentListener();
            HelperListenerManager.getInstance().setOnPaymentListener(null);
            if (null != onPaymentListener) {
                onPaymentListener.onPayment(mErrorVo, mPurchaseVo);
            }
        }
        super.onDestroy();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onActivityResult(int _requestCode, int _resultCode, Intent _intent) {
        switch (_requestCode) {
            case HelperDefine.REQUEST_CODE_IS_IAP_PAYMENT: {
                 if (_resultCode == RESULT_OK) {
                    finishPurchase(_intent);
                }
                else if (_resultCode == RESULT_CANCELED) {
                    Log.e(TAG, "Payment is canceled.");
                    cancelPurchase(_intent);
                }
                break;
            }
            case HelperDefine.REQUEST_CODE_IS_ENABLE_APPS: {
                if (checkAppsPackage(this)) {
                    startPaymentActivity();
                }
                break;
            }
        }
    }

    private void startPaymentActivity() {
        if (mIapHelper == null) {
            Log.e(TAG, "Fail to get IAP Helper instance");
            return;
        }
        try {
            Context context = this.getApplicationContext();
            Bundle bundle = new Bundle();
            bundle.putString(HelperDefine.KEY_NAME_THIRD_PARTY_NAME, context.getPackageName());
            bundle.putString(HelperDefine.KEY_NAME_ITEM_ID, mItemId);
            if (mPassThroughParam != null) {
                bundle.putString(HelperDefine.KEY_NAME_PASSTHROUGH_ID, mPassThroughParam);
            }
            bundle.putInt(HelperDefine.KEY_NAME_OPERATION_MODE, mMode);
            bundle.putString(HelperDefine.KEY_NAME_VERSION_CODE, HelperDefine.HELPER_VERSION);

            ComponentName com = new ComponentName(HelperDefine.GALAXY_PACKAGE_NAME,
                    HelperDefine.IAP_PACKAGE_NAME + ".activity.PaymentMethodListActivity");

            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_LAUNCHER);
            intent.setComponent(com);

            intent.putExtras(bundle);

            if (intent.resolveActivity(context.getPackageManager()) != null) {
                startActivityForResult(intent, HelperDefine.REQUEST_CODE_IS_IAP_PAYMENT);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}