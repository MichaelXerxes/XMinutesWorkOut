package com.example.xminutesworkout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.*
import com.example.xminutesworkout.databinding.ActivityExreciseBinding

class ExreciseActivity : AppCompatActivity() {
    private var binding:ActivityExreciseBinding?=null
    private var restTimer: CountDownTimer?=null
    private var restProgress=0

    private var exerciseTimer: CountDownTimer?=null
    private var exerciseProgress=0

    private var exerciseList:ArrayList<ExerciseModel>?=null
    private var currentExrPosition = -1

    private var exerciseLayout:LinearLayout?=null
    private var progressBarExer:ProgressBar?=null
    private var exerName:TextView?=null
    private var timerNameID:TextView?=null
    private var image:ImageView?=null

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
        setContentView(binding?.root)
        binding?.frameLayoutProgressBar?.visibility=View.VISIBLE
        binding?.tvtitle?.visibility=View.VISIBLE
        //binding?.ivImage1?.visibility=View.INVISIBLE
       // binding?.tvExercise?.visibility=View.INVISIBLE
       // binding?.frameExerciseLayoutProgressBar?.visibility=View.INVISIBLE
        if (restTimer!=null){
            restTimer?.cancel()
            restProgress=0
        }

        setRestProgressBar()
    }

   private fun setupExreciseView(){
        binding?.frameLayoutProgressBar?.visibility=View.INVISIBLE
        binding?.tvtitle?.visibility=View.INVISIBLE
      // binding?.ivImage1?.visibility=View.VISIBLE
      // binding?.tvExercise?.visibility=View.VISIBLE
     //   binding?.frameExerciseLayoutProgressBar?.visibility=View.VISIBLE
       image=findViewById(R.id.ivImageItsNameID)
       exerName=findViewById(R.id.tvExerciseNameID)
       exerciseLayout=findViewById(R.id.linlayExerciseID)
       setContentView(R.layout.exercise_layout)


        //reset Timer
        if(exerciseTimer!=null){
            exerciseTimer?.cancel()
            exerciseProgress=0
        }
       image?.setImageResource(exerciseList!![currentExrPosition].getImage())
       exerName?.text=exerciseList!![currentExrPosition].getName()


  //     binding?.ivImage1?.setImageResource(exerciseList!![currentExrPosition].getImage())
    //   binding?.tvExercise?.text=exerciseList!![currentExrPosition].getName()
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
        progressBarExer=findViewById(R.id.ExerciseprogressBarID)
        timerNameID=findViewById(R.id.tvExreciseTimerID)
        //binding?.ExerciseprogressBar?.progress = exerciseProgress
        progressBarExer?.progress=exerciseProgress

        exerciseTimer=object : CountDownTimer(8000,1000){
            override fun onTick(millisUntilFinished: Long) {
                exerciseProgress++
                //binding?.ExerciseprogressBar?.progress=8-exerciseProgress
               // binding?.tvExreciseTimer?.text=(8-exerciseProgress).toString()
                progressBarExer?.progress=8-exerciseProgress
                timerNameID?.text=(8-exerciseProgress).toString()
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