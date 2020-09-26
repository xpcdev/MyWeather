package com.myweather.android.ui.place

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.myweather.android.R
import kotlinx.android.synthetic.main.fragment_place.*

//实现Fragment
class PlaceFragment : Fragment() {

    //lazy函数，获取PlaceViewModel的实例，
    //允许在整个类中随时使用viewModel变量，
    //但不用关心什么时候进行初始化或者是否为空等前提条件
    val viewModel by lazy { ViewModelProviders.of(this).get(PlaceViewModel::class.java)}

    private lateinit var adapter : PlaceAdapter

    //加载fragment_place布局
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?{
        return inflater.inflate(R.layout.fragment_place,container,false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        //给RecyclerView设置LayoutManger
        val layoutManager = LinearLayoutManager(activity)
        recyclerView.layoutManager = layoutManager

        //给RecyclerView设置适配器
        //使用PlaceViewModel中的placeList作为数据源
        adapter = PlaceAdapter(this, viewModel.placeList)
        recyclerView.adapter = adapter

        //搜索城市数据
        //使用EditText的addTextChangedListener方法来监听搜索框内内容的变化
        searchPlaceEdit.addTextChangedListener {editable ->

            //获取输入框中的内容
            val content = editable.toString()

            //若输入框中内容不为空，则将内容传递给PlaceViewModel的searchPlaces方法
            if(content.isNotEmpty()){
                viewModel.searchPlaces(content)
            //若输入框中内容为空，则隐藏RecyclerView，并将背景图显示出来作为背景
            }else{
                recyclerView.visibility = View.GONE
                bgImageView.visibility = View.VISIBLE
                viewModel.placeList.clear()
                adapter.notifyDataSetChanged()
            }
        }

        //获取服务器响应数据
        //对PlaceViewModel中的placeViewData对象进行观察
        viewModel.placeLiveData.observe(this, Observer{ result ->

            //有数据变化时，回调到传入的Observer接口实现中
            val places = result.getOrNull()

            //判断回调数据
            //数据不为空，将数据添加到PlaceViewModel的placeList集合中，
            //调用PlaceAdapter刷新界面
            if(places != null){
                recyclerView.visibility = View.VISIBLE
                bgImageView.visibility = View.GONE
                viewModel.placeList.clear()
                viewModel.placeList.addAll(places)
                adapter.notifyDataSetChanged()
            //若数据为空，说明发生了异常，弹出Toast提示
            }else{
                Toast.makeText(activity,"未能查询到任何地点",Toast.LENGTH_SHORT).show()
                result.exceptionOrNull()?.printStackTrace()
            }
        })
    }
}


