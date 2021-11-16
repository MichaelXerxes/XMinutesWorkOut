package com.example.xminutesworkout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Toast
import com.example.xminutesworkout.databinding.ActivityExreciseBinding

class ExreciseActivity : AppCompatActivity() {
    private var binding:ActivityExreciseBinding?=null
    private var restTimer: CountDownTimer?=null
    private var restProgress=0

    private var exerciseTimer: CountDownTimer?=null
    private var exerciseProgress=0

    private var exerciseList:ArrayList<ExerciseModel>?=null
    private var currentExrPosition = -1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityExreciseBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setSupportActionBar(binding?.toolbarExrecise)
        // set back button on tool bar
        if(supportActionBar!=null){
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
        // list exercise
        exerciseList=Constants.defaultExerciseList()

        binding?.toolbarExrecise?.setNavigationOnClickListener {
            onBackPressed()
        }
        setupRestView()


    }
    private fun setupRestView(){
        if (restTimer!=null){
            restTimer?.cancel()
            restProgress=0
        }

        setRestProgressBar()
    }

   private fun setupExreciseView(){
        binding?.frameLayoutProgressBar?.visibility=View.INVISIBLE
        binding?.tvtitle?.text= "Exrecise Name"
        binding?.frameExerciseLayoutProgressBar?.visibility=View.VISIBLE
        //reset Timer
        if(exerciseTimer!=null){
            exerciseTimer?.cancel()
            exerciseProgress=0
        }
        setExreciseProgressBar()

    }
    private fun setRestProgressBar(){
        binding?.progressBar?.progress = restProgress

        restTimer=object : CountDownTimer(5000,1000){
            override fun onTick(millisUntilFinished: Long) {
                restProgress++
                binding?.progressBar?.progress=5-restProgress
                binding?.tvTimer?.text=(5-restProgress).toString()
            }

            override fun onFinish() {
                currentExrPosition++
                setupExreciseView()

            }
        }.start()
    }

    private fun setExreciseProgressBar(){
        binding?.ExerciseprogressBar?.progress = exerciseProgress

        exerciseTimer=object : CountDownTimer(30000,1000){
            override fun onTick(millisUntilFinished: Long) {
                exerciseProgress++
                binding?.ExerciseprogressBar?.progress=30-exerciseProgress
                binding?.tvExreciseTimer?.text=(30-exerciseProgress).toString()
            }

            override fun onFinish() {
                Toast.makeText(
                    this@ExreciseActivity,
                    "£0 seconds are over  rest view.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }.start()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (restTimer!=null){
            restTimer?.cancel()
            restProgress=0
        }
        if(exerciseTimer!=null){
            exerciseTimer?.cancel()
            exerciseProgress=0
        }
        binding=null
    }
}