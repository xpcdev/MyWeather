package com.myweather.android.logic.network

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.await
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

//定义一个统一的网络数据源访问入口
//封装所有网络请求的API

object MyWeatherNetwork {

    //使用ServiceCreator创建一个PlaceService接口的动态代理对象
    private val placeService = ServiceCreator.create(PlaceService::class.java)

    //定义一个searchPlaces()函数
    //在函数中调用PlacesService接口中定义的searchPlaces方法，以便发起搜索城市数据请求
    //定义await函数使用协程计数，将searchPlaces函数声明成为挂起函数
    //当外部调用searchPlaces函数时，retrofit就会立即发起网络请求并同时阻塞当前协程
    //直到服务器响应请求之后，await函数将解析出来的数据模型对象去除并返回，同时回复当前协程的执行
    //searchPlaces函数在得到await函数的返回值后将该数据返回到上一层
    suspend fun searchPlaces(query :String) = placeService.searchPlaces(query).await()

    private suspend fun <T> Call<T>.await() : T {

        return suspendCoroutine{ continuation ->
            enqueue(object : Callback<T> {

                override fun onResponse(call: Call<T>, response: Response<T>) {
                    val body = response.body()
                    if(body != null) continuation.resume(body)
                    else continuation.resumeWithException(
                        RuntimeException("response body is null")
                    )
                }

                override fun onFailure(call: Call<T>, t: Throwable) {
                    continuation.resumeWithException(t)
                }
            })
        }
    }
}