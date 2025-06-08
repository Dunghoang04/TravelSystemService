/**
 * Represents a service category entity in the TravelAgentService application.<br>
 * This class models a service category with an ID and name, used for categorizing services in the system.<br>
 * 
 * Project: TravelAgentService
 * Version: 1.0
 * Date: 2025-06-07
 * Bugs: No known issues.
 * 
 * Record of Change:
 * DATE            Version             AUTHOR                        DESCRIPTION
 * 2025-06-07      1.0                 Hoang Tuan Dung               First implementation
 * 2025-06-08      1.1                 Hoang Tuan Dung               Added comprehensive Javadoc and inline comments
 * 
 * @author Nhat Anh
 */
package model;

/**
 * Represents a service category entity with properties for category ID and name.
 */
public class ServiceCategory {
    private int serviceCategoryID;      // Unique identifier for the service category
    private String serviceCategoryName; // Name of the service category

    /**
     * Default constructor for the ServiceCategory class.
     * Initializes a new instance with default values.
     */
    public ServiceCategory() {
    }

    /**
     * Parameterized constructor for the ServiceCategory class.
     * Initializes a new instance with the specified category ID and name.
     *
     * @param serviceCategoryID the unique identifier for the service category
     * @param serviceCategoryName the name of the service category
     */
    public ServiceCategory(int serviceCategoryID, String serviceCategoryName) {
        this.serviceCategoryID = serviceCategoryID;
        this.serviceCategoryName = serviceCategoryName;
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
     * Gets the name of the service category.
     *
     * @return the service category name
     */
    public String getServiceCategoryName() {
        return serviceCategoryName;
    }

    /**
     * Sets the name of the service category.
     *
     * @param serviceCategoryName the service category name to set
     */
    public void setServiceCategoryName(String serviceCategoryName) {
        this.serviceCategoryName = serviceCategoryName;
    }

    /**
     * Returns a string representation of the ServiceCategory object.
     *
     * @return a string containing the category ID and name
     */
    @Override
    public String toString() {
        return "ServiceCategory{" + "serviceCategoryID=" + serviceCategoryID + ", serviceCategoryName=" + serviceCategoryName + '}';
    }
}