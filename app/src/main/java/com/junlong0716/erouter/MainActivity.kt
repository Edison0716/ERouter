package com.junlong0716.erouter

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

/**
 * @author EdisonLi
 * @version 1.0
 * @since 2020-05-31
 */
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun turn(view: View) {
        val intent = Intent()
        intent.setClass(this, MainActivity2::class.java)
        val bundle = Bundle()
        bundle.putString("extra_name", "ROUTER_TEST")
        intent.putExtras(bundle)
        startActivity(intent)
    }
}
