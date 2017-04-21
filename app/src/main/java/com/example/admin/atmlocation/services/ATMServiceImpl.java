package com.example.admin.atmlocation.services;

import android.content.Context;
import android.widget.Toast;

import com.example.admin.atmlocation.configs.ConfigRetrofit;
import com.example.admin.atmlocation.interfaces.ATMService;
import com.example.admin.atmlocation.interfaces.CallBack;
import com.example.admin.atmlocation.models.APIResponse;
import com.example.admin.atmlocation.models.MyATM;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * ATMServiceImpl class
 * Created by Admin on 3/11/2017.
 */

public class ATMServiceImpl {

    private Context mContext;
    private static final String BASE_URL = "http://at-nhanphan.890m.com/";

    public ATMServiceImpl(Context context) {
        this.mContext = context;
    }

    private ATMService mService = ConfigRetrofit.getClient(BASE_URL).create(ATMService.class);


    public void getATM(final CallBack<ArrayList<MyATM>> callBack) {
        Call<APIResponse> atms = mService.getAllATM();
        atms.enqueue(new Callback<APIResponse>() {
            @Override
            public void onResponse(Call<APIResponse> call, Response<APIResponse> response) {
                if (response.isSuccessful()) {
                callBack.next(response.body().getMyATMs());
                }
            }

            @Override
            public void onFailure(Call<APIResponse> call, Throwable t) {
                Toast.makeText(mContext, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void getAtmSearch(String bank, String district, final CallBack<ArrayList<MyATM>> callBack) {
        Call<APIResponse> nearATMs = mService.getATMSearch(bank, district);
        nearATMs.enqueue(new Callback<APIResponse>() {
            @Override
            public void onResponse(Call<APIResponse> call, Response<APIResponse> response) {
                if (response.isSuccessful()) {
                    callBack.next(response.body().getMyATMs());
                }
            }

            @Override
            public void onFailure(Call<APIResponse> call, Throwable t) {
                Toast.makeText(mContext, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

}
