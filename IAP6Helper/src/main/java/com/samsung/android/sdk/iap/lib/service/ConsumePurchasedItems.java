package com.samsung.android.sdk.iap.lib.service;

import android.content.Context;
import android.util.Log;

import com.samsung.android.sdk.iap.lib.R;
import com.samsung.android.sdk.iap.lib.helper.HelperDefine;
import com.samsung.android.sdk.iap.lib.helper.IapHelper;
import com.samsung.android.sdk.iap.lib.listener.OnConsumePurchasedItemsListener;
import com.samsung.android.sdk.iap.lib.vo.ConsumeVo;

import java.util.ArrayList;

/**
 * Created by sangbum7.kim on 2018-02-28.
 */

public class ConsumePurchasedItems extends BaseService {
    private static final String TAG = ConsumePurchasedItems.class.getSimpleName();

    private OnConsumePurchasedItemsListener mOnConsumePurchasedItemsListener = null;
    private static String mPurchaseIds = "";
    protected ArrayList<ConsumeVo> mConsumeList = null;

    public ConsumePurchasedItems(IapHelper _iapHelper, Context _context, OnConsumePurchasedItemsListener _onConsumePurchasedItemsListener) {
        super(_iapHelper, _context);
        mOnConsumePurchasedItemsListener = _onConsumePurchasedItemsListener;
    }

    public static void setPurchaseIds(String _purchaseIds) {
        mPurchaseIds = _purchaseIds;
    }

    public void setConsumeList(ArrayList<ConsumeVo> _consumeList) {
        this.mConsumeList = _consumeList;
    }

    @Override
    public void runServiceProcess() {
        if (mIapHelper != null) {
            if (mIapHelper.safeConsumePurchasedItems(ConsumePurchasedItems.this,
                    mPurchaseIds,
                    mIapHelper.getShowErrorDialog()) == true) {
                return;
            }
        }
        mErrorVo.setError(HelperDefine.IAP_ERROR_INITIALIZATION, mContext.getString(R.string.mids_sapps_pop_unknown_error_occurred));
        onEndProcess();
    }

    @Override
    public void onReleaseProcess() {
        Log.i(TAG, "ConsumePurchasedItems.onReleaseProcess");
        try {
            if (mOnConsumePurchasedItemsListener != null) {
                mOnConsumePurchasedItemsListener.onConsumePurchasedItems(mErrorVo, mConsumeList);
            }
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
    }
}
