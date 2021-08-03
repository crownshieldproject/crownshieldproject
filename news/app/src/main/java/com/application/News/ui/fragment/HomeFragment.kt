package com.application.News.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.application.News.R
import com.application.News.databinding.FragmentHomeBinding
import com.application.News.service.Status
import com.application.News.ui.adapter.*
import com.application.News.ui.model.*
import com.application.News.util.viewBinding
import com.application.viewmodel.ApiViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment(R.layout.fragment_home), RecentNewsAdapter.ManageCardInterface,TrendingNewsAdapter.ManageCardInterface {

    val binding by viewBinding(FragmentHomeBinding::bind)
    var slideList: ArrayList<BannerList>? = null
    var recentList: ArrayList<RecentRes>? = null
    var videoList: ArrayList<VideoRes>? = null
    var videoSplProgram: ArrayList<SpecialList>? = null
    var videoAdsList: ArrayList<AdsList>? = null
    var slideAdapter: SlideAdapter? = null
    var recentNewsAdapter: RecentNewsAdapter? = null
    var trendingNewsAdapter: TrendingNewsAdapter? = null
    var videoNewsAdapter: VideoNewsAdapter? = null
    var videoSplAdapter: VideoSplAdapter? = null
    var videoAdsAdapter: VideoAdsAdapter? = null
    val apiViewModel: ApiViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        setOnClickListener()
    }

    private fun setOnClickListener() {

    }

    private fun initViews() {
        slideList = ArrayList()
        recentList = ArrayList()
        videoList = ArrayList()
        videoSplProgram = ArrayList()
        videoAdsList = ArrayList()


        recentList!!.add(
                RecentRes(
                        R.drawable.news_details,
                        "jonathan Doe Prepares for the Next Olympics in 2022",
                        "Sports",
                        "Nov 5"
                )
        )
        recentList!!.add(
                RecentRes(
                        R.drawable.design,
                        "New One-click Features for Designers Make Their Projects Easier",
                        "Design",
                        "Nov 8"
                )
        )
        recentList!!.add(
                RecentRes(
                        R.drawable.tech,
                        "The 10 Most Expensive Laptops This Year are Equipped",
                        "Tech",
                        "Nov 12"
                )
        )

        recentList!!.add(
                RecentRes(
                        R.drawable.news_details,
                        "jonathan Doe Prepares for the Next Olympics in 2022",
                        "Sports",
                        "Nov 5"
                )
        )
        recentList!!.add(
                RecentRes(
                        R.drawable.design,
                        "New One-click Features for Designers Make Their Projects Easier",
                        "Design",
                        "Nov 8"
                )
        )
        recentList!!.add(
                RecentRes(
                        R.drawable.tech,
                        "The 10 Most Expensive Laptops This Year are Equipped",
                        "Tech",
                        "Nov 12"
                )
        )


        videoList!!.add(
                VideoRes(
                        "https://www.rmp-streaming.com/media/bbb-360p.mp4",
                        "jonathan Doe Prepares for the Next Olympics in 2022",
                        "Sports",
                        "Nov 5",
                        Video(
                                "https://instagram.fhan3-1.fna.fbcdn.net/vp/fdac380ecfa349f38f4f1701abcc29b5/5A681B7A/t51.2885-15/e15/26226589_171205453644367_3851432199505051648_n.jpg",
                                "https://instagram.fhan3-1.fna.fbcdn.net/vp/cc9f81b0cd5c3100895184072dac16d5/5A68A1A0/t50.2886-16/19231638_185054565422809_5378340150369583104_n.mp4",
                                0
                        )
                )
        )
        videoList!!.add(
                VideoRes(
                        "https://www.rmp-streaming.com/media/bbb-360p.mp4",
                        "New One-click Features for Designers Make Their Projects Easier",
                        "Design",
                        "Nov 8",
                        Video(
                                "https://instagram.fhan3-1.fna.fbcdn.net/vp/fdac380ecfa349f38f4f1701abcc29b5/5A681B7A/t51.2885-15/e15/26226589_171205453644367_3851432199505051648_n.jpg",
                                "https://instagram.fhan3-1.fna.fbcdn.net/vp/cc9f81b0cd5c3100895184072dac16d5/5A68A1A0/t50.2886-16/19231638_185054565422809_5378340150369583104_n.mp4",
                                0
                        )
                )
        )
        videoList!!.add(
                VideoRes(
                        "https://www.rmp-streaming.com/media/bbb-360p.mp4",
                        "The 10 Most Expensive Laptops This Year are Equipped",
                        "Tech",
                        "Nov 12",
                        Video(
                                "https://instagram.fhan3-1.fna.fbcdn.net/vp/fdac380ecfa349f38f4f1701abcc29b5/5A681B7A/t51.2885-15/e15/26226589_171205453644367_3851432199505051648_n.jpg",
                                "https://instagram.fhan3-1.fna.fbcdn.net/vp/cc9f81b0cd5c3100895184072dac16d5/5A68A1A0/t50.2886-16/19231638_185054565422809_5378340150369583104_n.mp4",
                                0
                        )
                )
        )


        val layoutManager1 = LinearLayoutManager(
                requireActivity(),
                LinearLayoutManager.VERTICAL,
                false
        )
        layoutManager1.isSmoothScrollbarEnabled = true
        binding.rvRecent.layoutManager = layoutManager1
        recentNewsAdapter = RecentNewsAdapter(requireActivity(), this)
        binding.rvRecent.adapter = recentNewsAdapter

        recentNewsAdapter!!.setValues(recentList!!)

        val layoutManager2 = GridLayoutManager(requireActivity(), 3)
        layoutManager2.isSmoothScrollbarEnabled = true
        binding.rvTrending.layoutManager = layoutManager2
        trendingNewsAdapter = TrendingNewsAdapter(requireActivity(), this)
        binding.rvTrending.adapter = trendingNewsAdapter

        trendingNewsAdapter!!.setValues(recentList!!)


        val layoutManager3 = LinearLayoutManager(
                requireActivity(),
                LinearLayoutManager.HORIZONTAL,
                false
        )

        layoutManager3.isSmoothScrollbarEnabled = true
        binding.rvVideo.layoutManager = layoutManager3
        videoNewsAdapter = VideoNewsAdapter(requireActivity())
        binding.rvVideo.adapter = videoNewsAdapter

        videoNewsAdapter!!.setValues(videoList!!)

        getHome()
    }

    private fun getHome() {
        apiViewModel.getHome.observe(requireActivity(), Observer {
            it?.let {
                when (it.status) {

                    Status.LOADING -> {
                        showProgress()
                    }
                    Status.SUCCESS -> {
                        dismissProgress()
                        if (it.data!!.Status.equals("true")) {
                            if (it.data.data.Banner.size > 0) {
                                slideList!!.addAll(it.data.data.Banner)
                                setSlide()
                            }

                            if (it.data.data.SpecialPrograms.size > 0){
                                videoSplProgram!!.clear()
                                for (i in 0..3) {
                                    videoSplProgram!!.add(it.data.data.SpecialPrograms[i])
                                }
                                setSpecial()
                            }

                            if (it.data.data.Advertisement.size > 0){
                                videoAdsList!!.addAll(it.data.data.Advertisement)
                                setAds()
                            }
                        } else {
                            toast(it.data.Message)
                        }
                    }
                    Status.ERROR -> {
                        dismissProgress()
                        toast(it.message!!)
                    }
                }
            }
        })
    }

    private fun setAds() {
        val layoutManager = LinearLayoutManager(
                requireActivity(),
                LinearLayoutManager.HORIZONTAL,
                false
        )

        layoutManager.isSmoothScrollbarEnabled = true
        binding.rvAds.layoutManager = layoutManager
        videoAdsAdapter = VideoAdsAdapter(requireActivity())
        binding.rvAds.adapter = videoAdsAdapter

        videoAdsAdapter!!.setValues(videoAdsList!!)

    }

    private fun setSpecial() {
        val layoutManager = LinearLayoutManager(
                requireActivity(),
                LinearLayoutManager.HORIZONTAL,
                false
        )

        layoutManager.isSmoothScrollbarEnabled = true
        binding.rvSpl.layoutManager = layoutManager
        videoSplAdapter = VideoSplAdapter(requireActivity())
        binding.rvSpl.adapter = videoSplAdapter
        videoSplAdapter!!.setValues(videoSplProgram!!)
    }

    private fun setSlide() {
        val layoutManager = LinearLayoutManager(
                requireActivity(),
                LinearLayoutManager.HORIZONTAL,
                false
        )
        binding.rvSlideHome.layoutManager = layoutManager
        binding.rvSlideHome.startAutoScroll()
        binding.rvSlideHome.isLoopEnabled = true
        binding.rvSlideHome.setCanTouch(true)
        slideAdapter = SlideAdapter(requireActivity())
        binding.rvSlideHome.adapter = slideAdapter
        slideAdapter!!.setValues(slideList!!)
    }

    override fun dataRecent() {
    }

    override fun dataTrend() {

    }

}