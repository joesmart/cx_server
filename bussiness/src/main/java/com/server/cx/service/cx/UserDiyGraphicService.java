package com.server.cx.service.cx;

import com.cl.cx.platform.dto.MGraphicDTO;
import com.server.cx.model.OperationResult;

import java.io.IOException;
import java.io.InputStream;

public interface UserDiyGraphicService {
    public void addFileStreamToResourceServer(String imsi, InputStream fileStream) throws IOException ;

    public abstract OperationResult delete(String id);

    public abstract OperationResult create(String imsi, MGraphicDTO mGraphicDTO);
}
