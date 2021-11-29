package com.test.iprobonus

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.test.iprobonus.Keys.ACCESS_KEY
import com.test.iprobonus.Keys.CLIENT_ID
import com.test.iprobonus.Keys.DEVICE_ID
import com.test.iprobonusview.MainFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val mainFragment = MainFragment.instanceWithParams(ACCESS_KEY, CLIENT_ID, DEVICE_ID)

        supportFragmentManager.beginTransaction()
            .replace(R.id.nav_host_fragment, mainFragment)
            .commitAllowingStateLoss()

    }
}