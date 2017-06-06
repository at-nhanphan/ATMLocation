package com.example.admin.findatm.services;

import android.content.Context;
import android.widget.Toast;

import com.example.admin.findatm.configs.ConfigRetrofit;
import com.example.admin.findatm.interfaces.ATMService;
import com.example.admin.findatm.interfaces.CallBack;
import com.example.admin.findatm.models.APIResponse;
import com.example.admin.findatm.models.MyATM;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * ATMServiceImpl class
 * Created by Admin on 3/11/2017.
 */

public class ATMServiceImpl {

    public static final String BASE_URL = "http://at-nhanphan.890m.com/";
    private ATMService mService;
    private final Context mContext;

    public ATMServiceImpl(Context context) {
        this.mContext = context;
    }

    public void getATM(double lat, double lng, int radius, final CallBack<ArrayList<MyATM>> callBack) {
        mService = ConfigRetrofit.getClient().create(ATMService.class);
        Call<APIResponse> atms = mService.getAllATM(lat, lng, radius);
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
        mService = ConfigRetrofit.getClient().create(ATMService.class);
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
