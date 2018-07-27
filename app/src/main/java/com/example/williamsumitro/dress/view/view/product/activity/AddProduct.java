package com.example.williamsumitro.dress.view.view.product.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.williamsumitro.dress.R;
import com.example.williamsumitro.dress.view.model.Addproduct_Price;
import com.example.williamsumitro.dress.view.model.dress_attribute.Decoration;
import com.example.williamsumitro.dress.view.model.dress_attribute.DressAttribute;
import com.example.williamsumitro.dress.view.model.dress_attribute.Fabrictype;
import com.example.williamsumitro.dress.view.model.dress_attribute.Material;
import com.example.williamsumitro.dress.view.model.dress_attribute.Neckline;
import com.example.williamsumitro.dress.view.model.dress_attribute.Patterntype;
import com.example.williamsumitro.dress.view.model.dress_attribute.Season;
import com.example.williamsumitro.dress.view.model.dress_attribute.Size;
import com.example.williamsumitro.dress.view.model.dress_attribute.Sleevelength;
import com.example.williamsumitro.dress.view.model.dress_attribute.Style;
import com.example.williamsumitro.dress.view.model.dress_attribute.Waiseline;
import com.example.williamsumitro.dress.view.presenter.api.apiService;
import com.example.williamsumitro.dress.view.presenter.api.apiUtils;
import com.example.williamsumitro.dress.view.presenter.helper.FinancialTextWatcher;
import com.example.williamsumitro.dress.view.presenter.session.SessionManagement;
import com.example.williamsumitro.dress.view.view.product.adapter.Decoration_Spinner_Adapter;
import com.example.williamsumitro.dress.view.view.product.adapter.Fabrictype_Spinner_Adapter;
import com.example.williamsumitro.dress.view.view.product.adapter.Material_Spinner_Adapter;
import com.example.williamsumitro.dress.view.view.product.adapter.Neckline_Spinner_Adapter;
import com.example.williamsumitro.dress.view.view.product.adapter.Patterntype_Spinner_Adapter;
import com.example.williamsumitro.dress.view.view.product.adapter.Price_Spinner_Adapter;
import com.example.williamsumitro.dress.view.view.product.adapter.Season_Spinner_Adapter;
import com.example.williamsumitro.dress.view.view.product.adapter.Size_RV_Adapter;
import com.example.williamsumitro.dress.view.view.product.adapter.Size_Spinner_Adapter;
import com.example.williamsumitro.dress.view.view.product.adapter.SleeveLength_Spinner_Adapter;
import com.example.williamsumitro.dress.view.view.product.adapter.Style_Spinner_Adapter;
import com.example.williamsumitro.dress.view.view.product.adapter.Waiseline_Spinner_Adapter;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import es.dmoral.toasty.Toasty;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddProduct extends AppCompatActivity {
    @BindView(R.id.addproduct_container) NestedScrollView container;
    @BindView(R.id.addproduct_toolbar) Toolbar toolbar;
    @BindView(R.id.addproduct_spinner_Waiseline) Spinner waiseline;
    @BindView(R.id.addproduct_spinner_Style) Spinner style;
    @BindView(R.id.addproduct_spinner_SleeveLength) Spinner sleevelength;
    @BindView(R.id.addproduct_spinner_Season) Spinner season;
    @BindView(R.id.addproduct_spinner_PatternType) Spinner patterntype;
    @BindView(R.id.addproduct_spinner_NeckLine) Spinner neckline;
    @BindView(R.id.addproduct_spinner_Material) Spinner material;
    @BindView(R.id.addproduct_spinner_FabricType) Spinner fabrictype;
    @BindView(R.id.addproduct_spinner_Decoration) Spinner decoration;
    @BindView(R.id.addproduct_layout_weight) TextInputLayout layout_weight;
    @BindView(R.id.addproduct_layout_name) TextInputLayout layout_name;
    @BindView(R.id.addproduct_layout_minorder) TextInputLayout layout_minorder;
    @BindView(R.id.addproduct_layout_description) TextInputLayout layout_description;
    @BindView(R.id.addproduct_et_weight) TextInputEditText weight;
    @BindView(R.id.addproduct_et_name) TextInputEditText name;
    @BindView(R.id.addproduct_et_MinOrder) TextInputEditText minorder;
    @BindView(R.id.addproduct_et_description) TextInputEditText description;
    @BindView(R.id.addproduct_civ_picture) CircleImageView picture;
    @BindView(R.id.addproduct_btn_addpricerange) Button addpricerange;
    @BindView(R.id.addproduct_ln_layout) LinearLayout layout;
    @BindView(R.id.addproduct_btn_submit) Button submit;
    @BindView(R.id.addproduct_tv_size) TextView tv_size;
    @BindView(R.id.addproduct_rvsize) RecyclerView rv_size;
    @BindView(R.id.addproduct_lnsize) LinearLayout lnsize;
    @BindView(R.id.addproduct_ln_sizedetail) LinearLayout lnsizedetail;
    @BindView(R.id.addproduct_img_caret) ImageView caret;
    @BindView(R.id.addproduct_btnclearallsize) Button clearallsize;
    @BindView(R.id.addproduct_btncheckallsize) Button checkallsize;
    @BindView(R.id.addproduct_btnapplysize) Button applyallsize;


    private static final int SELECT_PHOTO = 0;
    private ArrayList<String> list_size_result = new ArrayList<String>();

    private Context context;
    private int index=0;
    private DecimalFormat formatter;
    private apiService service;
    private List<Addproduct_Price> container_price;
    private List<Decoration> decorationList = new ArrayList<>();
    private List<Fabrictype> fabrictypeList = new ArrayList<>();
    private List<Material> materialList = new ArrayList<>();
    private List<Neckline> necklineList = new ArrayList<>();
    private List<Patterntype> patterntypeList = new ArrayList<>();
    private List<com.example.williamsumitro.dress.view.model.dress_attribute.Price> priceList = new ArrayList<>();
    private List<Season> seasonList = new ArrayList<>();
    private List<Size> sizeList = new ArrayList<>();
    private List<Sleevelength> sleevelengthList = new ArrayList<>();
    private List<Style> styleList = new ArrayList<>();
    private List<Waiseline> waiselineList = new ArrayList<>();
    private Decoration_Spinner_Adapter decoration_spinner_adapter;
    private Fabrictype_Spinner_Adapter fabrictype_spinner_adapter;
    private Material_Spinner_Adapter material_spinner_adapter;
    private Neckline_Spinner_Adapter neckline_spinner_adapter;
    private Patterntype_Spinner_Adapter patterntype_spinner_adapter;
    private Price_Spinner_Adapter price_spinner_adapter;
    private Season_Spinner_Adapter season_spinner_adapter;
    private Size_Spinner_Adapter size_spinner_adapter;
    private SleeveLength_Spinner_Adapter sleeveLength_spinner_adapter;
    private Style_Spinner_Adapter style_spinner_adapter;
    private Waiseline_Spinner_Adapter waiseline_spinner_adapter;
    private String decoration_id, fabrictype_id, material_id, neckline_id, patterntype_id, price_id, season_id, size_id, sleevelength_id, style_id, waiseline_id,
            mediapathPhoto, token;
    private AlertDialog dialog;
    private Drawable camera;
    private Boolean size_click = false;
    private SessionManagement sessionManagement;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        initView();
        setuptoolbar();
        api_getdressattributes();
        initOnClick();

        minorder.addTextChangedListener(new FinancialTextWatcher(minorder));
        weight.addTextChangedListener(new FinancialTextWatcher(weight));
    }
    private void initView(){
        ButterKnife.bind(this);
        context = this;
        camera = getResources().getDrawable(R.drawable.addcamera);
        picture.setImageDrawable(camera);
        sessionManagement = new SessionManagement(getApplicationContext());
        progressDialog = new ProgressDialog(this);
        service = apiUtils.getAPIService();
    }
    private void setuptoolbar(){
        setSupportActionBar(toolbar);
        final Drawable arrow = getResources().getDrawable(R.drawable.abc_ic_ab_back_material);
        arrow.setColorFilter(getResources().getColor(R.color.black), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(arrow);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Add Product");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
                overridePendingTransition(R.anim.slideleft, R.anim.fadeout);
                finish();
            }
        });
    }
    private void initOnClick(){
        picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initupload(SELECT_PHOTO);
            }
        });
        addpricerange.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
            @Override
            public void onClick(View view) {
                formatter = new DecimalFormat("#,###,###");
                int maxlength = 11;
                InputFilter[] fArray = new InputFilter[1];
                fArray[0] = new InputFilter.LengthFilter(maxlength);
                LinearLayout mainLinearLayout = new LinearLayout(context);
                LinearLayout.LayoutParams mainParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                mainLinearLayout.setOrientation(LinearLayout.VERTICAL);
                mainLinearLayout.setLayoutParams(mainParams);
                mainLinearLayout.setGravity(Gravity.CENTER);

                LinearLayout firstChildLinearLayout = new LinearLayout(context);
                LinearLayout.LayoutParams firstChildParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);

                firstChildLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
                firstChildLinearLayout.setLayoutParams(firstChildParams);

                LinearLayout.LayoutParams etParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                etParams.width = 150;

                TextView row = new TextView(context);
                LinearLayout.LayoutParams txtParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);
                txtParams.setMarginStart(2);
                txtParams.setMarginEnd(2);
                row.setGravity(Gravity.CENTER_VERTICAL);
                row.setLayoutParams(txtParams);
                row.setTextSize(12);
                row.setText(String.valueOf(index+1) + ". ");

                final EditText qtymin = new EditText(context);
                qtymin.setInputType(InputType.TYPE_CLASS_NUMBER);
                qtymin.setHint("Qty (Min)");
                qtymin.setLayoutParams(etParams);
                qtymin.setTextSize(12);
                qtymin.setFilters(fArray);
                qtymin.setSingleLine(true);
                qtymin.setMaxLines(1);
                qtymin.addTextChangedListener(new FinancialTextWatcher(qtymin));


                TextView textView = new TextView(context);
                txtParams.setMarginStart(5);
                txtParams.setMarginEnd(10);
                textView.setGravity(Gravity.CENTER_VERTICAL);
                textView.setLayoutParams(txtParams);
                textView.setTextSize(12);
                textView.setText("between");

                final EditText qtymax = new EditText(context);
                qtymax.setHint("Qty (Max)");
                qtymax.setLayoutParams(etParams);
                qtymax.setInputType(InputType.TYPE_CLASS_NUMBER);
                qtymax.setTextSize(12);
                qtymax.setFilters(fArray);
                qtymax.setSingleLine(true);
                qtymax.setMaxLines(1);
                qtymax.addTextChangedListener(new FinancialTextWatcher(qtymax));

                Button button = new Button(context);
                LinearLayout.LayoutParams buttonParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);
                buttonParams.width = 100;
                buttonParams.height = 50;
                button.setText("Max");
                button.setTextColor(getResources().getColor(R.color.white));
                button.setBackgroundResource(R.drawable.button_1_3_mini);
                button.setTextSize(10);
                button.setWidth(20);
                button.setHeight(10);
                button.setLayoutParams(buttonParams);

                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        qtymax.setText("max");
                    }
                });

                LinearLayout.LayoutParams etprice = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                etprice.weight = 1;
                EditText price = new EditText(context);
                price.setHint("Price");
                price.setLayoutParams(etprice);
                price.setInputType(InputType.TYPE_CLASS_NUMBER);
                price.setTextSize(12);
                price.setFilters(fArray);
                price.setSingleLine(true);
                price.setMaxLines(1);
                price.addTextChangedListener(new FinancialTextWatcher(price));

                firstChildLinearLayout.addView(row, 0);
                firstChildLinearLayout.addView(qtymin, 1);
                firstChildLinearLayout.addView(textView, 2);
                firstChildLinearLayout.addView(qtymax, 3);
                firstChildLinearLayout.addView(button, 4);
                firstChildLinearLayout.addView(price, 5);

                LinearLayout secondChildLinearLayout = new LinearLayout(context);
                LinearLayout.LayoutParams secondChildParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 1);
                secondChildParams.setMargins(0,5,0,5);
                secondChildLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
                secondChildLinearLayout.setBackgroundColor(getResources().getColor(R.color.grey));
                secondChildLinearLayout.setLayoutParams(secondChildParams);

                mainLinearLayout.addView(firstChildLinearLayout, 0);
                mainLinearLayout.addView(secondChildLinearLayout, 1);
                layout.addView(mainLinearLayout, index);
                index++;
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                api_addproduct();
            }
        });

    }
    private void getAllEditTextValues() {
        View v = null;
        container_price = new ArrayList<>();
        for (int i = 0; i < layout.getChildCount(); i++) {
            v = layout.getChildAt(i);
            if (v instanceof LinearLayout) {
                View tempView = ((LinearLayout) v).getChildAt(0);
                View et_qtymin = ((LinearLayout) tempView).getChildAt(1);
                View et_qtymax = ((LinearLayout) tempView).getChildAt(3);
                View et_price = ((LinearLayout) tempView).getChildAt(5);
                String et_minvalue = null;
                String et_maxvalue = null;
                String et_pricevalue = null;
                if (et_qtymin instanceof EditText) {
                    et_minvalue = FinancialTextWatcher.trimCommaOfString(((EditText) et_qtymin).getText().toString());
                    et_maxvalue = FinancialTextWatcher.trimCommaOfString(((EditText) et_qtymax).getText().toString());
                    et_pricevalue = FinancialTextWatcher.trimCommaOfString(((EditText) et_price).getText().toString());
                    container_price.add(new Addproduct_Price(et_minvalue, et_maxvalue, et_pricevalue));
                }

            }
        }
    }
    private void api_getdressattributes(){
        progressDialog.setMessage("Preparing ...");
        progressDialog.show();
        progressDialog.setCancelable(false);
        service.req_get_dress_attributes().enqueue(new Callback<DressAttribute>() {
            @Override
            public void onResponse(Call<DressAttribute> call, Response<DressAttribute> response) {
                if(response.code() == 200){
                    decorationList = response.body().getDecoration();
                    decoration_spinner_adapter = new Decoration_Spinner_Adapter(context, R.layout.item_spinner, decorationList);
                    decoration.setAdapter(decoration_spinner_adapter);
                    decoration.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            Decoration decoration = decoration_spinner_adapter.getItem(i);
                            decoration_id = String.valueOf(decoration.getDecorationId());
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });
                    fabrictypeList = response.body().getFabrictype();
                    fabrictype_spinner_adapter = new Fabrictype_Spinner_Adapter(context, R.layout.item_spinner, fabrictypeList);
                    fabrictype.setAdapter(fabrictype_spinner_adapter);
                    fabrictype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            Fabrictype fabrictype = fabrictype_spinner_adapter.getItem(i);
                            fabrictype_id = String.valueOf(fabrictype.getFabrictypeId());
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });
                    materialList = response.body().getMaterial();
                    material_spinner_adapter = new Material_Spinner_Adapter(context, R.layout.item_spinner, materialList);
                    material.setAdapter(material_spinner_adapter);
                    material.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            Material material = material_spinner_adapter.getItem(i);
                            material_id = String.valueOf(material.getMaterialId());
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });
                    necklineList = response.body().getNeckline();
                    neckline_spinner_adapter = new Neckline_Spinner_Adapter(context, R.layout.item_spinner, necklineList);
                    neckline.setAdapter(neckline_spinner_adapter);
                    neckline.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            Neckline neckline = neckline_spinner_adapter.getItem(i);
                            neckline_id = String.valueOf(neckline.getNecklineId());
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });
                    patterntypeList = response.body().getPatterntype();
                    patterntype_spinner_adapter = new Patterntype_Spinner_Adapter(context, R.layout.item_spinner, patterntypeList);
                    patterntype.setAdapter(patterntype_spinner_adapter);
                    patterntype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            Patterntype patterntype = patterntype_spinner_adapter.getItem(i);
                            patterntype_id = String.valueOf(patterntype.getPatterntypeId());
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });

                    seasonList = response.body().getSeason();
                    season_spinner_adapter = new Season_Spinner_Adapter(context, R.layout.item_spinner, seasonList);
                    season.setAdapter(season_spinner_adapter);
                    season.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            Season season = season_spinner_adapter.getItem(i);
                            season_id = String.valueOf(season.getSeasonId());
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });
                    sizeList = response.body().getSize();

                    RecyclerView.LayoutManager layoutManager = new GridLayoutManager(context, 3);
                        rv_size.setLayoutManager(layoutManager);
                        rv_size.setItemAnimator(new DefaultItemAnimator());
                        rv_size.setAdapter(new Size_RV_Adapter(context, sizeList));
                        checkallsize.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                int count = rv_size.getAdapter().getItemCount();
                                for(int i = 0; i< count; i++){
                                    LinearLayout item = (LinearLayout) rv_size.getChildAt(i);
                                    CheckBox checkBox = (CheckBox) item.findViewById(R.id.itemgridviewfilter_checkbox);
                                    checkBox.setChecked(true);
                                }
                            }
                        });
                        clearallsize.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                int count = rv_size.getAdapter().getItemCount();
                                for(int i = 0; i< count; i++){
                                    LinearLayout item = (LinearLayout) rv_size.getChildAt(i);
                                    CheckBox checkBox = (CheckBox) item.findViewById(R.id.itemgridviewfilter_checkbox);
                                    checkBox.setChecked(false);
                                }
                            }
                        });
                        applyallsize.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                StringBuilder stringBuilder = new StringBuilder();
                                int count = rv_size.getAdapter().getItemCount();
                                list_size_result.clear();
                                for(int i = 0; i< count; i++){
                                    LinearLayout item = (LinearLayout) rv_size.getChildAt(i);
                                    CheckBox checkBox = (CheckBox) item.findViewById(R.id.itemgridviewfilter_checkbox);
                                    TextView size_name = (TextView) item.findViewById(R.id.itemgridviewfilter_tvnamakota);
                                    if(checkBox.isChecked()){
                                        list_size_result.add(checkBox.getTag().toString());
                                        stringBuilder.append(size_name.getText());
                                        stringBuilder.append(" ");
                                    }
                                }
                                int count1 = list_size_result.size();
                                String temp = "";
                                for(int i = 0; i<count1; i++){
                                    if (i!=count1-1)
                                        temp += list_size_result.get(i).toLowerCase() + ", ";
                                    else
                                        temp += list_size_result.get(i).toLowerCase();
                                }
                                tv_size.setText(stringBuilder);
                                for(int i = 0; i< count; i++){
                                    LinearLayout item = (LinearLayout) rv_size.getChildAt(i);
                                    CheckBox checkBox = (CheckBox) item.findViewById(R.id.itemgridviewfilter_checkbox);
                                    checkBox.setChecked(false);
                                    lnsizedetail.setVisibility(View.GONE);
                                    size_click = false;
                                    caret.setImageResource(R.drawable.caret);
                                }
                            }
                        });
                        lnsize.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if (!size_click){
                                    lnsizedetail.setVisibility(View.VISIBLE);
                                    size_click = true;
                                    caret.setImageResource(R.drawable.caret1);
                                }
                                else {
                                    lnsizedetail.setVisibility(View.GONE);
                                    size_click = false;
                                    caret.setImageResource(R.drawable.caret);
                                }
                            }
                        });
                    sleevelengthList = response.body().getSleevelength();
                    sleeveLength_spinner_adapter = new SleeveLength_Spinner_Adapter(context, R.layout.item_spinner, sleevelengthList);
                    sleevelength.setAdapter(sleeveLength_spinner_adapter);
                    sleevelength.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            Sleevelength sleevelength = sleeveLength_spinner_adapter.getItem(i);
                            sleevelength_id = String.valueOf(sleevelength.getSleevelengthId());
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });
                    styleList = response.body().getStyle();
                    style_spinner_adapter = new Style_Spinner_Adapter(context, R.layout.item_spinner, styleList);
                    style.setAdapter(style_spinner_adapter);
                    style.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            Style style = style_spinner_adapter.getItem(i);
                            style_id = String.valueOf(style.getStyleId());
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });
                    waiselineList = response.body().getWaiseline();
                    waiseline_spinner_adapter = new Waiseline_Spinner_Adapter(context, R.layout.item_spinner, waiselineList);
                    waiseline.setAdapter(waiseline_spinner_adapter);
                    waiseline.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            Waiseline waiseline = waiseline_spinner_adapter.getItem(i);
                            waiseline_id = String.valueOf(waiseline.getWaiselineId());
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });

                }
            }

            @Override
            public void onFailure(Call<DressAttribute> call, Throwable t) {

            }
        });
        progressDialog.dismiss();
    }
    public  boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                return true;
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
                return false;
            }
        }
        else {
            return true;
        }
    }
    private void initupload(int RequestCode){
        if(isStoragePermissionGranted()){
            Intent galleryIntent = new Intent(Intent.ACTION_PICK);
            galleryIntent.setType("image/*");
            final Intent chooserIntent = Intent.createChooser(galleryIntent, getString(R.string.string_choose_image));
            startActivityForResult(chooserIntent, RequestCode);
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Intent galleryIntent = new Intent(Intent.ACTION_PICK);
        if(grantResults[0]== PackageManager.PERMISSION_GRANTED){
            galleryIntent.setType("image/*");
            final Intent chooserIntent = Intent.createChooser(galleryIntent, getString(R.string.string_choose_image));
            startActivityForResult(chooserIntent, 0);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (requestCode == SELECT_PHOTO && resultCode == RESULT_OK && null != data) {
                if(data == null){
                    Toasty.error(context, "Unable to pick image", Toast.LENGTH_LONG, true).show();
                }
                Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                if(cursor!=null){
                    cursor.moveToFirst();
                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    mediapathPhoto = cursor.getString(columnIndex);
                    Picasso.with(context).load(new File(mediapathPhoto))
                            .into(picture);
                    cursor.close();
                    dialog.dismiss();
                }
            }
            else {
                Toasty.error(context, "Please Try Again", Toast.LENGTH_LONG, true).show();
            }
        } catch (Exception e){}
    }

    private void api_addproduct(){
        getAllEditTextValues();
        layout_name.setErrorEnabled(false);
        layout_minorder.setErrorEnabled(false);
        layout_weight.setErrorEnabled(false);
        layout_description.setErrorEnabled(false);
        if (TextUtils.isEmpty(name.getText())) {
            layout_name.setErrorEnabled(true);
            layout_name.setError("Name of product is required");
            return;
        } else if (TextUtils.isEmpty(minorder.getText())) {
            layout_minorder.setErrorEnabled(true);
            layout_minorder.setError("Minimum order is required");
            return;
        } else if (TextUtils.isEmpty(weight.getText())) {
            layout_weight.setErrorEnabled(true);
            layout_weight.setError("Investment is required");
            return;
        }else if(picture.getDrawable().equals(camera)){
            Snackbar.make(container, "Please insert your photo", Snackbar.LENGTH_LONG).show();
            return;
        }else if(list_size_result.size() == 0){
            Snackbar.make(container, "Please select the size", Snackbar.LENGTH_LONG).show();
            return;
        }else if(container_price.size() == 0){
            Snackbar.make(container, "Please add your price", Snackbar.LENGTH_LONG).show();
            return;
        }else if(container_price.size()!=0) {
            Boolean check = true;
            int k = 0;
            String error = null;
            String regexStr = "^[0-9]*$";
            for (int i = 0; i < container_price.size() * 3; i++) {
                String qty_min = container_price.get(k).getQty_min(), qty_max = container_price.get(k).getQty_max(), price = container_price.get(k).getprice();
                int min, max, pric;
                if (qty_min.equals("")) {
                    check = false;
                    error = "Please insert the qty minimum at row " + String.valueOf(k + 1);
                    break;
                }
                if (qty_max.equals("")) {
                    check = false;
                    error = "Please insert the qty maximum at row " + String.valueOf(k + 1);
                    break;
                }
                if (!qty_max.trim().matches(regexStr) && !qty_max.equals("max")){
                    check = false;
                    error = "Qty maximum at row " + String.valueOf(k+1) + " can only be input as number or max";
                    break;
                }
                if (price.equals("")) {
                    check = false;
                    error = "Please insert the price at row " + String.valueOf(k + 1);
                    break;
                } else {
                    min = Integer.parseInt(qty_min);
                    pric = Integer.parseInt(price);
                    if (!qty_max.equals("max")) {
                        max = Integer.parseInt(qty_max);
                        if (min > max) {
                            check = false;
                            error = "Qty minimum cannot bigger than qty maximum at row " + String.valueOf(k + 1);
                            break;
                        }
                    }
                }

                if (i % 3 == 2) {
                    k++;
                }
            }
            k = 0;
            for (int i = 0; i <container_price.size() * 3; i++){
                if (k>0){
                    String qty_min = container_price.get(k).getQty_min(), qty_max = container_price.get(k-1).getQty_max(),
                            price_before = container_price.get(k-1).getprice(), price_after = container_price.get(k).getprice();
                    if (qty_max.equals("max")){
                        check = false;
                        error = "Qty max "+ String.valueOf(k)+ " can't set to be max because it is no the last from the price range";
                        break;
                    }
                    if (Integer.parseInt(qty_min) <= Integer.parseInt(qty_max)){
                        check = false;
                        error = "Qty minimum at row " + String.valueOf(k+1) + " must bigger than " + "qty maximum at row " + String.valueOf(k);
                        break;
                    }
                    if (Integer.parseInt(price_after) >= Integer.parseInt((price_before))){
                        check = false;
                        error = "Price at row " + String.valueOf(k+1) + " must below than " + "price at row " + String.valueOf(k);
                        break;
                    }
                }
                if (i % 3 == 2) {
                    k++;
                }
            }
            if (!check) {
                Toasty.error(context, error, Toast.LENGTH_SHORT, true).show();
                return;
            }
        }
        progressDialog.setMessage("Uploading, please wait ....");
        progressDialog.show();

        MediaType text = MediaType.parse("text/plain");

        File filephoto = new File(mediapathPhoto);
        RequestBody requestFileLogo = RequestBody.create(MediaType.parse("multipart/form-data"), filephoto);
        MultipartBody.Part body_photo = MultipartBody.Part.createFormData("photo", filephoto.getName(), requestFileLogo);
        HashMap<String, String> user = sessionManagement.getUserDetails();
        token = user.get(SessionManagement.TOKEN);

        RequestBody request_token = RequestBody.create(text, token);
        RequestBody request_name = RequestBody.create(text, name.getText().toString());
        RequestBody request_minorder = RequestBody.create(text, FinancialTextWatcher.trimCommaOfString(minorder.getText().toString()));
        RequestBody request_weight = RequestBody.create(text, FinancialTextWatcher.trimCommaOfString(weight.getText().toString()));
        RequestBody request_description = RequestBody.create(text, description.getText().toString());
        RequestBody request_style_id = RequestBody.create(text, style_id);
        RequestBody request_season_id = RequestBody.create(text, season_id);
        RequestBody request_neckline_id = RequestBody.create(text, neckline_id);
        RequestBody request_sleevelength_id = RequestBody.create(text, sleevelength_id);
        RequestBody request_waiseline_id = RequestBody.create(text, waiseline_id);
        RequestBody request_material_id = RequestBody.create(text, material_id);
        RequestBody request_fabrictype_id = RequestBody.create(text, fabrictype_id);
        RequestBody request_decoration_id = RequestBody.create(text, decoration_id);
        RequestBody request_patterntype_id = RequestBody.create(text, patterntype_id);

        MultipartBody.Part[] sizes = new MultipartBody.Part[list_size_result.size()];
        for(int i = 0; i<list_size_result.size();i++){
            sizes[i] = MultipartBody.Part.createFormData("size["+i+"]", list_size_result.get(i));
        }
        int panjang = container_price.size() * 3;
        int k = 0;
        MultipartBody.Part[] prices = new MultipartBody.Part[panjang];
        for(int i = 0; i<(panjang);i++){
            if(i%3==0){
                prices[i] = MultipartBody.Part.createFormData("price[" + k + "]" + "[qty_min]", container_price.get(k).getQty_min());
            }
            else if (i%3==1){
                prices[i] = MultipartBody.Part.createFormData("price[" + k + "]" + "[qty_max]", container_price.get(k).getQty_max());
            }
            else {
                prices[i] = MultipartBody.Part.createFormData("price[" + k + "]" + "[price]", container_price.get(k).getprice());
            }
            if(i%3==2){
                k++;
            }

        }

        service = apiUtils.getAPIService();
        service.req_add_product(request_token, request_name, request_minorder, request_weight, request_description, request_style_id, request_season_id, request_neckline_id,
                request_sleevelength_id, request_waiseline_id, request_material_id, request_fabrictype_id, request_decoration_id, request_patterntype_id, sizes, prices, body_photo).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    Log.i("debug", "onResponse: SUCCESS");
                    try{
                        JSONObject jsonResults = new JSONObject(response.body().string());
                        if(jsonResults.getString("message").toLowerCase().equals("product registered successfully ")){
                            String message = jsonResults.getString("message");
                            Toasty.success(context, message, Toast.LENGTH_SHORT, true).show();
                            finish();
                        }else{
                            String message = jsonResults.getString("message");
                            Toasty.error(context, message, Toast.LENGTH_SHORT, true).show();
                        }
                        //"message": "Nama Franchise sudah didaftarkan"
                        //"message": "Franchise registered successfully",
                    }catch (JSONException e){
                        e.printStackTrace();
                    }catch (IOException e){
                        e.printStackTrace();
                    }
                }
                else {
                    Log.i("debug", "onResponse: FAILED");
                }
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }
    private void initanim(Intent intent){
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
        overridePendingTransition(R.anim.slideright, R.anim.fadeout);
    }
}
