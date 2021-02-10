package com.samsung.android.sdk.iap.lib.vo;

import com.samsung.android.sdk.iap.lib.helper.HelperDefine;

public class ErrorVo {
    private int mErrorCode = HelperDefine.IAP_PAYMENT_IS_CANCELED;
    private String mErrorString = "";
    private String mErrorDetailsString = "";
    private String mExtraString = "";
    private boolean mShowDialog = false;

    public void setError(int _errorCode, String _errorString) {
        mErrorCode = _errorCode;
        mErrorString = _errorString;
    }

    public void setError(int _errorCode, String _errorString, String _errorDetails) {
        mErrorCode = _errorCode;
        mErrorString = _errorString;
        mErrorDetailsString = _errorDetails;
    }

    public int getErrorCode() {
        return mErrorCode;
    }

    public String getErrorString() {
        return mErrorString;
    }

    public String getErrorDetailsString() {
        return mErrorDetailsString;
    }

    public String getExtraString() {
        return mExtraString;
    }

    public void setExtraString(String _extraString) {
        mExtraString = _extraString;
    }

    public boolean isShowDialog() {
        return mShowDialog;
    }

    public void setShowDialog(boolean _showDialog) {
        mShowDialog = _showDialog;
    }

    public String dump() {
        String dump =
                "ErrorCode    : " + getErrorCode() + "\n" +
                        "ErrorString  : " + getErrorString() + "\n" +
                        "ErrorDetailsString  : " + getErrorDetailsString() + "\n" +
                        "ExtraString  : " + getExtraString();
        return dump;
    }
}
