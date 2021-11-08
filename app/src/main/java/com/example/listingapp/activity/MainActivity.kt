package com.example.listingapp.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.paging.PagedList
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.work.ListenableWorker
import com.android.volley.AuthFailureError
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.example.listingapp.R
import com.example.listingapp.adapter.RandomUserAdapter
import com.example.listingapp.adapter.RandomUserAdapterPaging
import com.example.listingapp.app.App
import com.example.listingapp.appconfig.AppConstant
import com.example.listingapp.databinding.ActivityMainBinding
import com.example.listingapp.roomDB.model.RandomUserModel
import com.example.listingapp.roomDB.model.WeatherDataModel
import com.example.listingapp.viewmodel.RandomUserVM
import com.example.listingapp.viewmodel.RandomUserViewModel
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.JsonHttpResponseHandler
import com.loopj.android.http.RequestParams
import cz.msebera.android.httpclient.Header
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.util.*

class MainActivity : AppCompatActivity() {
    lateinit var binding:ActivityMainBinding
    var adapter=RandomUserAdapter(this)
//    lateinit var adapter:RandomUserAdapterPaging
    lateinit var viewModel: RandomUserVM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding=DataBindingUtil.setContentView(this,R.layout.activity_main)
        RandomUserViewModel(application).getRandomUser()
        //for paging adapter init
        //adapter=RandomUserAdapterPaging(this@MainActivity)
        binding.rvProfileList.layoutManager = StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL)
        binding.rvProfileList.adapter=adapter
        viewModel=ViewModelProvider(this).get(RandomUserVM::class.java)
        binding.edSearch.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(s != null && s.length > 0){
                    adapter.setRandomUserDATA( App.get().db.randomUserDAO().searchFirstAndLastName("%"+s.toString()+"%") as ArrayList<RandomUserModel>)

                }else{
                    adapter.setRandomUserDATA( App.get().db.randomUserDAO().allRandomUsers as ArrayList<RandomUserModel>)

                }
                //for paging filter
       // adapter.getFilter(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {

            }
        })

        App.get().db.randomUserDAO().allRandomUsersLiveData.observe(this,object :Observer<List<RandomUserModel>>{
            override fun onChanged(t: List<RandomUserModel>?) {
                adapter.setRandomUserDATA(t as ArrayList<RandomUserModel>)
                if(t.size >0){
                    RandomUserViewModel(application).getWeatherDetails(t[0].latitude,t[0].longitude)
                }

            }
        })


//for paging list
        /*viewModel.allRandomUserData.observe(this,object :Observer<PagedList<RandomUserModel>>{
            override fun onChanged(t: PagedList<RandomUserModel>) {
                adapter.setData(t)
                if(t.size >0){
                    RandomUserViewModel(application).getWeatherDetails(t[0]!!.latitude,t[0]!!.longitude)
                }

            }
        })*/


        App.get().db.weatherDataDAO().allWeatherDetails.observe(this,object  : Observer<WeatherDataModel>{
            override fun onChanged(t: WeatherDataModel?) {
               if(t != null){
                   binding.header.txtWeatherLocation.setText(""+t.temperature+"°C"+" "+App.get().db.randomUserDAO().getCityName(""+0))
                   binding.header.txtWeatherType.setText(""+t.description)
                   val resourceID = resources.getIdentifier(
                       t.icon, "drawable",
                       packageName
                   )
                   binding.header.imgWeather.setImageResource(resourceID)
               }
            }
        })



    }


}