package com.server.cx.dao.cx;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import com.server.cx.exception.SystemException;

public interface GenericDao <T, PK extends Serializable> {
    /**  
     * Generic method used to get all objects of a particular type. This  
     * is the same as lookup up all rows in a table.  
     * @return List of populated objects  
     */ 
    List<T> getAll();  
 
    /**  
     * Generic method to get an object based on class and identifier. An  
     * ObjectRetrievalFailureException Runtime Exception is thrown if  
     * nothing is found.  
     *  
     * @param id the identifier (primary key) of the object to get  
     * @return a populated object  
     * @see org.springframework.orm.ObjectRetrievalFailureException  
     */ 
    T getById(PK id);  
 
    /**  
     * Checks for existence of an object of type T using the id arg.  
     * @param id the id of the entity  
     * @return - true if it exists, false if it doesn't  
     */ 
    boolean exists(PK id);  
 
    /**  
     * Generic method to save an object - handles both update and insert.  
     * @param object the object to save  
     * @return the persisted object  
     * @throws com.server.cx.exception.SystemException
     */ 
    T merge(T object) throws SystemException;  
 
    /**  
     * Generic method to delete an object based on class and id  
     * @param id the identifier (primary key) of the object to remove  
     */ 
    void remove(PK id);  
      
    /**  
     * Gets all records without duplicates.  
     * <p>Note that if you use this method, it is imperative that your model  
     * classes correctly implement the hashcode/equals methods</p>  
     * @return List of populated objects  
     */ 
    List<T> getAllDistinct();  
      
 
    /**  
     * Find a list of records by using a named query  
     * @param queryName query name of the named query  
     * @param queryParams a map of the query names and the values  
     * @return a list of the records found  
     */ 
    List<T> findByNamedQuery(String queryName, Map<String, Object> queryParams);
    
    /**
     * 判断指定ID的对象是否存在.
     * <p>If necessary, describe how it does and how to use it.</P>
     * @param id
     * @return
     */
    boolean isExists(Long id);
    
    /**
     * 更新指定的表记录.
     * <p>If necessary, describe how it does and how to use it.</P>
     * @param entity
     * @throws com.server.cx.exception.SystemException
     */
    void update(T entity)throws SystemException;  
    /**
     * 保存指定的表记录.
     * <p>If necessary, describe how it does and how to use it.</P>
     * @param entity
     * @throws com.server.cx.exception.SystemException
     */
    void save(T entity) throws SystemException;
    /**
     * 删除指定的表记录   
     * <p>If necessary, describe how it does and how to use it.</P>
     * @param entity
     * @throws com.server.cx.exception.SystemException
     */
    void delet(T entity)throws SystemException;
    
    /**
     * 删除所有的表记录
     * <p>If necessary, describe how it does and how to use it.</P>
     */
    void removeAll();
    
    void persist(T entity) throws SystemException;
}
