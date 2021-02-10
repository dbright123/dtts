package com.samsung.android.sdk.iap.lib.vo;

import android.util.Base64;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class OwnedProductVo extends BaseVo {
    private static final String TAG = OwnedProductVo.class.getSimpleName();

    private String mPaymentId;
    private String mPurchaseId;
    private String mPurchaseDate;
    private String mPassThroughParam;

    // Expiration date for a item which is "subscription" type
    // ========================================================================
    private String mSubscriptionEndDate = "";
    // ========================================================================

    private String mJsonString = "";

    public OwnedProductVo() {
    }

    public OwnedProductVo(String _jsonString) {
        super(_jsonString);
        setJsonString(_jsonString);

        try {
            JSONObject jObject = new JSONObject(_jsonString);
            setPaymentId(jObject.optString("mPaymentId"));
            setPurchaseId(jObject.optString("mPurchaseId"));
            setPurchaseDate(getDateString(jObject.optLong("mPurchaseDate")));
            jObject.remove("mPurchaseDate");
            jObject.put("mPurchaseDate", getPurchaseDate());

            String decodedPassThroughParam = new String(Base64.decode(jObject.optString("mPassThroughParam"), 0), "UTF-8");
            setPassThroughParam(decodedPassThroughParam);

            if (jObject.optLong("mSubscriptionEndDate") != 0) {
                setSubscriptionEndDate(getDateString(jObject.optLong("mSubscriptionEndDate")));
            }
            jObject.remove("mSubscriptionEndDate");
            jObject.put("mSubscriptionEndDate", getSubscriptionEndDate());
            setJsonString(jObject.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public String getPaymentId() {
        return mPaymentId;
    }

    public void setPaymentId(String _paymentId) {
        mPaymentId = _paymentId;
    }

    public String getPurchaseId() {
        return mPurchaseId;
    }

    public void setPurchaseId(String _purchaseId) {
        mPurchaseId = _purchaseId;
    }

    public String getPurchaseDate() {
        return mPurchaseDate;
    }

    public void setPurchaseDate(String _purchaseDate) {
        mPurchaseDate = _purchaseDate;
    }

    public String getSubscriptionEndDate() {
        return mSubscriptionEndDate;
    }

    public void setSubscriptionEndDate(String _subscriptionEndDate) {
        mSubscriptionEndDate = _subscriptionEndDate;
    }

    public String getPassThroughParam() {
        return mPassThroughParam;
    }

    public void setPassThroughParam(String _passThroughParam) {
        mPassThroughParam = _passThroughParam;
    }

    public String getJsonString() {
        return mJsonString;
    }

    public void setJsonString(String _jsonString) {
        mJsonString = _jsonString;
    }

    public String dump() {
        String dump = super.dump() + "\n";

        dump += "PaymentID                      : " + getPaymentId() + "\n" +
                "PurchaseID                     : " + getPurchaseId() + "\n" +
                "PurchaseDate                   : " + getPurchaseDate() + "\n" +
                "PassThroughParam               : " + getPassThroughParam() + "\n" +
                "SubscriptionEndDate            : " + getSubscriptionEndDate();

        return dump;
    }
}