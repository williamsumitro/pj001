package com.example.williamsumitro.dress.view.view.filter;

import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.crystal.crystalrangeseekbar.interfaces.OnRangeSeekbarChangeListener;
import com.crystal.crystalrangeseekbar.interfaces.OnSeekbarChangeListener;
import com.crystal.crystalrangeseekbar.widgets.CrystalRangeSeekbar;
import com.crystal.crystalrangeseekbar.widgets.CrystalSeekbar;
import com.example.williamsumitro.dress.R;
import com.example.williamsumitro.dress.view.presenter.InputFilterMinMax;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Filter extends AppCompatActivity {
    @BindView(R.id.filter_toolbar) Toolbar toolbar;
    @BindView(R.id.filter_crystalseekbar) CrystalSeekbar seekbar;
    @BindView(R.id.filter_tvminordervalue) TextView minordervalue;
    @BindView(R.id.filter_tvmaxordervalue) TextView maxordervalue;
    @BindView(R.id.filter_etminorder) EditText minorder;
    @BindView(R.id.filter_tvminpricerangevalue) TextView minpricerangevalue;
    @BindView(R.id.filter_tvmaxpricerangevalue) TextView maxpricerangevalue;
    @BindView(R.id.filter_rangeseekbar) CrystalRangeSeekbar rangeseekbar;
    @BindView(R.id.filter_etminpricerange) EditText minpricerange;
    @BindView(R.id.filter_etmaxpricerange) EditText maxpricerange;
    @BindView(R.id.filter_tvmaxpricerange) TextView maxprice;
    @BindView(R.id.filter_tvminpricerange) TextView minprice;
    @BindView(R.id.filter_rvcity) RecyclerView rvcity;
    @BindView(R.id.filter_tvcityresult) TextView cityresult;
    @BindView(R.id.filter_lncitydetail) LinearLayout citydetail;
    @BindView(R.id.filter_imgcitycaret) ImageView citycaret;
    @BindView(R.id.filter_btnclearallcity) Button city_clearall;
    @BindView(R.id.filter_btncheckallcity) Button city_checkall;
    @BindView(R.id.filter_btnapplycity) Button city_apply;
    @BindView(R.id.filter_lncity) LinearLayout city;
    @BindView(R.id.filter_rvshipment) RecyclerView rvshipment;
    @BindView(R.id.filter_tvshipmentresult) TextView shipmentresult;
    @BindView(R.id.filter_lnshipmentdetail) LinearLayout shipmentdetail;
    @BindView(R.id.filter_imgshipmentcaret) ImageView shipmentcaret;
    @BindView(R.id.filter_btnclearallshipment) Button shipment_clearall;
    @BindView(R.id.filter_btncheckallshipment) Button shipment_checkall;
    @BindView(R.id.filter_btnapplyshipment) Button shipment_apply;
    @BindView(R.id.filter_lnshipment) LinearLayout shipment;
    @BindView(R.id.filter_rvcolor) RecyclerView rvcolor;
    @BindView(R.id.filter_tvcolorresult) TextView colorresult;
    @BindView(R.id.filter_lncolordetail) LinearLayout colordetail;
    @BindView(R.id.filter_imgcolorcaret) ImageView colorcaret;
    @BindView(R.id.filter_btnclearallcolor) Button color_clearall;
    @BindView(R.id.filter_btncheckallcolor) Button color_checkall;
    @BindView(R.id.filter_btnapplycolor) Button color_apply;
    @BindView(R.id.filter_lncolor) LinearLayout color;
    @BindView(R.id.filter_rvstyle) RecyclerView rvstyle;
    @BindView(R.id.filter_tvstyleresult) TextView styleresult;
    @BindView(R.id.filter_lnstyledetail) LinearLayout styledetail;
    @BindView(R.id.filter_imgstylecaret) ImageView stylecaret;
    @BindView(R.id.filter_btnclearallstyle) Button style_clearall;
    @BindView(R.id.filter_btncheckallstyle) Button style_checkall;
    @BindView(R.id.filter_btnapplystyle) Button style_apply;
    @BindView(R.id.filter_lnstyle) LinearLayout style;
    @BindView(R.id.filter_rvrating) RecyclerView rvrating;
    @BindView(R.id.filter_tvratingresult) TextView ratingresult;
    @BindView(R.id.filter_lnratingdetail) LinearLayout ratingdetail;
    @BindView(R.id.filter_imgratingcaret) ImageView ratingcaret;
    @BindView(R.id.filter_btnclearallrating) Button rating_clearall;
    @BindView(R.id.filter_btncheckallrating) Button rating_checkall;
    @BindView(R.id.filter_btnapplyrating) Button rating_apply;
    @BindView(R.id.filter_lnrating) LinearLayout rating;
    @BindView(R.id.filter_rvsize) RecyclerView rvsize;
    @BindView(R.id.filter_tvsizeresult) TextView sizeresult;
    @BindView(R.id.filter_lnsizedetail) LinearLayout sizedetail;
    @BindView(R.id.filter_imgsizecaret) ImageView sizecaret;
    @BindView(R.id.filter_btnclearallsize) Button size_clearall;
    @BindView(R.id.filter_btncheckallsize) Button size_checkall;
    @BindView(R.id.filter_btnapplysize) Button size_apply;
    @BindView(R.id.filter_lnsize) LinearLayout size;
    @BindView(R.id.filter_rvseason) RecyclerView rvseason;
    @BindView(R.id.filter_tvseasonresult) TextView seasonresult;
    @BindView(R.id.filter_lnseasondetail) LinearLayout seasondetail;
    @BindView(R.id.filter_imgseasoncaret) ImageView seasoncaret;
    @BindView(R.id.filter_btnclearallseason) Button season_clearall;
    @BindView(R.id.filter_btncheckallseason) Button season_checkall;
    @BindView(R.id.filter_btnapplyseason) Button season_apply;
    @BindView(R.id.filter_lnseason) LinearLayout season;
    @BindView(R.id.filter_rvneckline) RecyclerView rvneckline;
    @BindView(R.id.filter_tvnecklineresult) TextView necklineresult;
    @BindView(R.id.filter_lnnecklinedetail) LinearLayout necklinedetail;
    @BindView(R.id.filter_imgnecklinecaret) ImageView necklinecaret;
    @BindView(R.id.filter_btnclearallneckline) Button neckline_clearall;
    @BindView(R.id.filter_btncheckallneckline) Button neckline_checkall;
    @BindView(R.id.filter_btnapplyneckline) Button neckline_apply;
    @BindView(R.id.filter_lnneckline) LinearLayout neckline;
    @BindView(R.id.filter_rvsleevelength) RecyclerView rvsleevelength;
    @BindView(R.id.filter_tvsleevelengthresult) TextView sleevelengthresult;
    @BindView(R.id.filter_lnsleevelengthdetail) LinearLayout sleevelengthdetail;
    @BindView(R.id.filter_imgsleevelengthcaret) ImageView sleevelengthcaret;
    @BindView(R.id.filter_btnclearallsleevelength) Button sleevelength_clearall;
    @BindView(R.id.filter_btncheckallsleevelength) Button sleevelength_checkall;
    @BindView(R.id.filter_btnapplysleevelength) Button sleevelength_apply;
    @BindView(R.id.filter_lnsleevelength) LinearLayout sleevelength;
    @BindView(R.id.filter_rvwaiseline) RecyclerView rvwaiseline;
    @BindView(R.id.filter_tvwaiselineresult) TextView waiselineresult;
    @BindView(R.id.filter_lnwaiselinedetail) LinearLayout waiselinedetail;
    @BindView(R.id.filter_imgwaiselinecaret) ImageView waiselinecaret;
    @BindView(R.id.filter_btnclearallwaiseline) Button waiseline_clearall;
    @BindView(R.id.filter_btncheckallwaiseline) Button waiseline_checkall;
    @BindView(R.id.filter_btnapplywaiseline) Button waiseline_apply;
    @BindView(R.id.filter_lnwaiseline) LinearLayout waiseline;
    @BindView(R.id.filter_rvmaterial) RecyclerView rvmaterial;
    @BindView(R.id.filter_tvmaterialresult) TextView materialresult;
    @BindView(R.id.filter_lnmaterialdetail) LinearLayout materialdetail;
    @BindView(R.id.filter_imgmaterialcaret) ImageView materialcaret;
    @BindView(R.id.filter_btnclearallmaterial) Button material_clearall;
    @BindView(R.id.filter_btncheckallmaterial) Button material_checkall;
    @BindView(R.id.filter_btnapplymaterial) Button material_apply;
    @BindView(R.id.filter_lnmaterial) LinearLayout material;
    @BindView(R.id.filter_rvfabrictype) RecyclerView rvfabrictype;
    @BindView(R.id.filter_tvfabrictyperesult) TextView fabrictyperesult;
    @BindView(R.id.filter_lnfabrictypedetail) LinearLayout fabrictypedetail;
    @BindView(R.id.filter_imgfabrictypecaret) ImageView fabrictypecaret;
    @BindView(R.id.filter_btnclearallfabrictype) Button fabrictype_clearall;
    @BindView(R.id.filter_btncheckallfabrictype) Button fabrictype_checkall;
    @BindView(R.id.filter_btnapplyfabrictype) Button fabrictype_apply;
    @BindView(R.id.filter_lnfabrictype) LinearLayout fabrictype;
    @BindView(R.id.filter_rvdecoration) RecyclerView rvdecoration;
    @BindView(R.id.filter_tvdecorationresult) TextView decorationresult;
    @BindView(R.id.filter_lndecorationdetail) LinearLayout decorationdetail;
    @BindView(R.id.filter_imgdecorationcaret) ImageView decorationcaret;
    @BindView(R.id.filter_btnclearalldecoration) Button decoration_clearall;
    @BindView(R.id.filter_btncheckalldecoration) Button decoration_checkall;
    @BindView(R.id.filter_btnapplydecoration) Button decoration_apply;
    @BindView(R.id.filter_lndecoration) LinearLayout decoration;
    @BindView(R.id.filter_rvpatterntype) RecyclerView rvpatterntype;
    @BindView(R.id.filter_tvpatterntyperesult) TextView patterntyperesult;
    @BindView(R.id.filter_lnpatterntypedetail) LinearLayout patterntypedetail;
    @BindView(R.id.filter_imgpatterntypecaret) ImageView patterntypecaret;
    @BindView(R.id.filter_btnclearallpatterntype) Button patterntype_clearall;
    @BindView(R.id.filter_btncheckallpatterntype) Button patterntype_checkall;
    @BindView(R.id.filter_btnapplypatterntype) Button patterntype_apply;
    @BindView(R.id.filter_lnpatterntype) LinearLayout patterntype;

    private Boolean cityclicked = false, shipping_clicked = false, color_clicked = false, style_clicked = false, rating_clicked = false, size_clicked = false, season_clicked = false,
            neckline_clicked = false, sleevelength_clicked = false, waiseline_clicked = false, material_clicked = false, fabrictype_clicked = false, decoration_clicked = false, patterntype_clicked = false;
    private DecimalFormat formatter;
    private String[] cityString = new String[]{
            "AMBON", "BALIKPAPAN", "BANDA ACEH", "BANDAR LAMPUNG", "BANDUNG", "BANJAR",
            "BANJAR BARU", "BANJARMASIN", "BATAM", "BATU", "BAUBAU", "BEKASI", "BENGKULU",
            "BIMA", "BINJAI", "BITUNG", "BLITAR", "BOGOR", "BONTANG", "BUKITTINGGI", "CILEGON",
            "CIMAHI", "CIREBON", "DENPASAR", "DEPOK", "DUMAI", "GORONTALO", "GUNUNGSITOLI",
            "JAKARTA BARAT", "JAKARTA PUSAT", "JAKARTA SELATAN", "JAKARTA TIMUR", "JAKARTA UTARA",
            "JAMBI", "JAYAPURA", "KEDIRI", "KENDARI", "KOTAMOBAGU", "KUPANG", "LANGSA", "LHOKSEUMAWE",
            "LUBUKLINGGAU", "MADIUN", "MAGELANG", "MAKASSAR", "MALANG", "MANADO", "MATARAM", "MEDAN",
            "METRO", "MOJOKERTO", "PADANG", "PADANG PANJANG", "PADANGSIDIMPUAN", "PAGAR ALAM",
            "PALANGKA RAYA", "PALEMBANG", "PALOPO", "PALU", "PANGKAL PINANG", "PAREPARE", "PARIAMAN",
            "PASURUAN", "PAYAKUMBUH", "PEKALONGAN", "PEKANBARU", "PEMATANG SIANTAR", "PONTIANAK",
            "PRABUMULIH", "PROBOLINGGO", "SABANG", "SALATIGA", "SAMARINDA", "SAWAH LUNTO", "SEMARANG",
            "SERANG", "SIBOLGA", "SINGKAWANG", "SOLOK", "SORONG", "SUBULUSSALAM", "SUKABUMI",
            "SUNGAI PENUH", "SURABAYA", "SURAKARTA", "TANGERANG", "TANGERANG SELATAN", "TANJUNG BALAI",
            "TANJUNG PINANG", "TARAKAN", "TASIKMALAYA", "TEBING TINGGI", "TEGAL", "TERNATE",
            "TIDORE KEPULAUAN", "TOMOHON", "TUAL", "YOGYAKARTA"
    };
    ArrayList<String> cityList = new ArrayList<String>();
    ArrayList<String> list_cityresult = new ArrayList<String>();

    private String[] shippingString = new String[]{
            "GO Send", "J&T Express", "JNE", "POS Indonesia", "TIKI"
    };
    ArrayList<String> shippingList = new ArrayList<String>();
    ArrayList<String> list_shippingresult = new ArrayList<String>();

    private String[] colorString = new String[]{
            "black", "blue", "red", "white", "yellow"
    };
    ArrayList<String> colorList = new ArrayList<String>();
    ArrayList<String> list_colorresult = new ArrayList<String>();

    private String[] styleString = new String[]{
            "bohemian", "brief", "casual", "cute", "fashion", "flare", "novelty", "ol", "party", "sexy", "vintage", "work"
    };
    ArrayList<String> styleList = new ArrayList<String>();
    ArrayList<String> list_styleresult = new ArrayList<String>();

    private String[] ratingString = new String[]{
            "1", "2", "3", "4", "5"
    };
    ArrayList<String> ratingList = new ArrayList<String>();
    ArrayList<String> list_ratingresult = new ArrayList<String>();

    private String[] sizeString = new String[]{
            "free", "S", "M","L", "XL"
    };
    ArrayList<String> sizeList = new ArrayList<String>();
    ArrayList<String> list_sizeresult = new ArrayList<String>();

    private String[] SeasonString = new String[]{
            "Autumn", "Spring", "Summer", "Winter"
    };
    ArrayList<String> SeasonList = new ArrayList<String>();
    ArrayList<String> list_Seasonresult = new ArrayList<String>();

    private String[] necklineString = new String[]{
            "backless", "boat-neck", "bowneck", "halter", "mandarin-collor", "o-neck", "open", "peterpan-collor", "ruffled", "Scoop", "slash-neck", "sqare-collor",
            "Sweetheart", "turndowncollor", "v-neck"
    };
    ArrayList<String> necklineList = new ArrayList<String>();
    ArrayList<String> list_necklineresult = new ArrayList<String>();

    private String[] sleevelengthString = new String[]{
            "butterfly", "cap-sleeves", "full", "half", "half-sleeve", "petal", "short", "sleeveless", "threequater", "turndowncollor"
    };
    ArrayList<String> sleevelengthList = new ArrayList<String>();
    ArrayList<String> list_sleevelengthresult = new ArrayList<String>();

    private String[] waiselineString = new String[]{
            "dropped", "empire", "natural", "princess"
    };
    ArrayList<String> waiselineList = new ArrayList<String>();
    ArrayList<String> list_waiselineresult = new ArrayList<String>();

    private String[] materialString = new String[]{
            "acrylic", "cashmere", "chiffonfabric", "cotton", "knitting", "lace", "linen", "lycra", "microfiber", "milksilk", "mix",
            "model", "nylon", "other", "polyster", "rayon", "shiffon", "silk", "sill", "spandex", "viscos", "wool"
    };
    ArrayList<String> materialList = new ArrayList<String>();
    ArrayList<String> list_materialresult = new ArrayList<String>();

    private String[] fabrictypeString = new String[]{
            "batik", "broadcloth", "chiffon", "Corduroy", "dobby", "flannael", "jersey", "knitted", "lace", "organza", "other", "poplin",
            "sattin", "shiffon", "terry", "tulle", "wollen", "worsted"
    };
    ArrayList<String> fabrictypeList = new ArrayList<String>();
    ArrayList<String> list_fabrictyperesult = new ArrayList<String>();

    private String[] decorationString = new String[]{
            "applique", "beading", "bow", "button", "cascading", "crystal", "draped", "embroidary", "feathers", "flowers", "hollowout", "lace", "none",
            "pearls", "plain", "pleat", "pockets", "rivet", "ruched", "ruffles", "sashes", "sequined", "tassel", "Tiered"
    };
    ArrayList<String> decorationList = new ArrayList<String>();
    ArrayList<String> list_decorationresult = new ArrayList<String>();

    private String[] patterntypeString = new String[]{
            "animal", "character", "dot", "floral", "geometric", "leapord", "none", "patchwork", "plaid", "print", "solid" ,"splice", "striped"
    };
    ArrayList<String> patterntypeList = new ArrayList<String>();
    ArrayList<String> list_patterntyperesult = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
        initView();
        setuptoolbar();
        initSeekbar();
        initRangeSeekbar();
        setupListView();
        setupCity();
        setupShipment();
        setupColor();
        setupStyle();
        setupRating();
        setupSize();
        setupSeason();
        setupNeckline();
        setupSleeveLength();
        setupWaiseline();
        setupMaterial();
        setupFabricType();
        setupDecoration();
        setupPatternType();
    }

    private void setupPatternType() {
        Collections.addAll(patterntypeList, patterntypeString);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 3);
        rvpatterntype.setLayoutManager(layoutManager);
        rvpatterntype.setItemAnimator(new DefaultItemAnimator());
        rvpatterntype.setAdapter(new FilterRVAdapter(this, patterntypeList));
        patterntype_checkall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int count = rvpatterntype.getAdapter().getItemCount();
                for(int i = 0; i< count; i++){
                    LinearLayout item = (LinearLayout) rvpatterntype.getChildAt(i);
                    CheckBox checkBox = (CheckBox) item.findViewById(R.id.itemgridviewfilter_checkbox);
                    checkBox.setChecked(true);
                }
            }
        });
        patterntype_clearall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int count = rvpatterntype.getAdapter().getItemCount();
                for(int i = 0; i< count; i++){
                    LinearLayout item = (LinearLayout) rvpatterntype.getChildAt(i);
                    CheckBox checkBox = (CheckBox) item.findViewById(R.id.itemgridviewfilter_checkbox);
                    checkBox.setChecked(false);
                }
            }
        });
        patterntype_apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int count = rvpatterntype.getAdapter().getItemCount();
                list_patterntyperesult.clear();
                for(int i = 0; i< count; i++){
                    LinearLayout item = (LinearLayout) rvpatterntype.getChildAt(i);
                    CheckBox checkBox = (CheckBox) item.findViewById(R.id.itemgridviewfilter_checkbox);
                    if(checkBox.isChecked()){
                        list_patterntyperesult.add(checkBox.getTag().toString());
                    }
                }
                int count1 = list_patterntyperesult.size();
                String temp = "";
                for(int i = 0; i<count1; i++){
                    if (i!=count1-1)
                        temp += list_patterntyperesult.get(i).toLowerCase() + ", ";
                    else
                        temp += list_patterntyperesult.get(i).toLowerCase();
                }
                patterntyperesult.setText(temp);
                for(int i = 0; i< count; i++){
                    LinearLayout item = (LinearLayout) rvpatterntype.getChildAt(i);
                    CheckBox checkBox = (CheckBox) item.findViewById(R.id.itemgridviewfilter_checkbox);
                    checkBox.setChecked(false);
                    patterntypedetail.setVisibility(View.GONE);
                    patterntype_clicked = false;
                    patterntypecaret.setImageResource(R.drawable.caret);
                }
            }
        });
        patterntype.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!patterntype_clicked){
                    patterntypedetail.setVisibility(View.VISIBLE);
                    patterntype_clicked = true;
                    patterntypecaret.setImageResource(R.drawable.caret1);
                }
                else {
                    patterntypedetail.setVisibility(View.GONE);
                    patterntype_clicked = false;
                    patterntypecaret.setImageResource(R.drawable.caret);
                }
            }
        });
    }

    private void setupDecoration() {
        Collections.addAll(decorationList, decorationString);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 3);
        rvdecoration.setLayoutManager(layoutManager);
        rvdecoration.setItemAnimator(new DefaultItemAnimator());
        rvdecoration.setAdapter(new FilterRVAdapter(this, decorationList));
        decoration_checkall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int count = rvdecoration.getAdapter().getItemCount();
                for(int i = 0; i< count; i++){
                    LinearLayout item = (LinearLayout) rvdecoration.getChildAt(i);
                    CheckBox checkBox = (CheckBox) item.findViewById(R.id.itemgridviewfilter_checkbox);
                    checkBox.setChecked(true);
                }
            }
        });
        decoration_clearall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int count = rvdecoration.getAdapter().getItemCount();
                for(int i = 0; i< count; i++){
                    LinearLayout item = (LinearLayout) rvdecoration.getChildAt(i);
                    CheckBox checkBox = (CheckBox) item.findViewById(R.id.itemgridviewfilter_checkbox);
                    checkBox.setChecked(false);
                }
            }
        });
        decoration_apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int count = rvdecoration.getAdapter().getItemCount();
                list_decorationresult.clear();
                for(int i = 0; i< count; i++){
                    LinearLayout item = (LinearLayout) rvdecoration.getChildAt(i);
                    CheckBox checkBox = (CheckBox) item.findViewById(R.id.itemgridviewfilter_checkbox);
                    if(checkBox.isChecked()){
                        list_decorationresult.add(checkBox.getTag().toString());
                    }
                }
                int count1 = list_decorationresult.size();
                String temp = "";
                for(int i = 0; i<count1; i++){
                    if (i!=count1-1)
                        temp += list_decorationresult.get(i).toLowerCase() + ", ";
                    else
                        temp += list_decorationresult.get(i).toLowerCase();
                }
                decorationresult.setText(temp);
                for(int i = 0; i< count; i++){
                    LinearLayout item = (LinearLayout) rvdecoration.getChildAt(i);
                    CheckBox checkBox = (CheckBox) item.findViewById(R.id.itemgridviewfilter_checkbox);
                    checkBox.setChecked(false);
                    decorationdetail.setVisibility(View.GONE);
                    decoration_clicked = false;
                    decorationcaret.setImageResource(R.drawable.caret);
                }
            }
        });
        decoration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!decoration_clicked){
                    decorationdetail.setVisibility(View.VISIBLE);
                    decoration_clicked = true;
                    decorationcaret.setImageResource(R.drawable.caret1);
                }
                else {
                    decorationdetail.setVisibility(View.GONE);
                    decoration_clicked = false;
                    decorationcaret.setImageResource(R.drawable.caret);
                }
            }
        });
    }

    private void setupFabricType() {
        Collections.addAll(fabrictypeList, fabrictypeString);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 3);
        rvfabrictype.setLayoutManager(layoutManager);
        rvfabrictype.setItemAnimator(new DefaultItemAnimator());
        rvfabrictype.setAdapter(new FilterRVAdapter(this, fabrictypeList));
        fabrictype_checkall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int count = rvfabrictype.getAdapter().getItemCount();
                for(int i = 0; i< count; i++){
                    LinearLayout item = (LinearLayout) rvfabrictype.getChildAt(i);
                    CheckBox checkBox = (CheckBox) item.findViewById(R.id.itemgridviewfilter_checkbox);
                    checkBox.setChecked(true);
                }
            }
        });
        fabrictype_clearall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int count = rvfabrictype.getAdapter().getItemCount();
                for(int i = 0; i< count; i++){
                    LinearLayout item = (LinearLayout) rvfabrictype.getChildAt(i);
                    CheckBox checkBox = (CheckBox) item.findViewById(R.id.itemgridviewfilter_checkbox);
                    checkBox.setChecked(false);
                }
            }
        });
        fabrictype_apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int count = rvfabrictype.getAdapter().getItemCount();
                list_fabrictyperesult.clear();
                for(int i = 0; i< count; i++){
                    LinearLayout item = (LinearLayout) rvfabrictype.getChildAt(i);
                    CheckBox checkBox = (CheckBox) item.findViewById(R.id.itemgridviewfilter_checkbox);
                    if(checkBox.isChecked()){
                        list_fabrictyperesult.add(checkBox.getTag().toString());
                    }
                }
                int count1 = list_fabrictyperesult.size();
                String temp = "";
                for(int i = 0; i<count1; i++){
                    if (i!=count1-1)
                        temp += list_fabrictyperesult.get(i).toLowerCase() + ", ";
                    else
                        temp += list_fabrictyperesult.get(i).toLowerCase();
                }
                fabrictyperesult.setText(temp);
                for(int i = 0; i< count; i++){
                    LinearLayout item = (LinearLayout) rvfabrictype.getChildAt(i);
                    CheckBox checkBox = (CheckBox) item.findViewById(R.id.itemgridviewfilter_checkbox);
                    checkBox.setChecked(false);
                    fabrictypedetail.setVisibility(View.GONE);
                    fabrictype_clicked = false;
                    fabrictypecaret.setImageResource(R.drawable.caret);
                }
            }
        });
        fabrictype.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!fabrictype_clicked){
                    fabrictypedetail.setVisibility(View.VISIBLE);
                    fabrictype_clicked = true;
                    fabrictypecaret.setImageResource(R.drawable.caret1);
                }
                else {
                    fabrictypedetail.setVisibility(View.GONE);
                    fabrictype_clicked = false;
                    fabrictypecaret.setImageResource(R.drawable.caret);
                }
            }
        });
    }

    private void setupMaterial() {
        Collections.addAll(materialList, materialString);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 3);
        rvmaterial.setLayoutManager(layoutManager);
        rvmaterial.setItemAnimator(new DefaultItemAnimator());
        rvmaterial.setAdapter(new FilterRVAdapter(this, materialList));
        material_checkall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int count = rvmaterial.getAdapter().getItemCount();
                for(int i = 0; i< count; i++){
                    LinearLayout item = (LinearLayout) rvmaterial.getChildAt(i);
                    CheckBox checkBox = (CheckBox) item.findViewById(R.id.itemgridviewfilter_checkbox);
                    checkBox.setChecked(true);
                }
            }
        });
        material_clearall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int count = rvmaterial.getAdapter().getItemCount();
                for(int i = 0; i< count; i++){
                    LinearLayout item = (LinearLayout) rvmaterial.getChildAt(i);
                    CheckBox checkBox = (CheckBox) item.findViewById(R.id.itemgridviewfilter_checkbox);
                    checkBox.setChecked(false);
                }
            }
        });
        material_apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int count = rvmaterial.getAdapter().getItemCount();
                list_materialresult.clear();
                for(int i = 0; i< count; i++){
                    LinearLayout item = (LinearLayout) rvmaterial.getChildAt(i);
                    CheckBox checkBox = (CheckBox) item.findViewById(R.id.itemgridviewfilter_checkbox);
                    if(checkBox.isChecked()){
                        list_materialresult.add(checkBox.getTag().toString());
                    }
                }
                int count1 = list_materialresult.size();
                String temp = "";
                for(int i = 0; i<count1; i++){
                    if (i!=count1-1)
                        temp += list_materialresult.get(i).toLowerCase() + ", ";
                    else
                        temp += list_materialresult.get(i).toLowerCase();
                }
                materialresult.setText(temp);
                for(int i = 0; i< count; i++){
                    LinearLayout item = (LinearLayout) rvmaterial.getChildAt(i);
                    CheckBox checkBox = (CheckBox) item.findViewById(R.id.itemgridviewfilter_checkbox);
                    checkBox.setChecked(false);
                    materialdetail.setVisibility(View.GONE);
                    material_clicked = false;
                    materialcaret.setImageResource(R.drawable.caret);
                }
            }
        });
        material.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!material_clicked){
                    materialdetail.setVisibility(View.VISIBLE);
                    material_clicked = true;
                    materialcaret.setImageResource(R.drawable.caret1);
                }
                else {
                    materialdetail.setVisibility(View.GONE);
                    material_clicked = false;
                    materialcaret.setImageResource(R.drawable.caret);
                }
            }
        });
    }

    private void setupWaiseline() {
        Collections.addAll(waiselineList, waiselineString);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 3);
        rvwaiseline.setLayoutManager(layoutManager);
        rvwaiseline.setItemAnimator(new DefaultItemAnimator());
        rvwaiseline.setAdapter(new FilterRVAdapter(this, waiselineList));
        waiseline_checkall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int count = rvwaiseline.getAdapter().getItemCount();
                for(int i = 0; i< count; i++){
                    LinearLayout item = (LinearLayout) rvwaiseline.getChildAt(i);
                    CheckBox checkBox = (CheckBox) item.findViewById(R.id.itemgridviewfilter_checkbox);
                    checkBox.setChecked(true);
                }
            }
        });
        waiseline_clearall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int count = rvwaiseline.getAdapter().getItemCount();
                for(int i = 0; i< count; i++){
                    LinearLayout item = (LinearLayout) rvwaiseline.getChildAt(i);
                    CheckBox checkBox = (CheckBox) item.findViewById(R.id.itemgridviewfilter_checkbox);
                    checkBox.setChecked(false);
                }
            }
        });
        waiseline_apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int count = rvwaiseline.getAdapter().getItemCount();
                list_waiselineresult.clear();
                for(int i = 0; i< count; i++){
                    LinearLayout item = (LinearLayout) rvwaiseline.getChildAt(i);
                    CheckBox checkBox = (CheckBox) item.findViewById(R.id.itemgridviewfilter_checkbox);
                    if(checkBox.isChecked()){
                        list_waiselineresult.add(checkBox.getTag().toString());
                    }
                }
                int count1 = list_waiselineresult.size();
                String temp = "";
                for(int i = 0; i<count1; i++){
                    if (i!=count1-1)
                        temp += list_waiselineresult.get(i).toLowerCase() + ", ";
                    else
                        temp += list_waiselineresult.get(i).toLowerCase();
                }
                waiselineresult.setText(temp);
                for(int i = 0; i< count; i++){
                    LinearLayout item = (LinearLayout) rvwaiseline.getChildAt(i);
                    CheckBox checkBox = (CheckBox) item.findViewById(R.id.itemgridviewfilter_checkbox);
                    checkBox.setChecked(false);
                    waiselinedetail.setVisibility(View.GONE);
                    waiseline_clicked = false;
                    waiselinecaret.setImageResource(R.drawable.caret);
                }
            }
        });
        waiseline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!waiseline_clicked){
                    waiselinedetail.setVisibility(View.VISIBLE);
                    waiseline_clicked = true;
                    waiselinecaret.setImageResource(R.drawable.caret1);
                }
                else {
                    waiselinedetail.setVisibility(View.GONE);
                    waiseline_clicked = false;
                    waiselinecaret.setImageResource(R.drawable.caret);
                }
            }
        });
    }

    private void setupSleeveLength() {
        Collections.addAll(sleevelengthList, sleevelengthString);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 3);
        rvsleevelength.setLayoutManager(layoutManager);
        rvsleevelength.setItemAnimator(new DefaultItemAnimator());
        rvsleevelength.setAdapter(new FilterRVAdapter(this, sleevelengthList));
        sleevelength_checkall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int count = rvsleevelength.getAdapter().getItemCount();
                for(int i = 0; i< count; i++){
                    LinearLayout item = (LinearLayout) rvsleevelength.getChildAt(i);
                    CheckBox checkBox = (CheckBox) item.findViewById(R.id.itemgridviewfilter_checkbox);
                    checkBox.setChecked(true);
                }
            }
        });
        sleevelength_clearall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int count = rvsleevelength.getAdapter().getItemCount();
                for(int i = 0; i< count; i++){
                    LinearLayout item = (LinearLayout) rvsleevelength.getChildAt(i);
                    CheckBox checkBox = (CheckBox) item.findViewById(R.id.itemgridviewfilter_checkbox);
                    checkBox.setChecked(false);
                }
            }
        });
        sleevelength_apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int count = rvsleevelength.getAdapter().getItemCount();
                list_sleevelengthresult.clear();
                for(int i = 0; i< count; i++){
                    LinearLayout item = (LinearLayout) rvsleevelength.getChildAt(i);
                    CheckBox checkBox = (CheckBox) item.findViewById(R.id.itemgridviewfilter_checkbox);
                    if(checkBox.isChecked()){
                        list_sleevelengthresult.add(checkBox.getTag().toString());
                    }
                }
                int count1 = list_sleevelengthresult.size();
                String temp = "";
                for(int i = 0; i<count1; i++){
                    if (i!=count1-1)
                        temp += list_sleevelengthresult.get(i).toLowerCase() + ", ";
                    else
                        temp += list_sleevelengthresult.get(i).toLowerCase();
                }
                sleevelengthresult.setText(temp);
                for(int i = 0; i< count; i++){
                    LinearLayout item = (LinearLayout) rvsleevelength.getChildAt(i);
                    CheckBox checkBox = (CheckBox) item.findViewById(R.id.itemgridviewfilter_checkbox);
                    checkBox.setChecked(false);
                    sleevelengthdetail.setVisibility(View.GONE);
                    sleevelength_clicked = false;
                    sleevelengthcaret.setImageResource(R.drawable.caret);
                }
            }
        });
        sleevelength.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!sleevelength_clicked){
                    sleevelengthdetail.setVisibility(View.VISIBLE);
                    sleevelength_clicked = true;
                    sleevelengthcaret.setImageResource(R.drawable.caret1);
                }
                else {
                    sleevelengthdetail.setVisibility(View.GONE);
                    sleevelength_clicked = false;
                    sleevelengthcaret.setImageResource(R.drawable.caret);
                }
            }
        });
    }

    private void setupNeckline() {
        Collections.addAll(necklineList, necklineString);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 3);
        rvneckline.setLayoutManager(layoutManager);
        rvneckline.setItemAnimator(new DefaultItemAnimator());
        rvneckline.setAdapter(new FilterRVAdapter(this, necklineList));
        neckline_checkall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int count = rvneckline.getAdapter().getItemCount();
                for(int i = 0; i< count; i++){
                    LinearLayout item = (LinearLayout) rvneckline.getChildAt(i);
                    CheckBox checkBox = (CheckBox) item.findViewById(R.id.itemgridviewfilter_checkbox);
                    checkBox.setChecked(true);
                }
            }
        });
        neckline_clearall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int count = rvneckline.getAdapter().getItemCount();
                for(int i = 0; i< count; i++){
                    LinearLayout item = (LinearLayout) rvneckline.getChildAt(i);
                    CheckBox checkBox = (CheckBox) item.findViewById(R.id.itemgridviewfilter_checkbox);
                    checkBox.setChecked(false);
                }
            }
        });
        neckline_apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int count = rvneckline.getAdapter().getItemCount();
                list_necklineresult.clear();
                for(int i = 0; i< count; i++){
                    LinearLayout item = (LinearLayout) rvneckline.getChildAt(i);
                    CheckBox checkBox = (CheckBox) item.findViewById(R.id.itemgridviewfilter_checkbox);
                    if(checkBox.isChecked()){
                        list_necklineresult.add(checkBox.getTag().toString());
                    }
                }
                int count1 = list_necklineresult.size();
                String temp = "";
                for(int i = 0; i<count1; i++){
                    if (i!=count1-1)
                        temp += list_necklineresult.get(i).toLowerCase() + ", ";
                    else
                        temp += list_necklineresult.get(i).toLowerCase();
                }
                necklineresult.setText(temp);
                for(int i = 0; i< count; i++){
                    LinearLayout item = (LinearLayout) rvneckline.getChildAt(i);
                    CheckBox checkBox = (CheckBox) item.findViewById(R.id.itemgridviewfilter_checkbox);
                    checkBox.setChecked(false);
                    necklinedetail.setVisibility(View.GONE);
                    neckline_clicked = false;
                    necklinecaret.setImageResource(R.drawable.caret);
                }
            }
        });
        neckline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!neckline_clicked){
                    necklinedetail.setVisibility(View.VISIBLE);
                    neckline_clicked = true;
                    necklinecaret.setImageResource(R.drawable.caret1);
                }
                else {
                    necklinedetail.setVisibility(View.GONE);
                    neckline_clicked = false;
                    necklinecaret.setImageResource(R.drawable.caret);
                }
            }
        });
    }

    private void setupSize() {
        Collections.addAll(sizeList, sizeString);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 3);
        rvsize.setLayoutManager(layoutManager);
        rvsize.setItemAnimator(new DefaultItemAnimator());
        rvsize.setAdapter(new FilterRVAdapter(this, sizeList));
        size_checkall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int count = rvsize.getAdapter().getItemCount();
                for(int i = 0; i< count; i++){
                    LinearLayout item = (LinearLayout) rvsize.getChildAt(i);
                    CheckBox checkBox = (CheckBox) item.findViewById(R.id.itemgridviewfilter_checkbox);
                    checkBox.setChecked(true);
                }
            }
        });
        size_clearall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int count = rvsize.getAdapter().getItemCount();
                for(int i = 0; i< count; i++){
                    LinearLayout item = (LinearLayout) rvsize.getChildAt(i);
                    CheckBox checkBox = (CheckBox) item.findViewById(R.id.itemgridviewfilter_checkbox);
                    checkBox.setChecked(false);
                }
            }
        });
        size_apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int count = rvsize.getAdapter().getItemCount();
                list_sizeresult.clear();
                for(int i = 0; i< count; i++){
                    LinearLayout item = (LinearLayout) rvsize.getChildAt(i);
                    CheckBox checkBox = (CheckBox) item.findViewById(R.id.itemgridviewfilter_checkbox);
                    if(checkBox.isChecked()){
                        list_sizeresult.add(checkBox.getTag().toString());
                    }
                }
                int count1 = list_sizeresult.size();
                String temp = "";
                for(int i = 0; i<count1; i++){
                    if (i!=count1-1)
                        temp += list_sizeresult.get(i).toLowerCase() + ", ";
                    else
                        temp += list_sizeresult.get(i).toLowerCase();
                }
                sizeresult.setText(temp);
                for(int i = 0; i< count; i++){
                    LinearLayout item = (LinearLayout) rvsize.getChildAt(i);
                    CheckBox checkBox = (CheckBox) item.findViewById(R.id.itemgridviewfilter_checkbox);
                    checkBox.setChecked(false);
                    sizedetail.setVisibility(View.GONE);
                    size_clicked = false;
                    sizecaret.setImageResource(R.drawable.caret);
                }
            }
        });
        size.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!size_clicked){
                    sizedetail.setVisibility(View.VISIBLE);
                    size_clicked = true;
                    sizecaret.setImageResource(R.drawable.caret1);
                }
                else {
                    sizedetail.setVisibility(View.GONE);
                    size_clicked = false;
                    sizecaret.setImageResource(R.drawable.caret);
                }
            }
        });
    }

    private void setupRating() {
        Collections.addAll(ratingList, ratingString);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 3);
        rvrating.setLayoutManager(layoutManager);
        rvrating.setItemAnimator(new DefaultItemAnimator());
        rvrating.setAdapter(new FilterRVAdapter(this, ratingList));
        rating_checkall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int count = rvrating.getAdapter().getItemCount();
                for(int i = 0; i< count; i++){
                    LinearLayout item = (LinearLayout) rvrating.getChildAt(i);
                    CheckBox checkBox = (CheckBox) item.findViewById(R.id.itemgridviewfilter_checkbox);
                    checkBox.setChecked(true);
                }
            }
        });
        rating_clearall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int count = rvrating.getAdapter().getItemCount();
                for(int i = 0; i< count; i++){
                    LinearLayout item = (LinearLayout) rvrating.getChildAt(i);
                    CheckBox checkBox = (CheckBox) item.findViewById(R.id.itemgridviewfilter_checkbox);
                    checkBox.setChecked(false);
                }
            }
        });
        rating_apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int count = rvrating.getAdapter().getItemCount();
                list_ratingresult.clear();
                for(int i = 0; i< count; i++){
                    LinearLayout item = (LinearLayout) rvrating.getChildAt(i);
                    CheckBox checkBox = (CheckBox) item.findViewById(R.id.itemgridviewfilter_checkbox);
                    if(checkBox.isChecked()){
                        list_ratingresult.add(checkBox.getTag().toString());
                    }
                }
                int count1 = list_ratingresult.size();
                String temp = "";
                for(int i = 0; i<count1; i++){
                    if (i!=count1-1)
                        temp += list_ratingresult.get(i).toLowerCase() + ", ";
                    else
                        temp += list_ratingresult.get(i).toLowerCase();
                }
                ratingresult.setText(temp);
                for(int i = 0; i< count; i++){
                    LinearLayout item = (LinearLayout) rvrating.getChildAt(i);
                    CheckBox checkBox = (CheckBox) item.findViewById(R.id.itemgridviewfilter_checkbox);
                    checkBox.setChecked(false);
                    ratingdetail.setVisibility(View.GONE);
                    rating_clicked = false;
                    ratingcaret.setImageResource(R.drawable.caret);
                }
            }
        });
        rating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!rating_clicked){
                    ratingdetail.setVisibility(View.VISIBLE);
                    rating_clicked = true;
                    ratingcaret.setImageResource(R.drawable.caret1);
                }
                else {
                    ratingdetail.setVisibility(View.GONE);
                    rating_clicked = false;
                    ratingcaret.setImageResource(R.drawable.caret);
                }
            }
        });
    }

    private void setupSeason() {
        Collections.addAll(SeasonList, SeasonString);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 3);
        rvseason.setLayoutManager(layoutManager);
        rvseason.setItemAnimator(new DefaultItemAnimator());
        rvseason.setAdapter(new FilterRVAdapter(this, SeasonList));
        season_checkall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int count = rvseason.getAdapter().getItemCount();
                for(int i = 0; i< count; i++){
                    LinearLayout item = (LinearLayout) rvseason.getChildAt(i);
                    CheckBox checkBox = (CheckBox) item.findViewById(R.id.itemgridviewfilter_checkbox);
                    checkBox.setChecked(true);
                }
            }
        });
        season_clearall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int count = rvseason.getAdapter().getItemCount();
                for(int i = 0; i< count; i++){
                    LinearLayout item = (LinearLayout) rvseason.getChildAt(i);
                    CheckBox checkBox = (CheckBox) item.findViewById(R.id.itemgridviewfilter_checkbox);
                    checkBox.setChecked(false);
                }
            }
        });
        season_apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int count = rvseason.getAdapter().getItemCount();
                list_Seasonresult.clear();
                for(int i = 0; i< count; i++){
                    LinearLayout item = (LinearLayout) rvseason.getChildAt(i);
                    CheckBox checkBox = (CheckBox) item.findViewById(R.id.itemgridviewfilter_checkbox);
                    if(checkBox.isChecked()){
                        list_Seasonresult.add(checkBox.getTag().toString());
                    }
                }
                int count1 = list_Seasonresult.size();
                String temp = "";
                for(int i = 0; i<count1; i++){
                    if (i!=count1-1)
                        temp += list_Seasonresult.get(i).toLowerCase() + ", ";
                    else
                        temp += list_Seasonresult.get(i).toLowerCase();
                }
                seasonresult.setText(temp);
                for(int i = 0; i< count; i++){
                    LinearLayout item = (LinearLayout) rvseason.getChildAt(i);
                    CheckBox checkBox = (CheckBox) item.findViewById(R.id.itemgridviewfilter_checkbox);
                    checkBox.setChecked(false);
                    seasondetail.setVisibility(View.GONE);
                    season_clicked = false;
                    seasoncaret.setImageResource(R.drawable.caret);
                }
            }
        });
        season.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!season_clicked){
                    seasondetail.setVisibility(View.VISIBLE);
                    season_clicked = true;
                    seasoncaret.setImageResource(R.drawable.caret1);
                }
                else {
                    seasondetail.setVisibility(View.GONE);
                    season_clicked = false;
                    seasoncaret.setImageResource(R.drawable.caret);
                }
            }
        });
    }

    private void setupStyle() {
        Collections.addAll(styleList, styleString);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 3);
        rvstyle.setLayoutManager(layoutManager);
        rvstyle.setItemAnimator(new DefaultItemAnimator());
        rvstyle.setAdapter(new FilterRVAdapter(this, styleList));
        style_checkall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int count = rvstyle.getAdapter().getItemCount();
                for(int i = 0; i< count; i++){
                    LinearLayout item = (LinearLayout) rvstyle.getChildAt(i);
                    CheckBox checkBox = (CheckBox) item.findViewById(R.id.itemgridviewfilter_checkbox);
                    checkBox.setChecked(true);
                }
            }
        });
        style_clearall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int count = rvstyle.getAdapter().getItemCount();
                for(int i = 0; i< count; i++){
                    LinearLayout item = (LinearLayout) rvstyle.getChildAt(i);
                    CheckBox checkBox = (CheckBox) item.findViewById(R.id.itemgridviewfilter_checkbox);
                    checkBox.setChecked(false);
                }
            }
        });
        style_apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int count = rvstyle.getAdapter().getItemCount();
                list_styleresult.clear();
                for(int i = 0; i< count; i++){
                    LinearLayout item = (LinearLayout) rvstyle.getChildAt(i);
                    CheckBox checkBox = (CheckBox) item.findViewById(R.id.itemgridviewfilter_checkbox);
                    if(checkBox.isChecked()){
                        list_styleresult.add(checkBox.getTag().toString());
                    }
                }
                int count1 = list_styleresult.size();
                String temp = "";
                for(int i = 0; i<count1; i++){
                    if (i!=count1-1)
                        temp += list_styleresult.get(i).toLowerCase() + ", ";
                    else
                        temp += list_styleresult.get(i).toLowerCase();
                }
                styleresult.setText(temp);
                for(int i = 0; i< count; i++){
                    LinearLayout item = (LinearLayout) rvstyle.getChildAt(i);
                    CheckBox checkBox = (CheckBox) item.findViewById(R.id.itemgridviewfilter_checkbox);
                    checkBox.setChecked(false);
                    styledetail.setVisibility(View.GONE);
                    style_clicked = false;
                    stylecaret.setImageResource(R.drawable.caret);
                }
            }
        });
        style.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!style_clicked){
                    styledetail.setVisibility(View.VISIBLE);
                    style_clicked = true;
                    stylecaret.setImageResource(R.drawable.caret1);
                }
                else {
                    styledetail.setVisibility(View.GONE);
                    style_clicked = false;
                    stylecaret.setImageResource(R.drawable.caret);
                }
            }
        });
    }

    private void setupColor() {
        Collections.addAll(colorList, colorString);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 2);
        rvcolor.setLayoutManager(layoutManager);
        rvcolor.setItemAnimator(new DefaultItemAnimator());
        rvcolor.setAdapter(new ColorRVAdapter(this, colorList));
        color_checkall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int count = rvcolor.getAdapter().getItemCount();
                for(int i = 0; i< count; i++){
                    LinearLayout item = (LinearLayout) rvcolor.getChildAt(i);
                    CheckBox checkBox = (CheckBox) item.findViewById(R.id.itemgridviewfilter1_checkbox);
                    checkBox.setChecked(true);
                }
            }
        });
        color_clearall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int count = rvcolor.getAdapter().getItemCount();
                for(int i = 0; i< count; i++){
                    LinearLayout item = (LinearLayout) rvcolor.getChildAt(i);
                    CheckBox checkBox = (CheckBox) item.findViewById(R.id.itemgridviewfilter1_checkbox);
                    checkBox.setChecked(false);
                }
            }
        });
        color_apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int count = rvcolor.getAdapter().getItemCount();
                list_colorresult.clear();
                for(int i = 0; i< count; i++){
                    LinearLayout item = (LinearLayout) rvcolor.getChildAt(i);
                    CheckBox checkBox = (CheckBox) item.findViewById(R.id.itemgridviewfilter1_checkbox);
                    if(checkBox.isChecked()){
                        list_colorresult.add(checkBox.getTag().toString());
                    }
                }
                int count1 = list_colorresult.size();
                String temp = "";
                for(int i = 0; i<count1; i++){
                    if (i!=count1-1)
                        temp += list_colorresult.get(i).toLowerCase() + ", ";
                    else
                        temp += list_colorresult.get(i).toLowerCase();
                }
                colorresult.setText(temp);
                for(int i = 0; i< count; i++){
                    LinearLayout item = (LinearLayout) rvcolor.getChildAt(i);
                    CheckBox checkBox = (CheckBox) item.findViewById(R.id.itemgridviewfilter1_checkbox);
                    checkBox.setChecked(false);
                    colordetail.setVisibility(View.GONE);
                    color_clicked = false;
                    colorcaret.setImageResource(R.drawable.caret);
                }
            }
        });
        color.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!color_clicked){
                    colordetail.setVisibility(View.VISIBLE);
                    color_clicked = true;
                    colorcaret.setImageResource(R.drawable.caret1);
                }
                else {
                    colordetail.setVisibility(View.GONE);
                    color_clicked = false;
                    colorcaret.setImageResource(R.drawable.caret);
                }
            }
        });
        
    }

    private void setupShipment() {
        Collections.addAll(shippingList, shippingString);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 3);
        rvshipment.setLayoutManager(layoutManager);
        rvshipment.setItemAnimator(new DefaultItemAnimator());
        rvshipment.setAdapter(new FilterRVAdapter(this, shippingList));
        shipment_checkall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int count = rvshipment.getAdapter().getItemCount();
                for(int i = 0; i< count; i++){
                    LinearLayout item = (LinearLayout) rvshipment.getChildAt(i);
                    CheckBox checkBox = (CheckBox) item.findViewById(R.id.itemgridviewfilter_checkbox);
                    checkBox.setChecked(true);
                }
            }
        });
        shipment_clearall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int count = rvshipment.getAdapter().getItemCount();
                for(int i = 0; i< count; i++){
                    LinearLayout item = (LinearLayout) rvshipment.getChildAt(i);
                    CheckBox checkBox = (CheckBox) item.findViewById(R.id.itemgridviewfilter_checkbox);
                    checkBox.setChecked(false);
                }
            }
        });
        shipment_apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int count = rvshipment.getAdapter().getItemCount();
                list_shippingresult.clear();
                for(int i = 0; i< count; i++){
                    LinearLayout item = (LinearLayout) rvshipment.getChildAt(i);
                    CheckBox checkBox = (CheckBox) item.findViewById(R.id.itemgridviewfilter_checkbox);
                    if(checkBox.isChecked()){
                        list_shippingresult.add(checkBox.getTag().toString());
                    }
                }
                int count1 = list_shippingresult.size();
                String temp = "";
                for(int i = 0; i<count1; i++){
                    if (i!=count1-1)
                        temp += list_shippingresult.get(i).toLowerCase() + ", ";
                    else
                        temp += list_shippingresult.get(i).toLowerCase();
                }
                shipmentresult.setText(temp);
                for(int i = 0; i< count; i++){
                    LinearLayout item = (LinearLayout) rvshipment.getChildAt(i);
                    CheckBox checkBox = (CheckBox) item.findViewById(R.id.itemgridviewfilter_checkbox);
                    checkBox.setChecked(false);
                    shipmentdetail.setVisibility(View.GONE);
                    shipping_clicked = false;
                    shipmentcaret.setImageResource(R.drawable.caret);
                }
            }
        });
        shipment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!shipping_clicked){
                    shipmentdetail.setVisibility(View.VISIBLE);
                    shipping_clicked = true;
                    shipmentcaret.setImageResource(R.drawable.caret1);
                }
                else {
                    shipmentdetail.setVisibility(View.GONE);
                    shipping_clicked = false;
                    shipmentcaret.setImageResource(R.drawable.caret);
                }
            }
        });
    }

    private void setupCity() {
        city_checkall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int count = rvcity.getAdapter().getItemCount();
                for(int i = 0; i< count; i++){
                   LinearLayout item = (LinearLayout) rvcity.getChildAt(i);
                    CheckBox checkBox = (CheckBox) item.findViewById(R.id.itemgridviewfilter_checkbox);
                    checkBox.setChecked(true);
                }
            }
        });
        city_clearall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int count = rvcity.getAdapter().getItemCount();
                for(int i = 0; i< count; i++){
                    LinearLayout item = (LinearLayout) rvcity.getChildAt(i);
                    CheckBox checkBox = (CheckBox) item.findViewById(R.id.itemgridviewfilter_checkbox);
                    checkBox.setChecked(false);
                }
            }
        });
        city_apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int count = rvcity.getAdapter().getItemCount();
                list_cityresult.clear();
                for(int i = 0; i< count; i++){
                    LinearLayout item = (LinearLayout) rvcity.getChildAt(i);
                    CheckBox checkBox = (CheckBox) item.findViewById(R.id.itemgridviewfilter_checkbox);
                    if(checkBox.isChecked()){
                        list_cityresult.add(checkBox.getTag().toString());
                    }
                }
                int count1 = list_cityresult.size();
                String temp = "";
                for(int i = 0; i<count1; i++){
                    if (i!=count1-1)
                        temp += list_cityresult.get(i).toLowerCase() + ", ";
                    else
                        temp += list_cityresult.get(i).toLowerCase();
                }
                cityresult.setText(temp);
                for(int i = 0; i< count; i++){
                    LinearLayout item = (LinearLayout) rvcity.getChildAt(i);
                    CheckBox checkBox = (CheckBox) item.findViewById(R.id.itemgridviewfilter_checkbox);
                    checkBox.setChecked(false);
                    citydetail.setVisibility(View.GONE);
                    cityclicked = false;
                    citycaret.setImageResource(R.drawable.caret);
                }
            }
        });
        city.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!cityclicked){
                    citydetail.setVisibility(View.VISIBLE);
                    cityclicked = true;
                    citycaret.setImageResource(R.drawable.caret1);
                }
                else {
                    citydetail.setVisibility(View.GONE);
                    cityclicked = false;
                    citycaret.setImageResource(R.drawable.caret);
                }
            }
        });
    }

    private void initSeekbar(){
        minorder.setText("1");
        minorder.setFilters(new InputFilter[]{new InputFilterMinMax("1","50")});
        minordervalue.setText("1");
        maxordervalue.setText("50");
        minorder.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!String.valueOf(charSequence).equals("")){
                    if (Integer.parseInt(String.valueOf(charSequence))>50){
                        minorder.setText("50");
                        minordervalue.setText("50");
                    }
                    else
                        minordervalue.setText(minorder.getText());
                }
                else if(String.valueOf(charSequence).equals(""))
                    minordervalue.setText("1");
                else
                    minordervalue.setText(minorder.getText());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        seekbar.setOnSeekbarChangeListener(new OnSeekbarChangeListener() {
            @Override
            public void valueChanged(Number value) {
                minorder.setText(String.valueOf(value));
                minordervalue.setText(String.valueOf(value));
            }
        });
    }
    private void initRangeSeekbar(){
        formatter = new DecimalFormat("#,###,###");
        minpricerange.setFilters(new InputFilter[]{new InputFilterMinMax("0","100000000")});
        maxpricerange.setFilters(new InputFilter[]{new InputFilterMinMax("0","100000000")});
        minprice.setText(formatter.format(Double.parseDouble("100000")));
        maxprice.setText(formatter.format(Double.parseDouble("100000000")));
        minpricerangevalue.setText(formatter.format(Double.parseDouble("100000")));
        maxpricerangevalue.setText(formatter.format(Double.parseDouble("100000000")));
        minpricerange.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!String.valueOf(charSequence).equals("")){
                    if (Integer.parseInt(String.valueOf(charSequence))>100000000){
                        minpricerange.setText("100000000");
                        minprice.setText(formatter.format(Double.parseDouble("100000000")));
                    }
                    else
                        minprice.setText(formatter.format(Double.parseDouble(minpricerange.getText().toString())));
                }
                else if(String.valueOf(charSequence).equals(""))
                    minprice.setText(formatter.format(Double.parseDouble("100000")));
                else
                    minprice.setText(formatter.format(Double.parseDouble(minpricerange.getText().toString())));
            }
            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        minpricerange.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(hasWindowFocus()){
                    if(!String.valueOf(minpricerange.getText()).equals("")){
                        if(Integer.parseInt(String.valueOf(minpricerange.getText()))<100000){
                            minpricerange.setText("100000");
                            minprice.setText(formatter.format(Double.parseDouble("100000")));
                        }
                        else
                            minprice.setText(formatter.format(Double.parseDouble(minpricerange.getText().toString())));
                    }
                    else if(String.valueOf(minpricerange.getText()).equals(""))
                        minprice.setText(formatter.format(Double.parseDouble("100000")));
                    else
                        minprice.setText(formatter.format(Double.parseDouble(minpricerange.getText().toString())));
                }
            }
        });
        maxpricerange.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!String.valueOf(charSequence).equals("")){
                    if (Integer.parseInt(String.valueOf(charSequence))>100000000){
                        maxpricerange.setText("100000000");
                        maxprice.setText(formatter.format(Double.parseDouble("100000000")));
                    }
                    else
                        maxprice.setText(formatter.format(Double.parseDouble(maxpricerange.getText().toString())));
                }
                else if(String.valueOf(charSequence).equals(""))
                    maxprice.setText(formatter.format(Double.parseDouble("100000")));
                else
                    maxprice.setText(formatter.format(Double.parseDouble(maxpricerange.getText().toString())));
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        maxpricerange.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(hasWindowFocus()){
                    if(!String.valueOf(maxpricerange.getText()).equals("")){
                        if(Integer.parseInt(String.valueOf(maxpricerange.getText()))<100000){
                            maxpricerange.setText("100000");
                            maxprice.setText(formatter.format(Double.parseDouble("100000")));
                        }
                        else
                            maxprice.setText(formatter.format(Double.parseDouble(maxpricerange.getText().toString())));
                    }
                    else if(String.valueOf(maxpricerange.getText()).equals(""))
                        maxprice.setText(formatter.format(Double.parseDouble("100000")));
                    else
                        maxprice.setText(formatter.format(Double.parseDouble(maxpricerange.getText().toString())));
                }
            }
        });
        rangeseekbar.setOnRangeSeekbarChangeListener(new OnRangeSeekbarChangeListener() {
            @Override
            public void valueChanged(Number minValue, Number maxValue) {
                minprice.setText(formatter.format(Double.parseDouble(String.valueOf(minValue))));
                maxprice.setText(formatter.format(Double.parseDouble(String.valueOf(maxValue))));
                minpricerange.setText(String.valueOf(minValue));
                minpricerangevalue.setText(formatter.format(Double.parseDouble(String.valueOf(minValue))));
                maxpricerangevalue.setText(formatter.format(Double.parseDouble(String.valueOf(maxValue))));
                maxpricerange.setText(String.valueOf(maxValue));
            }
        });
    }
    private void initView(){
        ButterKnife.bind(this);
    }

    private void setuptoolbar(){
        setSupportActionBar(toolbar);
        final Drawable arrow = getResources().getDrawable(R.drawable.abc_ic_ab_back_material);
        arrow.setColorFilter(getResources().getColor(R.color.black), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(arrow);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Filter");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
                overridePendingTransition(R.anim.slideleft, R.anim.fadeout);
                finish();
            }
        });
    }
    private void setupListView(){
        Collections.addAll(cityList, cityString);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 3);
        rvcity.setLayoutManager(layoutManager);
        rvcity.setItemAnimator(new DefaultItemAnimator());
        rvcity.setAdapter(new FilterRVAdapter(this, cityList));
    }
}
