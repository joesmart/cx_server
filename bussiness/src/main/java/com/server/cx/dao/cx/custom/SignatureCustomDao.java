package com.server.cx.dao.cx.custom;

import com.server.cx.entity.cx.Signature;

public interface SignatureCustomDao {

    public abstract Signature findSignatureByContent(String v);

}
