package ru.ssermakov.mystock.data.room.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "spares")
public class Spare {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private String partNumber;
    private String state;
    private String returnCode;
    private String quantity;
    private String location;

    public Spare() {
    }

    public Spare(String name,
                 String partNumber,
                 String state,
                 String returnCode,
                 String quantity,
                 String location) {
        this.name = name;
        this.partNumber = partNumber;
        this.state = state;
        this.returnCode = returnCode;
        this.quantity = quantity;
        this.location = location;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPartNumber() {
        return partNumber;
    }

    public void setPartNumber(String partNumber) {
        this.partNumber = partNumber;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(String returnCode) {
        this.returnCode = returnCode;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
