package com.server.cx.entity.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.Lists;
import com.server.cx.entity.basic.IdEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springside.modules.utils.Collections3;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * 用户.
 * <p/>
 * 使用JPA annotation定义ORM关系.
 * 使用Hibernate annotation定义JPA未覆盖的部分.
 *
 * @author calvin
 */
@Entity
//表名与类名不相同时重新定义表名.
@Table(name = "acct_user")
//默认的缓存策略.
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@XmlRootElement
public class User extends IdEntity {

    private String loginName;
    private String password;//为简化演示使用明文保存的密码
    private String name;
    private String email;
    /**
     * indicate user is login or not
     */
    @Column(nullable = false, columnDefinition = "boolean default true")
    private Boolean status;

    public Boolean isStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    private List<Group> groupList = Lists.newArrayList();//有序的关联对象集合

    @JsonProperty(value = "username")
    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    //多对多定义
    @ManyToMany
    @JoinTable(name = "acct_user_group", joinColumns = {@JoinColumn(name = "user_id")}, inverseJoinColumns = {@JoinColumn(name = "group_id")})
    //Fecth策略定义
    @Fetch(FetchMode.SUBSELECT)
    //集合按id排序.
    @OrderBy("id")
    //集合中对象id的缓存.
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    public List<Group> getGroupList() {
        return groupList;
    }

    public void setGroupList(List<Group> groupList) {
        this.groupList = groupList;
    }

    /**
     * 用户拥有的权限组名称字符串, 多个权限组名称用','分隔.
     */
    //非持久化属性.
    @Transient
    public String getGroupNames() {
        return Collections3.extractToString(groupList, "name", ", ");
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}