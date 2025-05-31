/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Nhat Anh
 */
public class ServiceCategory {
    private int serviceCategoryID;
    private String serviceCategoryName;

    public ServiceCategory() {
    }

    public ServiceCategory(int serviceCategoryID, String serviceCategoryName) {
        this.serviceCategoryID = serviceCategoryID;
        this.serviceCategoryName = serviceCategoryName;
    }

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

    @Override
    public String toString() {
        return "ServiceCategory{" + "serviceCategoryID=" + serviceCategoryID + ", serviceCategoryName=" + serviceCategoryName + '}';
    }
    
    
}
