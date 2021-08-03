package com.application.News.ui.adapter

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.MediaController
import androidx.recyclerview.widget.RecyclerView
import com.application.News.R
import com.application.News.ui.model.VideoRes
import kotlinx.android.synthetic.main.video_item.view.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class VideoNewsAdapter(var context: Context) : RecyclerView.Adapter<VideoNewsAdapter.ViewHolder>() {

    var list = emptyList<VideoRes>()
    var mediaControls:MediaController? = null


    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var slideTitle = itemView.slideTitle
        var videoView = itemView.videoView
        var slideCate = itemView.slideCate
        var playvideo = itemView.playvideo
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.video_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model = list[position]

        holder.slideTitle.setText(model.title)

//        val fmt = SimpleDateFormat("dd-MM-yyyy hh:mm")
//        val date: Date = fmt.parse(model.advertisementDate)
//
//        val fmtOut = SimpleDateFormat("MMM dd,yyyy")
//        val dateTimeString =  fmtOut.format(date)

        holder.slideCate.setText(model.cate + " • " + model.date)
        holder.videoView.setVideoPath(model.newsVideo)

        holder.playvideo.setOnClickListener {
            holder.videoView.start()
            holder.playvideo.visibility=View.GONE
            if (mediaControls == null) {
                // creating an object of media controller class
                mediaControls = MediaController(context)

                // set the anchor view for the video view
                mediaControls!!.setAnchorView(holder.videoView)
            }
            holder.videoView.setMediaController(mediaControls)

        }

    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun setValues(video: ArrayList<VideoRes>){
        list = video
        notifyDataSetChanged()
    }

}


