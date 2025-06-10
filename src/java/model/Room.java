/*
 * Copyright (C) 2025, Group 6.
 * ProjectCode/Short Name of Application: TravelAgentService 
 * Support Management and Provide Travel Service System 
 *
 * Record of change:
 * DATE        Version    AUTHOR            DESCRIPTION
 *  2025-06-07  1.0       NguyenVanVang     First implementation
 */
package model;

/**
 * Represents a room in the TravelAgentService system.
 * Contains details such as room ID, room type, number of rooms, price, and associated accommodation.
 *
 * @author NhatAnh
 */
public class Room {
    /** The unique identifier for the room (accommodation ID). */
    private int roomID;

    /** The type of the room (e.g., Phòng đơn, Phòng đôi). */
    private String roomTypes;

    /** The number of available rooms of this type. */
    private int numberOfRooms;

    /** The price per room. */
    private float priceOfRoom;

    /** The associated accommodation for this room. */
    private Accommodation accommodation;

    /**
     * Default constructor for Room.
     * Initializes an empty Room object.
     */
    public Room() {
    }

    /**
     * Parameterized constructor for Room.
     *
     * @param roomID        the unique identifier for the room
     * @param roomTypes     the type of the room
     * @param numberOfRooms the number of available rooms
     * @param priceOfRoom   the price per room
     */
    public Room(int roomID, String roomTypes, int numberOfRooms, float priceOfRoom) {
        // Initialize room attributes
        this.roomID = roomID;
        this.roomTypes = roomTypes;
        this.numberOfRooms = numberOfRooms;
        this.priceOfRoom = priceOfRoom;
    }

    /**
     * Gets the associated accommodation.
     *
     * @return the Accommodation object associated with this room
     */
    public Accommodation getAccommodation() {
        return accommodation;
    }

    /**
     * Sets the associated accommodation.
     *
     * @param accommodation the Accommodation object to associate with this room
     */
    public void setAccommodation(Accommodation accommodation) {
        this.accommodation = accommodation;
    }

    /**
     * Gets the room ID.
     *
     * @return the room ID
     */
    public int getRoomID() {
        return roomID;
    }

    /**
     * Sets the room ID.
     *
     * @param roomID the room ID to set
     */
    public void setRoomID(int roomID) {
        this.roomID = roomID;
    }

    /**
     * Gets the room type.
     *
     * @return the room type
     */
    public String getRoomTypes() {
        return roomTypes;
    }

    /**
     * Sets the room type.
     *
     * @param roomTypes the room type to set
     */
    public void setRoomTypes(String roomTypes) {
        this.roomTypes = roomTypes;
    }

    /**
     * Gets the number of available rooms.
     *
     * @return the number of rooms
     */
    public int getNumberOfRooms() {
        return numberOfRooms;
    }

    /**
     * Sets the number of available rooms.
     *
     * @param numberOfRooms the number of rooms to set
     */
    public void setNumberOfRooms(int numberOfRooms) {
        this.numberOfRooms = numberOfRooms;
    }

    /**
     * Gets the price per room.
     *
     * @return the price of the room
     */
    public double getPriceOfRoom() {
        return priceOfRoom;
    }

    /**
     * Sets the price per room.
     *
     * @param priceOfRoom the price of the room to set
     */
    public void setPriceOfRoom(float priceOfRoom) {
        this.priceOfRoom = priceOfRoom;
    }

    /**
     * Returns a string representation of the Room object.
     *
     * @return a string containing room details
     */
    @Override
    public String toString() {
        return "Room{" + "roomID=" + roomID + ", roomTypes=" + roomTypes + ", numberOfRooms=" + numberOfRooms + ", priceOfRoom=" + priceOfRoom + '}';
    }
}