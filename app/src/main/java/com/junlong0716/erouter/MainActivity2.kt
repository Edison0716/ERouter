package com.junlong0716.erouter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.erouter.annotation.Query

/**
 * @author EdisonLi
 * @version 1.0
 * @since 2020-05-31
 */
class MainActivity2 : AppCompatActivity() {

    @JvmField
    @Query(key = "extra_name")
    var extraStr: String = "DEFAULT_VALUE"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        // bind
        `MainActivity2$$QueryBinding`().bindExtras(this, intent.extras)

        Toast.makeText(this, extraStr, Toast.LENGTH_SHORT).show()
    }
}