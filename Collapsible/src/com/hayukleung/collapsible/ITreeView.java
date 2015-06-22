package com.hayukleung.collapsible;

import java.util.List;

public interface ITreeView {

    /**
     * 建树
     * 
     * @param all
     */
    public void buildTree(List<Element> all);

    /**
     * 排序
     * 
     * @param tops
     */
    public void sortTree(List<Element> tops);

    /**
     * 展开或闭合，展开后孩子结点默认闭合
     * 
     * @param element
     * @param position
     */
    public void toggle(IElement element, int position);

    /**
     * 递归地展开或闭合，展开后孩子结点保留之前的开闭状态
     * 
     * @param element
     * @param position
     */
    public void toggleRecursively(IElement element, final int position);

}
