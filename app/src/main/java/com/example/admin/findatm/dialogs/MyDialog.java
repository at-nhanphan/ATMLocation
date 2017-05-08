package com.example.admin.findatm.dialogs;

import android.content.Context;
import android.support.v7.app.AlertDialog;

/**
 * Custom dialog
 * Created by naunem on 08/04/2017.
 */

public class MyDialog {
    public static AlertDialog.Builder createAlertDialog(Context context, String title, String message) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(message);
        return alertDialog;
    }
}
