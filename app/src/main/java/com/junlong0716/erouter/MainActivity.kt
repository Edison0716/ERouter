package com.junlong0716.erouter

import android.content.Intent
import android.graphics.PixelFormat
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings.ACTION_MANAGE_OVERLAY_PERMISSION
import android.view.View
import android.view.WindowManager
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.junlong0716.erouter.support.utils.Logger

/**
 * @author EdisonLi
 * @version 1.0
 * @since 2020-05-31
 */
class MainActivity : AppCompatActivity() {

    companion object{
        const val GET_DIALOG_PERMISSION = 0
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        Logger.i("hha")
        try {
            showBtn()
        }catch (e:Exception){
            val intent = Intent(ACTION_MANAGE_OVERLAY_PERMISSION)
            intent.data = Uri.parse("package:$packageName")
            startActivityForResult(intent, GET_DIALOG_PERMISSION)
        }

    }

    private fun showBtn(){
        val button = Button(this)
        button.text = "button"
        val layoutParams = WindowManager.LayoutParams(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT,
            0,
            0,
            PixelFormat.TRANSPARENT
        )
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {
            setShowWhenLocked(true)
        }
        layoutParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_ERROR;
        layoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
        layoutParams.x = 100
        layoutParams.y = 300
        windowManager.addView(button,layoutParams)
    }

    fun turn(view: View) {
        val intent = Intent()
        intent.setClass(this, MainActivity2::class.java)
        val bundle = Bundle()
        bundle.putString("extra_name", "ROUTER_TEST")
        intent.putExtras(bundle)
        startActivity(intent)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        showBtn()
    }
}
