package com.application.News.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.application.News.R
import com.application.News.databinding.FragmentCategoryBinding
import com.application.News.databinding.FragmentSearchBinding
import com.application.News.service.Status
import com.application.News.ui.adapter.SearchCategoryListAdapter
import com.application.News.ui.model.CategoryData
import com.application.News.util.viewBinding
import com.application.viewmodel.ApiViewModel


class CategoryFragment : BaseFragment(R.layout.fragment_category),SearchCategoryListAdapter.ManageCardInterface {

    private val binding by viewBinding(FragmentCategoryBinding::bind)
//    var recentCateList: ArrayList<CategoryData>? = null
//    var searchCategoryListAdapter: SearchCategoryListAdapter? = null
    val apiViewModel: ApiViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        setOnClickListener()
    }

    private fun setOnClickListener() {

    }

    private fun initViews() {
//        recentCateList = ArrayList()

        val layoutManager = GridLayoutManager(
                requireActivity(),
                3
        )
        layoutManager.isSmoothScrollbarEnabled = true
        binding.rvCategoriesNews.layoutManager  = layoutManager
//        searchCategoryListAdapter = SearchCategoryListAdapter(requireActivity(), this)
//        binding.rvCategoriesNews.adapter = searchCategoryListAdapter

        getCategoryList()
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
//                                recentCateList!!.clear()
//                                recentCateList!!.add(CategoryData("1","All","1"))
//                                recentCateList!!.addAll(it.data.data)
//                                searchCategoryListAdapter!!.setValues(recentCateList!!)
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

    override fun searchCate() {

    }
}