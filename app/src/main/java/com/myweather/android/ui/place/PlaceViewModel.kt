package com.myweather.android.ui.place

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.myweather.android.logic.Repository
import com.myweather.android.logic.model.Place

//定义ViewModel层
class PlaceViewModel : ViewModel(){

    private val searchLiveData = MutableLiveData<String>()

    //缓存将在界面上显示的城市的数据
    val placeList = ArrayList<Place>()

    val placeLiveData = Transformations.switchMap(searchLiveData) { query ->
        Repository.searchPlaces(query)
    }

    //searchPlaces方法，将传入的搜索参数赋值给一个searchLiveData对象，
    //并使用Transformations.switchMap方法观察这个searchLiveData对象，
    //每当searchPlaces函数被调用时，switchMap方法所对应的转换函数就会执行，
    //在转换函数中调用仓库层中定义的searchPlaces方法就可以发起网络请求,
    //同时将仓库层返回的LiveData对象转换成一个可供activity观察的LiveData对象
    fun searchPlaces(query : String){
        searchLiveData.value = query
    }
}