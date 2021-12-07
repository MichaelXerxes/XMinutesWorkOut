package com.example.xminutesworkout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.xminutesworkout.databinding.ActivityFinishBinding

class FinishActivity : AppCompatActivity() {

    private var binding:ActivityFinishBinding?=null
    //private var exerciseList:ArrayList<ExerciseModel>?=null
    private var exerciseList:ArrayList<ExerciseModel>?=null
    private var exerciseAdapter:ExerciseStatusAdapter?=null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityFinishBinding.inflate(layoutInflater)
        exerciseList=Constants.forFinishActivityRView()
        exerciseAdapter= ExerciseStatusAdapter(exerciseList!!)


        setContentView(binding?.root)
         //exerciseList=intent.getLongArrayExtra("Exercise List")



        /*setSupportActionBar(binding?.toolbarFinishActivity)
        if (supportActionBar != null){
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
        binding?.toolbarFinishActivity?.setNavigationOnClickListener {
            onBackPressed()
        }*/

        binding?.btnFinish?.setOnClickListener {
            finish()
        }
        setupExerciseStatusRecyclerView()
    }
    private fun setupExerciseStatusRecyclerView(){
        binding?.rvFinishStatusID?.layoutManager=
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,
                false)


        binding?.rvFinishStatusID?.adapter=exerciseAdapter
        exerciseAdapter!!.notifyDataSetChanged()


    }
}