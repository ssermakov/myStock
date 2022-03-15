package ru.ssermakov.mystock.data.room.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity (tableName = "pbfSpares")
public class PbfSpare {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private String serialNumber;
    private String state;
    private String customer;
    private String quantity;
    private String comment;

    public PbfSpare() {
    }

    public PbfSpare(int id, String name, String serialNumber, String state, String customer, String quantity, String comment) {
        this.id = id;
        this.name = name;
        this.serialNumber = serialNumber;
        this.state = state;
        this.customer = customer;
        this.quantity = quantity;
        this.comment = comment;
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

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
