package ru.ssermakov.mystock.data.room.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "pbfSpares")
public class PbfSpare {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(defaultValue = "")
    private String name;
    @ColumnInfo(defaultValue = "")
    private String serialNumber;
    @ColumnInfo(defaultValue = "")
    private String state;
    @ColumnInfo(defaultValue = "")
    private String customer;
    @ColumnInfo(defaultValue = "")
    private String connectionType;
    @ColumnInfo(defaultValue = "")
    private String jiraWorkOrder;
    @ColumnInfo(defaultValue = "")
    private String quantity;
    @ColumnInfo(defaultValue = "")
    private String comment;

    public PbfSpare() {
    }

    public PbfSpare(String name,
                    String serialNumber,
                    String state,
                    String customer,
                    String connectionType,
                    String jiraWorkOrder,
                    String quantity,
                    String comment) {
        this.name = name;
        this.serialNumber = serialNumber;
        this.state = state;
        this.customer = customer;
        this.connectionType = connectionType;// sim, ethernet, wi-fi
        this.jiraWorkOrder = jiraWorkOrder;
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

    public String getConnectionType() {
        return connectionType;
    }

    public void setConnectionType(String connectionType) {
        this.connectionType = connectionType;
    }

    public String getJiraWorkOrder() {
        return jiraWorkOrder;
    }

    public void setJiraWorkOrder(String jiraWorkOrder) {
        this.jiraWorkOrder = jiraWorkOrder;
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
