/*
 * Copyright (C) 2025, Group 6.
 * ProjectCode/Short Name of Application: TravelAgentService 
 * Support Management and Provide Travel Service System 
 *
 * Record of change:
 * DATE        Version    AUTHOR            DESCRIPTION
 * 2025-07-08  1.0        Hung              First implementation
 */
package dao;

/**
 *
 * @author Dell
 */
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import model.RequestCancelFullDTO;
import model.TravelAgent;
public interface IRequestCancel {

    /**
     * Saves a new cancellation request to the database.
     *
     * @param bookID The unique identifier of the booking to be cancelled
     * @param userID The unique identifier of the user requesting the
     * cancellation
     * @param reason The reason provided for the cancellation
     * @throws SQLException if a database access error occurs
     */
    void saveCancelRequest(int bookID, int userID, String reason) throws SQLException;
    boolean createReasonCancel (int bookId,int userId, String reason) throws Exception;
    List<RequestCancelFullDTO> getAllTouristCancelRequests() throws Exception;
    
    List<RequestCancelFullDTO> searchTouristCancelRequestByEmail(String email) throws Exception;
    
    List<RequestCancelFullDTO> filterTouristCancelRequestByStatus(int status) throws Exception;
    
    List<RequestCancelFullDTO> getAllTravelAgentCancelRequests() throws Exception;
    
    List<RequestCancelFullDTO> searchTravelAgentCancelRequestByEmail(String email) throws Exception;
    
    List<RequestCancelFullDTO> filterTravelAgentCancelRequestByStatus(int status) throws Exception;
    
    RequestCancelFullDTO getRequestCancelFullById(int id) throws Exception;
    
    boolean changeStatusRequestCancel(int requestCancelId, String status) throws Exception;
    
    Map<RequestCancelFullDTO,TravelAgent> getTravelAgentRequestCancelFullById(int id) throws Exception;
   
}
