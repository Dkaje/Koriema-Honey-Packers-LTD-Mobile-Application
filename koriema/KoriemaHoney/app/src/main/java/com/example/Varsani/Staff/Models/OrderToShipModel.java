package com.example.Varsani.Staff.Models;

public class OrderToShipModel {
    String orderID;
    String orderStatus;
    String clientName;
    String orderDate;
    String county;
    String town;
    String address;

    public OrderToShipModel(String orderID, String clientName, String orderStatus, String orderDate
                            , String county, String town, String address){
        this.orderID=orderID ;
        this.clientName=clientName ;
        this.orderStatus=orderStatus ;
        this.orderDate=orderDate ;
        this.county=county ;
        this.town=town ;
        this.address=address ;
    }

    public String getOrderID() {
        return orderID;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public String getClientName() {
        return clientName;
    }


    public String getOrderDate() {
        return orderDate;
    }


    public String getCounty() {
        return county;
    }

    public String getTown() {
        return town;
    }

    public String getAddress() {
        return address;
    }
}
