package com.example.admin.atmlocation.services;

import android.content.Context;
import android.widget.Toast;

import com.example.admin.atmlocation.configs.ConfigRetrofit;
import com.example.admin.atmlocation.interfaces.ATMService;
import com.example.admin.atmlocation.interfaces.CallBack;
import com.example.admin.atmlocation.models.ATM;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 *
 * Created by Admin on 3/11/2017.
 */

public class ATMServiceImpl {

    private Context mContext;
    private final String API_KEY = "AIzaSyAOUO-7u3KmU9J3slECcUq_7gWryhp1Mhw";

    public ATMServiceImpl(Context context) {
        this.mContext = context;
    }

    public void getATM(String atm, final CallBack<ArrayList<ATM>> callBack) {
        ATMService service = ConfigRetrofit.getClient().create(ATMService.class);
        Call<APIResponse> arrs = service.getATM(atm, API_KEY);
        arrs.enqueue(new Callback<APIResponse>() {
            @Override
            public void onResponse(Call<APIResponse> call, Response<APIResponse> response) {
                callBack.next(response.body().getAtms());
            }

            @Override
            public void onFailure(Call<APIResponse> call, Throwable t) {
                Toast.makeText(mContext, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

}
