package com.server.cx.service.cx;

import com.cl.cx.platform.dto.DataPage;
import com.server.cx.entity.cx.GraphicInfo;

import java.util.concurrent.ExecutionException;

/**
 * User: yanjianzou
 * Date: 12-7-30
 * Time: 下午5:33
 * FileName:GraphicInfoService
 */
public interface GraphicInfoService {
    public abstract DataPage findGraphicInfoDataPageByCategoryId(String imsi, Long categoryId,Integer offset,Integer limit) throws ExecutionException;

    public abstract DataPage findHotGraphicInfoByDownloadNum(String imsi,Integer offset,Integer limit) throws ExecutionException;

    public abstract DataPage findRecommendGraphicAndPagination(String imsi, Integer offset, Integer limit) throws ExecutionException;

    public abstract boolean updateGraphicInfoUseCount(GraphicInfo graphicInfo);
}
