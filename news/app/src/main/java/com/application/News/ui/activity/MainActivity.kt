package com.application.News.ui.activity

import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.application.News.R
import com.application.News.databinding.ActivityMainBinding
import com.application.News.service.Status
import com.application.News.ui.fragment.CategoryFragment
import com.application.News.ui.fragment.HomeFragment
import com.application.News.ui.fragment.SearchFragment
import com.application.News.ui.helper.FragmentHelper
import com.application.News.util.viewBinding
import com.application.viewmodel.ApiViewModel
import com.bumptech.glide.Glide
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity() {

    val binding: ActivityMainBinding by viewBinding()
    var fragmentHelper: FragmentHelper? = null
    val apiViewModel: ApiViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViews()
        setOnClickListener()
        setUpFragments()
    }

    private fun initViews() {
        getLogo()

    }

    private fun getLogo() {
        apiViewModel.getLogo.observe(this, Observer {
            it?.let {
                when(it.status){

                    Status.LOADING -> {
                        showProgress()
                    }
                    Status.SUCCESS -> {
                        dismissProgress()
                        if (it.data!!.Status.equals("true")){
                            Glide.with(this).load(it.data.data.companyLogo).into(binding.appLogoImg)
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
        binding.bottomNavigationView.setOnNavigationItemSelectedListener(
            mOnNavigationItemSelectedListener
        )
    }

    private fun setUpFragments() {
        fragmentHelper = FragmentHelper(supportFragmentManager)
        fragmentHelper?.setUpFrame(HomeFragment(), binding.frameLayout)
        binding.bottomNavigationView?.selectedItemId = R.id.home
        binding.headTxt.text = "Home"
    }

    private val mOnNavigationItemSelectedListener =
            BottomNavigationView.OnNavigationItemSelectedListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.papular -> {
                        fragmentHelper?.push(SearchFragment())
                        binding.headTxt.text = "Papular"
                        return@OnNavigationItemSelectedListener true
                    }
                    R.id.search -> {
                        fragmentHelper?.push(SearchFragment())
                        binding.headTxt.text = "Search"
                        return@OnNavigationItemSelectedListener true
                    }
                    R.id.home -> {
                        fragmentHelper?.push(HomeFragment())
                        binding.headTxt.text = "Home"
                        return@OnNavigationItemSelectedListener true
                    }
                    R.id.cate -> {
                        fragmentHelper?.push(CategoryFragment())
                        binding.headTxt.text = "Category"
                        return@OnNavigationItemSelectedListener true
                    }R.id.settings -> {
                        fragmentHelper?.push(HomeFragment())
                    binding.headTxt.text = "Settings"
                        return@OnNavigationItemSelectedListener true
                    }

                }
                false
            }


}