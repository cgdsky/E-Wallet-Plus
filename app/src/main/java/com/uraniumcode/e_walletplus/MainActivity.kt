package com.uraniumcode.e_walletplus

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.Navigation
import com.uraniumcode.e_walletplus.fragments.HomeFragmentDirections
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        listeners()
    }

    private fun listeners(){
        img_back.setOnClickListener {
                onBackPressed()
        }
        img_add_wallet.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToAddWalletFragment()
            Navigation.findNavController(fragment_container_view).navigate(action)
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