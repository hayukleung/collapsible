package com.hayukleung.collapsible;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * 多级ListView TODO 待测试
 * 
 * @author HayukLeung
 * 
 */
public class TreeViewListView extends ListView {

    public TreeViewListView(Context context) {
        super(context);
    }

    public TreeViewListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TreeViewListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void setOnItemClickListener(OnItemClickListener listener) {
        if (listener instanceof TreeViewItemClickListener) {
            super.setOnItemClickListener(listener);
        } else {
            throw new RuntimeException("TreeViewListView应该设置TreeViewItemClickListener，而不是OnItemClickListener");
        }
    }

}
