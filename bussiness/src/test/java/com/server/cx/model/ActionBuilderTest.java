package com.server.cx.model;

import com.cl.cx.platform.dto.Actions;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.server.cx.service.cx.impl.BasicService;
import org.fest.assertions.Assertions;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * User: yanjianzou
 * Date: 12-8-20
 * Time: 下午2:03
 * FileName:ActionBuilderTest
 */
public class ActionBuilderTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(ActionBuilderTest.class);

    private ActionBuilder actionBuilder;
    private String baseHostAddress = "http://localhost:8080/CXServer/";
    private String restURL = "rs/";
    private String imageShowURL = "http://10.90.3.122:38183/resources/rs/graphics/show?id=";
    private String thumbnailSize = "128X128";
    private BasicService basicService;

    private ObjectMapper objectMapper;

    @Before
    public void setUp() throws Exception {
        actionBuilder = new ActionBuilder();
        basicService = new BasicService(baseHostAddress, restURL, imageShowURL, thumbnailSize);
        actionBuilder.setBasicService(basicService);
        this.objectMapper = new ObjectMapper().configure(SerializationFeature.WRITE_NULL_MAP_VALUES, false);
        this.objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
//        this.objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
    }

    @After
    public void tearDown() throws Exception {
        basicService = null;
        actionBuilder = null;
    }

    @Test
    public void testBuildGraphicItemAction() throws Exception {
        Actions actions = actionBuilder.buildGraphicItemAction("1234");
        String result = "{\"@type\":\"com.cl.cx.platform.dto.Actions\"," +
                "\"useURL\":{\"@type\":\"com.cl.cx.platform.dto.Action\",\"href\":\"http://localhost:8080/CXServer/rs/1234/mGraphics\",\"method\":\"POST\"}," +
                "\"collectURL\":{\"@type\":\"com.cl.cx.platform.dto.Action\",\"href\":\"http://localhost:8080/CXServer/rs/1234/myCollections\",\"method\":\"POST\"}," +
                "\"purchaseURL\":{\"@type\":\"com.cl.cx.platform.dto.Action\",\"href\":\"http://localhost:8080/CXServer/rs/1234/myPurchasedImages\",\"method\":\"POST\"}}";
        resultCompare(actions, result);
    }

    @Test
    public void testBuildHolidayMGraphicItemAction() throws Exception {
        Actions actions = actionBuilder.buildHolidayMGraphicItemAction("1234");
        String result = "{\"@type\":\"com.cl.cx.platform.dto.Actions\"," +
                "\"useURL\":{\"@type\":\"com.cl.cx.platform.dto.Action\",\"href\":\"http://localhost:8080/CXServer/rs/1234/holidayMGraphics\",\"method\":\"POST\"}," +
                "\"collectURL\":{\"@type\":\"com.cl.cx.platform.dto.Action\",\"href\":\"http://localhost:8080/CXServer/rs/1234/myCollections\",\"method\":\"POST\"}," +
                "\"purchaseURL\":{\"@type\":\"com.cl.cx.platform.dto.Action\",\"href\":\"http://localhost:8080/CXServer/rs/1234/myPurchasedImages\",\"method\":\"POST\"}}";
        resultCompare(actions, result);

    }

    @Test
    public void testBuildStatusMGraphicItemAction() throws Exception {
        Actions actions = actionBuilder.buildStatusMGraphicItemAction("1234");
        String result = "{\"@type\":\"com.cl.cx.platform.dto.Actions\"," +
                "\"useURL\":{\"@type\":\"com.cl.cx.platform.dto.Action\",\"href\":\"http://localhost:8080/CXServer/rs/1234/statusMGraphics\",\"method\":\"POST\"}," +
                "\"collectURL\":{\"@type\":\"com.cl.cx.platform.dto.Action\",\"href\":\"http://localhost:8080/CXServer/rs/1234/myCollections\",\"method\":\"POST\"}," +
                "\"purchaseURL\":{\"@type\":\"com.cl.cx.platform.dto.Action\",\"href\":\"http://localhost:8080/CXServer/rs/1234/myPurchasedImages\",\"method\":\"POST\"}}";
        resultCompare(actions, result);
    }

    @Test
    public void testBuildHolidayMGraphicItemEditAction() throws Exception {
        Actions actions = actionBuilder.buildHolidayMGraphicItemEditAction("1234", "1");
        String result = "{\"@type\":\"com.cl.cx.platform.dto.Actions\"," +
                "\"collectURL\":{\"@type\":\"com.cl.cx.platform.dto.Action\",\"href\":\"http://localhost:8080/CXServer/rs/1234/myCollections\",\"method\":\"POST\"}," +
                "\"removeURL\":{\"@type\":\"com.cl.cx.platform.dto.Action\",\"href\":\"http://localhost:8080/CXServer/rs/1234/holidayMGraphics/1\",\"method\":\"DELETE\"}," +
                "\"editURL\":{\"@type\":\"com.cl.cx.platform.dto.Action\",\"href\":\"http://localhost:8080/CXServer/rs/1234/holidayMGraphics/1\",\"method\":\"PUT\"}," +
                "\"purchaseURL\":{\"@type\":\"com.cl.cx.platform.dto.Action\",\"href\":\"http://localhost:8080/CXServer/rs/1234/myPurchasedImages\",\"method\":\"POST\"}}";
        resultCompare(actions, result);
    }

    private void resultCompare(Actions actions, String result) throws IOException {
        LOGGER.info(result);
        LOGGER.info(objectMapper.writeValueAsString(actions));
        Assertions.assertThat(result).isEqualTo(objectMapper.writeValueAsString(actions));
    }

    @Test
    public void testBuildHolidayMGraphicItemCreatedAction() throws Exception {
        Actions actions = actionBuilder.buildHolidayMGraphicItemCreatedAction("1234", "1");
        String result = "{\"@type\":\"com.cl.cx.platform.dto.Actions\"," +
                "\"removeURL\":{\"@type\":\"com.cl.cx.platform.dto.Action\",\"href\":\"http://localhost:8080/CXServer/rs/1234/holidayMGraphics/1\",\"method\":\"DELETE\"}}";
        resultCompare(actions, result);
    }

    @Test
    public void testBuildStatusMGraphicItemEditAction() throws Exception {
        Actions actions = actionBuilder.buildStatusMGraphicItemEditAction("1234", "1");
        String result = "{\"@type\":\"com.cl.cx.platform.dto.Actions\"," +
                "\"collectURL\":{\"@type\":\"com.cl.cx.platform.dto.Action\",\"href\":\"http://localhost:8080/CXServer/rs/1234/myCollections\",\"method\":\"POST\"}," +
                "\"removeURL\":{\"@type\":\"com.cl.cx.platform.dto.Action\",\"href\":\"http://localhost:8080/CXServer/rs/1234/statusMGraphics/1\",\"method\":\"DELETE\"}," +
                "\"editURL\":{\"@type\":\"com.cl.cx.platform.dto.Action\",\"href\":\"http://localhost:8080/CXServer/rs/1234/statusMGraphics/1\",\"method\":\"PUT\"}," +
                "\"purchaseURL\":{\"@type\":\"com.cl.cx.platform.dto.Action\",\"href\":\"http://localhost:8080/CXServer/rs/1234/myPurchasedImages\",\"method\":\"POST\"}}";
        resultCompare(actions, result);
    }

    @Test
    public void testBuildStatusMGraphicItemCreatedAction() throws Exception {
        Actions actions = actionBuilder.buildStatusMGraphicItemCreatedAction("1234", "1");
        String result = "{\"@type\":\"com.cl.cx.platform.dto.Actions\"," +
                "\"removeURL\":{\"@type\":\"com.cl.cx.platform.dto.Action\",\"href\":\"http://localhost:8080/CXServer/rs/1234/statusMGraphics/1\",\"method\":\"DELETE\"}}";
        resultCompare(actions, result);
    }

    @Test
    public void testBuildCategoriesAction() throws Exception {
        Actions actions = actionBuilder.buildCategoriesAction("1234", 1L);
        String result = "{\"@type\":\"com.cl.cx.platform.dto.Actions\"," +
                "\"zoneInURL\":{\"@type\":\"com.cl.cx.platform.dto.Action\",\"href\":\"http://localhost:8080/CXServer/rs/1234/graphicInfos?categoryId=1\",\"method\":\"GET\"}," +
                "\"zoneOutURL\":{\"@type\":\"com.cl.cx.platform.dto.Action\",\"href\":\"http://localhost:8080/CXServer/rs/1234/categories\",\"method\":\"GET\"}}";
        resultCompare(actions, result);
    }

    @Test
    public void testBuildHolidayTypeAction() throws Exception {
        Actions actions = actionBuilder.buildHolidayTypeAction("1234", 1L);
        String result = "{\"@type\":\"com.cl.cx.platform.dto.Actions\"," +
                "\"zoneInURL\":{\"@type\":\"com.cl.cx.platform.dto.Action\",\"href\":\"http://localhost:8080/CXServer/rs/1234/graphicInfos?holidayTypeId=1\",\"method\":\"GET\"}," +
                "\"zoneOutURL\":{\"@type\":\"com.cl.cx.platform.dto.Action\",\"href\":\"http://localhost:8080/CXServer/rs/1234/holidayTypes\",\"method\":\"GET\"}," +
                "\"useURL\":{\"@type\":\"com.cl.cx.platform.dto.Action\",\"href\":\"http://localhost:8080/CXServer/rs/1234/holidayMGraphics?immediate=true\",\"method\":\"POST\"}}";
        resultCompare(actions, result);
    }

    @Test
    public void testBuildHolidayTypeHasUsedActions() throws Exception {
        Actions actions = actionBuilder.buildHolidayTypeHasUsedActions("1234", 1L, "1");
        String result = "{\"@type\":\"com.cl.cx.platform.dto.Actions\"," +
                "\"zoneInURL\":{\"@type\":\"com.cl.cx.platform.dto.Action\",\"href\":\"http://localhost:8080/CXServer/rs/1234/graphicInfos?holidayTypeId=1\",\"method\":\"GET\"}," +
                "\"zoneOutURL\":{\"@type\":\"com.cl.cx.platform.dto.Action\",\"href\":\"http://localhost:8080/CXServer/rs/1234/holidayTypes\",\"method\":\"GET\"}," +
                "\"useURL\":{\"@type\":\"com.cl.cx.platform.dto.Action\",\"href\":\"http://localhost:8080/CXServer/rs/1234/holidayMGraphics?immediate=true\",\"method\":\"POST\"}," +
                "\"removeURL\":{\"@type\":\"com.cl.cx.platform.dto.Action\",\"href\":\"http://localhost:8080/CXServer/rs/1234/holidayMGraphics/1\",\"method\":\"DELETE\"}}";
        resultCompare(actions, result);
    }

    @Test
    public void testBuildStatusTypeAction() throws Exception {
        Actions actions = actionBuilder.buildStatusTypeAction("1234", 1L);
        String result = "{\"@type\":\"com.cl.cx.platform.dto.Actions\"," +
                "\"zoneInURL\":{\"@type\":\"com.cl.cx.platform.dto.Action\",\"href\":\"http://localhost:8080/CXServer/rs/1234/graphicInfos?statusTypeId=1\",\"method\":\"GET\"}," +
                "\"zoneOutURL\":{\"@type\":\"com.cl.cx.platform.dto.Action\",\"href\":\"http://localhost:8080/CXServer/rs/1234/statusTypes\",\"method\":\"GET\"}," +
                "\"useURL\":{\"@type\":\"com.cl.cx.platform.dto.Action\",\"href\":\"http://localhost:8080/CXServer/rs/1234/statusMGraphics?immediate=true\",\"method\":\"POST\"}}";
        resultCompare(actions, result);
    }

    @Test
    public void testBuildStatusTypeHasUsedActions() throws Exception {
        Actions actions = actionBuilder.buildStatusTypeHasUsedActions("1234", 1L, "1");
        String result = "{\"@type\":\"com.cl.cx.platform.dto.Actions\"," +
                "\"zoneInURL\":{\"@type\":\"com.cl.cx.platform.dto.Action\",\"href\":\"http://localhost:8080/CXServer/rs/1234/graphicInfos?statusTypeId=1\",\"method\":\"GET\"}," +
                "\"zoneOutURL\":{\"@type\":\"com.cl.cx.platform.dto.Action\",\"href\":\"http://localhost:8080/CXServer/rs/1234/statusTypes\",\"method\":\"GET\"}," +
                "\"useURL\":{\"@type\":\"com.cl.cx.platform.dto.Action\",\"href\":\"http://localhost:8080/CXServer/rs/1234/statusMGraphics?immediate=true\",\"method\":\"POST\"}," +
                "\"removeURL\":{\"@type\":\"com.cl.cx.platform.dto.Action\",\"href\":\"http://localhost:8080/CXServer/rs/1234/statusMGraphics/1\",\"method\":\"DELETE\"}}";
        resultCompare(actions, result);
    }

    @Test
    public void testBuildUserFavoriteItemAction() throws Exception {
        Actions actions = actionBuilder.buildUserFavoriteItemAction("1234", "1");
        String result = "{\"@type\":\"com.cl.cx.platform.dto.Actions\"," +
                "\"useURL\":{\"@type\":\"com.cl.cx.platform.dto.Action\",\"href\":\"http://localhost:8080/CXServer/rs/1234/mGraphics\",\"method\":\"POST\"}," +
                "\"removeURL\":{\"@type\":\"com.cl.cx.platform.dto.Action\",\"href\":\"http://localhost:8080/CXServer/rs/1234/myCollections/1\",\"method\":\"DELETE\"}," +
                "\"purchaseURL\":{\"@type\":\"com.cl.cx.platform.dto.Action\",\"href\":\"http://localhost:8080/CXServer/rs/1234/myPurchasedImages\",\"method\":\"POST\"}}";
        resultCompare(actions, result);
    }

    @Test
    public void testBuildUserOperableActions() throws Exception {
        Actions actions = actionBuilder.buildUserOperableActions("1234");
        String result = "{\"@type\":\"com.cl.cx.platform.dto.Actions\"," +
                "\"recommendURL\":{\"@type\":\"com.cl.cx.platform.dto.Action\",\"href\":\"http://localhost:8080/CXServer/rs/1234/graphicInfos?recommend=true\",\"method\":\"GET\"}," +
                "\"hotURL\":{\"@type\":\"com.cl.cx.platform.dto.Action\",\"href\":\"http://localhost:8080/CXServer/rs/1234/graphicInfos?hot=true\",\"method\":\"GET\"}," +
                "\"categoryURL\":{\"@type\":\"com.cl.cx.platform.dto.Action\",\"href\":\"http://localhost:8080/CXServer/rs/1234/categories\",\"method\":\"GET\"}," +
                "\"mgraphicsURL\":{\"@type\":\"com.cl.cx.platform.dto.Action\",\"href\":\"http://localhost:8080/CXServer/rs/1234/mGraphics\",\"method\":\"GET\"}," +
                "\"statusURL\":{\"@type\":\"com.cl.cx.platform.dto.Action\",\"href\":\"http://localhost:8080/CXServer/rs/1234/statusTypes\",\"method\":\"GET\"}," +
                "\"holidaysURL\":{\"@type\":\"com.cl.cx.platform.dto.Action\",\"href\":\"http://localhost:8080/CXServer/rs/1234/holidayTypes\",\"method\":\"GET\"}," +
                "\"customMGraphicsURL\":{\"@type\":\"com.cl.cx.platform.dto.Action\",\"href\":\"http://localhost:8080/CXServer/rs/1234/customMGraphics\",\"method\":\"GET\"}," +
                "\"versionURL\":{\"@type\":\"com.cl.cx.platform.dto.Action\",\"href\":\"http://localhost:8080/CXServer/rs/upgrade\",\"method\":\"GET\"}," +
                "\"suggestionURL\":{\"@type\":\"com.cl.cx.platform.dto.Action\",\"href\":\"http://localhost:8080/CXServer/rs/1234/suggestion\",\"method\":\"POST\"}," +
                "\"callURL\":{\"@type\":\"com.cl.cx.platform.dto.Action\",\"href\":\"http://localhost:8080/CXServer/rs/1234/callings\",\"method\":\"GET\"}," +
                "\"collectionsURL\":{\"@type\":\"com.cl.cx.platform.dto.Action\",\"href\":\"http://localhost:8080/CXServer/rs/1234/myCollections\",\"method\":\"GET\"}," +
                "\"registerURL\":{\"@type\":\"com.cl.cx.platform.dto.Action\",\"href\":\"http://localhost:8080/CXServer/rs/register\",\"method\":\"POST\"}," +
                "\"inviteFriendsURL\":{\"@type\":\"com.cl.cx.platform.dto.Action\",\"href\":\"http://localhost:8080/CXServer/rs/1234/sms\",\"method\":\"POST\"}," +
                "\"uploadContactsURL\":{\"@type\":\"com.cl.cx.platform.dto.Action\",\"href\":\"http://localhost:8080/CXServer/rs/1234/contacts\",\"method\":\"POST\"}," +
                "\"getContactsURL\":{\"@type\":\"com.cl.cx.platform.dto.Action\",\"href\":\"http://localhost:8080/CXServer/rs/1234/contacts\",\"method\":\"GET\"}," +
                "\"uploadCommonMGraphicURL\":{\"@type\":\"com.cl.cx.platform.dto.Action\",\"href\":\"http://localhost:8080/CXServer/rs/1234/userCommonMGraphic/upload\",\"method\":\"POST\"}}";
        resultCompare(actions, result);
    }

    @Test
    public void testBuildAnonymousActions() throws Exception {
        Actions actions = actionBuilder.buildAnonymousActions();
        String result = "{\"@type\":\"com.cl.cx.platform.dto.Actions\"," +
                "\"recommendURL\":{\"@type\":\"com.cl.cx.platform.dto.Action\",\"href\":\"http://localhost:8080/CXServer/rs/none/graphicInfos?recommend=true\"," +
                "\"method\":\"GET\"},\"hotURL\":{\"@type\":\"com.cl.cx.platform.dto.Action\",\"href\":\"http://localhost:8080/CXServer/rs/none/graphicInfos?hot=true\",\"method\":\"GET\"}," +
                "\"categoryURL\":{\"@type\":\"com.cl.cx.platform.dto.Action\",\"href\":\"http://localhost:8080/CXServer/rs/none/categories\",\"method\":\"GET\"}," +
                "\"statusURL\":{\"@type\":\"com.cl.cx.platform.dto.Action\",\"href\":\"http://localhost:8080/CXServer/rs/none/statusTypes\",\"method\":\"GET\"}," +
                "\"holidaysURL\":{\"@type\":\"com.cl.cx.platform.dto.Action\",\"href\":\"http://localhost:8080/CXServer/rs/none/holidayTypes\",\"method\":\"GET\"}," +
                "\"versionURL\":{\"@type\":\"com.cl.cx.platform.dto.Action\",\"href\":\"http://localhost:8080/CXServer/rs/upgrade\",\"method\":\"GET\"}," +
                "\"registerURL\":{\"@type\":\"com.cl.cx.platform.dto.Action\",\"href\":\"http://localhost:8080/CXServer/rs/register\",\"method\":\"POST\"}}";
        resultCompare(actions, result);
    }

    @Test
    public void testBuildMGraphicActions() throws Exception {
        Actions actions = actionBuilder.buildMGraphicActions("1234","15","1");
        String result = "{\"@type\":\"com.cl.cx.platform.dto.Actions\"," +
                "\"removeURL\":{\"@type\":\"com.cl.cx.platform.dto.Action\",\"href\":\"http://localhost:8080/CXServer/rs/1234/1/15\",\"method\":\"DELETE\"}," +
                "\"editURL\":{\"@type\":\"com.cl.cx.platform.dto.Action\",\"href\":\"http://localhost:8080/CXServer/rs/1234/1/15\",\"method\":\"PUT\"}}";
        resultCompare(actions, result);
    }

    @Test
    public void testBuildHistoryMGraphicActions() throws Exception {
        Actions actions = actionBuilder.buildHistoryMGraphicActions("1234","15");
        String result = "{\"@type\":\"com.cl.cx.platform.dto.Actions\"," +
                "\"useURL\":{\"@type\":\"com.cl.cx.platform.dto.Action\",\"href\":\"http://localhost:8080/CXServer/rs/1234/mGraphics\",\"method\":\"POST\"}," +
                "\"removeURL\":{\"@type\":\"com.cl.cx.platform.dto.Action\",\"href\":\"http://localhost:8080/CXServer/rs/1234/historyMGraphics/15\",\"method\":\"DELETE\"}}";
        resultCompare(actions, result);
    }
}
