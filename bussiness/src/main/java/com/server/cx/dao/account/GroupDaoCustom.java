package com.server.cx.dao.account;

/**
 * GroupDao的扩展行为interface.
 */
public interface GroupDaoCustom {

    /**
     * 因为Group中没有建立效率的方式进行删除User与Group的与User的关联,因此需要以较低多对多中间表中的数据.
     */
    void deleteWithReference(Long id);

}
