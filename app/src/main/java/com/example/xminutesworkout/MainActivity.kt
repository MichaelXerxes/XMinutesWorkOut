package com.example.xminutesworkout

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.FrameLayout
import android.widget.Toast
import com.example.xminutesworkout.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    //private var startBtn:FrameLayout?=null
    private var binding:ActivityMainBinding?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)


        binding?.flBtnStart?.setOnClickListener {
            Toast.makeText(this,
            "Start Stop",Toast.LENGTH_SHORT).show()
            val intent=Intent(this,ExreciseActivity::class.java)
            intent.putExtra("set",1)
            startActivity(intent)
        }
        binding?.flBtnStartSecondSet?.setOnClickListener {
            Toast.makeText(this,
                "Start Stop",Toast.LENGTH_SHORT).show()
            val intent=Intent(this,ExreciseActivity::class.java)
            intent.putExtra("set",2)
            startActivity(intent)
        }
        binding?.flBtnStartThirdSet?.setOnClickListener {
            Toast.makeText(this,
                "Start Stop",Toast.LENGTH_SHORT).show()
            val intent=Intent(this,ExreciseActivity::class.java)
            intent.putExtra("set",3)
            startActivity(intent)
        }


    }
// to avoid memory leak
    override fun onDestroy() {
        super.onDestroy()
        binding=null
    }

}