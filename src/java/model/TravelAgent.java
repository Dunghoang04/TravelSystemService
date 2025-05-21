/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;
import java.sql.Date;

public class TravelAgent {
    private int travelAgentID;
    private String travelAgentName;
    private String address;
    private String travelAgentGmail;
    private String hotline;
    private String taxCode;
    private Date establishmentDate;
    private String representativeFirstName;
    private String representativeLastName;
    private String representativePhone;

    // Constructor mặc định
    public TravelAgent() {
    }

    // Constructor đầy đủ
    public TravelAgent(int travelAgentID, String travelAgentName, String address, String travelAgentGmail, String hotline, String taxCode, Date establishmentDate, String representativeFirstName, String representativeLastName, String representativePhone) {
        this.travelAgentID = travelAgentID;
        this.travelAgentName = travelAgentName;
        this.address = address;
        this.travelAgentGmail = travelAgentGmail;
        this.hotline = hotline;
        this.taxCode = taxCode;
        this.establishmentDate = establishmentDate;
        this.representativeFirstName = representativeFirstName;
        this.representativeLastName = representativeLastName;
        this.representativePhone = representativePhone;
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

    public String getTravelAgentGmail() {
        return travelAgentGmail;
    }

    public void setTravelAgentGmail(String travelAgentGmail) {
        this.travelAgentGmail = travelAgentGmail;
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

    public String getRepresentativeFirstName() {
        return representativeFirstName;
    }

    public void setRepresentativeFirstName(String representativeFirstName) {
        this.representativeFirstName = representativeFirstName;
    }

    public String getRepresentativeLastName() {
        return representativeLastName;
    }

    public void setRepresentativeLastName(String representativeLastName) {
        this.representativeLastName = representativeLastName;
    }

    public String getRepresentativePhone() {
        return representativePhone;
    }

    public void setRepresentativePhone(String representativePhone) {
        this.representativePhone = representativePhone;
    }

    // toString
    @Override
    public String toString() {
        return "TravelAgent{" +
                "travelAgentID=" + travelAgentID +
                ", travelAgentName='" + travelAgentName + '\'' +
                ", address='" + address + '\'' +
                ", travelAgentGmail='" + travelAgentGmail + '\'' +
                ", hotline='" + hotline + '\'' +
                ", taxCode='" + taxCode + '\'' +
                ", establishmentDate=" + establishmentDate +
                ", representativeFirstName='" + representativeFirstName + '\'' +
                ", representativeLastName='" + representativeLastName + '\'' +
                ", representativePhone='" + representativePhone + '\'' +
                '}';
    }
}
