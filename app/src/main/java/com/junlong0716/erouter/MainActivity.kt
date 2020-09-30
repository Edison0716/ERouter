package com.junlong0716.erouter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.erouter.annotation.Query
import com.example.module.sample.a.Test
import java.lang.Exception

class MainActivity : AppCompatActivity() {

    @JvmField
    @Query(key = "extra_name")
    var name: String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        try {
            `MainActivity$$QueryBinding`().bindExtras(this, intent.extras)
        }catch (e:Exception){
            Log.d("onError",e.message.toString())
        }

//        mainActivity.bindExtras(this, intent.extras)

//        val test = Test()
//        Toast.makeText(this, test.name, Toast.LENGTH_SHORT).show()
    }
}
