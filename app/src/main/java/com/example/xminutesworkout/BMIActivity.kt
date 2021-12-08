package com.example.xminutesworkout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.xminutesworkout.databinding.ActivityBmiBinding
import java.math.BigDecimal
import java.math.RoundingMode

class BMIActivity : AppCompatActivity() {
    private var binding:ActivityBmiBinding?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityBmiBinding.inflate(layoutInflater)
        setContentView(binding?.root)


        setSupportActionBar(binding?.toolbarBMIid)
        if (supportActionBar != null){
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.title="CALCULATE BMI"
        }
        binding?.toolbarBMIid?.setNavigationOnClickListener {
            onBackPressed()
        }

        binding?.btnCalculateUnits?.setOnClickListener {
            calculateBMI()
        }

    }

    private fun validateMetricUnits():Boolean{
        var isValid=true
        if(binding?.cometUnitWeight?.text.toString().isEmpty()){
            isValid=false
        }else if(binding?.cometUnitHeight?.text.toString().isEmpty()){
            isValid=false
        }
        return isValid
    }
    private fun calculateBMI(){
        val bmi:Float=0f
            if(validateMetricUnits()){
                val weightValue:Float=binding?.cometUnitWeight?.text.toString().toFloat()
                val heightValue:Float=binding?.cometUnitHeight?.text.toString().toFloat()/100
                val bmi=weightValue/(heightValue*heightValue)
                displayBMIResults(bmi)
            }else {
                Toast.makeText(
                    this@BMIActivity, "Please Enter valid values",
                    Toast.LENGTH_SHORT
                ).show()
            }

    }
    private fun displayBMIResults(bmi :Float){
        val bmiLabel:String
        val bmiDescription:String

        if(bmi.compareTo(15f)<=0){
            bmiLabel="You`re very under Weight !"
            bmiDescription="Take The Action! You need to take better of yourself!Eat more :)"
        }else if(bmi.compareTo(15f) > 0 && bmi.compareTo(16f) <= 0 ){
            bmiLabel="You`re under Weight !"
            bmiDescription="Take The Action! You need to take better of yourself!Eat more :)"
        }else if(bmi.compareTo(16f) > 0 && bmi.compareTo(18.5f) <= 0 ){
            bmiLabel="You`re little under Weight !"
            bmiDescription="Take The Action! You need to take better of yourself!Eat more :)"
        }else if(bmi.compareTo(18.5f) > 0 && bmi.compareTo(25f) <= 0 ){
            bmiLabel="Normal Weight !"
            bmiDescription="Congratulations! You are in good shape!"
        }else if(bmi.compareTo(25f) > 0 && bmi.compareTo(30f) <= 0 ){
            bmiLabel="You`re  over Weight !"
            bmiDescription="Take The Action! You need to take better of yourself. Workout !"
        }else if(bmi.compareTo(30f) > 0 && bmi.compareTo(35f) <= 0 ){
            bmiLabel="Moderately obese ."
            bmiDescription="Take The Action! You need to take better of yourself. Workout !"
        }else if(bmi.compareTo(35f) > 0 && bmi.compareTo(40f) <= 0 ){
            bmiLabel="Severely obese ."
            bmiDescription="Take The Action! You really need to take better of yourself. Workout !"
        }else {
            bmiLabel="Very severely obese ."
            bmiDescription="Take The Action! OMG its dangerous for you. Workout !"
        }
        val bmiValue=BigDecimal(bmi.toDouble()).
        setScale(2,RoundingMode.HALF_EVEN).toString()

        binding?.displayBMIResult?.visibility=View.VISIBLE
        binding?.tvBMIValue?.text=bmiValue
        binding?.tvBMIType?.text=bmiLabel
        binding?.tvBMIDescription?.text=bmiDescription



    }
}