package com.uraniumcode.e_walletplus

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.navigation.Navigation
import com.google.android.gms.ads.MobileAds
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
        MobileAds.initialize(this){}
        listeners()
        controlWalletTouchAnimate()
        checkAndRequestNotificationPermission()
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

    private fun checkAndRequestNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    android.Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED) {
                requestPermissionLauncher.launch(android.Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { }

}