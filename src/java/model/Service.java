/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Nhat Anh
 */
public class Service {
    private int serviceID;
    private int serviceCategoryID;
    private String name;
    

    public Service() {
    }

    public Service(int serviceID, int serviceCategoryID, String name) {
        this.serviceID = serviceID;
        this.serviceCategoryID = serviceCategoryID;
        this.name = name;
    }

    public int getServiceID() {
        return serviceID;
    }

    public void setServiceID(int serviceID) {
        this.serviceID = serviceID;
    }

    public int getServiceCategoryID() {
        return serviceCategoryID;
    }

    public void setServiceCategoryID(int serviceCategoryID) {
        this.serviceCategoryID = serviceCategoryID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Service{" + "serviceID=" + serviceID + ", serviceCategoryID=" + serviceCategoryID + ", name=" + name + '}';
    }

    
}
