package com.samsung.android.sdk.iap.lib.vo;

import android.util.Base64;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class PurchaseVo extends BaseVo {
    private static final String TAG = PurchaseVo.class.getSimpleName();

    private String mPaymentId;
    private String mPurchaseId;
    private String mPurchaseDate;
    private String mVerifyUrl;
    private String mPassThroughParam;

    private String mItemImageUrl;
    private String mItemDownloadUrl;
    private String mReserved1;
    private String mReserved2;
    private String mOrderId;
    private String mUdpSignature;

    private String mJsonString;

    public PurchaseVo(String _jsonString) {
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

            setItemImageUrl(jObject.optString("mItemImageUrl"));
            setItemDownloadUrl(jObject.optString("mItemDownloadUrl"));
            setReserved1(jObject.optString("mReserved1"));
            setReserved2(jObject.optString("mReserved2"));
            setOrderId(jObject.optString("mOrderId"));

            setVerifyUrl(jObject.optString("mVerifyUrl"));
            setUdpSignature(jObject.optString("mUdpSignature"));

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

    public String getVerifyUrl() {
        return mVerifyUrl;
    }

    public void setVerifyUrl(String _verifyUrl) {
        mVerifyUrl = _verifyUrl;
    }

    public String getPassThroughParam() {
        return mPassThroughParam;
    }

    public void setPassThroughParam(String _passThroughParam) {
        mPassThroughParam = _passThroughParam;
    }

    public String getItemImageUrl() {
        return mItemImageUrl;
    }

    public void setItemImageUrl(String _itemImageUrl) {
        mItemImageUrl = _itemImageUrl;
    }

    public String getItemDownloadUrl() {
        return mItemDownloadUrl;
    }

    public void setItemDownloadUrl(String _itemDownloadUrl) {
        mItemDownloadUrl = _itemDownloadUrl;
    }

    public String getReserved1() {
        return mReserved1;
    }

    public void setReserved1(String _reserved1) {
        mReserved1 = _reserved1;
    }

    public String getReserved2() {
        return mReserved2;
    }

    public void setReserved2(String _reserved2) {
        mReserved2 = _reserved2;
    }

    public String getOrderId() {
        return mOrderId;
    }

    public void setOrderId(String orderId) {
        this.mOrderId = orderId;
    }

    public String getUdpSignature() {
        return mUdpSignature;
    }

    public void setUdpSignature(String udpSignature) {
        this.mUdpSignature = udpSignature;
    }

    public String getJsonString() {
        return mJsonString;
    }

    public void setJsonString(String _jsonString) {
        mJsonString = _jsonString;
    }

    public String dump() {
        String dump = super.dump() + "\n";

        dump += "PaymentID           : " + getPaymentId() + "\n" +
                "PurchaseId          : " + getPurchaseId() + "\n" +
                "PurchaseDate        : " + getPurchaseDate() + "\n" +
                "PassThroughParam    : " + getPassThroughParam() + "\n" +
                "VerifyUrl           : " + getVerifyUrl() + "\n" +
                "ItemImageUrl        : " + getItemImageUrl() + "\n" +
                "ItemDownloadUrl     : " + getItemDownloadUrl() + "\n" +
                "Reserved1           : " + getReserved1() + "\n" +
                "Reserved2           : " + getReserved2() + "\n" +
                "UdpSignature        : " + getUdpSignature();

        return dump;
    }
}