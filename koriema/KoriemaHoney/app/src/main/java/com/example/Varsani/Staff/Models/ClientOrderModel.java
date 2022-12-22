package com.example.Varsani.Staff.Models;

public class ClientOrderModel {
    String orderID;
    String orderStatus;
    String clientName;
    String mpesaCode;
    String orderCost;
    String orderDate;
    String shippingCost;
    String itemCost;
    String county;
    String town;
    String address;

    public ClientOrderModel(String orderID, String clientName, String mpesaCode,
                            String orderCost, String orderStatus, String orderDate,String shippingCost,String itemCost
                            ,String county,String town,String address){
        this.orderID=orderID ;
        this.clientName=clientName ;
        this.mpesaCode=mpesaCode ;
        this.orderCost =orderCost ;
        this.orderStatus=orderStatus ;
        this.orderDate=orderDate ;
        this.shippingCost=shippingCost ;
        this.itemCost=itemCost ;
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

    public String getMpesaCode() {
        return mpesaCode;
    }

    public String getOrderCost() {
        return orderCost;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public String getShippingCost() {
        return shippingCost;
    }

    public String getItemCost() {
        return itemCost;
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
