package com.example.xminutesworkout

object ConstantSecondSet {
    fun defaultExerciseList():ArrayList<ExerciseModel>{
        val exerciseList= ArrayList<ExerciseModel>()
        val jumpingjack1= ExerciseModel(
            1,"111",R.drawable.cw1,false,false
        )
        exerciseList.add(jumpingjack1)
        val jumpingjack2= ExerciseModel(
            2,"2222",R.drawable.cw3,false,false
        )
        exerciseList.add(jumpingjack2)
        val jumpingjack3= ExerciseModel(
            3,"333",R.drawable.cw1,false,false
        )
        exerciseList.add(jumpingjack3)
        val jumpingjack4= ExerciseModel(
            4,"444",R.drawable.cw3,false,false
        )
        exerciseList.add(jumpingjack4)
        val jumpingjack5= ExerciseModel(
            5,"555",R.drawable.cw1,false,false
        )
        exerciseList.add(jumpingjack5)

        return exerciseList
    }
    fun forFinishActivityRView():ArrayList<ExerciseModel>{
        val exerciseList= ArrayList<ExerciseModel>()

        for(i in 1..5){
            var exerciseModel: ExerciseModel?=null
            exerciseModel=ExerciseModel(i,"",0,true,false)
            exerciseList.add(exerciseModel)

        }
        return exerciseList
    }
}