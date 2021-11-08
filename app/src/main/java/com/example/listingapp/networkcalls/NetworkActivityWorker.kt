package com.example.listingapp.networkcalls

import android.content.Context
import android.util.Log
import androidx.work.ListenableWorker
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.android.volley.AuthFailureError
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.example.listingapp.app.App
import com.example.listingapp.appconfig.AppConstant
import com.example.listingapp.roomDB.model.RandomUserModel
import com.example.listingapp.roomDB.model.WeatherDataModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.lang.Exception
import java.util.ArrayList
import java.util.concurrent.CountDownLatch

class NetworkActivtyWorker(c: Context, workParameters: WorkerParameters) :
    Worker(c, workParameters) {

    private val con = c
    override fun doWork(): Result {
        var holder = CountDownLatch(1)
        var result: ListenableWorker.Result = Result.failure()

        var request_input: JSONObject = JSONObject()
        var request_url: String? = null
        var request_header: JSONObject = JSONObject()
        var request_type: String = ""


        try{
            request_type = inputData.getString(AppConstant.REQUEST_TYPE)!!

            if(request_type.equals(AppConstant.RANDOMUSER_DETAILS)){
                request_url = AppConstant.FETCH_RANDOMUSER_DETAILS_URL
                request_input = JSONObject()
            }
            else if(request_type.equals(AppConstant.WEATHER_REQUEST)){
                request_url=inputData.getString(AppConstant.REQUEST_URL)!!
            }
            else {

                request_input = JSONObject(inputData.getString(AppConstant.REQUEST_INPUT))
                request_url = inputData.getString(AppConstant.REQUEST_URL)!!
            }


            request_header = JSONObject(inputData.getString(AppConstant.REQUEST_HEADER))
        }catch (e:Exception){
            Log.e("jsonExce", e.toString())
            result = Result.failure()
            holder.countDown()
        }

        Log.d(
            "network call detail",
            " " + request_url + " " + request_input + " " + request_header.toString()
        )
        Log.d("input", "" + request_input.toString())
        //network Call request

        val getRequest = object : JsonObjectRequest(
            Request.Method.GET, request_url, request_input,
            Response.Listener { response ->
                if (response != null) {
                    try {

                        Log.d("response form work manager", "" + response.toString())

                        result = responseProcessor(response, request_type)

//                            result = Result.success()
                        holder.countDown()

                    } catch (e: JSONException) {

                        result = Result.failure()
                        e.printStackTrace()
                        holder.countDown()
                    }

                } else {
                    result = Result.failure()
                    holder.countDown()
                }
            },
            Response.ErrorListener { error ->

                result = Result.failure()
                holder.countDown()

            }) {

            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String>? {
                try {
                    return jsonToMap(request_header!!)
                } catch (e: JSONException) {

                    e.printStackTrace()
                }

                return null
            }
        }
        getRequest.retryPolicy = DefaultRetryPolicy(
            10000, 0,
            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        )

        getRequest.tag = request_url

        App.get().requestQueue.add(getRequest)
        App.get().requestQueue.cache.remove(request_url)
        App.get().requestQueue.cache.clear()




        try {
            holder.await()
        } catch (e: InterruptedException) {

        }




        return result
    }
    @Throws(JSONException::class)
    fun jsonToMap(t: JSONObject): HashMap<String, String> {

        val map = HashMap<String, String>()
        val keys = t.keys()

        while (keys.hasNext()) {
            val key = keys.next() as String
            val value = t.getString(key)
            map[key] = value
        }
        return map
    }
    fun responseProcessor(response: JSONObject, type: String): Result {

        var result = Result.success()
        when (type) {

            AppConstant.WEATHER_REQUEST ->{
                Log.d("weatherres",".."+response.toString())
               try {
                    var jsonObject:JSONObject =response
                    val weatherD = WeatherDataModel()
                   weatherD.id=1
                    weatherD.city = jsonObject.getString("name")
                    weatherD.condition =
                        jsonObject.getJSONArray("weather").getJSONObject(0).getInt("id")
                    weatherD.weatherType =
                        jsonObject.getJSONArray("weather").getJSONObject(0).getString("main")
                   weatherD.description=jsonObject.getJSONArray("weather").getJSONObject(0).getString("description")
                    weatherD.icon =updateWeatherIcon(weatherD.condition)
                    val tempResult: Double =
                        jsonObject.getJSONObject("main").getDouble("temp") - 273.15
                    val roundedValue = Math.rint(tempResult).toInt()
                    weatherD.temperature = Integer.toString(roundedValue)
                   weatherD.humidity=jsonObject.getJSONObject("main").getString("humidity")
                   weatherD.speed=jsonObject.getJSONObject("wind").getString("speed")

                   App.get().db.weatherDataDAO().INSERTWEATHERDETAILS(weatherD)

                } catch (e: JSONException) {
                    e.printStackTrace()

                }
            }

            AppConstant.RANDOMUSER_DETAILS -> {
                Log.d("response",".."+response.toString())
                //to avoid for loop
                /*val response_data =response.getJSONArray("results").toString()
                val type = object  :TypeToken<ArrayList<RandomUserModel>>() {}.type
                val data: ArrayList<RandomUserModel> = Gson().fromJson(response_data,type)
                App.get().db.randomUserDAO().INSERTRANDOMUSERS(data)*/

                //standard method
                var innerJosn: JSONArray? = null
                val data = java.util.ArrayList<RandomUserModel>()
                try{
                    innerJosn = response.getJSONArray("results")
                    var count=1
                    for (i in 0 until innerJosn.length()) {
                        var randomUserModel= RandomUserModel()
                        var jsonObject=innerJosn.getJSONObject(i)
                        randomUserModel.id=i
                       randomUserModel.gender= jsonObject.getString("gender")
                       randomUserModel.title=jsonObject.getJSONObject("name").getString("title")
                       randomUserModel.first=jsonObject.getJSONObject("name").getString("first")
                       randomUserModel.last=jsonObject.getJSONObject("name").getString("last")
                        randomUserModel.city=jsonObject.getJSONObject("location").getString("city")
                        randomUserModel.state=jsonObject.getJSONObject("location").getString("state")
                        randomUserModel.country=jsonObject.getJSONObject("location").getString("country")
                        randomUserModel.postcode=jsonObject.getJSONObject("location").getString("postcode")
                        randomUserModel.latitude=jsonObject.getJSONObject("location").getJSONObject("coordinates").getString("latitude")
                        randomUserModel.longitude=jsonObject.getJSONObject("location").getJSONObject("coordinates").getString("longitude")
                        randomUserModel.email=jsonObject.getString("email")
                        randomUserModel.uuid=jsonObject.getJSONObject("login").getString("uuid")
                        randomUserModel.username=jsonObject.getJSONObject("login").getString("username")
                        randomUserModel.date=jsonObject.getJSONObject("dob").getString("date")
                        randomUserModel.age=jsonObject.getJSONObject("dob").getString("age")
                        randomUserModel.phone=jsonObject.getString("phone")
                        randomUserModel.name=jsonObject.getJSONObject("id").getString("name")
                        randomUserModel.large=jsonObject.getJSONObject("picture").getString("large")
                        randomUserModel.medium=jsonObject.getJSONObject("picture").getString("medium")
                        randomUserModel.thumbnail=jsonObject.getJSONObject("picture").getString("thumbnail")
                        randomUserModel.displayno=count
                        if(count == 4){
                            count =1
                        }else{
                            count ++
                        }
                        data.add(randomUserModel)
                    }
                    App.get().db.randomUserDAO().INSERTRANDOMUSERS(data)
                }catch (e:Exception){
                    Log.d("error",e.toString())
                }



            }
        }
        return result
    }

    private fun updateWeatherIcon(condition: Int): String? {
        if (condition >= 0 && condition <= 300) {
            return "thunderstrom1"
        } else if (condition >= 300 && condition <= 500) {
            return "lightrain"
        } else if (condition >= 500 && condition <= 600) {
            return "shower"
        } else if (condition >= 600 && condition <= 700) {
            return "snow2"
        } else if (condition >= 701 && condition <= 771) {
            return "fog"
        } else if (condition >= 772 && condition <= 800) {
            return "overcast"
        } else if (condition == 800) {
            return "sunny"
        } else if (condition >= 801 && condition <= 804) {
            return "cloudy"
        } else if (condition >= 900 && condition <= 902) {
            return "thunderstrom1"
        }
        if (condition == 903) {
            return "snow1"
        }
        if (condition == 904) {
            return "sunny"
        }
        return if (condition >= 905 && condition <= 1000) {
            "thunderstrom2"
        } else "dunno"
    }
}