package com.uraniumcode.e_walletplus

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
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
import com.uraniumcode.e_walletplus.databinding.ActivityMainBinding
import com.uraniumcode.e_walletplus.fragments.HomeFragmentDirections
import com.uraniumcode.e_walletplus.utils.Constants

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        OneSignal.initWithContext(this)
        OneSignal.setAppId(Constants().ONE_SIGNAL_APP_ID)
        MobileAds.initialize(this) {}

        listeners()
        controlWalletTouchAnimate()
        checkAndRequestNotificationPermission()
    }

    private fun listeners() {
        binding.imgBack.setOnClickListener {
            onBackPressed()
        }

        binding.imgAddWallet.setOnClickListener {
            controlWalletTouchAnimate()
            val action = HomeFragmentDirections.actionHomeFragmentToAddWalletFragment()
            Navigation.findNavController(binding.fragmentContainerView).navigate(action)
        }
    }

    @SuppressLint("WrongConstant")
    private fun controlWalletTouchAnimate() {
        val prefs = application.getSharedPreferences(Constants().PREF_NAME, MODE_PRIVATE)
        val animator = ObjectAnimator.ofFloat(binding.imgTouch, "alpha", 0f, 1f)

        if (prefs.getBoolean(Constants().PREF_FIRST_TIME, true)) {
            binding.imgTouch.visibility = View.VISIBLE
            animator.duration = 1000
            animator.repeatMode = Animation.REVERSE
            animator.repeatCount = Animation.INFINITE
            animator.start()
        } else {
            binding.imgTouch.visibility = View.GONE
            animator.removeAllListeners()
        }
    }

    fun changeToolbarTitle(text: String) {
        binding.tvToolbarTitle.text = text
    }

    fun openCloseButtons(isBackButtonOpen: Boolean) {
        if (isBackButtonOpen) {
            binding.imgBack.visibility = View.VISIBLE
            binding.imgAddWallet.visibility = View.GONE
        } else {
            binding.imgBack.visibility = View.GONE
            binding.imgAddWallet.visibility = View.VISIBLE
        }
    }

    private fun checkAndRequestNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    android.Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                requestPermissionLauncher.launch(android.Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { }
}
