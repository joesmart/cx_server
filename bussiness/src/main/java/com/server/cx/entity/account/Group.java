package com.server.cx.entity.account;

import com.google.common.collect.Lists;
import com.server.cx.entity.basic.IdEntity;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.util.List;

/**
 * 权限组.
 * <p/>
 * 注释见{@link User}.
 *
 * @author calvin
 */
@Entity
@Table(name = "acct_group")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Group extends IdEntity {

    private String name;

    private List<String> permissionList = Lists.newArrayList();

    public Group() {
    }

    public Group(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    @Column(nullable = false, unique = true)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @ElementCollection
    @CollectionTable(name = "acct_group_permission", joinColumns = {@JoinColumn(name = "group_id")})
    @Column(name = "permission")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    public List<String> getPermissionList() {
        return permissionList;
    }

    public void setPermissionList(List<String> permissionList) {
        this.permissionList = permissionList;
    }

    @Transient
    public String getPermissionNames() {
        List<String> permissionNameList = Lists.newArrayList();
        for (String permission : permissionList) {
            permissionNameList.add(Permission.parse(permission).displayName);
        }
        return StringUtils.join(permissionNameList, ",");
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
