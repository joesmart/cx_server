package com.server.cx.dao.cx;

import com.server.cx.dao.cx.custom.UserInfoCustomDao;
import com.server.cx.entity.cx.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * User: yanjianzou
 * Date: 12-7-20
 * Time: 下午2:09
 * FileName:UserInfoDao
 */
public interface UserInfoDao extends JpaRepository<UserInfo, Long>,JpaSpecificationExecutor<UserInfo>, UserInfoCustomDao {
    public UserInfo findByImsi(String imsi);
}
