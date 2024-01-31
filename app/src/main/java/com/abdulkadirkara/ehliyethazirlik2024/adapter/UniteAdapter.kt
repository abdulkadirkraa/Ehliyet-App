package com.abdulkadirkara.ehliyethazirlik2024.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.abdulkadirkara.ehliyethazirlik2024.databinding.UniteBinding
import com.abdulkadirkara.ehliyethazirlik2024.model.UniteModel

class UniteAdapter(var unitlist:ArrayList<UniteModel>) :RecyclerView.Adapter<UniteAdapter.UniteViewHolder>(){
    inner class UniteViewHolder(val binding: UniteBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UniteViewHolder {
        val binding=UniteBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return UniteViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return unitlist.size
    }

    override fun onBindViewHolder(holder: UniteViewHolder, position: Int) {
        holder.binding.unitnameTv.text=unitlist.get(position).title
    }
}