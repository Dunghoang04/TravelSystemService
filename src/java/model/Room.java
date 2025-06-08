/*
 * Click https://netbeans.org/project/licenses/ to change this license
 * Click https://netbeans.org/features/ to edit this template
 */
/*
 * Copyright (C) 2025, Group 6.
 * ProjectCode/Short Name of Application: TravelSystemService 
 * Support Management and Provide Travel Service System 
 *
 * Record of change:
 * DATE        Version    AUTHOR            DESCRIPTION
 * 2025-06-07  1.0        Nguyễn Văn Vang   First implementation
 */
package model;

/**
 * Represents a room entity in the travel service system.<br>
 * This class is used to manage room details such as room ID, accommodation ID, room types, and pricing.<br>
 * <p>
 * Bugs: None known at this time.</p>
 *
 * @author Nguyễn Văn Vang
 */
public class Room {
    private int roomID; // Unique identifier for the room
    private int accommodationID; // Identifier of the associated accommodation
    private String roomTypes; // Type of the room (e.g., single, double, suite)
    private int numberOfRooms; // Number of rooms available of this type
    private float priceOfRoom; // Price of the room

    /**
     * Default constructor for Room.<br>
     * Creates an empty Room object with default values.
     */
    /*
     * Initializes a new Room instance with default values.
     * All fields are set to null or 0 depending on their type.
     */
    public Room() {
    }

    /**
     * Constructs a Room with all specified attributes.<br>
     *
     * @param roomID The unique identifier for the room
     * @param accommodationID The identifier of the associated accommodation
     * @param roomTypes The type of the room (e.g., single, double, suite)
     * @param numberOfRooms The number of rooms available of this type
     * @param priceOfRoom The price of the room
     */
    /*
     * Creates a new Room instance with all provided attributes.
     * Assigns values to all fields directly.
     */
    public Room(int roomID, int accommodationID, String roomTypes, int numberOfRooms, float priceOfRoom) {
        this.roomID = roomID;
        this.accommodationID = accommodationID;
        this.roomTypes = roomTypes;
        this.numberOfRooms = numberOfRooms;
        this.priceOfRoom = priceOfRoom;
    }

    /**
     * Gets the room's unique identifier.<br>
     *
     * @return The room ID as an integer
     */
    /*
     * Retrieves the unique identifier of the room.
     * Returns the current value of roomID.
     */
    public int getRoomID() {
        return roomID;
    }

    /**
     * Sets the room's unique identifier.<br>
     *
     * @param roomID The room ID to set
     */
    /*
     * Updates the unique identifier of the room.
     * Assigns the provided value to roomID.
     */
    public void setRoomID(int roomID) {
        this.roomID = roomID;
    }

    /**
     * Gets the accommodation's identifier.<br>
     *
     * @return The accommodation ID as an integer
     */
    /*
     * Retrieves the identifier of the associated accommodation.
     * Returns the current value of accommodationID.
     */
    public int getAccommodationID() {
        return accommodationID;
    }

    /**
     * Sets the accommodation's identifier.<br>
     *
     * @param accommodationID The accommodation ID to set
     */
    /*
     * Updates the identifier of the associated accommodation.
     * Assigns the provided value to accommodationID.
     */
    public void setAccommodationID(int accommodationID) {
        this.accommodationID = accommodationID;
    }

    /**
     * Gets the room's type.<br>
     *
     * @return The room type as a String
     */
    /*
     * Retrieves the type of the room.
     * Returns the current value of roomTypes.
     */
    public String getRoomTypes() {
        return roomTypes;
    }

    /**
     * Sets the room's type.<br>
     *
     * @param roomTypes The room type to set
     */
    /*
     * Updates the type of the room.
     * Assigns the provided value to roomTypes.
     */
    public void setRoomTypes(String roomTypes) {
        this.roomTypes = roomTypes;
    }

    /**
     * Gets the number of rooms available.<br>
     *
     * @return The number of rooms as an integer
     */
    /*
     * Retrieves the number of rooms available of this type.
     * Returns the current value of numberOfRooms.
     */
    public int getNumberOfRooms() {
        return numberOfRooms;
    }

    /**
     * Sets the number of rooms available.<br>
     *
     * @param numberOfRooms The number of rooms to set
     */
    /*
     * Updates the number of rooms available of this type.
     * Assigns the provided value to numberOfRooms.
     */
    public void setNumberOfRooms(int numberOfRooms) {
        this.numberOfRooms = numberOfRooms;
    }

    /**
     * Gets the price of the room.<br>
     *
     * @return The price as a float
     */
    /*
     * Retrieves the price of the room.
     * Returns the current value of priceOfRoom.
     */
    public float getPriceOfRoom() {
        return priceOfRoom;
    }

    /**
     * Sets the price of the room.<br>
     *
     * @param priceOfRoom The price to set
     */
    /*
     * Updates the price of the room.
     * Assigns the provided value to priceOfRoom.
     */
    public void setPriceOfRoom(float priceOfRoom) {
        this.priceOfRoom = priceOfRoom;
    }

    /**
     * Returns a string representation of the Room object.<br>
     *
     * @return A string containing all room attributes
     */
    /*
     * Generates a string representation of the Room object.
     * Includes all attributes in the format "Room{...}".
     */
    @Override
    public String toString() {
        return "Room{" + "roomID=" + roomID + ", accommodationID=" + accommodationID + ", roomTypes=" + roomTypes 
                + ", numberOfRooms=" + numberOfRooms + ", priceOfRoom=" + priceOfRoom + '}';
    }
}