package com.server.cx.entity.cx;

import com.server.cx.service.util.CheckStatusDesc;
import lombok.Data;

@Data
public class FileMeta {
    private String resourceId;
    private String name;
    private long size;
    private String url;
    private String thumbnail_url;
    private String delete_url;
    private String delete_type;
    private CheckStatusDesc checkStatusDesc;

}
