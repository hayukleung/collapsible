package com.hayukleung.collapsible;

import java.util.List;

/**
 * 结点模型接口
 * 
 * @author HayukLeung
 * 
 */
public interface IElement {

    /**
     * 获取结点名称
     * 
     * @return
     */
    public String getName();

    /**
     * 设置结点名称
     * 
     * @param name
     */
    public void setName(String name);

    /**
     * 获取结点在树中的深度
     * 
     * @return
     */
    public int getLevel();

    /**
     * 获取结点ID
     * 
     * @return
     */
    public int getId();

    /**
     * 设置结点ID
     * 
     * @param id
     */
    public void setId(int id);

    /**
     * 获取结点父结点的ID
     * 
     * @return
     */
    public int getParentId();

    /**
     * 设置结点父结点的ID
     * 
     * @param parentId
     */
    public void setParentId(int parentId);

    /**
     * 设置当前结点孩子
     * 
     * @param children
     */
    public void setChildren(List<Element> children);

    /**
     * 为当前结点增加一个孩子结点
     * 
     * @param child
     */
    public void addChild(Element child);

    /**
     * 为当前结点增加多个孩子结点
     * 
     * @param children
     */
    public void addChildren(List<Element> children);

    /**
     * 获取当前结点的孩子副本
     * 
     * @return
     */
    public List<IElement> getChildrenCopy();

    /**
     * 获取孩子结点数目
     * 
     * @return
     */
    public int getChildrenSize();

    /**
     * 是否拥有孩子结点
     * 
     * @return
     */
    public boolean hasChildren();

    /**
     * 遍历孩子结点，不包括孩子的孩子
     * 
     * @param listener
     */
    public void accessChildren(TraverseChildrenListener listener);

    /**
     * 递归遍历孩子结点，包括孩子的孩子
     * 
     * @param listener
     */
    public void accessChildrenRecursively(TraverseChildrenListener listener);

    /**
     * 当前结点是否展开孩子结点
     * 
     * @return
     */
    public boolean isExpanded();

    /**
     * 设置当前结点是否展开孩子结点
     * 
     * @param isExpanded
     */
    public void setExpanded(boolean isExpanded);

    /**
     * 获取当前结点的类型
     * 
     * @return
     */
    public int getType();

    /**
     * 获取当前结点的优先级
     * 
     * @return
     */
    public Integer getPriority();

    /**
     * 设置当前结点的优先级
     * 
     * @param priority
     */
    public void setPriority(int priority);

    /** 结点类型：组织 */
    public static final int TYPE_ORG = 0x0002;
    /** 结点类型：人员 */
    public static final int TYPE_USR = 0x0001;
    /** 表示该元素位于最顶层的层级 */
    public static final int LEVEL_ROOT = 0;

    /**
     * 遍历孩子结点监听
     * 
     * @author HayukLeung
     * 
     */
    public interface TraverseChildrenListener {

        /**
         * 对所有孩子结点进行操作，不包括孩子的孩子
         * 
         * @param children
         * @param nth
         *            第n次访问
         */
        public void doInTraverseChildren(List<Element> children, int nth);

        /**
         * 对单个孩子结点进行操作
         * 
         * @param child
         * @param nth
         *            第n次访问
         */
        public void doInTraverseChildren(Element child, int nth);
    }

}
