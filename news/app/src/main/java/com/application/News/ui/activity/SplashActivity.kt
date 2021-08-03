package com.application.News.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.application.News.R
import com.application.News.databinding.ActivitySplashBinding
import com.application.News.service.Status
import com.application.News.util.UtilsDefault
import com.application.News.util.viewBinding
import com.application.viewmodel.ApiViewModel
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashActivity : BaseActivity() {

    val binding: ActivitySplashBinding by viewBinding()
    val apiViewModel: ApiViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        initViews()
    }

    private fun initViews() {
       getLogo()
//        passData()
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
                            Glide.with(this).load(it.data.data.companyLogo).into(binding.logoSplash)
                            passData()
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

    fun passData(){
        Thread(Runnable {
            Thread.sleep(3000)
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }).start()
    }
}