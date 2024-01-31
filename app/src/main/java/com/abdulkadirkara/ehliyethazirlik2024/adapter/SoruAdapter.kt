package com.abdulkadirkara.ehliyethazirlik2024.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.abdulkadirkara.ehliyethazirlik2024.R
import com.abdulkadirkara.ehliyethazirlik2024.databinding.SoruBinding
import com.abdulkadirkara.ehliyethazirlik2024.model.SoruModel
import com.google.android.material.textfield.TextInputLayout
import com.squareup.picasso.Picasso

class SoruAdapter(private val sorulist:ArrayList<SoruModel>,var listener: OnItemClickListener) :RecyclerView.Adapter<SoruAdapter.SoruViewHolder>(){
    inner class SoruViewHolder(val binding: SoruBinding) :RecyclerView.ViewHolder(binding.root),View.OnClickListener{

        init {
            binding.root.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
            val position=adapterPosition

            if (position != RecyclerView.NO_POSITION){
                listener.onItemClickListener(position)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SoruViewHolder {
        val binding=SoruBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return SoruViewHolder(binding)
    }

    override fun getItemCount(): Int {
       return sorulist.size
    }

    override fun onBindViewHolder(holder: SoruViewHolder, position: Int) {
        holder.binding.soruTextview.text=sorulist.get(position).description
        holder.binding.opATv.text=sorulist.get(position).opA
        holder.binding.opBTv.text=sorulist.get(position).opB
        holder.binding.opCTv.text=sorulist.get(position).opC
        holder.binding.opDTv.text=sorulist.get(position).opD
        if (sorulist.get(position).imgurl.isNullOrEmpty()){
            holder.binding.soruImageview.visibility=View.GONE
        }else{
            Picasso.get().load(sorulist.get(position).imgurl).into(holder.binding.soruImageview)
            holder.binding.soruImageview.visibility=View.VISIBLE
        }

        holder.binding.opAlayout.setOnClickListener {
            if (sorulist.get(position).correctoption.equals("a")){
                holder.binding.opAlayout.setBackgroundColor(holder.binding.root.resources.getColor(R.color.dark_green))
                holder.binding.opATv.setBackgroundColor(holder.binding.root.resources.getColor(R.color.light_green))
            }else{
                holder.binding.opAlayout.setBackgroundColor(holder.binding.root.resources.getColor(R.color.dark_red))
                holder.binding.opATv.setBackgroundColor(holder.binding.root.resources.getColor(R.color.light_red))
            }
            setbtnwhite(holder,holder.binding.opBlayout,holder.binding.opBTv,
                holder.binding.opClayout,holder.binding.opCTv,
                holder.binding.opDlayout,holder.binding.opDTv)
        }
        holder.binding.opBlayout.setOnClickListener {
            if (sorulist.get(position).correctoption.equals("b")){
                holder.binding.opBlayout.setBackgroundColor(holder.binding.root.resources.getColor(R.color.dark_green))
                holder.binding.opBTv.setBackgroundColor(holder.binding.root.resources.getColor(R.color.light_green))
            }else{
                holder.binding.opBlayout.setBackgroundColor(holder.binding.root.resources.getColor(R.color.dark_red))
                holder.binding.opBTv.setBackgroundColor(holder.binding.root.resources.getColor(R.color.light_red))
            }
            setbtnwhite(holder,holder.binding.opAlayout,holder.binding.opATv,
                holder.binding.opClayout,holder.binding.opCTv,
                holder.binding.opDlayout,holder.binding.opDTv)
        }
        holder.binding.opClayout.setOnClickListener {
            if (sorulist.get(position).correctoption.equals("c")){
                holder.binding.opClayout.setBackgroundColor(holder.binding.root.resources.getColor(R.color.dark_green))
                holder.binding.opCTv.setBackgroundColor(holder.binding.root.resources.getColor(R.color.light_green))
            }else{
                holder.binding.opClayout.setBackgroundColor(holder.binding.root.resources.getColor(R.color.dark_red))
                holder.binding.opCTv.setBackgroundColor(holder.binding.root.resources.getColor(R.color.light_red))
            }
            setbtnwhite(holder,holder.binding.opAlayout,holder.binding.opATv,
                holder.binding.opBlayout,holder.binding.opBTv,
                holder.binding.opDlayout,holder.binding.opDTv)
        }
        holder.binding.opDlayout.setOnClickListener {
            if (sorulist.get(position).correctoption.equals("d")){
                holder.binding.opDlayout.setBackgroundColor(holder.binding.root.resources.getColor(R.color.dark_green))
                holder.binding.opDTv.setBackgroundColor(holder.binding.root.resources.getColor(R.color.light_green))
            }else{
                holder.binding.opDlayout.setBackgroundColor(holder.binding.root.resources.getColor(R.color.dark_red))
                holder.binding.opDTv.setBackgroundColor(holder.binding.root.resources.getColor(R.color.light_red))
            }
            setbtnwhite(holder,holder.binding.opAlayout,holder.binding.opATv,
                holder.binding.opBlayout,holder.binding.opBTv,
                holder.binding.opClayout,holder.binding.opCTv)
        }
    }

    private fun setbtnwhite(holder: SoruViewHolder,op1:TextInputLayout,op2: TextView,op3:TextInputLayout,op4: TextView,op5:TextInputLayout,op6:TextView){
        op1.setBackgroundColor(holder.binding.root.resources.getColor(R.color.white))
        op2.background=holder.binding.root.resources.getDrawable(R.drawable.op_bg)
        op3.setBackgroundColor(holder.binding.root.resources.getColor(R.color.white))
        op4.background=holder.binding.root.resources.getDrawable(R.drawable.op_bg)
        op5.setBackgroundColor(holder.binding.root.resources.getColor(R.color.white))
        op6.background=holder.binding.root.resources.getDrawable(R.drawable.op_bg)
    }
}