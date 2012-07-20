/*
 * Copyright (c) 2011 CieNet, Ltd.
 * Created on 2011-9-21
 */
package com.server.cx.entity.cx;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * POJO of Contact. Briefly describe what this class does.
 */
@Entity
@Table(name = "contact")
public class Contact{
    /**
     * identifier of this entity.
     */
    private int id;
    /**
     * imsi, to interact with user.
     */
    private String imsi;
    /**
     * name, attribute of contact.
     */
    private String name;
    /**
     * mobile1,  attribute of contact.
     */
    private String mobile1;
    /**
     * mobile2,  attribute of contact.
     */
    private String mobile2;
    /**
     * phone1,  attribute of contact.
     */
    private String phone1;
    /**
     * phone2,  attribute of contact.
     */
    private String phone2;
    /**
     * address,  attribute of contact.
     */
    private String address;
    /**
     * fax,  attribute of contact.
     */
    private String fax;
    /**
     * email,  attribute of contact.
     */
    private String email;
    /**
     * method to get address.
     * <p>If necessary, describe how it does and how to use it.</P>
     * @return String
     */
    public  String getAddress() {
        return address;
    }
    /**
     * method to get email.
     * <p>If necessary, describe how it does and how to use it.</P>
     * @return String
     */
    public  String getEmail() {
        return email;
    }
    /**
     * method to get fax.
     * <p>If necessary, describe how it does and how to use it.</P>
     * @return String
     */
    public  String getFax() {
        return fax;
    }

    /**
     * method to get identifiter.
     * <p>If necessary, describe how it does and how to use it.</P>
     * @return int
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public  int getId() {
        return id;
    }
    /**
     * method to get imsi.
     * <p>If necessary, describe how it does and how to use it.</P>
     * @return String
     */
    public  String getImsi() {
        return imsi;
    }
    /**
     * method to get mobile1.
     * <p>If necessary, describe how it does and how to use it.</P>
     * @return String
     */
    public  String getMobile1() {
        return mobile1;
    }
    /**
     * method to get mobile2.
     * <p>If necessary, describe how it does and how to use it.</P>
     * @return String
     */
    public  String getMobile2() {
        return mobile2;
    }

    /**
     * method to get name.
     * <p>If necessary, describe how it does and how to use it.</P>
     * @return String
     */
    public  String getName() {
        return name;
    }
    /**
     * method to get phone1.
     * <p>If necessary, describe how it does and how to use it.</P>
     * @return String
     */
    public  String getPhone1() {
        return phone1;
    }
    /**
     * method to get phone2.
     * <p>If necessary, describe how it does and how to use it.</P>
     * @return String
     */
    public  String getPhone2() {
        return phone2;
    }
    /**
     * method to set address with parameter.
     * <p>If necessary, describe how it does and how to use it.</P>
     * @param addr String
     */
    public  void setAddress( String addr) {
        this.address = addr;
    }
    /**
     * method to set email.
     * <p>If necessary, describe how it does and how to use it.</P>
     * @param email
     */
    public  void setEmail( String email) {
        this.email = email;
    }
    /**
     * method to set fax.
     * <p>If necessary, describe how it does and how to use it.</P>
     * @param fax String
     */
    public  void setFax( String fax) {
        this.fax = fax;
    }
    /**
     * method to set id.
     * <p>If necessary, describe how it does and how to use it.</P>
     * @param id
     */
    public  void setId( int id) {
        this.id = id;
    }
    /**
     * method to set imsi.
     * <p>If necessary, describe how it does and how to use it.</P>
     * @param imsi
     */
    public  void setimsi( String imsi) {
        this.imsi = imsi;
    }
    /**
     *method to set mobile1.
     * <p>If necessary, describe how it does and how to use it.</P>
     * @param mobile1
     */
    public  void setMobile1( String mobile1) {
        this.mobile1 = mobile1;
    }
    /**
     * method to set mobile2.
     * <p>If necessary, describe how it does and how to use it.</P>
     * @param mobile2
     */
    public  void setMobile2( String mobile2) {
        this.mobile2 = mobile2;
    }
    /**
     * method to set name.
     * <p>If necessary, describe how it does and how to use it.</P>
     * @param name
     */
    public  void setName( String name) {
        this.name = name;
    }
    /**
     * method to set phone1.
     * <p>If necessary, describe how it does and how to use it.</P>
     * @param phone1
     */
    public  void setPhone1( String phone1) {
        this.phone1 = phone1;
    }
    /**
     * method to set phone2.
     * <p>If necessary, describe how it does and how to use it.</P>
     * @param phone2
     */
    public  void setPhone2( String phone2) {
        this.phone2 = phone2;
    }

}
