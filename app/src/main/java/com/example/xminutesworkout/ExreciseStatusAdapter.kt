package com.example.xminutesworkout

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
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




        when{
            model.getIsSelected() ->{
                holder.tvItem.background=
                    ContextCompat.getDrawable(holder.itemView.context,
                    R.drawable.circular_thin_color__current)
                holder.tvItem.setTextColor(Color.parseColor("#212121"))

            }
            model.getIsCompleted() ->{

                    holder.tvItem.background=
                        ContextCompat.getDrawable(holder.itemView.context,
                            R.drawable.item_circular_accent_background1)
                    holder.tvItem.setTextColor(Color.parseColor("#212121"))

            }
            else ->{
                    holder.tvItem.background=
                        ContextCompat.getDrawable(holder.itemView.context,
                            R.drawable.circular_color_gray_background)
                    holder.tvItem.setTextColor(Color.parseColor("#212121"))
            }
        }

    }

    override fun getItemCount(): Int {
        return  exercises.size
    }
    class ViewHolder(binding: ExerciseStatusBinding)
        :RecyclerView.ViewHolder(binding.root){
            val tvItem=binding.tvItem

    }
    class ExerciseViewHolder(mergeBinding: ExerciseStatusBinding)
        :RecyclerView.ViewHolder(mergeBinding.root){
            val tvExercsieItem=mergeBinding.tvItem
        }
}