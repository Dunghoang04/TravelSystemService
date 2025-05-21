/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

public class ServiceCategory {
    private int serviceCategoryID;
    private String serviceCategoryName;

    // Constructor mặc định
    public ServiceCategory() {
    }

    // Constructor đầy đủ
    public ServiceCategory(int serviceCategoryID, String serviceCategoryName) {
        this.serviceCategoryID = serviceCategoryID;
        this.serviceCategoryName = serviceCategoryName;
    }

    // Getter và Setter
    public int getServiceCategoryID() {
        return serviceCategoryID;
    }

    public void setServiceCategoryID(int serviceCategoryID) {
        this.serviceCategoryID = serviceCategoryID;
    }

    public String getServiceCategoryName() {
        return serviceCategoryName;
    }

    public void setServiceCategoryName(String serviceCategoryName) {
        this.serviceCategoryName = serviceCategoryName;
    }

    // toString
    @Override
    public String toString() {
        return "ServiceCategory{" +
                "serviceCategoryID=" + serviceCategoryID +
                ", serviceCategoryName='" + serviceCategoryName + '\'' +
                '}';
    }
}
