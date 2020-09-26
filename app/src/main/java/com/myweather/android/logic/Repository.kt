package com.myweather.android.logic

import androidx.lifecycle.liveData
import com.myweather.android.logic.model.Place
import com.myweather.android.logic.network.MyWeatherNetwork
import kotlinx.coroutines.Dispatchers

//仓库层
//仓库层像是数据获取和缓存的中间层，
//本地没有缓存数据时从网络层获取，本地有缓存时直接返回缓存数据

//仓库层的统一封装入口
//搜索城市数据发起网络请求获取最新数据即可，不需要本地缓存
object Repository {

    //liveData函数，由lifecycle-livedata-ktx库提供，
    //可以自动构建并返回一个LiveData对象，在其代码块中提供一个挂起函数的上下文，
    //就可以在liveData函数的代码块中调用任意的挂起函数了，
    //使用Dispatchers.IO将liveData函数的线程转移到子线程中，
    //这是为了避免数据操作这样的耗时操作放在主线程中阻塞主线程
    fun searchPlaces(query : String) = liveData(Dispatchers.IO){

        val result = try{

            //调用searchPlaces函数来搜索城市数据，并判断服务器的响应状态
            //若响应状态时ok，使用kotlin内置的Result.success方法来包装获取的城市数据列表
            //否则就使用Result.failure方法来包装一个异常信息
            val placeResponse = MyWeatherNetwork.searchPlaces(query)

            if(placeResponse.status == "ok"){
                val places = placeResponse.places
                Result.success(places)
            }else{
                Result.failure(RuntimeException("response status is ${placeResponse}"))
            }
        }catch(e : Exception){
            Result.failure<List<Place>>(e)
        }
        //emit方法，发送包装后的结果
        emit(result)
    }
}