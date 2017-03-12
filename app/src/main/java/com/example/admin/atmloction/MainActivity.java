package com.example.admin.atmloction;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ATMListAdapter adapter;
    ArrayList<ATM> atms;
    ATMServiceImpl atmService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewList);
        LinearLayoutManager ln = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(ln);
        atms=new ArrayList<>();
        adapter = new ATMListAdapter(this,atms);
        atmService=new ATMServiceImpl(this);
        atmService.getATM("ATM+hoa+khanh+da+nang", new CallBack<ArrayList<ATM>>() {
            @Override
            public void next(ArrayList<ATM> atm) {
                Log.d("ccclclcl","djfhdjfh:"+atms.size());
                atms.addAll(atm);
                adapter.notifyDataSetChanged();
            }
        });

        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.search:
                break;
            case R.id.imgFavorite:
                break;
            case R.id.about:
                break;
            case R.id.setting:
                break;
        }
        return false;
    }
}
