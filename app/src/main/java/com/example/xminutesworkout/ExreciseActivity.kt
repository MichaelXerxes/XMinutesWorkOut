package com.example.xminutesworkout

import android.app.Dialog
import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.View
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.xminutesworkout.Exercises.ConstantSecondSet
import com.example.xminutesworkout.Exercises.ConstantThirdSet
import com.example.xminutesworkout.Exercises.Constants
import com.example.xminutesworkout.databinding.ActivityExreciseBinding
import com.example.xminutesworkout.databinding.CustomBackConfirmationBinding
import com.example.xminutesworkout.databinding.ExerciseLayoutBinding
import java.util.*
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
    private var player:MediaPlayer?=null

    private var exerciseAdapter:ExerciseStatusAdapter?=null

    private var numberExerciseSeconds:Int=0





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // list exercise

        //exerciseList=Constants.defaultExerciseList()
        setupProperExerciseList()
        tts= TextToSpeech(this,this)

        binding= ActivityExreciseBinding.inflate(layoutInflater)
        mergeBinding= ExerciseLayoutBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        //  toolbar
        setSupportActionBar(mergeBinding?.toolabarexerciseID)
        if (supportActionBar != null){
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
        mergeBinding?.toolabarexerciseID?.setNavigationOnClickListener {
            displayCustomDialogForBackButton()
        }
        //

        setupRestView()

        setupExerciseStatusRecyclerView()


    }
    private fun setupProperExerciseList(){
        var setnumber=intent.getIntExtra("set",0)
        when{
            setnumber==1 ->{
                exerciseList= Constants.defaultExerciseList()
            }
            setnumber==2 ->{
                exerciseList= ConstantSecondSet.defaultExerciseList()
            }
            setnumber==3 ->{
                exerciseList= ConstantThirdSet.defaultExerciseList()
            }
        }

        numberExerciseSeconds=intent.getIntExtra("Exercise_seconds",0)




    }
    private fun displayCustomDialogForBackButton(){
        val customDialog=Dialog(this)
        val customdialogBinding=CustomBackConfirmationBinding.inflate(layoutInflater)
        customDialog.setContentView(customdialogBinding.root)
        customDialog.setCanceledOnTouchOutside(false)
        customdialogBinding.btnYesID.setOnClickListener {
            this@ExreciseActivity.finish()
            customDialog.dismiss()
        }
        customdialogBinding.btnNoID.setOnClickListener {
            customDialog.dismiss()
        }
        customDialog.show()
    }

    override fun onBackPressed() {
        displayCustomDialogForBackButton()
        //super.onBackPressed()
    }

    private fun setupExerciseStatusRecyclerView(){
        binding?.rvExerciseStatus?.layoutManager=
            LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,
            false)

        exerciseAdapter= ExerciseStatusAdapter(exerciseList!!)
        binding?.rvExerciseStatus?.adapter=exerciseAdapter

        mergeBinding?.rvExerciseStatusID?.layoutManager=
            LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,
                false)
        mergeBinding?.rvExerciseStatusID?.adapter=exerciseAdapter
    }



    private fun setupRestView(){
        try {
            val soundURI= Uri.parse("android.resource://com.example.xminutesworkout/"+
            R.raw.mixkitdoublelittlebirdchirp)
            player=MediaPlayer.create(applicationContext,soundURI)
            player?.isLooping=false
            player?.start()

        }catch (e:Exception){
            e.printStackTrace()
        }



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
       try {
           binding?.tvNextExerciseName?.text =
               exerciseList!![currentExrPosition + 1].getName().toString()
       }catch (e:Exception){
           e.printStackTrace()
       }

        setExreciseProgressBar()

    }
    private fun setRestProgressBar(){
        binding?.progressBar?.progress = restProgress

        restTimer=object : CountDownTimer(2000,1000){
            override fun onTick(millisUntilFinished: Long) {
                restProgress++
                binding?.progressBar?.progress=5-restProgress
                binding?.tvTimer?.text=(5-restProgress).toString()

            }

            override fun onFinish() {
                currentExrPosition++
                /////
                exerciseList!![currentExrPosition].setIsSelected(true)
                //exerciseList!![currentExrPosition].setIsComplited(true)
                exerciseAdapter!!.notifyDataSetChanged()



                setupExreciseView()

            }
        }.start()
    }

    private fun setExreciseProgressBar(){
        mergeBinding?.ExerciseprogressBarID?.progress=exerciseProgress

        mergeBinding?.tvExreciseTimerID?.text=numberExerciseSeconds.toString()

        exerciseTimer=object : CountDownTimer((numberExerciseSeconds*1000).toLong(),1000){
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
                    ///////current exercise complited
                    exerciseList!![currentExrPosition].setIsSelected(false)
                    exerciseList!![currentExrPosition].setIsComplited(true)
                    exerciseAdapter!!.notifyDataSetChanged()
                    setupRestView()
                }else{
                    Toast.makeText(
                        this@ExreciseActivity,
                        "Congratulation you finish",
                        Toast.LENGTH_SHORT
                    ).show()


                    val intent=Intent(this@ExreciseActivity, FinishActivity::class.java)
                    startActivity(intent)
                    //intent.putExtra("Exercise List",exerciseList)
                    finish()
                }
            }
        }.start()
    }
    private fun startTimer(pauseOffsetL: Long) {

        exerciseTimer= object : CountDownTimer((timeLeftInSeconds.toInt()*1000).toLong()
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
                    exerciseList!![currentExrPosition].setIsSelected(false)
                    exerciseList!![currentExrPosition].setIsComplited(true)
                    exerciseAdapter!!.notifyDataSetChanged()
                    setupRestView()
                }else{
                    Toast.makeText(
                        this@ExreciseActivity,
                        "Congratulation you finish",
                        Toast.LENGTH_SHORT
                    ).show()
                    val intent=Intent(this@ExreciseActivity, FinishActivity::class.java)
                    startActivity(intent)
                    //intent.putExtra("Exercise List",exerciseList)
                    finish()
                }
            }

        }.start()
    }
    private fun pauseTimer(timer: CountDownTimer){
        timer.cancel()
        isPauseChecked=true
    }


  //       Test To Speech

    override fun onInit(status: Int) {
        if(status== TextToSpeech.SUCCESS){
            val result= tts?.setLanguage(Locale.ENGLISH)
            if(result==TextToSpeech.LANG_MISSING_DATA ||
                    result== TextToSpeech.LANG_NOT_SUPPORTED){
                Log.e("TTS","The language specified is not supported!")
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

//               Destroy

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
        if(player!=null){
            player!!.stop()
        }


        binding=null
        mergeBinding=null
    }

}