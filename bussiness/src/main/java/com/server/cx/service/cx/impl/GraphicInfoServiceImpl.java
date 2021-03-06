package com.server.cx.service.cx.impl;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.cl.cx.platform.dto.DataItem;
import com.cl.cx.platform.dto.DataPage;
import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.math.IntMath;
import com.server.cx.dao.cx.GraphicInfoDao;
import com.server.cx.dao.cx.HolidayTypeDao;
import com.server.cx.dao.cx.StatusTypeDao;
import com.server.cx.dao.cx.UserHolidayMGraphicDao;
import com.server.cx.dao.cx.UserInfoDao;
import com.server.cx.dao.cx.UserStatusMGraphicDao;
import com.server.cx.dao.cx.spec.GraphicInfoSpecifications;
import com.server.cx.entity.cx.GraphicInfo;
import com.server.cx.entity.cx.HolidayType;
import com.server.cx.entity.cx.MGraphic;
import com.server.cx.entity.cx.StatusType;
import com.server.cx.entity.cx.UserHolidayMGraphic;
import com.server.cx.entity.cx.UserInfo;
import com.server.cx.entity.cx.UserStatusMGraphic;
import com.server.cx.service.cx.GraphicInfoService;
import com.server.cx.service.util.ActionNames;
import com.server.cx.service.util.BusinessFunctions;

/**
 * User: yanjianzou Date: 12-7-30 Time: 下午5:35 FileName:GraphicInfoServiceImpl
 */
@Service("graphicInfoService")
@Transactional
public class GraphicInfoServiceImpl  extends CXCoinBasicService implements GraphicInfoService {

    public static final Logger LOGGER = LoggerFactory.getLogger(GraphicInfoServiceImpl.class);

    private final int RECOMMEND_SIZE_LIMIT = 20;
    private final int HOT_SIZE_LIMIT = 100;

    @Autowired
    private GraphicInfoDao graphicInfoDao;

    @Autowired
    private BasicService basicService;

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
        List<DataItem> graphicInfoItemList = transformToGraphicItemList(imsi, graphicInfoList, null,
            ActionNames.MGRAPHIC_ACTION);
        String queryCondition = "categoryId=" + categoryId;
        return generateDataPage(imsi, offset, limit, page, graphicInfoItemList, queryCondition, -1);
    }

    private List<DataItem> transformToGraphicItemList(final String imsi, List<GraphicInfo> graphicInfoList,
                                                      Map<String, ? extends MGraphic> usedGraphicInfo, ActionNames actionNames)
        throws ExecutionException {
        List<String> userCollectionList = basicService.getUserCollectionsCache().get(imsi);
        Function<GraphicInfo, DataItem> function = businessFunctions.graphicInfoTransformToGraphicInfoItem(imsi,
            userCollectionList, usedGraphicInfo, actionNames);
        return Lists.transform(graphicInfoList, function);
    }

    @Override
    public DataPage findHotGraphicInfoByDownloadNum(String imsi, Integer offset, Integer limit)
        throws ExecutionException {
        PageRequest pageRequest = new PageRequest(offset, limit, Sort.Direction.DESC, "useCount", "createdOn");
        Page page = graphicInfoDao.findAll(GraphicInfoSpecifications.hotCategoryTypeGraphicInfo(), pageRequest);
        List<DataItem> graphicInfoItemList = transformToGraphicItemList(imsi, page.getContent(), null,
            ActionNames.MGRAPHIC_ACTION);
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
        List<DataItem> graphicInfoItemList = transformToGraphicItemList(imsi, page.getContent(), null,
            ActionNames.MGRAPHIC_ACTION);
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
        return basicService.generateURL(imsi,"/graphicInfos?" + queryCondition + "&offset=" + offset + "&limit="  + limit);
    }

    @Override
    public boolean updateGraphicInfoUseCount(GraphicInfo graphicInfo) {
        Preconditions.checkNotNull(graphicInfo);
        Preconditions.checkNotNull(graphicInfo.getId());
        graphicInfo.setUseCount(graphicInfo.getUseCount() != null ? graphicInfo.getUseCount() + 1 : 1);
        GraphicInfo afterSavedGraphicInfo = graphicInfoDao.save(graphicInfo);
        return afterSavedGraphicInfo.getId() != null;
    }

    @Override
    public DataPage findStatusGraphicInfosByImsi(String imsi, long statusTypeId, Integer offset, Integer limit) throws ExecutionException {

        String queryCondition = "statusTypeId=" + statusTypeId;
        UserInfo userInfo = userInfoDao.findByImsi(imsi);
        StatusType statusType = statusTypeDao.findOne(statusTypeId);
        List<UserStatusMGraphic> statusMGraphic = userStatusMGraphicDao.findByUserInfoAndStatusType(userInfo,
            statusType);

        boolean existUserGraphicInfo = false;
        UserStatusMGraphic userStatusMGraphic ;
        GraphicInfo graphicInfo = null;
        String usedId = null;
        Map<String, MGraphic> usedGraphicInfos = null;

        if (statusMGraphic != null && statusMGraphic.size() > 0) {
            userStatusMGraphic = statusMGraphic.get(0);
            graphicInfo = userStatusMGraphic.getGraphicResource().getGraphicInfo();
            usedId = graphicInfo.getId();
            existUserGraphicInfo = true;
            usedGraphicInfos = Maps.newHashMap();
            usedGraphicInfos.put(graphicInfo.getId(), userStatusMGraphic);
        }

        PageRequest pageRequest = new PageRequest(offset, limit, Sort.Direction.DESC, "createdOn");
        Page page = graphicInfoDao.findAll(
            GraphicInfoSpecifications.statusTypeGraphicInfoExcludedUsed(statusTypeId, usedId), pageRequest);
        List<GraphicInfo> statusGraphicInfos = Lists.newArrayList(page.getContent().iterator());

        if (existUserGraphicInfo) {
            statusGraphicInfos.add(0, graphicInfo);
        }

        List<DataItem> dataItems = transformToGraphicItemList(imsi, statusGraphicInfos, usedGraphicInfos,
            ActionNames.STATUS_MGRAPHIC_ACTION);

        return generateDataPage(imsi, offset, limit, page, dataItems, queryCondition, page.getTotalPages());
    }

    @Override
    public DataPage findHolidayGraphicInfosByImsi(String imsi, Long holidayTypeId, Integer offset, Integer limit)
        throws ExecutionException {
        LOGGER.info("imsi = " + imsi);
        LOGGER.info("holidayTypeId = " + holidayTypeId);

        String queryCondition = "holidayTypeId=" + holidayTypeId;

        UserInfo userInfo = userInfoDao.findByImsi(imsi);
        HolidayType holidayType = holidayTypeDao.findOne(holidayTypeId);
        List<UserHolidayMGraphic> userHolidayGraphicInfos = userHolidayMGraphicDao.findByUserInfoAndHolidayType(
            userInfo, holidayType);
        boolean existUserGraphicInfo = false;
        UserHolidayMGraphic userHolidayMGraphic;
        GraphicInfo graphicInfo = null;
        String usedId = null;
        Map<String, MGraphic> usedGraphicInfos = null;
        if (userHolidayGraphicInfos != null && userHolidayGraphicInfos.size() > 0) {
            userHolidayMGraphic = userHolidayGraphicInfos.get(0);
            graphicInfo = userHolidayMGraphic.getGraphicResource().getGraphicInfo();
            usedId = graphicInfo.getId();
            existUserGraphicInfo = true;
            LOGGER.info(graphicInfo.getName());
            usedGraphicInfos = Maps.newHashMap();
            usedGraphicInfos.put(graphicInfo.getId(), userHolidayMGraphic);
        }

        PageRequest pageRequest = new PageRequest(offset, limit, Sort.Direction.DESC, "createdOn");
        Page page = graphicInfoDao.findAll(
            GraphicInfoSpecifications.holidayTypeGraphicInfoExcludedUsed(holidayTypeId, usedId), pageRequest);
        List<GraphicInfo> holidayGraphicInfos = Lists.newArrayList(page.getContent());
        if (existUserGraphicInfo) {
            holidayGraphicInfos.add(0, graphicInfo);
        }
        
        List<DataItem> dataItems = transformToGraphicItemList(imsi, holidayGraphicInfos, usedGraphicInfos,
            ActionNames.HOLIDAY_MGRAPHIC_ACTION);

        DataPage dataPage = generateDataPage(imsi, offset, limit, page, dataItems, queryCondition, page.getTotalPages());

        return dataPage;
    }

    @Transactional(readOnly=false)
    @Override
    public void addGraphicInfo(GraphicInfo graphicInfo) {
        graphicInfoDao.save(graphicInfo);
    }
}
