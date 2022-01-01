package com.example.xminutesworkout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.xminutesworkout.Dao.HistoryDao
import com.example.xminutesworkout.Dao.HistoryEntity
import com.example.xminutesworkout.Dao.WorkOutApp
import com.example.xminutesworkout.Exercises.Constants
import com.example.xminutesworkout.databinding.ActivityFinishBinding
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class FinishActivity : AppCompatActivity() {

    private var binding:ActivityFinishBinding?=null

    private var exerciseList:ArrayList<ExerciseModel>?=null
    private var exerciseAdapter:ExerciseStatusAdapter?=null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityFinishBinding.inflate(layoutInflater)
        exerciseList= Constants.forFinishActivityRView()
        exerciseAdapter= ExerciseStatusAdapter(exerciseList!!)


        setContentView(binding?.root)


        binding?.btnFinish?.setOnClickListener {
            finish()
        }
        setupExerciseStatusRecyclerView()

        val historyDao=(application as WorkOutApp).db.historyDao()
        addHisoryDateToDataBase(historyDao)
    }
    private fun setupExerciseStatusRecyclerView(){
        binding?.rvFinishStatusID?.layoutManager=
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,
                false)


        binding?.rvFinishStatusID?.adapter=exerciseAdapter
        exerciseAdapter!!.notifyDataSetChanged()


    }
    private fun addExtraToDaoHistoryList():IntArray{
        val list: IntArray? =intent.getIntArrayExtra("for Dao")
        return list!!
    }

    private fun addHisoryDateToDataBase(historyDao: HistoryDao){
        val exercisedate=Calendar.getInstance()
        val dateTime=exercisedate.time

        val sdf=SimpleDateFormat("dd MMM yyyy HH:mm:ss",Locale.getDefault())
        val date=sdf.format(dateTime)

        val listInt:IntArray= addExtraToDaoHistoryList()

        lifecycleScope.launch {
            //historyDao.insert(HistoryEntity(date=date))
            historyDao.insert(HistoryEntity(date=date,sets =listInt[0].toString(), durationExer = listInt[1].toString(),
            duartionRest = listInt[2].toString()))
            Toast.makeText(applicationContext,"Record saved",Toast.LENGTH_SHORT).show()
        }
    }
    override fun onDestroy(){
        super.onDestroy()
        binding=null
        exerciseList=null
        exerciseAdapter=null
    }
}