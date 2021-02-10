package com.samsung.android.sdk.iap.lib.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.samsung.android.sdk.iap.lib.helper.HelperDefine;
import com.samsung.android.sdk.iap.lib.helper.HelperUtil;
import com.samsung.android.sdk.iap.lib.helper.IapHelper;

/**
 * Created by sangbum7.kim on 2018-03-07.
 */

public class CheckPackageActivity extends Activity {
    private static final String TAG = CheckPackageActivity.class.getSimpleName();
    private boolean mFinishFlag = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFinishFlag = true;
        Intent intent = getIntent();
        if (intent != null) {
            Bundle extras = intent.getExtras();
            if (extras != null) {
                int DialogType = extras.getInt("DialogType");
                switch (DialogType) {
                    case HelperDefine.DIALOG_TYPE_INVALID_PACKAGE: {
                        HelperUtil.showInvalidGalaxyStoreDialog(this);
                        mFinishFlag = false;
                    }
                    break;
                    case HelperDefine.DIALOG_TYPE_DISABLE_APPLICATION: {
                        HelperUtil.showEnableGalaxyStoreDialog(this);
                        mFinishFlag = false;
                    }
                    break;
                    case HelperDefine.DIALOG_TYPE_APPS_DETAIL: {
                        HelperUtil.showUpdateGalaxyStoreDialog(this);
                        mFinishFlag = false;
                    }
                    break;
                }
            }
        }
        if (mFinishFlag) {
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        IapHelper.getInstance(getApplicationContext()).dispose();
    }
}
