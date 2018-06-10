package com.example.williamsumitro.dress.view.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by WilliamSumitro on 01/06/2018.
 */

public class StoreResponse {
    @SerializedName("have_store")
    @Expose
    private Boolean haveStore;
    @SerializedName("store")
    @Expose
    private StoreDetails store;

    public Boolean getHaveStore() {
        return haveStore;
    }

    public void setHaveStore(Boolean haveStore) {
        this.haveStore = haveStore;
    }

    public StoreDetails getStore() {
        return store;
    }

    public void setStore(StoreDetails store) {
        this.store = store;
    }
}
