package com.application.News.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.application.News.R
import com.application.News.databinding.FragmentSearchBinding
import com.application.News.service.Status
import com.application.News.ui.adapter.RecentNewsAdapter
import com.application.News.ui.adapter.RecentSearchAdapter
import com.application.News.ui.adapter.SearchCategoryListAdapter
import com.application.News.ui.adapter.SlideAdapter
import com.application.News.ui.model.CategoryData
import com.application.News.ui.model.RecentSearch
import com.application.News.ui.model.SlideRes
import com.application.News.util.viewBinding
import com.application.viewmodel.ApiViewModel
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : BaseFragment(R.layout.fragment_search),RecentSearchAdapter.ManageCardInterface,SearchCategoryListAdapter.ManageCardInterface {

    private val binding by viewBinding(FragmentSearchBinding::bind)
    var recentSearchList: ArrayList<RecentSearch>? = null
    var recentCateList: ArrayList<CategoryData>? = null
    var recentSearchAdapter: RecentSearchAdapter? = null
    var searchCategoryListAdapter: SearchCategoryListAdapter? = null
    val apiViewModel: ApiViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        setOnClickListener()
    }

    private fun initViews() {
        recentSearchList = ArrayList()
        recentCateList = ArrayList()

        getCategoryList()

        recentSearchList!!.add(RecentSearch("Sports"))
        recentSearchList!!.add(RecentSearch("Cricket"))
        recentSearchList!!.add(RecentSearch("Video"))
        recentSearchList!!.add(RecentSearch("Music"))


        val layoutManager = LinearLayoutManager(
                requireActivity(),
                LinearLayoutManager.VERTICAL,
                false
        )
        layoutManager.isSmoothScrollbarEnabled = true
        binding.rvRecentSearch.layoutManager  = layoutManager
        recentSearchAdapter = RecentSearchAdapter(requireActivity(), this)
        binding.rvRecentSearch.adapter = recentSearchAdapter

        recentSearchAdapter!!.setValues(recentSearchList!!)

        val layoutManager1 = GridLayoutManager(
                requireActivity(),
                3
        )
        layoutManager1.isSmoothScrollbarEnabled = true
        binding.rvCategories.layoutManager  = layoutManager1
        searchCategoryListAdapter = SearchCategoryListAdapter(requireActivity(), this)
        binding.rvCategories.adapter = searchCategoryListAdapter

    }

    private fun getCategoryList() {
        apiViewModel.getCategory.observe(requireActivity(), Observer {
            it?.let {
                when(it.status){

                    Status.LOADING -> {
                        showProgress()
                    }
                    Status.SUCCESS -> {
                        dismissProgress()
                        if (it.data!!.Status.equals("true")){
                            if (it.data.data.size > 0){
                                recentCateList!!.clear()
                                recentCateList!!.add(CategoryData("1","All","1"))
                                recentCateList!!.addAll(it.data.data)
                                searchCategoryListAdapter!!.setValues(recentCateList!!)
                            }
                        }else{
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

    private fun setOnClickListener() {

    }

    override fun dataRecent() {

    }

    override fun searchCate() {

    }
}