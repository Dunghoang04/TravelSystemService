/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Nhat Anh
 */
public class TourServiceDetail {
    private int detailID;
    private int tourID;
    private int serviceID;
    private String serviceName;
    private int status;

    public TourServiceDetail() {
    }

    public TourServiceDetail(int detailID, int tourID, int serviceID, String serviceName, int status) {
        this.detailID = detailID;
        this.tourID = tourID;
        this.serviceID = serviceID;
        this.serviceName = serviceName;
        this.status = status;
    }

    public TourServiceDetail(int tourID, int serviceID, String serviceName, int status) {
        this.detailID = detailID;
        this.tourID = tourID;
        this.serviceID = serviceID;
        this.serviceName = serviceName;
    }

    public int getDetailID() {
        return detailID;
    }

    public void setDetailID(int detailID) {
        this.detailID = detailID;
    }

    public int getTourID() {
        return tourID;
    }

    public void setTourID(int tourID) {
        this.tourID = tourID;
    }

    public int getServiceID() {
        return serviceID;
    }

    public void setServiceID(int serviceID) {
        this.serviceID = serviceID;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    
    @Override
    public String toString() {
        return "TourServiceDetail{" + "detailID=" + detailID + ", tourID=" + tourID + ", serviceID=" + serviceID + ", serviceName=" + serviceName + ", status=" + status + '}';
    }

    
    
    
    
}
