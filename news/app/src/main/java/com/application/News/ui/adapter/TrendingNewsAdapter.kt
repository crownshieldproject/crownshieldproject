package com.application.News.ui.adapter

import android.content.Context
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.application.News.R
import com.application.News.ui.model.RecentRes
import com.application.News.util.blur.Blurry
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.trending_item.view.*

class TrendingNewsAdapter(var context: Context, var manageCardInterface: ManageCardInterface) : RecyclerView.Adapter<TrendingNewsAdapter.ViewHolder>(){

    var list = emptyList<RecentRes>()

    class ViewHolder(itemView:View):RecyclerView.ViewHolder(itemView) {
        var slideImg = itemView.slideImg
        var slideTitle = itemView.slideTitle
        var slideCate = itemView.slideCate
    }

    interface ManageCardInterface {
        fun dataTrend()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.trending_item,parent,false)
        return TrendingNewsAdapter.ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model = list[position]

        val icon = BitmapFactory.decodeResource(context.resources,
                model.newsImg)
        Blurry.with(context).from(icon).into(holder.slideImg)
        holder.slideTitle.setText(model.title)
        holder.slideCate.setText(model.cate+" • "+model.date)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun setValues(trending:ArrayList<RecentRes>){
        list = trending
        notifyDataSetChanged()
    }
}