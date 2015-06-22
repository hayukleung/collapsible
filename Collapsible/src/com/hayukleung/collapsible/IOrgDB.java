package com.hayukleung.collapsible;

import java.util.List;

/**
 * TODO 组织架构数据库接口
 * 
 * @author HayukLeung
 * 
 */
public interface IOrgDB {

    /**
     * 查询所有元素
     * 
     * @return
     */
    public List<Element> queryAll();
}
