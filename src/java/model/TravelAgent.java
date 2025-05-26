/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.sql.Date;

public class TravelAgent {

    private int travelAgentID;
    private String travelAgentName;
    private String travelAgentEmail;
    private String hotline;
    private String address;
    private Date establishmentDate;
    private String taxCode;
    private String reFirstName;
    private String reLastName;
    private String rePhone;
    private String reAddress;
    private Date reDob;
    private String idCard;

    // Constructor mặc định
    public TravelAgent() {
    }

    // Constructor đầy đủ

    public TravelAgent(int travelAgentID, String travelAgentName, String travelAgentEmail, String hotline, String address, Date establishmentDate, String taxCode, String reFirstName, String reLastName, String rePhone, String reAddress, Date reDob, String idCard) {
        this.travelAgentID = travelAgentID;
        this.travelAgentName = travelAgentName;
        this.travelAgentEmail = travelAgentEmail;
        this.hotline = hotline;
        this.address = address;
        this.establishmentDate = establishmentDate;
        this.taxCode = taxCode;
        this.reFirstName = reFirstName;
        this.reLastName = reLastName;
        this.rePhone = rePhone;
        this.reAddress = reAddress;
        this.reDob = reDob;
        this.idCard = idCard;
    }
    
    
    

    public String getReFirstName() {
        return reFirstName;
    }

    public void setReFirstName(String reFirstName) {
        this.reFirstName = reFirstName;
    }

    public String getReLastName() {
        return reLastName;
    }

    public void setReLastName(String reLastName) {
        this.reLastName = reLastName;
    }

    public String getRePhone() {
        return rePhone;
    }

    public void setRePhone(String rePhone) {
        this.rePhone = rePhone;
    }

    public String getReAddress() {
        return reAddress;
    }

    public void setReAddress(String reAddress) {
        this.reAddress = reAddress;
    }

    public Date getReDob() {
        return reDob;
    }

    public void setReDob(Date reDob) {
        this.reDob = reDob;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }
    
    
    

    // Getter và Setter
    public int getTravelAgentID() {
        return travelAgentID;
    }

    public void setTravelAgentID(int travelAgentID) {
        this.travelAgentID = travelAgentID;
    }

    public String getTravelAgentName() {
        return travelAgentName;
    }

    public void setTravelAgentName(String travelAgentName) {
        this.travelAgentName = travelAgentName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTravelAgentEmail() {
        return travelAgentEmail;
    }

    public void setTravelAgentEmail(String travelAgentEmail) {
        this.travelAgentEmail = travelAgentEmail;
    }

    public String getHotline() {
        return hotline;
    }

    public void setHotline(String hotline) {
        this.hotline = hotline;
    }

    public String getTaxCode() {
        return taxCode;
    }

    public void setTaxCode(String taxCode) {
        this.taxCode = taxCode;
    }

    public Date getEstablishmentDate() {
        return establishmentDate;
    }

    public void setEstablishmentDate(Date establishmentDate) {
        this.establishmentDate = establishmentDate;
    }
    @Override
    public String toString() {
        return "TravelAgent{" + "travelAgentID=" + travelAgentID + ", travelAgentName=" + travelAgentName + ", travelAgentEmail=" + travelAgentEmail + ", hotline=" + hotline + ", address=" + address + ", establishmentDate=" + establishmentDate + ", taxCode=" + taxCode + ", reFirstName=" + reFirstName + ", reLastName=" + reLastName + ", rePhone=" + rePhone + ", reAddress=" + reAddress + ", reDob=" + reDob + ", idCard=" + idCard + '}';
    }

}
