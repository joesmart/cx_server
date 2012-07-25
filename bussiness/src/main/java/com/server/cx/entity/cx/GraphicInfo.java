package com.server.cx.entity.cx;

import com.server.cx.entity.basic.AuditableStringEntity;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * User: yanjianzou
 * Date: 12-7-25
 * Time: 下午2:03
 * FileName:GraphicInfo
 */
@Entity
@Table(name="graphic_infos")
public class GraphicInfo extends AuditableStringEntity {
    private String name;
    private String signature;
    private Integer recommendLevel;
    private Integer popularLevel;
    private Integer useCount;
    private Float price;

    private String owner;
    private String category;
}
