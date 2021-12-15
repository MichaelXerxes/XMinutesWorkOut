package com.example.xminutesworkout

import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.NumberPicker
import android.widget.Toast
import com.example.xminutesworkout.databinding.ActivityMainBinding
import com.example.xminutesworkout.databinding.MenuPopupBinding

class MainActivity : AppCompatActivity() {
    //private var startBtn:FrameLayout?=null
    private var binding:ActivityMainBinding?=null
    private var numberSets:Int=0
    private var exerciseSeconds:Int=1
    private var restSeconds:Int=1
    private var execsieName:String=""
    private var valuesListForNextIntenty:ArrayList<Int> ?=null




   // private var tvInput: TextView?=null
    private var numeric:Boolean=false
    //private var lastDot:Boolean=false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)




        binding?.flBtnStart?.setOnClickListener {
            showMenu()

           /* Toast.makeText(this,
            "Start Stop",Toast.LENGTH_SHORT).show()
            val intent=Intent(this,ExreciseActivity::class.java)
            intent.putExtra("set",1)
            startActivity(intent)

            */
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
        binding?.bmiCalculator?.setOnClickListener {
            val intent=Intent(this,BMIActivity::class.java)
            startActivity(intent)
        }
        binding?.btnHistory?.setOnClickListener {
            val intent=Intent(this,HistoryActivity::class.java)
            startActivity(intent)
        }


    }


    private fun showMenu(){
        val menuDialog=Dialog(this)
        var popupBinding= MenuPopupBinding.inflate(layoutInflater)
        menuDialog.setContentView(popupBinding.root)
        menuDialog.setCanceledOnTouchOutside(false)
        popupBinding.numberPickerforSets.minValue=1
        popupBinding.numberPickerforSets.maxValue=4
        popupBinding.numberPickerforExerciseinSeconds.minValue=1
        popupBinding.numberPickerforExerciseinSeconds.maxValue=60
        popupBinding.numberPickerforRestinSeconds.minValue=1
        popupBinding.numberPickerforRestinSeconds.maxValue=30
        valuesListForNextIntenty= ArrayList()

        popupBinding.btnNoID.setOnClickListener{
            menuDialog.dismiss()
        }
        popupBinding.btnYesID.setOnClickListener {

            //////////////////////////TEst Area

            valuesListForNextIntenty!!.add(popupBinding.numberPickerforSets.value)
            valuesListForNextIntenty!!.add(popupBinding.numberPickerforExerciseinSeconds.value)
            valuesListForNextIntenty!!.add(popupBinding.numberPickerforRestinSeconds.value)

            /*


            popupBinding.numberPickerforSets.setOnValueChangedListener { picker, oldVal, newVal ->
                valuesListForNextIntenty!!.add(newVal)
            }


            popupBinding.numberPickerforExerciseinSeconds.setOnValueChangedListener { picker, oldVal, newVal ->
                valuesListForNextIntenty!!.add(newVal)
            }
            popupBinding.numberPickerforRestinSeconds.setOnValueChangedListener { picker, oldVal, newVal ->
                valuesListForNextIntenty!!.add(newVal)
            }

             */



            menuDialog.dismiss()
            Toast.makeText(this,
                "Start Stop",Toast.LENGTH_SHORT).show()
            val intent=Intent(this,ExreciseActivity::class.java)
            intent.putExtra("set",1)
            intent.putExtra("Exercise_seconds", valuesListForNextIntenty!![1])
            intent.putExtra("Number_sets",valuesListForNextIntenty!![0])
            intent.putExtra("Rest_seconds",valuesListForNextIntenty!![2])
            //intent.putExtra("ArrayAny",valuesListForNextIntenty)
           // intent.putExtra("IntArray",valuesListForNextIntenty)
            startActivity(intent)

        }
        menuDialog.show()
    }
// to avoid memory leak
    override fun onDestroy() {
        super.onDestroy()
        binding=null
        exerciseSeconds=1
        restSeconds=1
        execsieName=""
        if(valuesListForNextIntenty!=null){
            valuesListForNextIntenty=null
    }
    }

}