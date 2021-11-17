package com.example.xminutesworkout

object Constants {


    fun defaultExerciseList():ArrayList<ExerciseModel>{
        val exerciseList= ArrayList<ExerciseModel>()
        val jumpingjack= ExerciseModel(
            1,"Jumping Jacks",R.drawable.cw1,false,false
        )
        exerciseList.add(jumpingjack)
        val jumpingjack1= ExerciseModel(
            2,"2222---2222",R.drawable.cw3,false,false
        )
        exerciseList.add(jumpingjack1)
        val jumpingjack2= ExerciseModel(
            3,"333---333",0,false,false
        )
        exerciseList.add(jumpingjack2)

        return exerciseList
    }
}