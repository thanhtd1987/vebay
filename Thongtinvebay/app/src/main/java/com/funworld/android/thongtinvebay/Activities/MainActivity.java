package com.funworld.android.thongtinvebay.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.funworld.android.thongtinvebay.R;
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    String[] mLocationArray, mOriginalPriceArray;
    boolean isOneWayFlight = true;
    LinearLayout llReturnLayout;
    TextView tvPriceNet;
    AutoCompleteTextView actOriPriceArrival;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_ui_2);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
        RadioGroup rgFlightType = (RadioGroup)findViewById(R.id.rg_flight_type);
        llReturnLayout = (LinearLayout) findViewById(R.id.ll_return_layout);
        actOriPriceArrival = (AutoCompleteTextView) findViewById(R.id.act_ori_price_arrival);
        tvPriceNet = (TextView) findViewById(R.id.tv_price_net);

        //init data
        mLocationArray = getResources().getStringArray(R.array.locations);
        mOriginalPriceArray = getResources().getStringArray(R.array.predict_prices);
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line, mOriginalPriceArray);
//        actOriPriceArrival.setAdapter(adapter);
        proccessDataJson();

        actOriPriceArrival.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.d("autotext","----------------pos:"+position+" ----net: "+map.get(position).second);
                tvPriceNet.setText(map.get(position).second);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //set event
        rgFlightType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.rd_one_way) {
                    isOneWayFlight = true;
                    llReturnLayout.setVisibility(View.GONE);
                }
                else { //R.id.rd_return
                    isOneWayFlight = false;
                    llReturnLayout.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = getAssets().open("data_vietjet.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }
    List<Pair<String, String>> map = new ArrayList<>();
    public void proccessDataJson()
    {
        try {
            JSONObject obj = new JSONObject(loadJSONFromAsset());
//            JSONArray arrayPrices = obj.getJSONArray("prices");
            Type listType = new TypeToken<List<price>>() {}.getType();
            List<price> list = (new Gson()).fromJson(obj.getJSONArray("prices").toString(), listType);
            List<String> strlist =  new ArrayList<>();

            for(price p : list)
            {
                strlist.add(p.getNet());
                map.add(new Pair<String, String>(p.getOriginal(),p.getNet()));
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line, strlist.toArray(new String[strlist.size()]));
            actOriPriceArrival.setAdapter(adapter);
//            ArrayList<HashMap<String, String>> formList = new ArrayList<HashMap<String, String>>();


//
//            for (int i = 0; i < m_jArry.length(); i++) {
//                JSONObject jo_inside = m_jArry.getJSONObject(i);
//                Log.d("Details-->", jo_inside.getString("formule"));
//                String formula_value = jo_inside.getString("formule");
//                String url_value = jo_inside.getString("url");
//
//                //Add your values in your `ArrayList` as below:
//                m_li = new HashMap<String, String>();
//                m_li.put("formule", formula_value);
//                m_li.put("url", url_value);
//
//                formList.add(m_li);
//            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    class price{
        @SerializedName("ori")
        String original;
        @SerializedName("net")
        String net;

        public String getNet() {
            return net;
        }

        public String getOriginal() {
            return original;
        }
    }
}
