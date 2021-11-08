package com.example.listingapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.work.*
import com.example.listingapp.app.App
import com.example.listingapp.appconfig.AppConstant
import com.example.listingapp.networkcalls.NetworkActivtyWorker
import org.json.JSONObject
import java.util.HashMap

class RandomUserViewModel(c: Application) : AndroidViewModel(c) {
    val context = c

    private var workmanager: WorkManager = WorkManager.getInstance()
    private val constraints =
        Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()

    fun getRandomUser(){
        val header = HashMap<String, String>()
        header["url"] = AppConstant.FETCH_RANDOMUSER_DETAILS_URL
        header["accept"] = "http/https"
        val headerjson = JSONObject(header as Map<*, *>).toString()

        val onTimeWorkRequestBuilderGetAllRandomUsers: OneTimeWorkRequest.Builder =
            OneTimeWorkRequest.Builder(NetworkActivtyWorker::class.java)
                .addTag(AppConstant.RANDOMUSER_DETAILS)
                .setConstraints(constraints)

        onTimeWorkRequestBuilderGetAllRandomUsers.setInputData(
            getInputDataForWorkmanager(
                header["url"]!!,
                "{}",
                headerjson,
                AppConstant.RANDOMUSER_DETAILS
            )
        )


        val onTimeWorkRequestGetAllLead: OneTimeWorkRequest =
            onTimeWorkRequestBuilderGetAllRandomUsers.build()

        workmanager.beginUniqueWork(
            AppConstant.RANDOMUSER_DETAILS,
            ExistingWorkPolicy.REPLACE,
            onTimeWorkRequestGetAllLead
        ).enqueue()


    }
    fun getWeatherDetails(lat:String,lon:String){
        val header = HashMap<String, String>()
        header["url"] = AppConstant.FETCH_WEATHER_URL+"?lat="+lat+"&lon="+lon+"&appid="+AppConstant.WEATHER_API_KEY
        header["accept"] = "http/https"
        val headerjson = JSONObject(header as Map<*, *>).toString()
        val onTimeWorkRequestBuilderWeatherRequest: OneTimeWorkRequest.Builder =
            OneTimeWorkRequest.Builder(NetworkActivtyWorker::class.java)
                .addTag(AppConstant.WEATHER_REQUEST)
                .setConstraints(constraints)

        onTimeWorkRequestBuilderWeatherRequest.setInputData(
            getInputDataForWorkmanager(
                header["url"]!!,
                "{}",
                headerjson,
                AppConstant.WEATHER_REQUEST
            )
        )


        val onTimeWorkRequestgetWeatherRequest: OneTimeWorkRequest =
            onTimeWorkRequestBuilderWeatherRequest.build()

        workmanager.beginUniqueWork(
            AppConstant.WEATHER_REQUEST,
            ExistingWorkPolicy.REPLACE,
            onTimeWorkRequestgetWeatherRequest
        ).enqueue()
    }


    private fun getInputDataForWorkmanager(
        requestUrl: String,
        requestInput: String,
        reuestHeader: String,
        type: String
    ): Data {

        return Data.Builder()
            .putString(AppConstant.REQUEST_URL, requestUrl)
            .putString(AppConstant.REQUEST_HEADER, reuestHeader)
            .putString(AppConstant.REQUEST_INPUT, requestInput)
            .putString(AppConstant.REQUEST_TYPE, type)
            .build()
    }


    fun getWorkmanager(): WorkManager {
        return workmanager
    }
}