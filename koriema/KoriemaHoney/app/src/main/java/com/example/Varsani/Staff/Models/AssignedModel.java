package com.example.Varsani.Staff.Models;

public class AssignedModel {

    String orderID;
    String clientName;
    String orderStatus;
    String address;
    String county;
    String town;

    public AssignedModel(String orderID,String orderStatus,String clientName,String address,String county,String town){
        this.orderID=orderID;
        this.orderStatus=orderStatus;
        this.clientName=clientName;
        this.address=address;
        this.county=county;
        this.town=town;
    }

    public String getOrderID() {
        return orderID;
    }

    public String getClientName() {
        return clientName;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public String getAddress() {
        return address;
    }

    public String getCounty() {
        return county;
    }

    public String getTown() {
        return town;
    }
}
