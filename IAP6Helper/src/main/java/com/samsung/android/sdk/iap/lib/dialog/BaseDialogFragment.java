package com.samsung.android.sdk.iap.lib.dialog;

import android.app.ActionBar;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.samsung.android.sdk.iap.lib.R;

public class BaseDialogFragment extends DialogFragment implements View.OnClickListener {
    private static final String TAG = BaseDialogFragment.class.getSimpleName();

    private int dialogWidth;
    private String title;
    private CharSequence message;
    private String messageExtra = "";
    private String positiveBtnText;
    private String negativeBtnText;
    private OnClickListener positiveButtonListener = null;
    private OnClickListener negativeButtonListener = null;

    public static final String IAP_DIALOG_TAG = "IAP_dialog";

    public interface OnClickListener {
        void onClick();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        dialogWidth = getDialogWidth();
        getDialog().getWindow().setLayout(dialogWidth, ActionBar.LayoutParams.WRAP_CONTENT);
    }

    @Override
    public void onDestroyView() {
        if (getDialog() != null && getRetainInstance()) {
            getDialog().setDismissMessage(null);
        }
        super.onDestroyView();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view;
        if (isDarkMode()) {
            view = getActivity().getLayoutInflater().inflate(R.layout.dialog_dark, null);
        } else {
            view = getActivity().getLayoutInflater().inflate(R.layout.dialog_light, null);
        }

        ((TextView) view.findViewById(R.id.dialog_title)).setText(title);
        ((TextView) view.findViewById(R.id.dialog_message)).setText(message);
        ((TextView) view.findViewById(R.id.dialog_message)).setLinksClickable(true);
        ((TextView) view.findViewById(R.id.dialog_message)).setMovementMethod(LinkMovementMethod.getInstance());

        if (messageExtra == null || messageExtra.isEmpty()) {
            view.findViewById(R.id.dialog_message_extra).setVisibility(View.GONE);
        } else {
            ((TextView) view.findViewById(R.id.dialog_message_extra))
                    .setText(getString(R.string.ids_com_body_error_code_c) + " " + messageExtra);
            view.findViewById(R.id.dialog_message_extra).setVisibility(View.VISIBLE);
        }

        ((Button) view.findViewById(R.id.dialog_ok_btn)).setText(positiveBtnText);
        view.findViewById(R.id.dialog_ok_btn).setOnClickListener(this);

        if (negativeButtonListener == null) {
            view.findViewById(R.id.dialog_cancel_btn).setVisibility(View.GONE);
            view.findViewById(R.id.dialog_btn_padding).setVisibility(View.GONE);
        } else {
            ((Button) view.findViewById(R.id.dialog_cancel_btn)).setText(negativeBtnText);
            view.findViewById(R.id.dialog_cancel_btn).setVisibility(View.VISIBLE);
            view.findViewById(R.id.dialog_cancel_btn).setOnClickListener(this);
            view.findViewById(R.id.dialog_btn_padding).setVisibility(View.VISIBLE);
        }

        Dialog dialog = new Dialog(getActivity(), R.style.Theme_DialogTransparent);
        dialog.setContentView(view);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);

        dialog.getWindow().setGravity(Gravity.BOTTOM);
        dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        TypedValue dimValue = new TypedValue();
        if (isDarkMode()) {
            getResources().getValue(R.integer.dim_dark, dimValue, true);
        } else {
            getResources().getValue(R.integer.dim_light, dimValue, true);
        }
        dialog.getWindow().setDimAmount(dimValue.getFloat());

        return dialog;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        dialogWidth = getDialogWidth();
        getDialog().getWindow().setLayout(dialogWidth, ActionBar.LayoutParams.WRAP_CONTENT);
    }

    public BaseDialogFragment setDialogTitle(String _title) {
        if (!TextUtils.isEmpty(_title)) {
            this.title = _title;
        }
        return this;
    }

    public BaseDialogFragment setDialogMessageText(CharSequence _message) {
        if (!TextUtils.isEmpty(_message)) {
            this.message = _message;
        }
        return this;
    }

    public BaseDialogFragment setDialogMessageExtra(String _extra) {
        if (!TextUtils.isEmpty(_extra)) {
            this.messageExtra = _extra;
        }
        return this;
    }

    public BaseDialogFragment setDialogPositiveButton(String _positiveBtnText, final OnClickListener listener) {
        if (!TextUtils.isEmpty(_positiveBtnText)) {
            positiveBtnText = _positiveBtnText;
        } else {
            positiveBtnText = (String) getText(android.R.string.ok);
        }
        if (listener != null) {
            positiveButtonListener = listener;
        }
        return this;
    }

    public BaseDialogFragment setDialogNegativeButton(String _negativeBtnText, final OnClickListener listener) {
        if (!TextUtils.isEmpty(_negativeBtnText)) {
            negativeBtnText = _negativeBtnText;
        }
        if (listener != null) {
            negativeButtonListener = listener;
        }
        return this;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.dialog_ok_btn) {
            Runnable OkBtnRunnable = new Runnable() {
                @Override
                public void run() {
                    positiveButtonListener.onClick();
                }
            };
            OkBtnRunnable.run();
        } else if (v.getId() == R.id.dialog_cancel_btn) {
            Runnable CancelBtnRunnable = new Runnable() {
                @Override
                public void run() {
                    negativeButtonListener.onClick();
                }
            };
            CancelBtnRunnable.run();
        }
        dismiss();
    }

    private int getDialogWidth() {
        TypedValue outValue = new TypedValue();
        getResources().getValue(R.integer.dialog_width_percentage, outValue, true);
        float ratio = outValue.getFloat();
        int width = (int) (getResources().getDisplayMetrics().widthPixels * ratio);
        return width;
    }

    private boolean isDarkMode() {
        try {
            // getContext() requires M OS or higher version
            int nightModeFlags = 0;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                nightModeFlags = getContext().getResources().getConfiguration().uiMode &
                        Configuration.UI_MODE_NIGHT_MASK;
            }
            switch (nightModeFlags) {
                case Configuration.UI_MODE_NIGHT_YES:
                    return true;
                case Configuration.UI_MODE_NIGHT_NO:
                case Configuration.UI_MODE_NIGHT_UNDEFINED:
                default:
                    return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}