package com.example.megarideapplication.utilis;

import android.content.Context;
import android.graphics.Color;

import com.kaopiz.kprogresshud.KProgressHUD;

public class UtilsProgressbar {
    public static KProgressHUD showProgressDialog(Context context) {

        return KProgressHUD.create(context)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setCancellable(true)
                .setAnimationSpeed(2)
//                .setBackgroundColor(Color.WHITE)
                .setWindowColor(Color.parseColor("#C4C4C6"))
//                .setDetailsLabel("", Color.GRAY)
                .setDimAmount(0.5f);

    }
}
