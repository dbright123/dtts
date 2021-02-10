package com.samsung.android.sdk.iap.lib.helper.task;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.samsung.android.iap.IAPConnector;
import com.samsung.android.sdk.iap.lib.R;
import com.samsung.android.sdk.iap.lib.helper.HelperDefine;
import com.samsung.android.sdk.iap.lib.service.ProductsDetails;
import com.samsung.android.sdk.iap.lib.vo.ProductVo;

import java.util.ArrayList;

/**
 * Asynchronized Task to load a list of items
 */
public class GetProductsDetailsTask extends BaseTask {
    private static final String TAG = GetProductsDetailsTask.class.getSimpleName();
    private String mProductIds = "";
    ArrayList<ProductVo> mProductsDetails = new ArrayList<ProductVo>();

    public GetProductsDetailsTask
            (
                    ProductsDetails _baseService,
                    IAPConnector _iapConnector,
                    Context _context,
                    String _productIDs,
                    boolean _showErrorDialog,
                    int _mode
            ) {
        super(_baseService, _iapConnector, _context, _showErrorDialog, _mode);
        mProductIds = _productIDs;

        _baseService.setProductsDetails(mProductsDetails);
    }

    @Override
    protected Boolean doInBackground(String... params) {
        try {
            int pagingIndex = 1;
            do {
                // 1) call getProductsDetails() method of IAPService
                // ---- Order Priority ----
                //  1. if productIds is not empty, the infomations abouts products included in the productIds are returned
                //  2. if productIds is empty, the infomations about all products in this package are returned on a page by page
                // ============================================================
                Bundle bundle = mIapConnector.getProductsDetails(
                        mPackageName,
                        mProductIds,
                        pagingIndex,
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
                if (mErrorVo.getErrorCode() == HelperDefine.IAP_ERROR_NONE) {
                    if (bundle != null) {
                        String nextPagingIndex = bundle.getString(HelperDefine.NEXT_PAGING_INDEX);
                        if (nextPagingIndex != null && nextPagingIndex.length() > 0) {
                            pagingIndex = Integer.parseInt(nextPagingIndex);
                            Log.i(TAG, "PagingIndex = " + nextPagingIndex);
                        } else {
                            pagingIndex = -1;
                        }

                        ArrayList<String> productStringList =
                                bundle.getStringArrayList(HelperDefine.KEY_NAME_RESULT_LIST);

                        if (productStringList != null) {
                            for (String productString : productStringList) {
                                ProductVo productVo = new ProductVo(productString);
                                mProductsDetails.add(productVo);
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
                    return true;
                }
                // ============================================================
            } while (pagingIndex > 0);
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
