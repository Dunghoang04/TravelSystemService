/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.sql.Date;

/**
 *
 * @author Nhat Anh
 */
public class TravelAgent extends User{
    private int travelAgentID;
    private String travelAgentName;
    private String travelAgentAddress;
    private String travelAgentGmail;
    private String hotLine;
    private String taxCode;
    private Date establishmentDate;
    private String representativeIDCard;
    private Date dateOfIssue;

    public TravelAgent() {
    }

    public TravelAgent(int travelAgentID, String travelAgentName, String travelAgentAddress, String travelAgentGmail, String hotLine, String taxCode, Date establishmentDate, String representativeIDCard, Date dateOfIssue, int userID, String gmail, int roleID, String password, String firstName, String lastName, Date dob, String gender, String address, String phone, Date createDate, Date updateDate, int status) {
        super(userID, gmail, roleID, password, firstName, lastName, dob, gender, address, phone, createDate, updateDate, status);
        this.travelAgentID = travelAgentID;
        this.travelAgentName = travelAgentName;
        this.travelAgentAddress = travelAgentAddress;
        this.travelAgentGmail = travelAgentGmail;
        this.hotLine = hotLine;
        this.taxCode = taxCode;
        this.establishmentDate = establishmentDate;
        this.representativeIDCard = representativeIDCard;
        this.dateOfIssue = dateOfIssue;
    }
    
    

    
    

    public TravelAgent(String travelAgentName, String travelAgentAddress, String travelAgentGmail, String hotLine, String taxCode, Date establishmentDate, String representativeIDCard, Date dateOfIssue, int userID, String gmail, int roleID, String password, String firstName, String lastName, Date dob, String gender, String address, String phone, Date createDate, Date updateDate, int status) {
        super(userID, gmail, roleID, password, firstName, lastName, dob, gender, address, phone, createDate, updateDate, status);
        this.travelAgentName = travelAgentName;
        this.travelAgentAddress = travelAgentAddress;
        this.travelAgentGmail = travelAgentGmail;
        this.hotLine = hotLine;
        this.taxCode = taxCode;
        this.establishmentDate = establishmentDate;
        this.representativeIDCard = representativeIDCard;
        this.dateOfIssue = dateOfIssue;
    }
    
    

    public TravelAgent(int travelAgentID, int userID, String travelAgentName, String address, String travelAgentGmail, String hotLine, String taxCode, Date establishmentDate, String representativeIDCard, Date dateOfIssue) {
        this.travelAgentID = travelAgentID;
        this.travelAgentName = travelAgentName;
        this.travelAgentAddress = travelAgentAddress;
        this.travelAgentGmail = travelAgentGmail;
        this.hotLine = hotLine;
        this.taxCode = taxCode;
        this.establishmentDate = establishmentDate;
        this.representativeIDCard = representativeIDCard;
        this.dateOfIssue = dateOfIssue;
    }

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

    public String getTravelAgentAddress() {
        return travelAgentAddress;
    }

    public void setTravelAgentAddress(String travelAgentAddress) {
        this.travelAgentAddress = travelAgentAddress;
    }

    public String getTravelAgentGmail() {
        return travelAgentGmail;
    }

    public void setTravelAgentGmail(String travelAgentGmail) {
        this.travelAgentGmail = travelAgentGmail;
    }

    public String getHotLine() {
        return hotLine;
    }

    public void setHotLine(String hotLine) {
        this.hotLine = hotLine;
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

    public String getRepresentativeIDCard() {
        return representativeIDCard;
    }

    public void setRepresentativeIDCard(String representativeIDCard) {
        this.representativeIDCard = representativeIDCard;
    }

    public Date getDateOfIssue() {
        return dateOfIssue;
    }

    public void setDateOfIssue(Date dateOfIssue) {
        this.dateOfIssue = dateOfIssue;
    }

    @Override
    public String toString() {
        return super.toString() + "TravelAgent{" + "travelAgentID=" + travelAgentID + ", travelAgentName=" + travelAgentName + ", travelAgentAddress=" + travelAgentAddress + ", travelAgentGmail=" + travelAgentGmail + ", hotLine=" + hotLine + ", taxCode=" + taxCode + ", establishmentDate=" + establishmentDate + ", representativeIDCard=" + representativeIDCard + ", dateOfIssue=" + dateOfIssue + '}';
    }

    
    
    
}
