package com.server.cx.service.cx;

import org.fest.assertions.Fail;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springside.modules.test.spring.SpringTransactionalTestCase;
import com.cl.cx.platform.dto.CXCoinAccountDTO;
import com.cl.cx.platform.dto.DataPage;
import com.cl.cx.platform.dto.OperationDescription;
import com.server.cx.constants.Constants;
import com.server.cx.dao.cx.CXCoinAccountDao;
import com.server.cx.dao.cx.UserInfoDao;
import com.server.cx.entity.cx.CXCoinAccount;
import com.server.cx.entity.cx.UserInfo;
import com.server.cx.exception.SystemException;
import static org.fest.assertions.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

@ContextConfiguration(locations = {"/applicationContext.xml"})
@ActiveProfiles(profiles = {"test"})
public class CXCoinServiceTest extends SpringTransactionalTestCase {
    @Autowired
    private UserInfoDao userInfoDao;
    
    @Autowired
    private CXCoinService cxCoinService;
    
    @Autowired
    private CXCoinAccountDao cxCoinAccountDao;
    
    @Test
    public void test_has_registered_exception() {
        UserInfo userInfo = userInfoDao.findOne("1");
        CXCoinAccountDTO cxCoinAccountDTO = new CXCoinAccountDTO();
        cxCoinAccountDTO.setName("Account1");
        cxCoinAccountDTO.setPassword("112233");
        try {
            cxCoinService.register(userInfo.getImsi(), cxCoinAccountDTO);
            Fail.fail("没有抛出异常");
        } catch(SystemException e) {
            
        }
    }
    
    /**
     * 这个test case必须重新设置总酷币的数量，没法跟其他的case一起测试
     * Briefly describe what it does.
     * <p>If necessary, describe how it does and how to use it.</P>
     */
    @Test
    public void test_total_cxcoin_not_enough_exception() {
        UserInfo userInfo = userInfoDao.findOne("4");
        CXCoinAccountDTO cxCoinAccountDTO = new CXCoinAccountDTO();
        cxCoinAccountDTO.setName("Account4");
        cxCoinAccountDTO.setPassword("112233");
        try {
            cxCoinService.register(userInfo.getImsi(), cxCoinAccountDTO);
            Fail.fail("没有抛出异常");
        } catch(SystemException e) {
            
        }
    }
    
    @Test
    public void test_cxcoin_succssful() {
        UserInfo userInfo = userInfoDao.findOne("4");
        CXCoinAccountDTO cxCoinAccountDTO = new CXCoinAccountDTO();
        cxCoinAccountDTO.setName("Account4");
        cxCoinAccountDTO.setPassword("112233");
        OperationDescription operationDescription = cxCoinService.register(userInfo.getImsi(), cxCoinAccountDTO);
        assertThat(operationDescription.getDealResult()).isEqualTo(Constants.SUCCESS_FLAG);
        System.out.println("ooperationDescription = " + operationDescription);
    }
    
    @Test
    public void test_login_successful() {
        UserInfo userInfo = userInfoDao.findOne("1");
        CXCoinAccountDTO cxCoinAccountDTO = new CXCoinAccountDTO();
        cxCoinAccountDTO.setName("Account1");
        cxCoinAccountDTO.setPassword("123456");
        OperationDescription operationDescription = cxCoinService.login(userInfo.getImsi(), cxCoinAccountDTO);
        System.out.println("operationDescription = " + operationDescription);
    }
    
    @Test
    public void test_login_fail() {
        UserInfo userInfo = userInfoDao.findOne("1");
        CXCoinAccountDTO cxCoinAccountDTO = new CXCoinAccountDTO();
        cxCoinAccountDTO.setName("Account1");
        cxCoinAccountDTO.setPassword("12345");
        OperationDescription operationDescription = cxCoinService.login(userInfo.getImsi(), cxCoinAccountDTO);
        System.out.println("operationDescription = " + operationDescription);
        assertThat(operationDescription.getErrorCode()).isEqualTo(406);
    }
    
    @Test
    public void test_consume_cxcoin_successful() {
        UserInfo userInfo = userInfoDao.findOne("1");
        CXCoinAccountDTO cxCoinAccountDTO = new CXCoinAccountDTO();
        cxCoinAccountDTO.setName("Account1");
        cxCoinAccountDTO.setPassword("123456");
        CXCoinAccount cxCoinAccount = cxCoinAccountDao.findByNameAndPasswordAndUserInfo("Account1", "123456", userInfo);
        Double before = cxCoinAccount.getCoin();
        OperationDescription operationDescription = cxCoinService.consumeCXCoin(userInfo.getImsi(), cxCoinAccountDTO);
        System.out.println("operationDescription = " + operationDescription);
        assertThat(operationDescription.getDealResult()).isEqualTo(Constants.SUCCESS_FLAG);
        CXCoinAccount cxCoinAccount2 = cxCoinAccountDao.findByNameAndPasswordAndUserInfo("Account1", "123456", userInfo);
        Double after = cxCoinAccount2.getCoin();
        assertEquals(before, after - 5, 1e-3);
    }
    
    /**
     * 这个test case必须重新设置总酷币的数量，没法跟其他的case一起测试
     * Briefly describe what it does.
     * <p>If necessary, describe how it does and how to use it.</P>
     */
    @Test
    public void test_consume_cxcoin_not_enough_exception() {
        UserInfo userInfo = userInfoDao.findOne("1");
        CXCoinAccountDTO cxCoinAccountDTO = new CXCoinAccountDTO();
        cxCoinAccountDTO.setName("Account1");
        cxCoinAccountDTO.setPassword("123456");
        try {
            cxCoinService.consumeCXCoin(userInfo.getImsi(), cxCoinAccountDTO);
            Fail.fail("没有抛出异常");
        } catch(SystemException e) {
            
        }
    }
    
    @Test
    public void test_get_cxcoin_account_not_exist_imsi_exception() {
        String imsi = "460000302740008";
        try {
            cxCoinService.getCXCoinAccount(imsi);
            Fail.fail("没有抛出异常");
        } catch(SystemException e) {
            
        }
    }
    
    @Test
    public void test_get_cxcoin_account_successful() {
        String imsi = "13146001000";
        CXCoinAccount cxCoinAccount = cxCoinService.getCXCoinAccount(imsi);
        System.out.println("cxCoinAccount = " + cxCoinAccount);
        assertThat(cxCoinAccount.getName()).isEqualTo("Account1");
    }
    
    @Test
    public void test_get_user_cxCoinRecords() {
        DataPage dataPage = cxCoinService.getUserCXCoinRecords("13146001003", 0, 2);
        System.out.println("dataPage = " + dataPage);
    }
}
