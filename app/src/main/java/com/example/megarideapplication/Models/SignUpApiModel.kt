package com.example.megarideapplication.Models
import com.google.gson.annotations.SerializedName


public class SignUpApiModel(
    @SerializedName("data")
    val `data`: Data = Data(),
    @SerializedName("message")
    val message: String = "",
    @SerializedName("status")
    val status: Boolean = false
) {
    data class Data(
        @SerializedName("a_code")
        val aCode: String = "",
        @SerializedName("code")
        val code: String = "",
        @SerializedName("created_at")
        val createdAt: String = "",
        @SerializedName("email")
        val email: String = "",
        @SerializedName("email_code")
        val emailCode: String = "",
        @SerializedName("f_code")
        val fCode: String = "",
        @SerializedName("fb_token")
        val fbToken: String = "",
        @SerializedName("g_token")
        val gToken: String = "",
        @SerializedName("id")
        val id: String = "",
        @SerializedName("image")
        val image: String = "",
        @SerializedName("modified")
        val modified: String = "",
        @SerializedName("name")
        val name: String = "",
        @SerializedName("nationality")
        val nationality: String = "",
        @SerializedName("password")
        val password: String = "",
        @SerializedName("phone_number")
        val phoneNumber: String = "",
        @SerializedName("status")
        val status: String = "",
        @SerializedName("user_type")
        val userType: String = ""
    )
}