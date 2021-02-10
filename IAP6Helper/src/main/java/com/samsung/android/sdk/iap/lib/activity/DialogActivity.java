package com.samsung.android.sdk.iap.lib.activity;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;

import com.samsung.android.sdk.iap.lib.R;
import com.samsung.android.sdk.iap.lib.dialog.BaseDialogFragment;
import com.samsung.android.sdk.iap.lib.helper.HelperDefine;
import com.samsung.android.sdk.iap.lib.helper.HelperUtil;

/**
 * Created by sangbum7.kim on 2018-03-05.
 */

public class DialogActivity extends Activity {
    private static final String TAG = DialogActivity.class.getSimpleName();
    private String mExtraString = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        if (intent != null) {
            Bundle extras = intent.getExtras();
            if (extras != null) {
                if (HelperDefine.DIALOG_TYPE_NOTIFICATION == extras.getInt("DialogType")) {
                    String title = extras.getString("Title");
                    String message = extras.getString("Message");
                    HelperUtil.showIapErrorDialog(
                            this,
                            title,
                            message,
                            new BaseDialogFragment.OnClickListener() {
                                @Override
                                public void onClick() {
                                    finish();
                                }
                            },
                            null);
                }
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
