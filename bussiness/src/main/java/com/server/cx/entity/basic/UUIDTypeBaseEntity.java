package com.server.cx.entity.basic;

import java.util.UUID;

/**
 * Created with IntelliJ IDEA.
 * User: yanjianzou
 * Date: 9/21/12
 * Time: 2:19 PM
 * To change this template use File | Settings | File Templates.
 */

//@MappedSuperclass
//@ToString
//@EqualsAndHashCode
public class UUIDTypeBaseEntity {
//    @Id
//    @Type(type = "uuid-binary")
//    @GeneratedValue(generator = "system-uuid")
//    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
//    @Column(columnDefinition = "BINARY(16)", length = 16)
    private UUID id;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }


}
