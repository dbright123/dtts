package com.samsung.android.sdk.iap.lib.service;

import android.content.Context;
import android.util.Log;

import com.samsung.android.sdk.iap.lib.R;
import com.samsung.android.sdk.iap.lib.helper.HelperDefine;
import com.samsung.android.sdk.iap.lib.helper.IapHelper;
import com.samsung.android.sdk.iap.lib.listener.OnGetProductsDetailsListener;
import com.samsung.android.sdk.iap.lib.vo.ProductVo;

import java.util.ArrayList;

/**
 * Created by sangbum7.kim on 2018-02-28.
 */

public class ProductsDetails extends BaseService {
    private static final String TAG = ProductsDetails.class.getSimpleName();

    private OnGetProductsDetailsListener mOnGetProductsDetailsListener = null;
    private static String mProductIds = "";
    protected ArrayList<ProductVo> mProductsDetails = null;

    public ProductsDetails(IapHelper _iapHelper, Context _context, OnGetProductsDetailsListener _onGetProductsDetailsListener) {
        super(_iapHelper, _context);
        mOnGetProductsDetailsListener = _onGetProductsDetailsListener;
    }

    public static void setProductId(String _productIds) {
        mProductIds = _productIds;
    }

    public void setProductsDetails(ArrayList<ProductVo> _ProductsDetails) {
        this.mProductsDetails = _ProductsDetails;
    }

    @Override
    public void runServiceProcess() {
        Log.i(TAG, "succeedBind");
        if (mIapHelper != null) {
            if (mIapHelper.safeGetProductsDetails(ProductsDetails.this,
                    mProductIds,
                    mIapHelper.getShowErrorDialog()) == true) {
                return;
            }
        }
        mErrorVo.setError(HelperDefine.IAP_ERROR_INITIALIZATION, mContext.getString(R.string.mids_sapps_pop_unknown_error_occurred));
        onEndProcess();
    }

    @Override
    public void onReleaseProcess() {
        Log.i(TAG, "OwnedProduct.onEndProcess");
        try {
            if (mOnGetProductsDetailsListener != null) {
                mOnGetProductsDetailsListener.onGetProducts(mErrorVo, mProductsDetails);
            }
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
    }
}
