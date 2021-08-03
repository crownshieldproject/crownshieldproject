package com.application.News.ui.adapter

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.pm.ActivityInfo
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.MediaController
import android.widget.RelativeLayout
import androidx.recyclerview.widget.RecyclerView
import com.application.News.R
import com.application.News.ui.model.SpecialList
import com.application.News.ui.model.VideoRes
import kotlinx.android.synthetic.main.video_item.view.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class VideoSplAdapter(var context: Context) : RecyclerView.Adapter<VideoSplAdapter.ViewHolder>() {

    var list = emptyList<SpecialList>()
    var mediaControls:MediaController? = null


    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var slideTitle = itemView.slideTitle
        var videoView = itemView.videoView
        var slideCate = itemView.slideCate
        var playvideo = itemView.playvideo
        var zoomIN = itemView.zoomIN
        var zoomOut = itemView.zoomOut
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.video_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model = list[position]

        holder.slideTitle.setText(model.specialContent)

        val fmt = SimpleDateFormat("yyyy-MM-dd")
        val date: Date = fmt.parse(model.specialProgramsDate)

        val fmtOut = SimpleDateFormat("MMM dd,yyyy")
        val dateTimeString =  fmtOut.format(date)

        holder.slideCate.setText(dateTimeString)
        holder.videoView.setVideoPath(model.specialProgramVideo)

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

//        holder.zoomIN.setOnClickListener {
////            enterFullScreen()
//            holder.zoomOut.visibility = View.VISIBLE
//            holder.zoomIN.visibility = View.GONE
//        }
//
//        holder.zoomOut.setOnClickListener {
////            enterFullScreen()
//            holder.zoomOut.visibility = View.GONE
//            holder.zoomIN.visibility = View.VISIBLE
//        }


    }

    fun getActivity(context: Context?): Activity? {
        if (context == null) return null
        if (context is Activity) return context
        return if (context is ContextWrapper) getActivity(context.baseContext) else null
    }

//    private fun enterFullScreen() {
//        getActivity(context)!!.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
//        getActivity(context)!!.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE)
//        getActivity(context)!!.getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
//        var layoutParams = RelativeLayout.LayoutParams(
//                RelativeLayout.LayoutParams.MATCH_PARENT,
//                RelativeLayout.LayoutParams.MATCH_PARENT
//        )
//        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM)
//        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP)
//        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT)
//        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT)
//    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun setValues(video: ArrayList<SpecialList>){
        list = video
        notifyDataSetChanged()
    }

}


