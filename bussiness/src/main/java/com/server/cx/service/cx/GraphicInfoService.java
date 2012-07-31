package com.server.cx.service.cx;

import com.server.cx.dto.DataPage;

/**
 * User: yanjianzou
 * Date: 12-7-30
 * Time: 下午5:33
 * FileName:GraphicInfoService
 */
public interface GraphicInfoService {
    public abstract DataPage findGraphicInfoDataPageByCategoryId(final String imsi, Long categoryId,Integer offset,Integer limit);
}
