package com.example.megarideapplication.Models
import com.google.gson.annotations.SerializedName


public class RiderequestcarownerModel(
    @SerializedName("data")
    val `data`: Data = Data(),
    @SerializedName("message")
    val message: String = "",
    @SerializedName("status")
    val status: Boolean = false
) {
    data class Data(
        @SerializedName("available_seats")
        val availableSeats: String = "",
        @SerializedName("car_model")
        val carModel: String = "",
        @SerializedName("date")
        val date: String = "",
        @SerializedName("id")
        val id: String = "",
        @SerializedName("no_plate")
        val noPlate: String = "",
        @SerializedName("required_seats")
        val requiredSeats: String = "",
        @SerializedName("time")
        val time: String = "",
        @SerializedName("user_type")
        val userType: String = ""
    )
}