package com.samsung.android.sdk.iap.lib.vo;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

public class ConsumeVo {
    private static final String TAG = ConsumeVo.class.getSimpleName();

    private String mPurchaseId;
    private String mStatusString;
    private int mStatusCode;

    private String mJsonString = "";

    public ConsumeVo(String _jsonString) {
        setJsonString(_jsonString);
        try {
            JSONObject jObject = new JSONObject(_jsonString);

            Log.i(TAG, jObject.toString(4));

            setPurchaseId(jObject.optString("mPurchaseId"));
            setStatusString(jObject.optString("mStatusString"));
            setStatusCode(jObject.optInt("mStatusCode"));
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getPurchaseId() {
        return mPurchaseId;
    }

    public void setPurchaseId(String _paymentId) {
        mPurchaseId = _paymentId;
    }

    public String getStatusString() {
        return mStatusString;
    }

    public void setStatusString(String _statusString) {
        mStatusString = _statusString;
    }

    public int getStatusCode() {
        return mStatusCode;
    }

    public void setStatusCode(int _statusCode) {
        mStatusCode = _statusCode;
    }

    public String getJsonString() {
        return mJsonString;
    }

    public void setJsonString(String _jsonString) {
        mJsonString = _jsonString;
    }

    public String dump() {
        String dump = null;

        dump = "PurchaseId       : " + getPurchaseId() + "\n" +
                "StatusString     : " + getStatusString() + "\n" +
                "StatusCode       : " + getStatusCode();

        return dump;
    }
}