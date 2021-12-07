package com.example.xminutesworkout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.xminutesworkout.databinding.ActivityFinishBinding

class FinishActivity : AppCompatActivity() {

    private var binding:ActivityFinishBinding?=null

    private var exerciseAdapter:ExerciseStatusAdapter?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityFinishBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        val exerciseList=intent.getArrArrayListExtra<ExerciseModel>("Exercise List")


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

        exerciseAdapter= ExerciseStatusAdapter(exerciseList!!)
        binding?.rvFinishStatusID?.adapter=exerciseAdapter


    }
}