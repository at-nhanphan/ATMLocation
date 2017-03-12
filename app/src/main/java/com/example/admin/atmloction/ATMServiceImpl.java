package com.example.admin.atmloction;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Admin on 3/11/2017.
 */

public class ATMServiceImpl {
    private Context context;
    public ATMServiceImpl(Context context){
        this.context=context;
    }
    public void getATM(String atm,final  CallBack<ArrayList<ATM>> callBack){
        ATMService ser=ConfigRetrofit.getClient().create(ATMService.class);
        Call<APIResponse> moi=ser.getATM(atm,"AIzaSyAOUO-7u3KmU9J3slECcUq_7gWryhp1Mhw");
        moi.enqueue(new Callback<APIResponse>() {
            @Override
            public void onResponse(Call<APIResponse> call, Response<APIResponse> response) {
                 callBack.next(response.body().getAtms());
            }

            @Override
            public void onFailure(Call<APIResponse> call, Throwable t) {
                Toast.makeText(context,t.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }

}
