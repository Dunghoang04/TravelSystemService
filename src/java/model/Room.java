/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Nhat Anh
 */
public class Room {
    private int roomID;
    private String roomTypes;
    private int numberOfRooms;
    private double priceOfRoom;

    public Room() {
    }

    public Room(int roomID, String roomTypes, int numberOfRooms,  double priceOfRoom) {
        this.roomID = roomID;
        this.roomTypes = roomTypes;
        this.numberOfRooms = numberOfRooms;
        this.priceOfRoom = priceOfRoom;
    }

    public int getRoomID() {
        return roomID;
    }

    public void setRoomID(int roomID) {
        this.roomID = roomID;
    }

    public String getRoomTypes() {
        return roomTypes;
    }

    public void setRoomTypes(String roomTypes) {
        this.roomTypes = roomTypes;
    }

    public int getNumberOfRooms() {
        return numberOfRooms;
    }

    public void setNumberOfRooms(int numberOfRooms) {
        this.numberOfRooms = numberOfRooms;
    }



    public double getPriceOfRoom() {
        return priceOfRoom;
    }

    public void setPriceOfRoom(double priceOfRoom) {
        this.priceOfRoom = priceOfRoom;
    }

    @Override
    public String toString() {
        return "TypeRoom{" + "roomID=" + roomID + ", roomTypes=" + roomTypes + ", numberOfRooms=" + numberOfRooms + ", priceOfRoom=" + priceOfRoom + '}';
    }
    
    
}
