package com.hayukleung.collapsible;

import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

/**
 * 树点击事件
 * 
 * @author HayukLeung
 * 
 */
public class TreeViewItemClickListener implements OnItemClickListener {

    private TreeViewAdapter mTreeViewAdapter;
    private TreeViewClickListener mTreeViewClickListener;

    /**
     * 构造函数
     * 
     * @param treeViewAdapter
     * @param treeViewClickListener
     */
    public TreeViewItemClickListener(TreeViewAdapter treeViewAdapter, TreeViewClickListener treeViewClickListener) {

        if (null == treeViewAdapter || null == treeViewClickListener) {
            throw new RuntimeException("TreeView点击事件构造函数传参不能为空");
        }
        this.mTreeViewAdapter = treeViewAdapter;
        this.mTreeViewClickListener = treeViewClickListener;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        // 点击的item代表的元素
        Element element = (Element) mTreeViewAdapter.getItem(position);

        if (IElement.TYPE_USR == element.getType()) {
            // 人员结点
            mTreeViewClickListener.onUsrClick(element, position);
        } else {

            if (mTreeViewClickListener.onOrgClick(element, position)) {
                // 锁定本次点击
            } else {
                // 额外执行toggle
                // mTreeViewAdapter.toggle(element, position);
                mTreeViewAdapter.toggleRecursively(element, position);
                mTreeViewAdapter.notifyDataSetChanged();
            }
        }
    }

    /**
     * 树点击监听
     * 
     * @author HayukLeung
     * 
     */
    public interface TreeViewClickListener {

        /**
         * 人员结点点击
         * 
         * @param usr
         * @param position
         */
        public void onUsrClick(IElement usr, int position);

        /**
         * 组织结点点击
         * 
         * @param org
         * @param position
         * @return true: 锁定本次点击，不再执行额外操作 false: 额外执行toggle
         */
        public boolean onOrgClick(IElement org, int position);
    }
}