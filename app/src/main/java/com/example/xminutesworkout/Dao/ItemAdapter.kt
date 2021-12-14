package com.example.xminutesworkout.Dao


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.xminutesworkout.R
import com.example.xminutesworkout.databinding.RowLayoutBinding


class ItemAdapter(private var items:ArrayList<String>,
                 // private var deleteListener:(id:Int)->Unit,
)
    :RecyclerView.Adapter<ItemAdapter.ViewHolder>() {



    class ViewHolder(binding: RowLayoutBinding):RecyclerView.ViewHolder(binding.root){
        val llforRV=binding.llforRecyclerview
        val tvID=binding.tvID
        val date=binding.tvDate
        val delete=binding.ivDelete
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(RowLayoutBinding.inflate(LayoutInflater.from(parent.context),parent
            ,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val date:String=items.get(position)

        holder.tvID.text= (position+1).toString()
        holder.date.text=date

        if(position%2==0){
            holder.llforRV.setBackgroundColor(ContextCompat.getColor(holder.itemView.context,
                R.color.teal_200))
        }else{
            holder.llforRV.setBackgroundColor(ContextCompat.getColor(holder.itemView.context,
                R.color.white))
        }



        holder.delete.setOnClickListener {
           //deleteListener.invoke(item.id)
        }
    }

    override fun getItemCount(): Int {
        return  items.size
    }
}