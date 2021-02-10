package com.samsung.android.sdk.iap.lib.vo;

import org.json.JSONException;
import org.json.JSONObject;

public class ProductVo extends BaseVo {
    private static final String TAG = ProductVo.class.getSimpleName();

    //Subscription data
    private String mSubscriptionDurationUnit;
    private String mSubscriptionDurationMultiplier;

    // Tiered Subscription data
    private String mTieredPrice = "";
    private String mTieredPriceString = "";
    private String mTieredSubscriptionYN = "";
    private String mTieredSubscriptionDurationUnit = "";
    private String mTieredSubscriptionDurationMultiplier = "";
    private String mTieredSubscriptionCount = "";
    private String mShowStartDate = "";
    private String mShowEndDate = "";

    private String mItemImageUrl;
    private String mItemDownloadUrl;
    private String mReserved1;
    private String mReserved2;
    private String mFreeTrialPeriod;

    private String mJsonString;

    public ProductVo() {
    }

    public ProductVo(String _jsonString) {
        super(_jsonString);
        setJsonString(_jsonString);

        try {
            JSONObject jObject = new JSONObject(_jsonString);

            setSubscriptionDurationUnit(jObject.optString("mSubscriptionDurationUnit"));
            setSubscriptionDurationMultiplier(jObject.optString("mSubscriptionDurationMultiplier"));

            setTieredSubscriptionYN(jObject.optString("mTieredSubscriptionYN"));
            setTieredSubscriptionDurationUnit(jObject.optString("mTieredSubscriptionDurationUnit"));
            setTieredSubscriptionDurationMultiplier(jObject.optString("mTieredSubscriptionDurationMultiplier"));
            setTieredSubscriptionCount(jObject.optString("mTieredSubscriptionCount"));
            setTieredPrice(jObject.optString("mTieredPrice"));
            setTieredPriceString(jObject.optString("mTieredPriceString"));
            setShowStartDate(getDateString(jObject.optLong("mShowStartDate")));
            setShowEndDate(getDateString(jObject.optLong("mShowEndDate")));

            setItemImageUrl(jObject.optString("mItemImageUrl"));
            setItemDownloadUrl(jObject.optString("mItemDownloadUrl"));
            setReserved1(jObject.optString("mReserved1"));
            setReserved2(jObject.optString("mReserved2"));
            setFreeTrialPeriod(jObject.optString("mFreeTrialPeriod"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String getSubscriptionDurationUnit() {
        return mSubscriptionDurationUnit;
    }

    public void setSubscriptionDurationUnit(String _subscriptionDurationUnit) {
        mSubscriptionDurationUnit = _subscriptionDurationUnit;
    }

    public String getSubscriptionDurationMultiplier() {
        return mSubscriptionDurationMultiplier;
    }

    public void setSubscriptionDurationMultiplier(
            String _subscriptionDurationMultiplier) {
        mSubscriptionDurationMultiplier = _subscriptionDurationMultiplier;
    }

    public String getTieredSubscriptionYN() {
        return mTieredSubscriptionYN;
    }

    public void setTieredSubscriptionYN(String _tieredSubscriptionYN) {
        this.mTieredSubscriptionYN = _tieredSubscriptionYN;
    }

    public String getTieredPrice() {
        return mTieredPrice;
    }

    public void setTieredPrice(String _tieredPrice) {
        this.mTieredPrice = _tieredPrice;
    }

    public String getTieredPriceString() {
        return mTieredPriceString;
    }

    public void setTieredPriceString(String _tieredPriceString) {
        this.mTieredPriceString = _tieredPriceString;
    }

    public String getTieredSubscriptionDurationUnit() {
        return mTieredSubscriptionDurationUnit;
    }

    public void setTieredSubscriptionDurationUnit(String _tieredSubscriptionDurationUnit) {
        this.mTieredSubscriptionDurationUnit = _tieredSubscriptionDurationUnit;
    }

    public String getTieredSubscriptionDurationMultiplier() {
        return mTieredSubscriptionDurationMultiplier;
    }

    public void setTieredSubscriptionDurationMultiplier(String _tieredSubscriptionDurationMultiplier) {
        this.mTieredSubscriptionDurationMultiplier = _tieredSubscriptionDurationMultiplier;
    }

    public String getTieredSubscriptionCount() {
        return mTieredSubscriptionCount;
    }

    public void setTieredSubscriptionCount(String _tieredSubscriptionCount) {
        this.mTieredSubscriptionCount = _tieredSubscriptionCount;
    }

    public String getShowStartDate() {
        return mShowStartDate;
    }

    public void setShowStartDate(String showStartDate) {
        this.mShowStartDate = showStartDate;
    }

    public String getShowEndDate() {
        return mShowEndDate;
    }

    public void setShowEndDate(String showEndDate) {
        this.mShowEndDate = showEndDate;
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

    public String getFreeTrialPeriod() {
        return mFreeTrialPeriod;
    }

    public void setFreeTrialPeriod(String _freeTrialPeriod) {
        mFreeTrialPeriod = _freeTrialPeriod;
    }

    public String getJsonString() {
        return mJsonString;
    }

    public void setJsonString(String _jsonString) {
        mJsonString = _jsonString;
    }

    public String tieredDump() {
        String dump = "";
        if (getTieredSubscriptionYN().equals("Y") == true) {
            dump = "TieredSubscriptionYN                 : " + getTieredSubscriptionYN() + "\n" +
                    "TieredPrice                          : " + getTieredPrice() + "\n" +
                    "TieredPriceString                    : " + getTieredPriceString() + "\n" +
                    "TieredSubscriptionCount              : " + getTieredSubscriptionCount() + "\n" +
                    "TieredSubscriptionDurationUnit       : " + getTieredSubscriptionDurationUnit() + "\n" +
                    "TieredSubscriptionDurationMultiplier : " + getTieredSubscriptionDurationMultiplier() + "\n" +
                    "ShowStartDate                        : " + getShowStartDate() + "\n" +
                    "ShowEndDate                          : " + getShowEndDate();

        }
        return dump;
    }

    public String dump() {
        String dump = super.dump() + "\n";

        dump += "SubscriptionDurationUnit       : "
                + getSubscriptionDurationUnit() + "\n" +
                "SubscriptionDurationMultiplier : " +
                getSubscriptionDurationMultiplier() + "\n" +
                "ItemImageUrl    : " + getItemImageUrl() + "\n" +
                "ItemDownloadUrl : " + getItemDownloadUrl() + "\n" +
                "Reserved1       : " + getReserved1() + "\n" +
                "Reserved2       : " + getReserved2() + "\n" +
                "FreeTrialPeriod : " + getFreeTrialPeriod() + "\n" +
                tieredDump();
        return dump;
    }
}