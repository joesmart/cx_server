package com.server.cx.service.cx.impl;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.server.cx.dao.cx.GraphicInfoDao;
import com.server.cx.dao.cx.spec.GraphicInfoSpecifications;
import com.server.cx.dto.Action;
import com.server.cx.dto.DataPage;
import com.server.cx.dto.GraphicInfoItem;
import com.server.cx.entity.cx.GraphicInfo;
import com.server.cx.service.cx.GraphicInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Nullable;
import java.util.List;

/**
 * User: yanjianzou
 * Date: 12-7-30
 * Time: 下午5:35
 * FileName:GraphicInfoServiceImpl
 */
@Service("graphicInfoService")
@Transactional
public class GraphicInfoServiceImpl extends  BasicService implements GraphicInfoService {
    @Autowired
    private GraphicInfoDao graphicInfoDao;
    @Override
    public DataPage findGraphicInfoDataPageByCategoryId(final String imsi, Long categoryId, Integer offset, Integer limit) {
        final String baseHref = baseHostAddress + restURL + imsi + "/graphicInfos?categoryId=" + categoryId + "&offset=" + offset + "&limit=" + limit;
        PageRequest pageRequest = new PageRequest(offset, limit, Sort.Direction.DESC, "createdOn");
        Page page = graphicInfoDao.findAll(GraphicInfoSpecifications.categoryTypeGraphicInfo(categoryId), pageRequest);
        List<GraphicInfo> graphicInfoList = page.getContent();

        List<GraphicInfoItem> graphicInfoItemList = Lists.transform(graphicInfoList, new Function<GraphicInfo, GraphicInfoItem>() {
            @Override
            public GraphicInfoItem apply(@Nullable GraphicInfo input) {
                GraphicInfoItem graphicInfoItem = new GraphicInfoItem();
                graphicInfoItem.setId(input.getId());
                graphicInfoItem.setName(input.getName());
                graphicInfoItem.setSignature(input.getSignature());
                graphicInfoItem.setDownloadNumber(String.valueOf(input.getUseCount()));
                graphicInfoItem.setAuditPassed(true);
                graphicInfoItem.setPrice(input.getPrice());
                if (input.getPrice() > 0.0F) {
                    graphicInfoItem.setPurchased(false);
                }
                graphicInfoItem.setCollected(false);
                graphicInfoItem.setLevel(input.getLevel());
                if(input.getGraphicResources().size() >0 ){
                    graphicInfoItem.setThumbnailPath(imageShowURL+input.getGraphicResources().get(0).getResourceId()+"&"+thumbnailSize);
                    graphicInfoItem.setSourceImagePath(imageShowURL+input.getGraphicResources().get(0).getResourceId());
                }
                graphicInfoItem.setHref(baseHostAddress + restURL + imsi + "/graphicInfos/" + input.getId());
                Action action = actionBuilder.buildGraphicItemAction(imsi);
                graphicInfoItem.setAction(action);
                return graphicInfoItem;
            }
        });

        DataPage dataPage = new DataPage();
        dataPage.setLimit(page.getSize());
        dataPage.setOffset(page.getNumber());
        dataPage.setTotal(page.getTotalPages());
        dataPage.setItems(graphicInfoItemList);
        dataPage.setHref(baseHref);
        if (offset > 0) {
            int previousOffset = offset - 1;
            dataPage.setPrevious(baseHostAddress + restURL + imsi + "/graphicInfos?categoryId=" + categoryId + "&offset=" + previousOffset + "&limit=" + limit);
        }
        if (offset + 1 < page.getTotalPages()) {
            int nextOffset = offset + 1;
            dataPage.setNext(baseHostAddress + restURL + imsi + "/graphicInfos?categoryId=" + categoryId + "&offset=" + nextOffset + "&limit=" + limit);
        }
        dataPage.setFirst(baseHostAddress + restURL + imsi + "/graphicInfos?categoryId=" + categoryId + "&offset=0&limit=" + limit);
        dataPage.setLast(baseHostAddress + restURL + imsi + "/graphicInfos?categoryId=" + categoryId + "&offset=" + (dataPage.getTotal() - 1) + "&limit=" + limit);
        return dataPage;
    }
}
