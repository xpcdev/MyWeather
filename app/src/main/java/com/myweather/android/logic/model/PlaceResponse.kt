package com.myweather.android.logic.model

import android.location.Location
import com.google.gson.annotations.SerializedName

//数据模型
//定义城市类和城市属性

data class PlaceResponse(val status : String, val places :  List<Place>)

data class Place(val name : String, val  location : Location,
                 @SerializedName("formatted_address")
                 val address : String)

data class Location(val lng : String, val lat : String)