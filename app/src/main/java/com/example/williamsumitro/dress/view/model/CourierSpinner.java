package com.example.williamsumitro.dress.view.model;

/**
 * Created by WilliamSumitro on 14/06/2018.
 */

public class CourierSpinner {
    private String courier_id;
    private String courier_name;
    private String courier_service;
    private String fee;
    private String estd;

    public CourierSpinner(String courier_id, String courier_name, String courier_service, String fee, String estd){
        this.courier_id = courier_id;
        this.courier_name = courier_name;
        this.courier_service = courier_service;
        this.fee = fee;
        this.estd = estd;
    }

    public String getCourier_name() {
        return courier_name;
    }

    public void setCourier_name(String courier_name) {
        this.courier_name = courier_name;
    }

    public String getCourier_service() {
        return courier_service;
    }

    public void setCourier_service(String courier_service) {
        this.courier_service = courier_service;
    }

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }

    public String getEstd() {
        return estd;
    }

    public void setEstd(String estd) {
        this.estd = estd;
    }

    public String getCourier_id() {
        return courier_id;
    }

    public void setCourier_id(String courier_id) {
        this.courier_id = courier_id;
    }
}
