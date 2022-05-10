package com.example.megarideapplication.utilis;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

public class ShareMemory {

    public static String sharename = "MegaRide";

    SharedPreferences prefs;

    @SuppressLint("StaticFieldLeak")
    public static Context mContext;
    @SuppressLint("StaticFieldLeak")
    public static ShareMemory mInstence;

    public boolean isThisSessionFromLink;

    public ShareMemory(Context ctx) {
        prefs = ctx.getSharedPreferences(sharename, Context.MODE_PRIVATE);
        isThisSessionFromLink = false;
    }

    public static void init(Context context) {
        mContext = context;
    }

    public static ShareMemory getmInstence() {
        if (mInstence != null) {
            return mInstence;
        } else {
            mInstence = new ShareMemory(mContext.getApplicationContext());
        }
        return mInstence;
    }


    /* public void setClientService(String clientService) {
         prefs.edit().putString("clientService", clientService).apply();
     }
     public String getClientService() {
         return prefs.getString("clientService", null);
     }

     public void setAuthKey(String authKey) {
         prefs.edit().putString("authKey", authKey).apply();
     }

     public String getAuthKey() {
         return prefs.getString("authKey", null);
     }

     public void setContentType(String authKey) {
         prefs.edit().putString("Content_Type", authKey).apply();
     }

     public String getContentType() {
         return prefs.getString("Content_Type", null);
     }

     public void setUserToken(String userToken) {
         prefs.edit().putString("userToken", userToken).apply();
     }

     public String getUserToken() {
         return prefs.getString("userToken", null);
     }*/
    public void setUserName(String userName){
        prefs.edit().putString("userName", userName).apply();
    }
    public String getUserName(){
        return prefs.getString("userName", null);
    }
    public void setUserEmail(String userEmail){
        prefs.edit().putString("userEmail", userEmail).apply();
    }
    public String getUserEmail(){
        return prefs.getString("userEmail", null);
    }
    public void setUserId(String userId) {

        prefs.edit().putString("userId", userId).apply();
    }

    public String getUserId() {
        return prefs.getString("userId", "");
    }


    public void setProfileImageUrl(String imageType) {

        prefs.edit().putString("profileImageUrl", imageType).apply();
    }

    public String getProfileImageUrl() {

        return prefs.getString("profileImageUrl", "");
    }
    public void setClickTime(Integer clickTime) {

        prefs.edit().putInt("clickTime", clickTime).apply();
    }

    public int getClickTime() {

        return prefs.getInt("clickTime", 0);
    }

    public void setAmibitionId(Integer amibitionId , Integer amibitionValue) {

        prefs.edit().putInt(String.valueOf(amibitionId),amibitionValue).apply();
    }

    public int getAmibitionId(Integer amibitionId) {

        return prefs.getInt(String.valueOf(amibitionId), 0);
    }
    public void setAmibitionTime(Integer amibitionid , Integer amibitionTimeValue) {

        prefs.edit().putString(amibitionid+"t",amibitionTimeValue+"").apply();
    }

    public String getAmibitionTime(Integer amibitionid) {

        return prefs.getString(amibitionid+"t", "0");
    }
    public void deleteAllSharedPrefs(){
        prefs.edit().clear().apply();
    }

}
