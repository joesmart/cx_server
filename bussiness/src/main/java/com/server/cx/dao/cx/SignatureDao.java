package com.server.cx.dao.cx;

import com.server.cx.dao.cx.custom.SignatureCustomDao;
import com.server.cx.entity.cx.Signature;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * User: yanjianzou
 * Date: 12-7-20
 * Time: 下午2:32
 * FileName:SignatureDao
 */
public interface SignatureDao extends PagingAndSortingRepository<Signature, Long>, SignatureCustomDao {
}
