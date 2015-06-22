package com.hayukleung.collapsible;

import android.content.Context;
import android.widget.Toast;

/**
 * 
 * @author HayukLeung
 * 
 */
public class ToastUtil {

    public static void showToast(Context context, CharSequence text) {
        showToast(context, text, Toast.LENGTH_SHORT);
    }

    public static void showToast(Context context, CharSequence text, int duration) {
        CustomToast toast = new CustomToast(context);
        toast.setDuration(duration);
        toast.setText(text);
        toast.show();
    }

    public static void showToast(Context context, int resId) {
        showToast(context, resId, Toast.LENGTH_SHORT);
    }

    public static void showToast(Context context, int resId, int duration) {
        CustomToast toast = new CustomToast(context);
        toast.setDuration(duration);
        toast.setText(resId);
        toast.show();
    }
}
