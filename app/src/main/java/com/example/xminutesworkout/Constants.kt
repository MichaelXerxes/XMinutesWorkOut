package com.example.xminutesworkout

object Constants {


    fun defaultExerciseList():ArrayList<ExerciseModel>{
        val exerciseList= ArrayList<ExerciseModel>()
        val jumpingjack= ExerciseModel(
            1,"Jumping Jacks",0,false,false
        )
        exerciseList.add(jumpingjack)
        val jumpingjack1= ExerciseModel(
            2,"2222222222",0,false,false
        )
        exerciseList.add(jumpingjack1)
        val jumpingjack2= ExerciseModel(
            3,"333333",0,false,false
        )
        exerciseList.add(jumpingjack2)

        return exerciseList
    }
}