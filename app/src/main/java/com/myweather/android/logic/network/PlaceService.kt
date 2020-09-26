package com.myweather.android.logic.network

import com.myweather.android.MyWeatherApplication
import com.myweather.android.logic.model.PlaceResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

//Retrofit接口
//用于访问彩云天气城市搜索API
//API1,用于访问城市数据信息
//https://api.caiyunapp.com/v2/place?query=城市名称&token={令牌号}&lang=语言
//API2,用于查看具体信息
//https://api.caiyunapp.com/v2.5/{令牌号}/经度，纬度/realtime.json

//PlaceService接口
interface PlaceService {

    //访问城市信息
    //使用@GET注解，当使用searchPlaces方法查询城市信息的时候，
    //Retrofit就会自动发起一条GET请求，访问@GET注解中配置的地址，
    //访问城市信息的API1中只有query这个参数需要动态指定，因为这个参数表示要查询的城市名称，
    //使用@Query注解的方式来实现，token参数和lang参数不会变，固定写在@GET注解中即可
    //返回值被声明成Call<PlaceResponse>，Retrofit会将返回的json数据自动解析成PlaceResponse对象
    @GET("v2/place?token=${MyWeatherApplication.TOKEN}&lang=zh_CN")
    fun searchPlaces(@Query("query") query : String) : Call<PlaceResponse>
}