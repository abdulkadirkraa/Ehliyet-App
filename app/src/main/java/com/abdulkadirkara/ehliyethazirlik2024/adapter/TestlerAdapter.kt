package com.abdulkadirkara.ehliyethazirlik2024.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.abdulkadirkara.ehliyethazirlik2024.databinding.TestBinding
import com.abdulkadirkara.ehliyethazirlik2024.databinding.TestlerBinding
import com.abdulkadirkara.ehliyethazirlik2024.model.TestlerModel

class TestlerAdapter(val testlerlist:ArrayList<TestlerModel>,val listener: OnItemClickListener):RecyclerView.Adapter<TestlerAdapter.TestlerViewHolder>() {
    inner class TestlerViewHolder(val binding:TestBinding):RecyclerView.ViewHolder(binding.root),View.OnClickListener{

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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TestlerViewHolder {
        val binding= TestBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return TestlerViewHolder(binding)
    }

    override fun getItemCount(): Int {
       return testlerlist.size
    }

    override fun onBindViewHolder(holder: TestlerViewHolder, position: Int) {
        holder.binding.testTv.text=testlerlist.get(position).title
    }
}