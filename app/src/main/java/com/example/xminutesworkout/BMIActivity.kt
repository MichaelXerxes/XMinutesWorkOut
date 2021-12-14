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

    private var currentUnitView:String= METRIC_UNITS
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

        makeVisibleMetricView()//default setup

        //for radio groups
        binding?.radioGUnits?.setOnCheckedChangeListener { _, checkedId: Int ->
            if (checkedId==R.id.rbMetricUntis){
                makeVisibleMetricView()
            }else{
                makeVisibleUSAView()
            }
        }

        binding?.btnCalculateUnits?.setOnClickListener {
            calculateUnits()
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
    private fun validateUSAcUnits():Boolean{
        var isValid=true
        when {
            binding?.cometUSAUnitWeight?.text.toString().isEmpty() -> {
                isValid = false
            }
            binding?.cometUSAunitHeightFeet?.text.toString().isEmpty() -> {
                isValid = false
            }
            binding?.cometUSAunitHeightInch?.text.toString().isEmpty() -> {
                isValid = false
            }
        }
        return isValid
    }
    private fun calculateMetricBMI(){
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
    private fun calculateUSABMI(){
        if(validateUSAcUnits()){
            val usaUnitsWeight:Float=binding?.cometUSAUnitWeight?.text.toString().toFloat()
            val usaUnitsHeightFeet:String=binding?.cometUSAunitHeightFeet?.text.toString()
            val usaUnitsHeightInch:String=binding?.cometUSAunitHeightInch?.text.toString()

            val heightValue=usaUnitsHeightInch.toFloat()+usaUnitsHeightFeet.toFloat()*12

            val bmi=703*(usaUnitsWeight/(heightValue*heightValue))

            displayBMIResults(bmi)
        }else {
            Toast.makeText(
                this@BMIActivity, "Please Enter valid values",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
    private fun calculateUnits(){
        if (currentUnitView== METRIC_UNITS){
            calculateMetricBMI()
        }else{
            calculateUSABMI()
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
    private fun makeVisibleMetricView(){
        currentUnitView= METRIC_UNITS
        binding?.metrciUnitIdWeight?.visibility=View.VISIBLE
        binding?.metrciUnitIdHeight?.visibility=View.VISIBLE
        binding?.metricUSAUnitIdWeight?.visibility=View.INVISIBLE
        binding?.metricUSAUnitsHeightFeet?.visibility=View.INVISIBLE
        binding?.metricUSAUnitsHeightInch?.visibility=View.INVISIBLE

        binding?.cometUnitWeight?.text!!.clear()
        binding?.cometUnitHeight?.text!!.clear()

        binding?.displayBMIResult?.visibility=View.INVISIBLE

    }
    private fun makeVisibleUSAView(){
        currentUnitView= USA_UNITS
        binding?.metrciUnitIdWeight?.visibility=View.INVISIBLE
        binding?.metrciUnitIdHeight?.visibility=View.INVISIBLE
        binding?.metricUSAUnitIdWeight?.visibility=View.VISIBLE
        binding?.metricUSAUnitsHeightFeet?.visibility=View.VISIBLE
        binding?.metricUSAUnitsHeightInch?.visibility=View.VISIBLE

        binding?.cometUSAUnitWeight?.text!!.clear()
        binding?.cometUSAunitHeightFeet?.text!!.clear()
        binding?.cometUSAunitHeightInch?.text!!.clear()

        binding?.displayBMIResult?.visibility=View.INVISIBLE

    }
    companion object{
        private const val METRIC_UNITS="METRIC_UNITS_VIEW"
        private const val USA_UNITS="USA_UNITS_VIEW"
    }
}