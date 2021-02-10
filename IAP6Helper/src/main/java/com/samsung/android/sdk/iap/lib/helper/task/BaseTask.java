package com.samsung.android.sdk.iap.lib.helper.task;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.samsung.android.iap.IAPConnector;
import com.samsung.android.sdk.iap.lib.R;
import com.samsung.android.sdk.iap.lib.helper.HelperDefine;
import com.samsung.android.sdk.iap.lib.service.BaseService;
import com.samsung.android.sdk.iap.lib.vo.ErrorVo;

/**
 * Created by sangbum7.kim on 2017-09-01.
 */

public class BaseTask extends AsyncTask<String, Object, Boolean> {
    private static final String TAG = BaseTask.class.getSimpleName();

    protected BaseService mBaseService;
    protected IAPConnector mIapConnector;
    protected Context mContext;
    protected int mMode;
    protected String mPackageName = "";

    protected ErrorVo mErrorVo = new ErrorVo();

    public BaseTask(BaseService _baseService,
            IAPConnector _iapConnector,
            Context _context,
            boolean _showErrorDialog,
            int _mode) {

        mBaseService = _baseService;
        mIapConnector = _iapConnector;
        mContext = _context;
        if (mContext != null) {
            mPackageName = mContext.getPackageName();
        }
        mMode = _mode;
        mErrorVo.setShowDialog(_showErrorDialog);
        mBaseService.setErrorVo(mErrorVo);
    }

    @Override
    protected Boolean doInBackground(String... params) {
        return true;
    }

    @Override
    protected void onPostExecute(Boolean _result) {
        // ================================================================
        if (!_result) {
            mErrorVo.setError(mErrorVo.getErrorCode(), mContext.getString(R.string.mids_sapps_pop_unknown_error_occurred));
        }
        // ================================================================

        mBaseService.onEndProcess();
    }

    @Override
    protected void onCancelled() {
        Log.e(TAG, "onCancelled: task cancelled");
    }
}
