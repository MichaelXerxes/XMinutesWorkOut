package com.example.xminutesworkout

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.xminutesworkout.databinding.ExerciseStatusBinding

class ExerciseStatusAdapter(var exercises:ArrayList<ExerciseModel>):
    RecyclerView.Adapter<ExerciseStatusAdapter.ViewHolder>() {









    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ExerciseStatusBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model: ExerciseModel=exercises[position]
        holder.tvItem.text=model.getId().toString()
    }

    override fun getItemCount(): Int {
        return  exercises.size
    }
    class ViewHolder(binding: ExerciseStatusBinding)
        :RecyclerView.ViewHolder(binding.root){
            val tvItem=binding.tvItem

    }
}