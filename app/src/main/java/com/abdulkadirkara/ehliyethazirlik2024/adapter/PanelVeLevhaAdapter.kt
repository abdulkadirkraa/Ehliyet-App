package com.abdulkadirkara.ehliyethazirlik2024.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.abdulkadirkara.ehliyethazirlik2024.databinding.PanelVeLevhaBinding
import com.abdulkadirkara.ehliyethazirlik2024.model.PanelVeLevhaModel
import com.squareup.picasso.Picasso

class PanelVeLevhaAdapter(private val panelvelevhalist:ArrayList<PanelVeLevhaModel>) :RecyclerView.Adapter<PanelVeLevhaAdapter.PanelvelevhaViewHolder>() {
    class PanelvelevhaViewHolder(val binding: PanelVeLevhaBinding):RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PanelvelevhaViewHolder {
        val binding=PanelVeLevhaBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return PanelvelevhaViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return panelvelevhalist.size
    }

    override fun onBindViewHolder(holder: PanelvelevhaViewHolder, position: Int) {
        holder.binding.titleTextview.text=panelvelevhalist.get(position).title
        holder.binding.descriptionTextview.text=panelvelevhalist.get(position).description
        Picasso.get().load(panelvelevhalist.get(position).imgurl).into(holder.binding.imageView)
    }
}