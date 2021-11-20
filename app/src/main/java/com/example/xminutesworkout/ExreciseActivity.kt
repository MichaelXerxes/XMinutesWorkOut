package com.example.xminutesworkout

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.AttributeSet
import android.view.View
import android.widget.*
import com.example.xminutesworkout.databinding.ActivityExreciseBinding
import com.example.xminutesworkout.databinding.ExerciseLayoutBinding

class ExreciseActivity : AppCompatActivity() {
    private var binding:ActivityExreciseBinding?=null
    private var restTimer: CountDownTimer?=null
    private var restProgress=0

    private var exerciseTimer: CountDownTimer?=null
    private var exerciseProgress=0

    private var exerciseList:ArrayList<ExerciseModel>?=null
    private var currentExrPosition = -1



    private var mergeBinding: ExerciseLayoutBinding?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // list exercise
        exerciseList=Constants.defaultExerciseList()

        binding= ActivityExreciseBinding.inflate(layoutInflater)
        mergeBinding= ExerciseLayoutBinding.inflate(layoutInflater)

        setContentView(binding?.root)

        setSupportActionBar(binding?.toolbarExrecise)
        // set back button on tool bar
        if(supportActionBar!=null){
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }



        binding?.toolbarExrecise?.setNavigationOnClickListener {
            onBackPressed()
        }
        setupRestView()


    }



    private fun setupRestView(){
        setContentView(binding?.root)
        binding?.frameLayoutProgressBar?.visibility=View.VISIBLE
        binding?.tvtitle?.visibility=View.VISIBLE

        if (restTimer!=null){
            restTimer?.cancel()
            restProgress=0
        }

        setRestProgressBar()
    }

   private fun setupExreciseView(){
        binding?.frameLayoutProgressBar?.visibility=View.INVISIBLE
        binding?.tvtitle?.visibility=View.INVISIBLE


       setContentView(mergeBinding?.root)

       mergeBinding?.ivImageItsNameID?.setImageResource(exerciseList!![currentExrPosition].getImage())
       mergeBinding?.tvExerciseNameID?.text=exerciseList!![currentExrPosition].getName()

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
        mergeBinding?.ExerciseprogressBarID?.progress=exerciseProgress

        exerciseTimer=object : CountDownTimer(8000,1000){
            override fun onTick(millisUntilFinished: Long) {
                exerciseProgress++

                mergeBinding?.ExerciseprogressBarID?.progress=8-exerciseProgress
                mergeBinding?.tvExreciseTimerID?.text=(8-exerciseProgress).toString()

            }

            override fun onFinish() {
                if(currentExrPosition<exerciseList?.size!!-1){
                    setupRestView()
                }else{
                    Toast.makeText(
                        this@ExreciseActivity,
                        "Congratulation you finish",
                        Toast.LENGTH_SHORT
                    ).show()
                }
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