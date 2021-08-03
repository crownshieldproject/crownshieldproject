package com.application.News.ui.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.application.News.R
import com.application.News.ui.model.CategoryData
import com.application.News.ui.model.RecentRes
import com.application.News.ui.model.RecentSearch
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.category_list_search_item.view.*

class SearchCategoryListAdapter(var context: Context, var manageCardInterface: ManageCardInterface) : RecyclerView.Adapter<SearchCategoryListAdapter.ViewHolder>(){

    var list = emptyList<CategoryData>()
    var rowIndex = 0

    class ViewHolder(itemView:View):RecyclerView.ViewHolder(itemView) {
        var slideTitle = itemView.slideTitle
        var llLayout = itemView.llLayout
    }

    interface ManageCardInterface {
        fun searchCate()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.category_list_search_item,parent,false)
        return SearchCategoryListAdapter.ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model = list[position]

        holder.slideTitle.setText(model.categoryName.trim())

        holder.llLayout.setOnClickListener {
            rowIndex=position
            notifyDataSetChanged()
        }

        if (rowIndex.equals(position)){
            holder.llLayout.setBackgroundResource(R.drawable.item_bg)
            holder.slideTitle.setTextColor(Color.parseColor("#FFFFFF"))
        }else{
            holder.llLayout.setBackgroundResource(R.drawable.item_unselected_bg)
            holder.slideTitle.setTextColor(Color.parseColor("#000000"))
        }

    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun setValues(recent:ArrayList<CategoryData>){
        list = recent
        notifyDataSetChanged()
    }
}