package com.server.cx.service.cx.impl;

import com.cl.cx.platform.dto.DataItem;
import com.cl.cx.platform.dto.DataPage;
import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.math.IntMath;
import com.server.cx.dao.cx.*;
import com.server.cx.dao.cx.spec.GraphicInfoSpecifications;
import com.server.cx.entity.cx.*;
import com.server.cx.service.cx.GraphicInfoService;
import com.server.cx.service.util.ActionNames;
import com.server.cx.service.util.BusinessFunctions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * User: yanjianzou Date: 12-7-30 Time: 下午5:35 FileName:GraphicInfoServiceImpl
 */
@Service("graphicInfoService")
@Transactional
public class GraphicInfoServiceImpl extends BasicService implements GraphicInfoService {

    public static final Logger LOGGER = LoggerFactory.getLogger(GraphicInfoServiceImpl.class);

    private final int RECOMMEND_SIZE_LIMIT = 20;
    private final int HOT_SIZE_LIMIT = 100;

    @Autowired
    private GraphicInfoDao graphicInfoDao;

    @Autowired
    private BusinessFunctions businessFunctions;

    @Autowired
    private UserInfoDao userInfoDao;

    @Autowired
    private StatusTypeDao statusTypeDao;
    
    @Autowired
    private HolidayTypeDao holidayTypeDao;

    @Autowired
    private UserStatusMGraphicDao userStatusMGraphicDao;
    
    @Autowired
    private UserHolidayMGraphicDao userHolidayMGraphicDao;

    @Override
    public DataPage findGraphicInfoDataPageByCategoryId(final String imsi, Long categoryId, Integer offset,
                                                        Integer limit) throws ExecutionException {
        PageRequest pageRequest = new PageRequest(offset, limit, Sort.Direction.DESC, "createdOn");
        Page page = graphicInfoDao.findAll(GraphicInfoSpecifications.categoryTypeGraphicInfo(categoryId), pageRequest);
        List<GraphicInfo> graphicInfoList = page.getContent();
        List<DataItem> graphicInfoItemList = transformToGraphicItemList(imsi, graphicInfoList, null, ActionNames.MGRAPHIC_ACTION);
        String queryCondition = "categoryId=" + categoryId;
        return generateDataPage(imsi, offset, limit, page, graphicInfoItemList, queryCondition, -1);
    }

    private List<DataItem> transformToGraphicItemList(final String imsi, List<GraphicInfo> graphicInfoList, Map<String, String> usedGraphicInfo, ActionNames actionNames)
        throws ExecutionException {
        List<String> userCollectionList = userCollectionsCache.get(imsi);
        Function<GraphicInfo,DataItem> function = businessFunctions.graphicInfoTransformToGraphicInfoItem(imsi, userCollectionList, usedGraphicInfo, actionNames);
        return Lists.transform(graphicInfoList,function);
    }

    @Override
    public DataPage findHotGraphicInfoByDownloadNum(String imsi, Integer offset, Integer limit)
        throws ExecutionException {
        PageRequest pageRequest = new PageRequest(offset, limit, Sort.Direction.DESC, "useCount", "createdOn");
        Page page = graphicInfoDao.findAll(GraphicInfoSpecifications.hotCategoryTypeGraphicInfo(), pageRequest);
        List<DataItem> graphicInfoItemList = transformToGraphicItemList(imsi, page.getContent(), null, ActionNames.MGRAPHIC_ACTION);
        String condition = "hot=true";
        setUpTotalPageLimit(limit, page.getTotalPages(), HOT_SIZE_LIMIT);
        return generateDataPage(imsi, offset, limit, page, graphicInfoItemList, condition, -1);
    }

    @Override
    public DataPage findRecommendGraphicAndPagination(String imsi, Integer offset, Integer limit)
        throws ExecutionException {
        PageRequest pageRequest = new PageRequest(offset, limit, Sort.Direction.DESC, "useCount", "createdOn");
        Page page = graphicInfoDao.findAll(GraphicInfoSpecifications.recommendGraphicInfo(), pageRequest);
        setUpTotalPageLimit(limit, page.getTotalPages(), RECOMMEND_SIZE_LIMIT);
        List<DataItem> graphicInfoItemList = transformToGraphicItemList(imsi, page.getContent(), null, ActionNames.MGRAPHIC_ACTION);
        String condition = "recommend=true";
        DataPage dataPage = generateDataPage(imsi, offset, limit, page, graphicInfoItemList, condition, -1);
        return dataPage;
    }

    private int setUpTotalPageLimit(Integer limit, int realTotalPage, int totalRecords) {
        int mod = IntMath.mod(totalRecords, limit);
        int totalPage = mod > 0 ? totalRecords / limit + 1 : totalRecords / limit;
        if (realTotalPage < totalPage) {
            return realTotalPage;
        } else {
            return totalPage;
        }
    }

    private DataPage generateDataPage(String imsi, Integer offset, Integer limit, Page page,
                                      List<DataItem> graphicInfoItemList, String condition, int totalPage) {
        DataPage dataPage = new DataPage();
        dataPage.setLimit(page.getSize());
        dataPage.setOffset(page.getNumber());
        dataPage.setTotal(totalPage < 0 ? page.getTotalPages() : totalPage);
        dataPage.setItems(graphicInfoItemList);
        dataPage.setHref(generatePageURL(imsi, offset, limit, condition));

        if (offset > 0) {
            int previousOffset = offset - 1;
            dataPage.setPrevious(generatePageURL(imsi, previousOffset, limit, condition));
        }
        if (offset + 1 < dataPage.getTotal()) {
            int nextOffset = offset + 1;
            dataPage.setNext(generatePageURL(imsi, nextOffset, limit, condition));
        }
        dataPage.setFirst(generatePageURL(imsi, 0, limit, condition));
        dataPage.setLast(generatePageURL(imsi, (dataPage.getTotal() - 1), limit, condition));
        return dataPage;
    }

    private String generatePageURL(String imsi, int offset, Integer limit, String queryCondition) {
        return baseHostAddress + restURL + imsi + "/graphicInfos?" + queryCondition + "&offset=" + offset + "&limit="
            + limit;
    }

    @Override
    public boolean updateGraphicInfoUseCount(GraphicInfo graphicInfo) {
        Preconditions.checkNotNull(graphicInfo);
        Preconditions.checkNotNull(graphicInfo.getId());
        graphicInfo.setUseCount(graphicInfo.getUseCount() != null ? graphicInfo.getUseCount() + 1 : 1);
        GraphicInfo afterSavedGraphicInfo = graphicInfoDao.save(graphicInfo);
        if (afterSavedGraphicInfo.getId() != null) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public DataPage findStatusGraphicInfosByImsi(String imsi, long statusTypeId, Integer offset, Integer limit) {
        final String baseHref = baseHostAddress + restURL + imsi + "/statusTypes/" + statusTypeId + "?offset=" + offset
            + "&limit=" + limit;

        UserInfo userInfo = userInfoDao.findByImsi(imsi);
        StatusType statusType = statusTypeDao.findOne(statusTypeId);
        List<UserStatusMGraphic> userStatusGraphicInfos = userStatusMGraphicDao.findByUserInfoAndStatusType(userInfo,
            statusType);
        boolean existUserGraphicInfo = userStatusGraphicInfos != null && !userStatusGraphicInfos.isEmpty();

        String usedId = existUserGraphicInfo ? userStatusGraphicInfos.get(0).getGraphicInfo().getId() : null;
        PageRequest pageRequest = new PageRequest(offset, limit, Sort.Direction.DESC, "createdOn");
        Page page = graphicInfoDao.findAll(
            GraphicInfoSpecifications.statusTypeGraphicInfoExcludedUsed(statusTypeId, usedId), pageRequest);
        List<GraphicInfo> statusGraphicInfos = Lists.newArrayList(page.getContent().iterator());

        if (existUserGraphicInfo) {
            statusGraphicInfos.add(0, userStatusGraphicInfos.get(0).getGraphicInfo());
        }

        List<DataItem> dataItems = transformTostStatusGraphicInfoList(statusGraphicInfos);

        if (existUserGraphicInfo) {
            dataItems.get(0).setInUsing(true);
        }

        DataPage dataPage = new DataPage();
        dataPage.setLimit(page.getSize());
        dataPage.setOffset(page.getNumber());
        dataPage.setTotal(page.getTotalPages());
        dataPage.setItems(dataItems);
        dataPage.setHref(baseHref);
        if (offset > 0) {
            int previousOffset = offset - 1;
            dataPage.setPrevious(baseHostAddress + restURL + imsi + "/statusTypes/" + statusTypeId + "?offset="
                + previousOffset + "&limit=" + limit);
        }
        if (offset + 1 < page.getTotalPages()) {
            int nextOffset = offset + 1;
            dataPage.setNext(baseHostAddress + restURL + imsi + "/statusTypes/" + statusTypeId + "?offset="
                + nextOffset + "&limit=" + limit);
        }
        dataPage.setFirst(baseHostAddress + restURL + imsi + "/statusTypes/" + statusTypeId + "?offset=0&limit="
            + limit);
        dataPage.setLast(baseHostAddress + restURL + imsi + "/statusTypes/" + statusTypeId + "?offset="
            + (dataPage.getTotal() - 1) + "&limit=" + limit);
        return dataPage;
    }

    private List<DataItem> transformTostStatusGraphicInfoList(List<GraphicInfo> usingStatusGraphicInfos) {
        return Lists.transform(usingStatusGraphicInfos, businessFunctions.statusGraphicInfoTransformToDataItem());
    }

    @Override
    public DataPage findHolidayGraphicInfosByImsi(String imsi, Long holidayTypeId, Integer offset, Integer limit) throws ExecutionException {
        final String baseHref = baseHostAddress + restURL + imsi + "/holidayTypes/" + holidayTypeId + "?offset=" + offset
            + "&limit=" + limit;
        String queryCondition = "holidayTypeId=" + holidayTypeId;
        LOGGER.info("imsi = " + imsi);
        LOGGER.info("holidayTypeId = " + holidayTypeId);
        UserInfo userInfo = userInfoDao.findByImsi(imsi);
        HolidayType holidayType = holidayTypeDao.findOne(holidayTypeId);
        List<UserHolidayMGraphic> userHolidayGraphicInfos = userHolidayMGraphicDao.findByUserInfoAndHolidayType(userInfo,
            holidayType);
        boolean existUserGraphicInfo =false;
        UserHolidayMGraphic userHolidayMGraphic = null;
        GraphicInfo graphicInfo = null;
        String usedId =null;
        Map<String,String> usedGraphicInfos = null;
        if(userHolidayGraphicInfos!= null && userHolidayGraphicInfos.size() > 0){
            userHolidayMGraphic = userHolidayGraphicInfos.get(0);
            graphicInfo = userHolidayMGraphic.getGraphicInfo();
            usedId = graphicInfo.getId();
            existUserGraphicInfo = true;
            LOGGER.info(graphicInfo.getName());
            usedGraphicInfos = Maps.newHashMap();
            usedGraphicInfos.put(graphicInfo.getId(),userHolidayMGraphic.getId());
        }

        PageRequest pageRequest = new PageRequest(offset, limit, Sort.Direction.DESC, "createdOn");
        Page page = graphicInfoDao.findAll(
            GraphicInfoSpecifications.holidayTypeGraphicInfoExcludedUsed(holidayTypeId, usedId), pageRequest);
        List<GraphicInfo> holidayGraphicInfos = Lists.newArrayList(page.getContent());
        if (existUserGraphicInfo) {
            holidayGraphicInfos.add(0, graphicInfo);
        }
        List<DataItem> dataItems = transformToGraphicItemList(imsi, holidayGraphicInfos, usedGraphicInfos, ActionNames.HOLIDAY_MGRAPHIC_ACTION);

        DataPage dataPage = generateDataPage(imsi,offset,limit,page,dataItems,queryCondition,page.getTotalPages());

        return dataPage;
    }
}
