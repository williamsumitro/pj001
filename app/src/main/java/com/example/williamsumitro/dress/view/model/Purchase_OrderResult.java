package com.example.williamsumitro.dress.view.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by WilliamSumitro on 17/06/2018.
 */

public class Purchase_OrderResult {
    @SerializedName("transaction_id")
    @Expose
    private Integer transactionId;
    @SerializedName("store_id")
    @Expose
    private Integer storeId;
    @SerializedName("store_name")
    @Expose
    private String storeName;
    @SerializedName("order_number")
    @Expose
    private String orderNumber;
    @SerializedName("invoice_date")
    @Expose
    private String invoiceDate;
    @SerializedName("state")
    @Expose
    private String state;
    @SerializedName("order_status")
    @Expose
    private String orderStatus;
    @SerializedName("user_id")
    @Expose
    private Integer userId;
    @SerializedName("receiver_name")
    @Expose
    private String receiverName;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("province")
    @Expose
    private Integer province;
    @SerializedName("province_name")
    @Expose
    private String provinceName;
    @SerializedName("city")
    @Expose
    private Integer city;
    @SerializedName("city_name")
    @Expose
    private String cityName;
    @SerializedName("phone_number")
    @Expose
    private String phoneNumber;
    @SerializedName("postal_code")
    @Expose
    private String postalCode;
    @SerializedName("courier_id")
    @Expose
    private Integer courierId;
    @SerializedName("courier_name")
    @Expose
    private String courierName;
    @SerializedName("courier_service")
    @Expose
    private String courierService;
    @SerializedName("note")
    @Expose
    private Object note;
    @SerializedName("total_qty")
    @Expose
    private String totalQty;
    @SerializedName("total_weight")
    @Expose
    private String totalWeight;
    @SerializedName("subtotal_price")
    @Expose
    private String subtotalPrice;
    @SerializedName("shipping_price")
    @Expose
    private Integer shippingPrice;
    @SerializedName("total_price")
    @Expose
    private String totalPrice;
    @SerializedName("product_ordered")
    @Expose
    private ArrayList<Product> productOrdered = null;
    @SerializedName("product_accepted")
    @Expose
    private ArrayList<ProductAccepted> productAccepted = null;
    @SerializedName("product_rejected")
    @Expose
    private ArrayList<ProductRejected> productRejected = null;

    public Integer getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Integer transactionId) {
        this.transactionId = transactionId;
    }

    public Integer getStoreId() {
        return storeId;
    }

    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(String invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getProvince() {
        return province;
    }

    public void setProvince(Integer province) {
        this.province = province;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public Integer getCity() {
        return city;
    }

    public void setCity(Integer city) {
        this.city = city;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public Integer getCourierId() {
        return courierId;
    }

    public void setCourierId(Integer courierId) {
        this.courierId = courierId;
    }

    public String getCourierName() {
        return courierName;
    }

    public void setCourierName(String courierName) {
        this.courierName = courierName;
    }

    public String getCourierService() {
        return courierService;
    }

    public void setCourierService(String courierService) {
        this.courierService = courierService;
    }

    public Object getNote() {
        return note;
    }

    public void setNote(Object note) {
        this.note = note;
    }

    public String getTotalQty() {
        return totalQty;
    }

    public void setTotalQty(String totalQty) {
        this.totalQty = totalQty;
    }

    public String getTotalWeight() {
        return totalWeight;
    }

    public void setTotalWeight(String totalWeight) {
        this.totalWeight = totalWeight;
    }

    public String getSubtotalPrice() {
        return subtotalPrice;
    }

    public void setSubtotalPrice(String subtotalPrice) {
        this.subtotalPrice = subtotalPrice;
    }

    public Integer getShippingPrice() {
        return shippingPrice;
    }

    public void setShippingPrice(Integer shippingPrice) {
        this.shippingPrice = shippingPrice;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public ArrayList<Product> getProduct() {
        return productOrdered;
    }

    public void setProduct(ArrayList<Product> productOrdered) {
        this.productOrdered = productOrdered;
    }

    public ArrayList<ProductAccepted> getProductAccepted() {
        return productAccepted;
    }

    public void setProductAccepted(ArrayList<ProductAccepted> productAccepted) {
        this.productAccepted = productAccepted;
    }

    public ArrayList<ProductRejected> getProductRejected() {
        return productRejected;
    }

    public void setProductRejected(ArrayList<ProductRejected> productRejected) {
        this.productRejected = productRejected;
    }
}
