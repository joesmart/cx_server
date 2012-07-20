package com.server.cx.dao.cx;

import com.server.cx.entity.cx.Signature;

public interface SignatureDao {

    public abstract Signature findSignatureByContent(String v);
    
    public Signature getById(Long id);
    
}
