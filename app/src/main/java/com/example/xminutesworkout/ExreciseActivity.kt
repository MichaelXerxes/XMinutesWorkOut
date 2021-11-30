package com.example.xminutesworkout

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.speech.tts.TextToSpeech
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.*
import com.example.xminutesworkout.databinding.ActivityExreciseBinding
import com.example.xminutesworkout.databinding.ExerciseLayoutBinding
import java.util.*
import java.util.function.BinaryOperator
import kotlin.collections.ArrayList

class ExreciseActivity : AppCompatActivity(), TextToSpeech.OnInitListener {
    private var binding:ActivityExreciseBinding?=null
    private var mergeBinding: ExerciseLayoutBinding?=null

    private var restTimer: CountDownTimer?=null
    private var restProgress=0

    private var exerciseTimer: CountDownTimer?=null
    private var exerciseProgress=0

    private var exerciseList:ArrayList<ExerciseModel>?=null
    private var currentExrPosition = -1

    private var pauseOffset:Long = 0
    private var isPauseChecked:Boolean=false

    private var index=0
    private var timeLeftInSeconds:Int=0

    private var tts:TextToSpeech?=null





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // list exercise
        exerciseList=Constants.defaultExerciseList()
        tts= TextToSpeech(this,this)

        binding= ActivityExreciseBinding.inflate(layoutInflater)
        mergeBinding= ExerciseLayoutBinding.inflate(layoutInflater)

        setContentView(binding?.root)
        setSupportActionBar(mergeBinding?.toolabarexerciseID)



        mergeBinding?.toolabarexerciseID?.setNavigationOnClickListener {
            onBackPressed()
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
        binding?.tvNextExerciseName?.visibility=View.VISIBLE
        binding?.tvNextExercise?.visibility=View.VISIBLE

        if (restTimer!=null){
            restTimer?.cancel()
            restProgress=0
        }


        binding?.tvNextExerciseName?.text =
            exerciseList!![currentExrPosition+1].getName().toString()
        speakOut("Rest Now! Ha Ha")

        setRestProgressBar()
    }

   private fun setupExreciseView(){
        binding?.frameLayoutProgressBar?.visibility=View.INVISIBLE
        binding?.tvtitle?.visibility=View.INVISIBLE
        binding?.tvNextExerciseName?.visibility=View.INVISIBLE
        binding?.tvNextExercise?.visibility=View.INVISIBLE


       setContentView(mergeBinding?.root)

       mergeBinding?.ivImageItsNameID?.setImageResource(exerciseList!![currentExrPosition].getImage())
       mergeBinding?.tvExerciseNameID?.text=exerciseList!![currentExrPosition].getName()


        //reset Timer
        if(exerciseTimer!=null){
            exerciseTimer?.cancel()
            exerciseProgress=0
        }
       // speak Out !!
       speakOut(exerciseList!![currentExrPosition].getName())

       binding?.tvNextExerciseName?.text=exerciseList!![currentExrPosition+1].getName().toString()

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
                timeLeftInSeconds=(millisUntilFinished/1000).toInt()

                val long1:Long=(8-exerciseProgress).toLong()


                mergeBinding?.pausePlayBtnID?.setOnClickListener {
                    index++
                    if (index%2!=0){
                        pauseTimer(exerciseTimer!!)
                    }
                    else{
                        startTimer(long1)
                        isPauseChecked=false
                    }
                }

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
    private fun startTimer(pauseOffsetL: Long) {

        exerciseTimer= object : CountDownTimer((timeLeftInSeconds*1000).toLong()
            ,1000){
            override fun onTick(millisUntilFinished: Long) {
                //pauseOffset=timeDuration - millisUntilFinished

                mergeBinding?.ExerciseprogressBarID?.progress=(millisUntilFinished/1000).toInt()
                //mergeBinding?.tvExreciseTimerID?.text=(8-exerciseProgress).toString()
                mergeBinding?.tvExreciseTimerID?.text=(millisUntilFinished/1000).toString()
                timeLeftInSeconds=(millisUntilFinished/1000).toInt()
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
    private fun pauseTimer(timer: CountDownTimer){
        timer.cancel()
        isPauseChecked=true
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
            index=0
            timeLeftInSeconds=0
        }
        if(tts!=null){
            tts!!.stop()
            tts!!.shutdown()
        }


        binding=null
        mergeBinding=null
    }

    override fun onInit(status: Int) {
        if(status== TextToSpeech.SUCCESS){
            val result= tts?.setLanguage(Locale.ENGLISH)
            if(result==TextToSpeech.LANG_MISSING_DATA ||
                    result== TextToSpeech.LANG_NOT_SUPPORTED){
                Log.e("TTS","The :anguage specified is not supported!")
            }else{
                Log.e("TTS","Initialization Failed!")
            }
        }
    }
    private fun speakOut(text: String){
        if (tts!=null) {
            tts!!.speak(text, TextToSpeech.QUEUE_FLUSH, null, "")
        }
    }

}