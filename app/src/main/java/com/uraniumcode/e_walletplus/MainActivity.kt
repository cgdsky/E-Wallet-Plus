package com.uraniumcode.e_walletplus

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import androidx.navigation.Navigation
import com.onesignal.OneSignal
import com.uraniumcode.e_walletplus.fragments.HomeFragmentDirections
import com.uraniumcode.e_walletplus.utils.Constants
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        OneSignal.initWithContext(this)
        OneSignal.setAppId(Constants().ONE_SIGNAL_APP_ID)
        listeners()
        controlWalletTouchAnimate()
    }

    private fun listeners(){
        img_back.setOnClickListener {
                onBackPressed()
        }
        img_add_wallet.setOnClickListener {
            controlWalletTouchAnimate()
            val action = HomeFragmentDirections.actionHomeFragmentToAddWalletFragment()
            Navigation.findNavController(fragment_container_view).navigate(action)
        }
    }

    @SuppressLint("WrongConstant")
    private fun controlWalletTouchAnimate(){
        val prefs: SharedPreferences = application.getSharedPreferences(Constants().PREF_NAME, MODE_PRIVATE)
        val objAnimator = ObjectAnimator.ofFloat(img_touch, "alpha", 0f, 1f)

        if (prefs.getBoolean(Constants().PREF_FIRST_TIME, true)) {
        img_touch.visibility = View.VISIBLE
        objAnimator.duration = 1000
        objAnimator.repeatMode = Animation.REVERSE
        objAnimator.repeatCount = Animation.INFINITE
        objAnimator.start()
        }else{
            img_touch.visibility = View.GONE
            objAnimator.removeAllListeners()
        }
    }

    fun changeToolbarTitle(text : String){
        tv_toolbar_title.text = text
    }

    fun openCloseButtons(isBackButtonOpen : Boolean){
        if(isBackButtonOpen){
            img_back.visibility = View.VISIBLE
            img_add_wallet.visibility = View.GONE
        }else{
            img_back.visibility = View.GONE
            img_add_wallet.visibility = View.VISIBLE
        }
    }



}