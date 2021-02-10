package com.samsung.android.sdk.iap.lib.helper;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.text.Html;
import android.util.Log;

import com.samsung.android.sdk.iap.lib.R;
import com.samsung.android.sdk.iap.lib.activity.BaseActivity;
import com.samsung.android.sdk.iap.lib.dialog.BaseDialogFragment;
import com.samsung.android.sdk.iap.lib.vo.ErrorVo;

/**
 * Created by sangbum7.kim on 2017-08-17.
 */

public class HelperUtil {
    private static final String TAG = HelperUtil.class.getSimpleName();

    /**
     * show a dialog
     *
     * @param activity         The activity adding the fragment that displays a dialog
     * @param title            The title to display
     * @param message          The message to display
     * @param positiveListener The listener to be invoked when the positive button of the dialog is pressed
     * @param negativeListener The listener to be invoked when the negative button of the dialog is pressed
     */
    public static void showIapErrorDialog(
            final Activity activity,
            String title,
            String message,
            final BaseDialogFragment.OnClickListener positiveListener,
            final BaseDialogFragment.OnClickListener negativeListener) {
        showIapErrorDialog(activity, title, message, "", positiveListener, negativeListener);
    }

    /**
     * show a dialog
     *
     * @param activity         The activity adding the fragment that displays a dialog
     * @param title            The title to display
     * @param message          The message to display
     * @param messageExtra     The extra message to display
     * @param positiveListener The listener to be invoked when the positive button of the dialog is pressed
     * @param negativeListener The listener to be invoked when the negative button of the dialog is pressed
     */
    public static void showIapErrorDialog(
            final Activity activity,
            String title,
            String message,
            String messageExtra,
            final BaseDialogFragment.OnClickListener positiveListener,
            final BaseDialogFragment.OnClickListener negativeListener) {
        new BaseDialogFragment()
                .setDialogTitle(title)
                .setDialogMessageText(message)
                .setDialogMessageExtra(messageExtra)
                .setDialogPositiveButton(activity.getString(android.R.string.ok), positiveListener)
                .setDialogNegativeButton(activity.getString(android.R.string.cancel), negativeListener)
                .show(activity.getFragmentManager(), BaseDialogFragment.IAP_DIALOG_TAG);
    }

    /**
     * show a dialog to update the Galaxy Store
     *
     * @param activity The activity adding the fragment that displays a dialog
     */
    public static void showUpdateGalaxyStoreDialog(final Activity activity) {
        // TODO: both title and message will be changed as UX Guide
        new BaseDialogFragment()
                .setDialogTitle(activity.getString(R.string.dream_ph_pheader_couldnt_complete_purchase))
                .setDialogMessageText(activity.getString(
                        R.string.dream_ph_body_to_complete_this_purchase_you_need_to_update_the_galaxy_store))
                .setDialogPositiveButton(
                        activity.getString(android.R.string.ok),
                        new BaseDialogFragment.OnClickListener() {
                            @Override
                            public void onClick() {
                                goGalaxyStoreDetailPage(activity.getApplicationContext());
                                activity.finish();
                            }
                        })
                .setDialogNegativeButton(
                        activity.getString(android.R.string.cancel),
                        new BaseDialogFragment.OnClickListener() {
                            @Override
                            public void onClick() {
                                activity.finish();
                            }
                        })
                .show(activity.getFragmentManager(), BaseDialogFragment.IAP_DIALOG_TAG);
    }

    /**
     * show a dialog to enable the Galaxy Store
     *
     * @param activity The activity adding the fragment that displays a dialog
     */
    public static void showEnableGalaxyStoreDialog(final Activity activity) {
        // TODO: both title and message will be changed as UX Guide
        new BaseDialogFragment()
                .setDialogTitle(activity.getString(R.string.dream_ph_pheader_couldnt_complete_purchase))
                .setDialogMessageText(
                        activity.getString(R.string.dream_ph_body_to_complete_this_purchase_you_need_to_enable_the_galaxy_store_in_settings))
                .setDialogPositiveButton(
                        activity.getString(android.R.string.ok),
                        new BaseDialogFragment.OnClickListener() {
                            @Override
                            public void onClick() {
                                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                intent.setData(Uri.parse("package:" + HelperDefine.GALAXY_PACKAGE_NAME));
                                activity.startActivityForResult(intent, HelperDefine.REQUEST_CODE_IS_ENABLE_APPS);

                                activity.finish();
                            }
                        })
                .setDialogNegativeButton(
                        activity.getString(android.R.string.cancel),
                        new BaseDialogFragment.OnClickListener() {
                            @Override
                            public void onClick() {
                                activity.finish();
                            }
                        })
                .show(activity.getFragmentManager(), BaseDialogFragment.IAP_DIALOG_TAG);
    }

    /**
     * show a dialog to notice that the Galaxy Store is invalid
     *
     * @param activity The activity adding the fragment that displays a dialog
     */
    public static void showInvalidGalaxyStoreDialog(final Activity activity) {
        final String ERROR_ISSUER_IAP_CLIENT = "IC";
        final int ERROR_CODE_INVALID_GALAXY_STORE = 10002;

        String source = String.format(
                activity.getString(
                        R.string.dream_ph_body_contact_p1sscustomer_servicep2ss_for_more_information_n_nerror_code_c_p3ss),
                "<a href=\"http://help.content.samsung.com\">", "</a>",
                ERROR_ISSUER_IAP_CLIENT + ERROR_CODE_INVALID_GALAXY_STORE);

        CharSequence errorMessage;
        // fromHtml(String) was deprecated in N OS
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            errorMessage = Html.fromHtml(source);
        } else {
            errorMessage = Html.fromHtml(source, Html.FROM_HTML_MODE_LEGACY);
        }
        new BaseDialogFragment()
                .setDialogTitle(activity.getString(R.string.dream_ph_pheader_couldnt_complete_purchase))
                .setDialogMessageText(errorMessage)
                .setDialogMessageExtra(ERROR_ISSUER_IAP_CLIENT + ERROR_CODE_INVALID_GALAXY_STORE)
                .setDialogPositiveButton(
                        activity.getString(android.R.string.ok),
                        new BaseDialogFragment.OnClickListener() {
                            @Override
                            public void onClick() {
                                activity.finish();
                            }
                        })
                .show(activity.getFragmentManager(), BaseDialogFragment.IAP_DIALOG_TAG);
    }

    private static void goGalaxyStoreDetailPage(Context context) {
        // Link of Galaxy Store for IAP install
        // ------------------------------------------------------------
        Uri appsDeepLink = Uri.parse("samsungapps://StoreVersionInfo/");
        // ------------------------------------------------------------

        Intent intent = new Intent();
        intent.setData(appsDeepLink);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
                    Intent.FLAG_ACTIVITY_CLEAR_TOP |
                    Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
        } else {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
                    Intent.FLAG_ACTIVITY_CLEAR_TOP);
        }

        if (intent.resolveActivity(context.getPackageManager()) != null) {
            context.startActivity(intent);
        }
    }

    /**
     * Check that Galaxy Store is installed
     *
     * @param _context Context
     * @return If it is true Galaxy Store is installed. otherwise, not installed.
     */
    static public boolean isInstalledAppsPackage(Context _context) {
        PackageManager pm = _context.getPackageManager();
        try {
            PackageInfo packageInfo = pm.getPackageInfo(HelperDefine.GALAXY_PACKAGE_NAME, PackageManager.GET_META_DATA);
            int versionType = packageInfo.versionCode / 100000000;
            Log.i(TAG, "isInstalledAppsPackage : " + packageInfo.versionCode + ", " + versionType);
            switch (versionType) {
                case 4: {
                    return packageInfo.versionCode >= HelperDefine.APPS_PACKAGE_VERSION;
                }
                case 5: {
                    return true;
//                    return packageInfo.versionCode >= HelperDefine.APPS_PACKAGE_VERSION_GO;
                }
                case 6: {
                    return packageInfo.versionCode >= HelperDefine.APPS_PACKAGE_VERSION_INDIA;
                }
                // Unverified version
                default:
                    return true;
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    static public boolean isEnabledAppsPackage(Context context) {
        //// TODO: 2017-08-16 Make sure the status is normal
        int status = context.getPackageManager().getApplicationEnabledSetting(HelperDefine.GALAXY_PACKAGE_NAME);
        Log.i(TAG, "isEnabledAppsPackage: status " + status);
        return !((status == PackageManager.COMPONENT_ENABLED_STATE_DISABLED) || (status == PackageManager.COMPONENT_ENABLED_STATE_DISABLED_USER));
    }


    /**
     * check validation of installed Galaxy Store in your device
     *
     * @param _context
     * @return If it is true Galaxy Store is valid. otherwise, is not valid.
     */
    static public boolean isValidAppsPackage(Context _context) {
        boolean result = true;
        try {
            Signature[] sigs = _context.getPackageManager().getPackageInfo(
                    HelperDefine.GALAXY_PACKAGE_NAME,
                    PackageManager.GET_SIGNATURES).signatures;
            if (sigs[0].hashCode() != HelperDefine.APPS_SIGNATURE_HASHCODE) {
                result = false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            result = false;
        }

        return result;
    }

    /**
     * SamsungAccount authentication
     *
     * @param _activity
     */
    static public boolean startAccountActivity(final Activity _activity) {
        ComponentName com = new ComponentName(HelperDefine.GALAXY_PACKAGE_NAME,
                HelperDefine.IAP_PACKAGE_NAME + ".activity.AccountActivity");
        Context context = _activity.getApplicationContext();

        Intent intent = new Intent();
        intent.setComponent(com);

        if (intent.resolveActivity(context.getPackageManager()) != null) {
            _activity.startActivityForResult(intent,
                    HelperDefine.REQUEST_CODE_IS_ACCOUNT_CERTIFICATION);
            return true;
        }
        return false;
    }

    /**
     * go to about page of Galaxy Store in order to install IAP package.
     */
    static public void installAppsPackage(final BaseActivity _activity) {
        // Set error in order to notify result to third-party application.
        // ====================================================================
        ErrorVo errorVo = new ErrorVo();
        _activity.setErrorVo(errorVo);

        errorVo.setError(
                HelperDefine.IAP_PAYMENT_IS_CANCELED,
                _activity.getString(R.string.mids_sapps_pop_payment_canceled));
        // ====================================================================

        // Show information dialog
        // ====================================================================
        showUpdateGalaxyStoreDialog(_activity);
        // ====================================================================
    }

    static public int checkAppsPackage(Context _context) {
        // 1. If Galaxy Store is installed
        // ====================================================================
        if (HelperUtil.isInstalledAppsPackage(_context)) {
            // 1) If Galaxy Store is enabled
            // ================================================================
            if (!HelperUtil.isEnabledAppsPackage(_context)) {
                return HelperDefine.DIALOG_TYPE_DISABLE_APPLICATION;
                // ================================================================
                // 2) If Galaxy Store is valid
                // ================================================================
            } else if (HelperUtil.isValidAppsPackage(_context)) {
                return HelperDefine.DIALOG_TYPE_NONE;
            } else {
                // ------------------------------------------------------------
                // show alert dialog if Galaxy Store is invalid
                // ------------------------------------------------------------
                return HelperDefine.DIALOG_TYPE_INVALID_PACKAGE;
                // ------------------------------------------------------------
            }
            // ================================================================

            // ====================================================================
            // 2. If Galaxy Store is not installed
            // ====================================================================
        } else {
            // When user click the OK button on the dialog,
            // go to Galaxy Store Detail page
            // ====================================================================
            return HelperDefine.DIALOG_TYPE_APPS_DETAIL;
        }
        // ====================================================================
    }
}
