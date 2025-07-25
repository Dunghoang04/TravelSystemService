/*
 * Copyright (C) 2025, Group 6.
 * ProjectCode/Short Name of Application: TravelAgentService 
 * Support Management and Provide Travel Service System 
 *
 * Record of change:
 * DATE        Version    AUTHOR            DESCRIPTION
 * 2025-06-07  1.0        Hung              First implementation
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
