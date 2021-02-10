package com.samsung.android.sdk.iap.lib.helper;

/**
 * Created by sangbum7.kim on 2017-07-17.
 */

public class HelperDefine {
    public static final String HELPER_VERSION = "6.1.0.004";
    // IAP Signature HashCode - Used to validate IAP package
    // ========================================================================
    public static final int APPS_SIGNATURE_HASHCODE = 0x79998D13;
    public static final int APPS_PACKAGE_VERSION = 450301000;
    public static final int APPS_PACKAGE_VERSION_GO = 510130000;
    public static final int APPS_PACKAGE_VERSION_INDIA = 660107000;

    // ========================================================================

    // Name of IAP Package and Service
    // ========================================================================
    public static final String GALAXY_PACKAGE_NAME = "com.sec.android.app.samsungapps";
    public static final String IAP_PACKAGE_NAME = "com.samsung.android.iap";
    public static final String IAP_SERVICE_NAME =
            "com.samsung.android.iap.service.IAPService";
    // ========================================================================

    // result code for binding to IAPService
    // ========================================================================
    public static final int IAP_RESPONSE_RESULT_OK = 0;
    public static final int IAP_RESPONSE_RESULT_UNAVAILABLE = 2;
    // ========================================================================

    // BUNDLE KEY
    // ========================================================================
    public static final String KEY_NAME_THIRD_PARTY_NAME = "THIRD_PARTY_NAME";
    public static final String KEY_NAME_STATUS_CODE = "STATUS_CODE";
    public static final String KEY_NAME_ERROR_STRING = "ERROR_STRING";
    public static final String KEY_NAME_ERROR_DETAILS = "ERROR_DETAILS";
    public static final String KEY_NAME_ITEM_ID = "ITEM_ID";
    public static final String KEY_NAME_PASSTHROUGH_ID = "PASSTHROUGH_ID";
    public static final String KEY_NAME_RESULT_LIST = "RESULT_LIST";
    public static final String KEY_NAME_OPERATION_MODE = "OPERATION_MODE";
    public static final String KEY_NAME_RESULT_OBJECT = "RESULT_OBJECT";
    public static final String KEY_NAME_VERSION_CODE = "VERSION_CODE";
    public static final String NEXT_PAGING_INDEX = "NEXT_PAGING_INDEX";
    // ========================================================================

    // Item Type
    // ========================================================================
    public static final String PRODUCT_TYPE_ITEM = "item";
    public static final String PRODUCT_TYPE_SUBSCRIPTION = "subscription";
    public static final String PRODUCT_TYPE_ALL = "all";

    // Define request code to IAPService.
    // ========================================================================
    public static final int REQUEST_CODE_IS_IAP_PAYMENT = 1;
    public static final int REQUEST_CODE_IS_ACCOUNT_CERTIFICATION = 2;
    public static final int REQUEST_CODE_IS_ENABLE_APPS = 3;
    // ========================================================================

    // Define dialog type to DialogActivity
    public static final int DIALOG_TYPE_NONE = 0;
    public static final int DIALOG_TYPE_NOTIFICATION = 1;
    public static final int DIALOG_TYPE_INVALID_PACKAGE = 2;
    public static final int DIALOG_TYPE_DISABLE_APPLICATION = 3;
    public static final int DIALOG_TYPE_APPS_DETAIL = 4;

    // Define request parameter to IAPService
    // ========================================================================
    public static final int PASSTHROGUH_MAX_LENGTH = 255;
    // ========================================================================

    // Define status code notify to 3rd-party application
    // ========================================================================
    /**
     * Success
     */
    final public static int IAP_ERROR_NONE = 0;

    /**
     * Payment is cancelled
     */
    final public static int IAP_PAYMENT_IS_CANCELED = 1;

    /**
     * IAP initialization error
     */
    final public static int IAP_ERROR_INITIALIZATION = -1000;

    /**
     * IAP need to be upgraded
     */
    final public static int IAP_ERROR_NEED_APP_UPGRADE = -1001;

    /**
     * Common error
     */
    final public static int IAP_ERROR_COMMON = -1002;

    /**
     * Repurchase NON-CONSUMABLE item
     */
    final public static int IAP_ERROR_ALREADY_PURCHASED = -1003;

    /**
     * When PaymentMethodList Activity is called without Bundle data
     */
    final public static int IAP_ERROR_WHILE_RUNNING = -1004;

    /**
     * does not exist item or item group id
     */
    final public static int IAP_ERROR_PRODUCT_DOES_NOT_EXIST = -1005;

    /**
     * After purchase request not received the results can not be determined whether to buy. So, the confirmation of purchase list is needed.
     */
    final public static int IAP_ERROR_CONFIRM_INBOX = -1006;

    /**
     * Error when item group id does not exist
     */
    public static final int IAP_ERROR_ITEM_GROUP_DOES_NOT_EXIST = -1007;

    /**
     * Error when network is not available
     */
    public static final int IAP_ERROR_NETWORK_NOT_AVAILABLE = -1008;

    /**
     * IOException
     */
    public static final int IAP_ERROR_IOEXCEPTION_ERROR = -1009;

    /**
     * SocketTimeoutException
     */
    public static final int IAP_ERROR_SOCKET_TIMEOUT = -1010;

    /**
     * ConnectTimeoutException
     */
    public static final int IAP_ERROR_CONNECT_TIMEOUT = -1011;

    /**
     * The Item is not for sale in the country
     */
    public static final int IAP_ERROR_NOT_EXIST_LOCAL_PRICE = -1012;

    /**
     * IAP is not serviced in the country
     */
    public static final int IAP_ERROR_NOT_AVAILABLE_SHOP = -1013;

    /**
     * SA not logged in
     */
    public static final int IAP_ERROR_NEED_SA_LOGIN = -1014;
    // ========================================================================

    /**
     * initial state
     */
    protected static final int STATE_TERM = 0;

    /**
     * state of bound to IAPService successfully
     */
    protected static final int STATE_BINDING = 1;

    /**
     * state of InitIapTask successfully finished
     */
    protected static final int STATE_READY = 2;
    // ========================================================================

    // IAP Operation Mode
    // ========================================================================
    public enum OperationMode {
        OPERATION_MODE_TEST_FAILURE(-1),
        OPERATION_MODE_PRODUCTION(0),
        OPERATION_MODE_TEST(1);

        final private int value;
        OperationMode(int value) {
            this.value = value;
        }
        public int getValue() {
            return value;
        }
    }
    // ========================================================================
}
