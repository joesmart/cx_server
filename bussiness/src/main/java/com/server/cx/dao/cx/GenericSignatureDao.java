package com.server.cx.dao.cx;

import com.server.cx.entity.cx.Signature;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * User: yanjianzou
 * Date: 12-7-20
 * Time: 下午2:32
 * FileName:GenericSignatureDao
 */
public interface GenericSignatureDao extends PagingAndSortingRepository<Signature, Long> {
}
