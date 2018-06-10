package com.example.williamsumitro.dress.view.presenter.api;

import com.example.williamsumitro.dress.view.model.Price;
import com.example.williamsumitro.dress.view.model.dress_attribute.Size;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by WilliamSumitro on 30/05/2018.
 */

public class Body_addproduct {
    @SerializedName("token")
    @Expose
    private String token;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("min_order")
    @Expose
    private String min_order;

    @SerializedName("weight")
    @Expose
    private String weight;

    @SerializedName("description")
    @Expose
    private String description;

    @SerializedName("style_id")
    @Expose
    private String style_id;

    @SerializedName("season_id")
    @Expose
    private String season_id;

    @SerializedName("neckline_id")
    @Expose
    private String neckline_id;

    @SerializedName("sleevelength_id")
    @Expose
    private String sleevelength_id;

    @SerializedName("waiseline_id")
    @Expose
    private String waiseline_id;

    @SerializedName("material_id")
    @Expose
    private String material_id;

    @SerializedName("fabrictype_id")
    @Expose
    private String fabrictype_id;

    @SerializedName("decoration_id")
    @Expose
    private String decoration_id;

    @SerializedName("patterntype_id")
    @Expose
    private String patterntype_id;

    @SerializedName("size[]")
    @Expose
    private ArrayList<String> size;

    @SerializedName("price[]")
    @Expose
    private List<Price> price;

}
