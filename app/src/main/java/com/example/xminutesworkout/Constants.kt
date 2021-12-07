package com.example.xminutesworkout

object Constants {


    fun defaultExerciseList():ArrayList<ExerciseModel>{
        val exerciseList= ArrayList<ExerciseModel>()
        val jumpingjack1= ExerciseModel(
            1,"Jumping Jacks",R.drawable.cw1,false,false
        )
        exerciseList.add(jumpingjack1)
        val jumpingjack2= ExerciseModel(
            2,"2222---2222",R.drawable.cw3,false,false
        )
        exerciseList.add(jumpingjack2)
        val jumpingjack3= ExerciseModel(
            3,"333---333",R.drawable.cw1,false,false
        )
        exerciseList.add(jumpingjack3)
        val jumpingjack4= ExerciseModel(
            4,"444  444",R.drawable.cw3,false,false
        )
        exerciseList.add(jumpingjack4)
        val jumpingjack5= ExerciseModel(
            5,"555  555",R.drawable.cw1,false,false
        )
        exerciseList.add(jumpingjack5)

        return exerciseList
    }
}