package com.example.xminutesworkout

import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.xminutesworkout.Dao.HistoryDao
import com.example.xminutesworkout.Dao.HistoryEntity
import com.example.xminutesworkout.Dao.ItemAdapter
import com.example.xminutesworkout.Dao.WorkOutApp
import com.example.xminutesworkout.databinding.ActivityBmiBinding
import com.example.xminutesworkout.databinding.ActivityHistoryBinding
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class HistoryActivity : AppCompatActivity() {
    private var binding:ActivityHistoryBinding?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding?.root)


        setSupportActionBar(binding?.toolbarHistoryid)
        if (supportActionBar != null){
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.title="HISTORY"
        }
        binding?.toolbarHistoryid?.setNavigationOnClickListener {
            onBackPressed()
        }
        val dao = (application as WorkOutApp).db.historyDao()
        getHistoryExerciseData(dao)
    }
    private fun getHistoryExerciseData(
        //historyList:ArrayList<HistoryEntity>,
                                       historyDao: HistoryDao){

        lifecycleScope.launch {
            historyDao.fetchAllHistory().collect { allCompletedExerciseList ->
                if(allCompletedExerciseList.isNotEmpty()){
                    binding?.rvItemsList?.layoutManager=LinearLayoutManager(this@HistoryActivity)

                    binding?.tvNoRecordsAvailable?.visibility= View.INVISIBLE
                    binding?.rvItemsList?.visibility= View.VISIBLE

                    val dates=ArrayList<String>()
                    val Sets=ArrayList<String>()
                    val Exer=ArrayList<String>()
                    val Rest=ArrayList<String>()
                    for(date in allCompletedExerciseList){
                        dates.add(date.date)
                    }
                    for(set in allCompletedExerciseList){
                        Sets.add(set.sets)
                    }
                    for(exr in allCompletedExerciseList){
                        Exer.add(exr.durationExer)
                    }
                    for(res in allCompletedExerciseList){
                        Rest.add(res.duartionRest)
                    }
                    val historyAdapter=ItemAdapter(dates,Sets,Exer,Rest)
                        //,{ deleteID ->
                       // deleteRecordAlert(deleteID,historyDao)
                   // }
                    binding?.rvItemsList?.adapter=historyAdapter



                }else{
                    binding?.tvNoRecordsAvailable?.visibility=View.VISIBLE
                    binding?.rvItemsList?.visibility=View.INVISIBLE
                }
            }
        }
    }
    private fun deleteRecordAlert(id:Int,historyDao: HistoryDao){
        val builder= AlertDialog.Builder(this)
        builder.setTitle("Delete Record")

        builder.setPositiveButton("Yes"){
                dialogInterface, _ ->
            lifecycleScope.launch {
                historyDao.delete(HistoryEntity(id))
                Toast.makeText(applicationContext,"Record was Deleted", Toast.LENGTH_SHORT).show()
            }
            dialogInterface.dismiss()
        }

        builder.setNegativeButton("No") { dialogInterface, _ ->

            dialogInterface.dismiss()
        }

        val alertDialog: AlertDialog =builder.create()
        alertDialog.setCancelable(false)
        alertDialog.show()
    }
    override fun onDestroy(){
        super.onDestroy()
        binding=null
    }
}