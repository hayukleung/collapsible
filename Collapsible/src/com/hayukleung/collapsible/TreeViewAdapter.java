package com.hayukleung.collapsible;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hayukleung.collapsible.R;
import com.hayukleung.collapsible.IElement.TraverseChildrenListener;

/**
 * 树状结构数据适配器 TODO 考虑UI与性能
 * 
 * @author HayukLeung
 * 
 */
public class TreeViewAdapter extends BaseAdapter implements ITreeView {

    /** 元素数据源 */
    private List<Element> mAllElements;

    public List<Element> getAllElements() {
        return mAllElements;
    }

    /** 树中元素 */
    private List<Element> mVisibleElements;

    public List<Element> getVisibleElements() {
        return mVisibleElements;
    }

    /** LayoutInflater */
    private LayoutInflater mInflater;

    /** item的行首缩进基数 */
    private int mIndentionBase;

    /**
     * 构造函数
     * 
     * @param visibleElements
     * @param allElements
     * @param inflater
     */
    public TreeViewAdapter(List<Element> visibleElements, List<Element> allElements, LayoutInflater inflater) {
        this.mVisibleElements = visibleElements;
        this.mAllElements = allElements;
        this.mInflater = inflater;
        this.mIndentionBase = 50;
    }

    @Override
    public int getCount() {
        return mVisibleElements.size();
    }

    @Override
    public Object getItem(int position) {
        return mVisibleElements.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.item_treeview, null);
            holder.rlItem = (RelativeLayout) convertView.findViewById(R.id.ItemTreeView$rl_item);
            holder.imgToggle = (ImageView) convertView.findViewById(R.id.ItemTreeView$img_toggle);
            holder.txtName = (TextView) convertView.findViewById(R.id.ItemTreeView$txt_name);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        IElement element = mVisibleElements.get(position);

        holder.txtName.setText(element.getName());

        if (IElement.TYPE_ORG == element.getType()) {
            // 组织类型
            if (element.hasChildren()) {
                // 有孩子结点
                holder.rlItem.setBackgroundResource(R.drawable.selector_item_treeview_org);
                if (!element.isExpanded()) {
                    // 已闭合
                    holder.imgToggle.setImageResource(R.drawable.treeview_close);
                    holder.imgToggle.setVisibility(View.VISIBLE);
                } else {
                    // 已展开
                    holder.imgToggle.setImageResource(R.drawable.treeview_open);
                    holder.imgToggle.setVisibility(View.VISIBLE);
                }
            } else {
                // 没有孩子结点
                holder.rlItem.setBackgroundResource(R.drawable.bg_item_treeview_org_off);
                holder.imgToggle.setVisibility(View.GONE);
            }

        } else {
            // 人员类型
            holder.rlItem.setBackgroundResource(R.drawable.selector_item_treeview_usr);
            holder.imgToggle.setImageResource(R.drawable.treeview_close);
            holder.imgToggle.setVisibility(View.GONE);
        }

        int level = element.getLevel();
        // holder.imgToggle.setLeft(mIndentionBase * (level + 1));
        holder.rlItem.setPadding(mIndentionBase * (level + 1), holder.rlItem.getPaddingTop(), holder.rlItem.getPaddingRight(), holder.rlItem.getPaddingBottom());
        // holder.imgToggle.setPadding(mIndentionBase * (level + 1), holder.imgToggle.getPaddingTop(), holder.imgToggle.getPaddingRight(), holder.imgToggle.getPaddingBottom());

        return convertView;
    }

    @Override
    public void notifyDataSetChanged() {

        super.notifyDataSetChanged();
    }

    /**
     * 
     * @author HayukLeung
     * 
     */
    static class ViewHolder {
        RelativeLayout rlItem;
        ImageView imgToggle;
        TextView txtName;
    }

    @Override
    public void buildTree(List<Element> all) {

        List<Element> temp = new ArrayList<Element>();
        temp.addAll(all);
        for (Element element1 : all) {
            temp.remove(element1);
            for (Element element2 : temp) {
                if (element2.getParentId() == element1.getId()) {
                    element1.addChild(element2);
                }
            }
            temp.add(element1);
        }
    }

    @Override
    public void sortTree(List<Element> tops) {

        if (null == tops || tops.size() <= 0) {
            return;
        }

        Collections.sort(tops);
        for (IElement element : tops) {
            element.accessChildrenRecursively(new TraverseChildrenListener() {

                @Override
                public void doInTraverseChildren(Element child, int nth) {
                    // TODO Auto-generated method stub
                }

                @Override
                public void doInTraverseChildren(List<Element> children, int nth) {
                    Collections.sort(children);
                }
            });
        }
    }

    /**
     * 展开下一级孩子
     * 
     * @param element
     * @param position
     */
    private void expand(IElement element, final int position) {

        // 展开操作
        element.setExpanded(true);
        element.accessChildren(new TraverseChildrenListener() {

            @Override
            public void doInTraverseChildren(Element child, int nth) {
                mVisibleElements.add(position + nth, child);
                // LogMgr.showLog("expand " + child.getName() + " at position " + (position + nth));
            }

            @Override
            public void doInTraverseChildren(List<Element> children, int nth) {
                // TODO Auto-generated method stub
            }
        });
    }

    /**
     * 闭合结点
     * 
     * @param element
     * @param position
     */
    private void collapse(IElement element, int position) {

        // 闭合操作
        element.setExpanded(false);
        // 删除节点内部对应子节点数据，包括子节点的子节点
        ArrayList<IElement> elementsToDel = new ArrayList<IElement>();
        // 从position+1开始进行遍历
        for (int i = position + 1; i < mVisibleElements.size(); i++) {
            if (element.getLevel() >= mVisibleElements.get(i).getLevel())
                break;
            elementsToDel.add(mVisibleElements.get(i));
        }
        mVisibleElements.removeAll(elementsToDel);
    }

    @Override
    public void toggle(IElement element, int position) {

        if (IElement.TYPE_USR == element.getType()) {
            return;
        }

        if (element.isExpanded()) {
            collapse(element, position);
        } else {
            expand(element, position);
            element.accessChildren(new TraverseChildrenListener() {

                @Override
                public void doInTraverseChildren(Element child, int nth) {
                    // 默认闭合
                    child.setExpanded(false);
                }

                @Override
                public void doInTraverseChildren(List<Element> children, int nth) {
                    // TODO Auto-generated method stub

                }
            });
        }
    }

    @Override
    public void toggleRecursively(IElement element, final int position) {

        if (IElement.TYPE_USR == element.getType()) {
            return;
        }

        if (element.isExpanded()) {
            collapse(element, position);
        } else {
            expand(element, position);
            // 这里不应该递归调用，第一层是toggle，接下来应该用refresh
            element.accessChildren(new TraverseChildrenListener() {

                @Override
                public void doInTraverseChildren(Element child, int nth) {
                    // refreshRecursively(child, position + nth);
                    refreshRecursively(child, -1);
                }

                @Override
                public void doInTraverseChildren(List<Element> children, int nth) {
                    // TODO Auto-generated method stub
                }
            });
        }
    }

    /**
     * 用于刷新重新展开的结点的孩子的闭合状态
     * 
     * @param element
     * @param position
     */
    private void refreshRecursively(IElement element, final int position) {

        if (IElement.TYPE_USR == element.getType()) {
            return;
        }

        if (element.isExpanded()) {

            final int start = mVisibleElements.indexOf(element);
            expand(element, start);
            element.accessChildren(new TraverseChildrenListener() {

                @Override
                public void doInTraverseChildren(Element child, int nth) {
                    // refreshRecursively(child, start + nth);
                    refreshRecursively(child, -1);
                }

                @Override
                public void doInTraverseChildren(List<Element> children, int nth) {
                    // TODO Auto-generated method stub
                }
            });
        }
    }
}
