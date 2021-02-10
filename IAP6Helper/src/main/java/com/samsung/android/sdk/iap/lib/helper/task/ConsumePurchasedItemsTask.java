package com.samsung.android.sdk.iap.lib.helper.task;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.samsung.android.iap.IAPConnector;
import com.samsung.android.sdk.iap.lib.R;
import com.samsung.android.sdk.iap.lib.helper.HelperDefine;
import com.samsung.android.sdk.iap.lib.service.ConsumePurchasedItems;
import com.samsung.android.sdk.iap.lib.vo.ConsumeVo;

import java.util.ArrayList;

/**
 * Asynchronized Task to load a list of items
 */
public class ConsumePurchasedItemsTask extends BaseTask {
    private static final String TAG = GetOwnedListTask.class.getSimpleName();
    private String mPurchaseIds = "";

    ArrayList<ConsumeVo> mConsumeList = new ArrayList<ConsumeVo>();

    public ConsumePurchasedItemsTask
            (
                    ConsumePurchasedItems _baseService,
                    IAPConnector _iapConnector,
                    Context _context,
                    String _purchaseIds,
                    boolean _showErrorDialog,
                    int _mode
            ) {
        super(_baseService, _iapConnector, _context, _showErrorDialog, _mode);
        mPurchaseIds = _purchaseIds;

        _baseService.setConsumeList(mConsumeList);
    }

    @Override
    protected Boolean doInBackground(String... params) {
        try {
            // 1) call getItemList() method of IAPService
            // ============================================================
            Bundle bundle = mIapConnector.consumePurchasedItems(
                    mPackageName,
                    mPurchaseIds,
                    mMode);
            // ============================================================

            // 2) save status code, error string and extra String.
            // ============================================================
            if (bundle != null) {
                mErrorVo.setError(bundle.getInt(HelperDefine.KEY_NAME_STATUS_CODE),
                        bundle.getString(HelperDefine.KEY_NAME_ERROR_STRING));
            } else {
                mErrorVo.setError(
                        HelperDefine.IAP_ERROR_COMMON,
                        mContext.getString(
                                R.string.mids_sapps_pop_unknown_error_occurred));
            }
            // ============================================================

            // 3) If item list is loaded successfully,
            //    make item list by Bundle data
            // ============================================================
            // ============================================================

            // 3) If item list is loaded successfully,
            //    make item list by Bundle data
            // ============================================================
            if (mErrorVo.getErrorCode() == HelperDefine.IAP_ERROR_NONE) {
                if (bundle != null) {
                    ArrayList<String> consumePurchasedItemsStringList =
                            bundle.getStringArrayList(HelperDefine.KEY_NAME_RESULT_LIST);

                    if (consumePurchasedItemsStringList != null) {
                        for (String consumePurchasedItemString : consumePurchasedItemsStringList) {
                            ConsumeVo consumeVo = new ConsumeVo(consumePurchasedItemString);
                            mConsumeList.add(consumeVo);
                        }
                    } else {
                        Log.i(TAG, "Bundle Value 'RESULT_LIST' is null.");
                    }
                }
            }
            // ============================================================
            // 4) If failed, print log.
            // ============================================================
            else {
                Log.e(TAG, mErrorVo.getErrorString());
            }
            // ============================================================
        } catch (Exception e) {
            mErrorVo.setError(
                    HelperDefine.IAP_ERROR_COMMON,
                    mContext.getString(
                            R.string.mids_sapps_pop_unknown_error_occurred));

            e.printStackTrace();
            return false;
        }

        return true;
    }
}
