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

public class ParameterService {
    RequestQueue requestQueue;
    Context mContext;
    ResponseInterfaces responceInterfaces;
    ResponseErrorInterface responseErrorInterface;
    KProgressHUD kProgressHUD;

    public ParameterService(Context mContext, ResponseInterfaces favouriteInterfaces, ResponseErrorInterface responseErrorInterface) {
        this.mContext = mContext;
        this.responceInterfaces = favouriteInterfaces;
        this.responseErrorInterface = responseErrorInterface;
        this.requestQueue = Volley.newRequestQueue(mContext);
        kProgressHUD = UtilsProgressbar.showProgressDialog(mContext);

    }
    public interface ResponseInterfaces {

        void getResponses(Object o);


    }
    public interface ResponseErrorInterface {
        void getError(Object o);
    }



    public void getData(JSONObject params, final String url) {

        kProgressHUD.show();

        final JsonObjectRequest jsonObject = new JsonObjectRequest(Request.Method.POST, url,  params, response -> {

            kProgressHUD.dismiss();
            responceInterfaces.getResponses(response);

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
                    .build().show();
*/

            responseErrorInterface.getError( error);

        }) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                headers.put("Content-Type","application/json" );

                return headers;
            }
        };

        jsonObject.setRetryPolicy(new DefaultRetryPolicy(6000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        requestQueue.add(jsonObject);
    }
}
