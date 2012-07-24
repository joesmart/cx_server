package com.server.cx.entity.cx;

import java.util.List;

public interface BaseDao<B extends LongTypeIdentifiable> {
  B getNewInstance();

  List<B> getAll();

  B getById(Long id);

  void save(B obj);

  void persist(B obj);

  void update(B obj);

  void saveOrUpdate(B obj);

  void delete(B obj);

  int getCountAll();

  boolean isExists(Long id);
}
