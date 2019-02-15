package ru.ssermakov.mystock.data.room.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "spares")
public class Spare {

    @PrimaryKey (autoGenerate = true)
    private int id;
    private String name;
    private String partNumber;
    private String state;
    private String returnCode;
    private int quantity;

    public Spare(String name, String partNumber, String state, String returnCode, int quantity) {
        this.name = name;
        this.partNumber = partNumber;
        this.state = state;
        this.returnCode = returnCode;
        this.quantity = quantity;
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

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
