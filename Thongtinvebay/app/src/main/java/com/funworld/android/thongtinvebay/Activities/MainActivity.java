package com.funworld.android.thongtinvebay.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioGroup;

import com.funworld.android.thongtinvebay.R;

public class MainActivity extends AppCompatActivity {
    String[] mLocationArray;
    boolean isOneWayFlight = true;
    LinearLayout llReturnLayout;
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

        //init data
        mLocationArray = getResources().getStringArray(R.array.locations);
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

}
