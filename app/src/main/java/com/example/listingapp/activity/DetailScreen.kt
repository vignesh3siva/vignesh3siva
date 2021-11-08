package com.example.listingapp.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.example.listingapp.R
import com.example.listingapp.app.App
import com.example.listingapp.databinding.ActivityDetailScreenBinding
import com.example.listingapp.roomDB.model.RandomUserModel
import com.example.listingapp.roomDB.model.WeatherDataModel
import com.example.listingapp.viewmodel.RandomUserViewModel
import com.squareup.picasso.Picasso

class DetailScreen : AppCompatActivity() {
    lateinit var binding: ActivityDetailScreenBinding
    var data = RandomUserModel()
    var id = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail_screen)
        //intent action
        if (intent.hasExtra("id")) {
            id = intent.getIntExtra("id", 0)
        }

        //back button action
        binding.header.imgBack.visibility = View.VISIBLE
        binding.header.imgBack.setOnClickListener {
            val i = Intent(this, MainActivity::class.java)
            startActivity(i)
            finish()
        }

        //loading ui
        data = App.get().db.randomUserDAO().getRandomUserByID("" + id)
        if (data != null) {
            RandomUserViewModel(application).getWeatherDetails(data.latitude, data.longitude)
            App.get().db.weatherDataDAO().allWeatherDetails.observe(this, object :
                Observer<WeatherDataModel> {
                override fun onChanged(t: WeatherDataModel?) {
                    if (t != null) {
                        loadUI(t, data)
                    }
                }
            })
        }
    }

    fun loadUI(t: WeatherDataModel, data: RandomUserModel) {
        binding.header.txtWeatherLocation.setText(
            "" + t.temperature + "°C" + " " + App.get().db.randomUserDAO()
                .getCityName("" + id)
        )
        binding.header.txtWeatherType.setText("" + t.description)
        val resourceID = resources.getIdentifier(
            t.icon, "drawable",
            packageName
        )
        binding.header.imgWeather.setImageResource(resourceID)
        binding.txtSpeed.setText(t.speed)
        binding.txtHumidity.setText(t.humidity)


        Picasso.with(this).load(data.large).into(binding.imgProfileImage)
        binding.txtName.setText(data.first + " " + data.last)

        binding.txtGender.setText(data.gender)
        binding.txtPhone.setText(data.phone)
        binding.txtUsername.setText(data.username)
        binding.txtCountry.setText(data.country)

    }
}