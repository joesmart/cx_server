package com.server.cx.dao.cx;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import com.server.cx.entity.cx.CXCoinAccount;

public interface CXCoinAccountDao extends JpaRepository<CXCoinAccount, String>, JpaSpecificationExecutor<CXCoinAccount> {
    public CXCoinAccount findByImsi(String imsi);

    public CXCoinAccount findByNameAndPasswordAndImsi(String name, String password, String imsi);

    public CXCoinAccount findByNameAndPassword(String name, String password);

    public CXCoinAccount findByName(String email);

}
