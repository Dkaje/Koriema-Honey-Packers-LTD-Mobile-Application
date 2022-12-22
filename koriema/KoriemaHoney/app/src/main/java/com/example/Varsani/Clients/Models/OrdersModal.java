package com.example.Varsani.Clients.Models;

public class OrdersModal {
    private String orderID;
    private String orderCost;
    private String mpesaCode;
    private String orderDate;
    private String orderstatus;
    private String shippingCost;
    private String itemCost;

    public OrdersModal(String orderID,String orderCost,String mpesaCode,
                       String orderDate,String orderstatus,String shippingCost,String itemCost){
         this.orderID=orderID;
         this.orderCost=orderCost;
         this.mpesaCode=mpesaCode;
         this.orderDate=orderDate;
         this.orderstatus=orderstatus;
         this.shippingCost=shippingCost;
         this.itemCost=itemCost;
    }

    public String getOrderID() {
        return orderID;
    }

    public String getOrderCost() {
        return orderCost;
    }

    public String getMpesaCode() {
        return mpesaCode;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public String getOrderstatus() {
        return orderstatus;
    }

    public String getShippingCost() {
        return shippingCost;
    }

    public String getItemCost() {
        return itemCost;
    }
}
