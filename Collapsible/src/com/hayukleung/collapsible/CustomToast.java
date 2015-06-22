package com.hayukleung.collapsible;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class CustomToast extends Toast {

    private TextView mTxtContent;

    /**
     * @param context
     */
    public CustomToast(Context context) {
        super(context);
        View layout = LayoutInflater.from(context).inflate(R.layout.layout_custom_toast, null);
        mTxtContent = (TextView) layout.findViewById(R.id.LayoutCustomToast$txt);
        setGravity(Gravity.FILL_HORIZONTAL | Gravity.TOP, 0, 0);

        setDuration(Toast.LENGTH_SHORT);
        setView(layout);
    }

    @Override
    public void setText(CharSequence s) {
        mTxtContent.setText(s);
    }

    @Override
    public void setText(int resId) {
        mTxtContent.setText(resId);
    }
}
