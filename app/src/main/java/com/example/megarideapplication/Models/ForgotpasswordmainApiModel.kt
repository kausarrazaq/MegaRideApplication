package com.example.megarideapplication.Models
import com.google.gson.annotations.SerializedName


public class ForgotpasswordmainApiModel(
    @SerializedName("data")
    val `data`: Data = Data(),
    @SerializedName("message")
    val message: String = "",
    @SerializedName("status")
    val status: Boolean = false
) {
    class Data
}