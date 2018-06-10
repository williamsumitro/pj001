package com.example.williamsumitro.dress.view.model.dress_attribute;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by WilliamSumitro on 29/05/2018.
 */

public class DressAttribute {
    @SerializedName("style")
    @Expose
    private List<Style> style = null;
    @SerializedName("price")
    @Expose
    private List<Price> price = null;
    @SerializedName("size")
    @Expose
    private List<Size> size = null;
    @SerializedName("season")
    @Expose
    private List<Season> season = null;
    @SerializedName("neckline")
    @Expose
    private List<Neckline> neckline = null;
    @SerializedName("sleevelength")
    @Expose
    private List<Sleevelength> sleevelength = null;
    @SerializedName("waiseline")
    @Expose
    private List<Waiseline> waiseline = null;
    @SerializedName("material")
    @Expose
    private List<Material> material = null;
    @SerializedName("fabrictype")
    @Expose
    private List<Fabrictype> fabrictype = null;
    @SerializedName("decoration")
    @Expose
    private List<Decoration> decoration = null;
    @SerializedName("patterntype")
    @Expose
    private List<Patterntype> patterntype = null;

    public List<Style> getStyle() {
        return style;
    }

    public void setStyle(List<Style> style) {
        this.style = style;
    }

    public List<Price> getPrice() {
        return price;
    }

    public void setPrice(List<Price> price) {
        this.price = price;
    }

    public List<Size> getSize() {
        return size;
    }

    public void setSize(List<Size> size) {
        this.size = size;
    }

    public List<Season> getSeason() {
        return season;
    }

    public void setSeason(List<Season> season) {
        this.season = season;
    }

    public List<Neckline> getNeckline() {
        return neckline;
    }

    public void setNeckline(List<Neckline> neckline) {
        this.neckline = neckline;
    }

    public List<Sleevelength> getSleevelength() {
        return sleevelength;
    }

    public void setSleevelength(List<Sleevelength> sleevelength) {
        this.sleevelength = sleevelength;
    }

    public List<Waiseline> getWaiseline() {
        return waiseline;
    }

    public void setWaiseline(List<Waiseline> waiseline) {
        this.waiseline = waiseline;
    }

    public List<Material> getMaterial() {
        return material;
    }

    public void setMaterial(List<Material> material) {
        this.material = material;
    }

    public List<Fabrictype> getFabrictype() {
        return fabrictype;
    }

    public void setFabrictype(List<Fabrictype> fabrictype) {
        this.fabrictype = fabrictype;
    }

    public List<Decoration> getDecoration() {
        return decoration;
    }

    public void setDecoration(List<Decoration> decoration) {
        this.decoration = decoration;
    }

    public List<Patterntype> getPatterntype() {
        return patterntype;
    }

    public void setPatterntype(List<Patterntype> patterntype) {
        this.patterntype = patterntype;
    }
}
