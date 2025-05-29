/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Nhat Anh
 */
public class CancelReason {
    private int cancelReasonID;
    private String reason;
    private int status;

    public CancelReason() {
    }

    public CancelReason(int cancelReasonID, String reason, int status) {
        this.cancelReasonID = cancelReasonID;
        this.reason = reason;
        this.status = status;
    }

    public int getCancelReasonID() {
        return cancelReasonID;
    }

    public void setCancelReasonID(int cancelReasonID) {
        this.cancelReasonID = cancelReasonID;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
    

    @Override
    public String toString() {
        return "CancelReason{" + "cancelReasonID=" + cancelReasonID + ", reason=" + reason + ", status=" + status + '}';
    }
    
    
    
}
