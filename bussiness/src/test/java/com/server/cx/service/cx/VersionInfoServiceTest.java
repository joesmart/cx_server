package com.server.cx.service.cx;

import static org.fest.assertions.Assertions.assertThat;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springside.modules.test.spring.SpringTransactionalTestCase;
import com.server.cx.constants.Constants;
import com.server.cx.dto.VersionInfoDTO;

@ContextConfiguration(locations = {"/applicationContext.xml"})
@ActiveProfiles(profiles = {"test"})
public class VersionInfoServiceTest extends SpringTransactionalTestCase {
    @Autowired
    private VersionInfoService versionInfoService;

    @Test
    public void testCheckIsTheLatestVersion() {
        VersionInfoDTO versionInfoDTO = versionInfoService.checkIsTheLatestVersion("13146001000", "3.0.105");
        assertThat(versionInfoDTO.getFlag()).isEqualTo(Constants.SERVER_HAVE_NEWVERION);
    }
    
    @Test
    public void test_check_user_not_exist() {
        VersionInfoDTO versionInfoDTO = versionInfoService.checkIsTheLatestVersion("13146001002", "3.0.105");
        System.out.println(versionInfoDTO.getFlag());
        assertThat(versionInfoDTO.getFlag()).isEqualTo(Constants.USER_DATA_ERROR_FLAG);
    }
    
    @Test
    public void test_check_force_update() {
        VersionInfoDTO versionInfoDTO = versionInfoService.checkIsTheLatestVersion("13146001000", "3.1.2.122");
        assertThat(versionInfoDTO.getFlag()).isEqualTo(Constants.SERVER_HAVE_NEWVERION);
        assertThat(versionInfoDTO.getForceUpdate()).isEqualTo("true");
        
    }

}
