package com.application.News.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.application.News.R
import com.application.News.ui.model.RecentRes
import com.application.News.ui.model.RecentSearch
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.recent_search_item.view.*

class RecentSearchAdapter(var context: Context, var manageCardInterface: ManageCardInterface) : RecyclerView.Adapter<RecentSearchAdapter.ViewHolder>(){

    var list = emptyList<RecentSearch>()

    class ViewHolder(itemView:View):RecyclerView.ViewHolder(itemView) {
        var slideTitle = itemView.slideTitle
    }

    interface ManageCardInterface {
        fun dataRecent()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.recent_search_item,parent,false)
        return RecentSearchAdapter.ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model = list[position]

        holder.slideTitle.setText(model.searchName)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun setValues(recent:ArrayList<RecentSearch>){
        list = recent
        notifyDataSetChanged()
    }
}