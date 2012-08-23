package com.server.cx.service.cx;

import java.io.IOException;
import java.io.InputStream;

public interface UserDiyGraphicService {
    public void addFileStreamToResourceServer(String imsi, InputStream fileStream) throws IOException ;
}
