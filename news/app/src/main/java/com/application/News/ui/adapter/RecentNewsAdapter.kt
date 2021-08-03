package com.application.News.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.application.News.R
import com.application.News.ui.model.RecentRes
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.recent_item.view.*

class RecentNewsAdapter(var context: Context, var manageCardInterface: ManageCardInterface) : RecyclerView.Adapter<RecentNewsAdapter.ViewHolder>(){

    var list = emptyList<RecentRes>()

    class ViewHolder(itemView:View):RecyclerView.ViewHolder(itemView) {
        var slideImg = itemView.slideImg
        var slideTitle = itemView.slideTitle
        var slideCate = itemView.slideCate
    }

    interface ManageCardInterface {
        fun dataRecent()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.recent_item,parent,false)
        return RecentNewsAdapter.ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model = list[position]

        Glide.with(context).load(model.newsImg).into(holder.slideImg)
        holder.slideTitle.setText(model.title)
        holder.slideCate.setText(model.cate+" • "+model.date)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun setValues(recent:ArrayList<RecentRes>){
        list = recent
        notifyDataSetChanged()
    }
}