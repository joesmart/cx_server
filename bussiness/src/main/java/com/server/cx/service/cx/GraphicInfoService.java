package com.server.cx.service.cx;

import com.server.cx.dto.DataPage;

/**
 * User: yanjianzou
 * Date: 12-7-30
 * Time: 下午5:33
 * FileName:GraphicInfoService
 */
public interface GraphicInfoService {
    public abstract DataPage findGraphicInfoDataPageByCategoryId(String imsi, Long categoryId,Integer offset,Integer limit);

    public abstract DataPage findHotGraphicInfoByDownloadNum(String imsi,Integer offset,Integer limit);

    public abstract DataPage findRecommendGraphicAndPagination(String imsi, Integer offset, Integer limit);
}
