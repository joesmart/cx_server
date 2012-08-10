package com.server.cx.service.cx.impl;

import com.cl.cx.platform.dto.DataItem;
import com.cl.cx.platform.dto.DataPage;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.math.IntMath;
import com.server.cx.dao.cx.GraphicInfoDao;
import com.server.cx.dao.cx.spec.GraphicInfoSpecifications;
import com.server.cx.entity.cx.GraphicInfo;
import com.server.cx.service.cx.GraphicInfoService;
import com.server.cx.service.util.BusinessFunctions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * User: yanjianzou
 * Date: 12-7-30
 * Time: 下午5:35
 * FileName:GraphicInfoServiceImpl
 */
@Service("graphicInfoService")
@Transactional
public class GraphicInfoServiceImpl extends BasicService implements GraphicInfoService {

    private final int RECOMMEND_SIZE_LIMIT = 20;
    private final int HOT_SIZE_LIMIT = 100;

    @Autowired
    private GraphicInfoDao graphicInfoDao;

    @Autowired
    private BusinessFunctions businessFunctions;

    @Override
    public DataPage findGraphicInfoDataPageByCategoryId(final String imsi, Long categoryId, Integer offset, Integer limit) throws ExecutionException {
        PageRequest pageRequest = new PageRequest(offset, limit, Sort.Direction.DESC, "createdOn");
        Page page = graphicInfoDao.findAll(GraphicInfoSpecifications.categoryTypeGraphicInfo(categoryId), pageRequest);
        List<GraphicInfo> graphicInfoList = page.getContent();
        List<DataItem> graphicInfoItemList = transformToGraphicItemList(imsi, graphicInfoList);
        String queryCondition = "categoryId=" + categoryId;
        return generateDataPage(imsi, offset, limit, page, graphicInfoItemList, queryCondition, -1);
    }


    private List<DataItem> transformToGraphicItemList(final String imsi, List<GraphicInfo> graphicInfoList) throws ExecutionException {
        List<String> userCollectionList = userCollectionsCache.get(imsi);
        return Lists.transform(graphicInfoList, businessFunctions.graphicInfoTransformToGraphicInfoItem(imsi, userCollectionList));
    }

    @Override
    public DataPage findHotGraphicInfoByDownloadNum(String imsi, Integer offset, Integer limit) throws ExecutionException {
        PageRequest pageRequest = new PageRequest(offset, limit, Sort.Direction.DESC, "useCount", "createdOn");
        Page page = graphicInfoDao.findAll(GraphicInfoSpecifications.hotCategoryTypeGraphicInfo(), pageRequest);
        List<DataItem> graphicInfoItemList = transformToGraphicItemList(imsi, page.getContent());
        String condition = "hot=true";
        setUpTotalPageLimit(limit, page.getTotalPages(), HOT_SIZE_LIMIT);
        return generateDataPage(imsi, offset, limit, page, graphicInfoItemList, condition, -1);
    }

    @Override
    public DataPage findRecommendGraphicAndPagination(String imsi, Integer offset, Integer limit) throws ExecutionException {
        PageRequest pageRequest = new PageRequest(offset, limit, Sort.Direction.DESC, "useCount", "createdOn");
        Page page = graphicInfoDao.findAll(GraphicInfoSpecifications.recommendGraphicInfo(), pageRequest);
        setUpTotalPageLimit(limit, page.getTotalPages(), RECOMMEND_SIZE_LIMIT);
        List<DataItem> graphicInfoItemList = transformToGraphicItemList(imsi, page.getContent());
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

    private DataPage generateDataPage(String imsi, Integer offset, Integer limit, Page page, List<DataItem> graphicInfoItemList, String condition, int totalPage) {
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
        return baseHostAddress + restURL + imsi + "/graphicInfos?" + queryCondition + "&offset=" + offset + "&limit=" + limit;
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
}
