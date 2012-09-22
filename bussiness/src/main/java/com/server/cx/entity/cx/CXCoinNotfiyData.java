package com.server.cx.entity.cx;

import com.server.cx.entity.basic.AuditableEntity;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "cxcoin_notfiy_data")
@ToString
public class CXCoinNotfiyData extends AuditableEntity {
    private String sellerEmail;
    private String partner;
    private Integer paymentType;
    private String buyerEmail;
    private String tradeNo;
    private String buyerId;
    private Integer quantity;
    private Double totalFee;
    private String useCoupon;
    private String isTotalFeeAdjust;
    private Double price;
    private String outTradeNo;
    private Date gmtCreate;
    private String sellerId;
    private String subject;
    private String tradeStatus;
    private Double discount;
    private Boolean status;
    
    public String getBuyerEmail() {
        return buyerEmail;
    }

    public String getBuyerId() {
        return buyerId;
    }

    public Double getDiscount() {
        return discount;
    }

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public String getIsTotalFeeAdjust() {
        return isTotalFeeAdjust;
    }

    public String getOutTradeNo() {
        return outTradeNo;
    }

    public String getPartner() {
        return partner;
    }

    public Integer getPaymentType() {
        return paymentType;
    }

    public Double getPrice() {
        return price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public String getSellerEmail() {
        return sellerEmail;
    }

    public String getSellerId() {
        return sellerId;
    }

    public Boolean getStatus() {
        return status;
    }

    public String getSubject() {
        return subject;
    }

    public Double getTotalFee() {
        return totalFee;
    }

    public String getTradeNo() {
        return tradeNo;
    }

    public String getTradeStatus() {
        return tradeStatus;
    }

    public String getUseCoupon() {
        return useCoupon;
    }

    public void setBuyerEmail(String buyerEmail) {
        this.buyerEmail = buyerEmail;
    }

    public void setBuyerId(String buyerId) {
        this.buyerId = buyerId;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public void setIsTotalFeeAdjust(String isTotalFeeAdjust) {
        this.isTotalFeeAdjust = isTotalFeeAdjust;
    }

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }

    public void setPartner(String partner) {
        this.partner = partner;
    }

    public void setPaymentType(Integer paymentType) {
        this.paymentType = paymentType;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public void setSellerEmail(String sellerEmail) {
        this.sellerEmail = sellerEmail;
    }

    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setTotalFee(Double totalFee) {
        this.totalFee = totalFee;
    }

    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
    }

    public void setTradeStatus(String tradeStatus) {
        this.tradeStatus = tradeStatus;
    }

    public void setUseCoupon(String useCoupon) {
        this.useCoupon = useCoupon;
    }
}
