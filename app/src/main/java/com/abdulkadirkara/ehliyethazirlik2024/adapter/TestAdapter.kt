package com.abdulkadirkara.ehliyethazirlik2024.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.abdulkadirkara.ehliyethazirlik2024.databinding.TestlerBinding
import com.abdulkadirkara.ehliyethazirlik2024.model.TestModel

class TestAdapter(private val testlist:ArrayList<TestModel>,var listener: OnItemClickListener):RecyclerView.Adapter<TestAdapter.TestViewHolder>() {
    inner class TestViewHolder(val binding:TestlerBinding):RecyclerView.ViewHolder(binding.root),View.OnClickListener{

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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TestViewHolder {
        val binding= TestlerBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return TestViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return testlist.size
    }

    override fun onBindViewHolder(holder: TestViewHolder, position: Int) {
        holder.binding.testnameTv.text=testlist.get(position).title
        holder.binding.testsubtitleTv.text=testlist.get(position).subtitle
    }
}