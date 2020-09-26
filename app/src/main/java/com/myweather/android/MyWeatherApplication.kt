package com.myweather.android

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context

/*使用MVVM分层架构的设计，
* 从ViewModel层开始就不再持有Activity的引用，
* 自定义Context以便给项目提供全局Context,
* 定义完成后还需要在AndroidManifest.xml中的<application>标签中
* 使用android:name指定.MyWeatherApplication，
* 指定完成后，就可以在程序中使用MyWeatherApplication.context获取Context对象*/
class MyWeatherApplication : Application() {

    companion object{
        @SuppressLint("StaticFieldLeak")
        lateinit var context : Context

        //彩云天气的令牌值，也是使用api获取天气数据的关键
        const val TOKEN = "SHRn7SKL2vkW7aiE"
    }

    override fun onCreate(){
        super.onCreate()
        context = applicationContext
    }

}