/**
 * Represents a service entity in the TravelAgentService application.<br>
 * This class models a service with an ID, category ID, and name, used for managing services in the system.<br>
 * 
 * Project: TravelAgentService
 * Version: 1.0
 * Date: 2025-06-07
 * Bugs: No known issues.
 * 
 * Record of Change:
 * DATE            Version             AUTHOR                         DESCRIPTION
 * 2025-06-07      1.0                 Hoang Tuan Dung                First implementation
 * 2025-06-08      1.1                 Hoang Tuan Dung                Added comprehensive Javadoc and inline comments
 * 
 * @author Nhat Anh
 */
package model;

/**
 * Represents a service entity with properties for service ID, category ID, and name.
 */
public class Service {
    private int serviceID;          // Unique identifier for the service
    private int serviceCategoryID;  // Identifier for the service category
    private String name;            // Name of the service
    private int travelAgentId;      // ID of the travel agent (must be positive)
    private int status;            // Status of the service (0 or 1)

    /**
     * Default constructor for the Service class.
     * Initializes a new instance with default values.
     */
    public Service() {
    }

    /**
     * Parameterized constructor for the Service class.
     * Initializes a new instance with the specified service ID, category ID, name, travel agent ID, and status.
     *
     * @param serviceID the unique identifier for the service (must be positive)
     * @param serviceCategoryID the identifier for the service category (must be positive)
     * @param name the name of the service (max 255 characters, not null)
     * @param travelAgentId the ID of the travel agent (must be positive)
     * @param status the status of the service (must be 0 or 1)
     * @throws IllegalArgumentException if any parameter is invalid
     */
    public Service(int serviceID, int serviceCategoryID, String name, int travelAgentId, int status) {
        this.serviceID = serviceID;
        this.serviceCategoryID = serviceCategoryID;
        this.name = name;
        this.travelAgentId=travelAgentId;
        this.status=status;
    }

    /**
     * Gets the service ID.
     *
     * @return the service ID
     */
    public int getServiceID() {
        return serviceID;
    }

    /**
     * Sets the service ID.
     *
     * @param serviceID the service ID to set
     */
    public void setServiceID(int serviceID) {
        this.serviceID = serviceID;
    }

    /**
     * Gets the service category ID.
     *
     * @return the service category ID
     */
    public int getServiceCategoryID() {
        return serviceCategoryID;
    }

    /**
     * Sets the service category ID.
     *
     * @param serviceCategoryID the service category ID to set
     */
    public void setServiceCategoryID(int serviceCategoryID) {
        this.serviceCategoryID = serviceCategoryID;
    }

    /**
     * Gets the name of the service.
     *
     * @return the service name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the service.
     *
     * @param name the service name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the travel agent ID.
     *
     * @return the travel agent ID
     */
    public int getTravelAgentId() {
        return travelAgentId;
    }

    /**
     * Sets the travel agent ID.
     *
     * @param travelAgentId the travel agent ID to set (must be positive)
     * @throws IllegalArgumentException if travelAgentId is less than or equal to 0
     */
    public void setTravelAgentId(int travelAgentId) {
        this.travelAgentId = travelAgentId;
    }

    /**
     * Gets the status of the service.
     *
     * @return the status (0 or 1)
     */
    public int getStatus() {
        return status;
    }

    /**
     * Sets the status of the service.
     *
     * @param status the status to set (must be 0 or 1)
     * @throws IllegalArgumentException if status is not 0 or 1
     */
    public void setStatus(int status) {
        this.status = status;
    }

    /**
     * Returns a string representation of the Service object.
     *
     * @return a string containing all service properties
     */
    @Override
    public String toString() {
        return "Service{" + "serviceID=" + serviceID + ", serviceCategoryID=" + serviceCategoryID + ", name=" + name + ", travelAgentId=" + travelAgentId + ", status=" + status + '}';
    }
    
    

    /**
     * Returns a string representation of the Service object.
     *
     * @return a string containing the service ID, category ID, and name
     */
    
}
