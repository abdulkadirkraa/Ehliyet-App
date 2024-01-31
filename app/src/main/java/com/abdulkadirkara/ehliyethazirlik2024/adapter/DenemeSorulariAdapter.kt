package com.abdulkadirkara.ehliyethazirlik2024.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.abdulkadirkara.ehliyethazirlik2024.R
import com.abdulkadirkara.ehliyethazirlik2024.databinding.DenemesoruBinding
import com.abdulkadirkara.ehliyethazirlik2024.model.DenemeSorulariModel
import com.squareup.picasso.Picasso

class DenemeSorulariAdapter(private val sorulist:ArrayList<DenemeSorulariModel>,var listener: OnItemClickListener)
    :RecyclerView.Adapter<DenemeSorulariAdapter.DenemeSorulariViewHolder>() {

    var dogrular=0
    var yanlislar=0


    inner class DenemeSorulariViewHolder(val binding:DenemesoruBinding) : RecyclerView.ViewHolder(binding.root),
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DenemeSorulariViewHolder {
        val binding=DenemesoruBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return DenemeSorulariViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return sorulist.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: DenemeSorulariViewHolder, position: Int) {
        holder.binding.soruTextview.text=sorulist.get(position).description
        holder.binding.category.text="Kategori: ${sorulist.get(position).category}"
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

        val cevap=sorulist[position].correctoption
        holder.binding.opAlayout.setOnClickListener {
            cevapyesil(cevap,holder)
            tiklamakapa(holder)
            if (cevap.equals("a")){
                dogrular++
            }else{
                holder.binding.opAlayout.setBackgroundColor(holder.binding.root.resources.getColor(R.color.dark_red))
                holder.binding.opATv.setBackgroundColor(holder.binding.root.resources.getColor(R.color.light_red))
                yanlislar++
            }
        }
        holder.binding.opBlayout.setOnClickListener {
            cevapyesil(cevap,holder)
            tiklamakapa(holder)
            if (cevap.equals("b")){
                dogrular++
            }else{
                holder.binding.opBlayout.setBackgroundColor(holder.binding.root.resources.getColor(R.color.dark_red))
                holder.binding.opBTv.setBackgroundColor(holder.binding.root.resources.getColor(R.color.light_red))
                yanlislar++
            }
        }
        holder.binding.opClayout.setOnClickListener {
            cevapyesil(cevap,holder)
            tiklamakapa(holder)
            if (cevap.equals("c")){
                dogrular++
            }else{
                holder.binding.opClayout.setBackgroundColor(holder.binding.root.resources.getColor(R.color.dark_red))
                holder.binding.opCTv.setBackgroundColor(holder.binding.root.resources.getColor(R.color.light_red))
                yanlislar++
            }
        }
        holder.binding.opDlayout.setOnClickListener {
            cevapyesil(cevap,holder)
            tiklamakapa(holder)
            if (cevap.equals("d")){
                dogrular++
            }else{
                holder.binding.opDlayout.setBackgroundColor(holder.binding.root.resources.getColor(R.color.dark_red))
                holder.binding.opDTv.setBackgroundColor(holder.binding.root.resources.getColor(R.color.light_red))
                yanlislar++
            }
        }
    }

    private fun tiklamakapa(holder: DenemeSorulariViewHolder) {
        holder.binding.opAlayout.isClickable=false
        holder.binding.opBlayout.isClickable=false
        holder.binding.opClayout.isClickable=false
        holder.binding.opDlayout.isClickable=false
    }

    private fun cevapyesil(cevap: String,holder: DenemeSorulariAdapter.DenemeSorulariViewHolder) {
        when(cevap){
            "a"->{
                holder.binding.opAlayout.setBackgroundColor(holder.binding.root.resources.getColor(R.color.dark_green))
                holder.binding.opATv.setBackgroundColor(holder.binding.root.resources.getColor(R.color.light_green))
            }
            "b"-> {
                holder.binding.opBlayout.setBackgroundColor(holder.binding.root.resources.getColor(R.color.dark_green))
                holder.binding.opBTv.setBackgroundColor(holder.binding.root.resources.getColor(R.color.light_green))
            }
            "c"->{
                holder.binding.opClayout.setBackgroundColor(holder.binding.root.resources.getColor(R.color.dark_green))
                holder.binding.opCTv.setBackgroundColor(holder.binding.root.resources.getColor(R.color.light_green))
            }
            else->{
                holder.binding.opDlayout.setBackgroundColor(holder.binding.root.resources.getColor(R.color.dark_green))
                holder.binding.opDTv.setBackgroundColor(holder.binding.root.resources.getColor(R.color.light_green))
            }
        }
    }
}