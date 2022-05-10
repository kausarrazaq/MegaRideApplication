package com.example.megarideapplication.WebServices;

import android.content.Context;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.megarideapplication.utilis.UtilsProgressbar;
import com.kaopiz.kprogresshud.KProgressHUD;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class POSTService {

    Context mContext;
    ResponseInterface responseInterface;
    RequestQueue mRequestQueue;
    KProgressHUD kProgressHUD;
    int requestMethod;

    public POSTService(Context mContext, ResponseInterface responseInterface) {
        this.mContext = mContext;
        this.responseInterface = responseInterface;
        this.mRequestQueue = Volley.newRequestQueue(mContext);
        kProgressHUD = UtilsProgressbar.showProgressDialog(mContext);
    }

    public interface ResponseInterface {
        void getResponse(Object o);

        void finish();
    }

    public interface ResponseErrorInterface {
        void getError();
    }

    public void getDataWithoutParams(String url, int method) {
        kProgressHUD.show();

        if (method == Request.Method.GET) {
            this.requestMethod = method;
        } else {
            this.requestMethod = method;
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(requestMethod, url, null, response -> {

            kProgressHUD.dismiss();
            responseInterface.getResponse(response);

        }, error -> {
            kProgressHUD.dismiss();
//                new iOSDialogBuilder(mContext)
//                        .setTitle(mContext.getString(R.string.title))
//                        .setSubtitle(mContext.getString(R.string.subtitlenet))
//                        .setBoldPositiveLabel(true)
//                        .setCancelable(false)
//                        .setPositiveListener(mContext.getString(R.string.ok1), new iOSDialogClickListener() {
//                            @Override
//                            public void onClick(iOSDialog dialog) {
//                                dialog.dismiss();
//
//                            }
//                        })
//                        .build().show();
        }) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json");
                return headers;
            }
        };
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(6000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        mRequestQueue.add(jsonObjectRequest);
    }

    public void putData(JSONObject params, final String url) {


        kProgressHUD.show();


        final JsonObjectRequest jsonObject = new JsonObjectRequest(Request.Method.PUT, url, params, response -> {

            kProgressHUD.dismiss();
            responseInterface.getResponse(response);

        }, error -> {
            kProgressHUD.dismiss();

            /*new iOSDialogBuilder(mContext)
                    .setTitle(mContext.getString(R.string.title))
                    .setSubtitle(mContext.getString(R.string.subtitlenet))
                    .setBoldPositiveLabel(true)
                    .setCancelable(false)
                    .setPositiveListener(mContext.getString(R.string.ok1), new iOSDialogClickListener() {
                        @Override
                        public void onClick(iOSDialog dialog) {
                            dialog.dismiss();

                        }
                    })
                    .build().show();*/


        }) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json");

                return headers;
            }
        };
        jsonObject.setRetryPolicy(new DefaultRetryPolicy(6000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        mRequestQueue.add(jsonObject);
    }
}
