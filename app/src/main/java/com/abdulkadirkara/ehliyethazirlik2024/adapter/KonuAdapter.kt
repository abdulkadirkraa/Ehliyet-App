package com.abdulkadirkara.ehliyethazirlik2024.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.abdulkadirkara.ehliyethazirlik2024.databinding.KonularBinding
import com.abdulkadirkara.ehliyethazirlik2024.model.KonuModel

class KonuAdapter(private val konulist:ArrayList<KonuModel>, var listener: OnItemClickListener):
    RecyclerView.Adapter<KonuAdapter.KonuViewHolder>() {
    inner class KonuViewHolder(val binding: KonularBinding): RecyclerView.ViewHolder(binding.root),
        View.OnClickListener{

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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KonuViewHolder {
        val binding=KonularBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return KonuViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return konulist.size
    }

    override fun onBindViewHolder(holder: KonuViewHolder, position: Int) {
        holder.binding.testnameTv.text=konulist.get(position).title
        holder.binding.testsubtitleTv.text=konulist.get(position).subtitle
    }
}