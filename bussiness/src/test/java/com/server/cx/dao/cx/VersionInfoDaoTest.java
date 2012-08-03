package com.server.cx.dao.cx;

import com.server.cx.entity.cx.VersionInfo;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springside.modules.test.spring.SpringTransactionalTestCase;

import java.util.List;

import static org.fest.assertions.Assertions.assertThat;

@ContextConfiguration(locations = {"/applicationContext.xml"})
public class VersionInfoDaoTest extends SpringTransactionalTestCase {
    @Autowired
    private VersionInfoDao versionInfoDao;

    @Test
    public void should_find_all_verioninfo() {
        List<VersionInfo> versionInfos = (List<VersionInfo>) versionInfoDao.findAll();
        assertThat(versionInfos.size()).isGreaterThan(0);
        assertThat(versionInfos.get(0).getVersion()).isEqualTo("3.0.122");
        assertThat(versionInfos.size()).isEqualTo(2);
    }
}
