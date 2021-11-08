package com.example.listingapp.adapter

import android.content.Context
import android.content.Intent
import android.text.Layout
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter.FilterResults
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.listingapp.R
import com.example.listingapp.activity.DetailScreen
import com.example.listingapp.activity.MainActivity
import com.example.listingapp.app.App
import com.example.listingapp.databinding.RandomUserAdapterSinBinding
import com.example.listingapp.roomDB.model.RandomUserModel
import com.squareup.picasso.Picasso
import java.util.ArrayList
import java.util.logging.Filter
import java.util.logging.LogRecord

class RandomUserAdapter (c: Context) : androidx.recyclerview.widget.RecyclerView.Adapter<androidx.recyclerview.widget.RecyclerView.ViewHolder>() {
    private val context = c
    private lateinit var binding: RandomUserAdapterSinBinding
    var data=java.util.ArrayList<RandomUserModel>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
       binding=DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.random_user_adapter_sin, parent, false)
        return ViewHolder(binding)
    }
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        var viewholder = holder as ViewHolder
        if(position % 2 ==0){
            viewholder.view_binding.imgLargeViewLf.layoutParams.height=600
        }else{
            viewholder.view_binding.imgLargeViewLf.layoutParams.height=400
        }

        Picasso.with(context).load(data[position].large).placeholder(context.resources.getDrawable(R.drawable.index)).into(viewholder.view_binding.imgLargeViewLf)
        viewholder.view_binding.txtLargeViewLf.setText(""+data[position].first+" "+data[position].last)

        viewholder.itemView.setOnClickListener {
            val i = Intent(context, DetailScreen::class.java)
            i.putExtra("id",data[position].id)
            context.startActivity(i)
        }

    }
    fun setRandomUserDATA(data:java.util.ArrayList<RandomUserModel>){
        this.data=data
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return data.size
    }
    class ViewHolder(binding: RandomUserAdapterSinBinding):androidx.recyclerview.widget.RecyclerView.ViewHolder(binding.root){
        var view_binding = binding
    }
}