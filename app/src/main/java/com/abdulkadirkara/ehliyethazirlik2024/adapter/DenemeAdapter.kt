package com.abdulkadirkara.ehliyethazirlik2024.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.abdulkadirkara.ehliyethazirlik2024.databinding.DenemelerBinding
import com.abdulkadirkara.ehliyethazirlik2024.model.DenemeModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore

class DenemeAdapter(val denemelerlist:ArrayList<DenemeModel>,val listener: OnItemClickListener) : RecyclerView.Adapter<DenemeAdapter.DenemeViewHolder>(){
    inner class DenemeViewHolder(val binding:DenemelerBinding):RecyclerView.ViewHolder(binding.root),
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DenemeViewHolder {
       val binding=DenemelerBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return DenemeViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return denemelerlist.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: DenemeViewHolder, position: Int) {
        val db=Firebase.firestore
        val auth=FirebaseAuth.getInstance()

        holder.itemView.setOnLongClickListener {
            MaterialAlertDialogBuilder(holder.itemView.context.applicationContext)
                .setTitle("Kayıtlı Sonuçları Silmek")
                .setMessage("Kayıtlı deneme sonucunu silmeye emin misin?")
                .setPositiveButton("Evet"){dialog,which->
                    val kullaniciBelgesiRef = db.collection("kullanicilar").document(auth.currentUser!!.uid)
                    kullaniciBelgesiRef.update(
                        "deneme1dogru", "",
                        "deneme1yanlis", "",
                        "deneme1bos", "",
                        "deneme1sure",""
                    ).addOnSuccessListener {
                        Toast.makeText(holder.itemView.context,"Sonuçlar Sıfırlandı", Toast.LENGTH_SHORT).show()
                        holder.binding.deneme1Cv.visibility=View.VISIBLE
                        holder.binding.deneme2Cv.visibility=View.GONE
                    }
                }
                .setNegativeButton("Hayır") { dialog, which ->
                    // Respond to negative button press
                }
                .show()
            true
        }

        db.collection("kullanicilar").document(auth.currentUser!!.uid).get().addOnSuccessListener {
            if (it.exists()){
                // Kullanıcının belgesi bulundu

                // Belgeden deneme bilgilerini al
                val deneme1dogru = it.getString("deneme1dogru")
                val deneme1yanlis = it.getString("deneme1yanlis")
                val deneme1bos = it.getString("deneme1bos")
                val deneme1sure = it.getString("deneme1sure")

                if (deneme1dogru == null || deneme1yanlis == null || deneme1bos == null) {
                    holder.binding.deneme1Cv.visibility=View.VISIBLE
                    holder.binding.deneme2Cv.visibility=View.GONE
                    notifyItemChanged(position)
                }else{
                    holder.binding.deneme1Cv.visibility=View.GONE
                    holder.binding.deneme2Cv.visibility=View.VISIBLE
                    holder.binding.denemename2Tv.text=denemelerlist.get(position).title
                    holder.binding.harcanantimeTv.text="$deneme1sure dakika"
                    holder.binding.dogrusayisiTv.text=deneme1dogru.toString()
                    holder.binding.yanlissayisiTv.text=deneme1yanlis.toString()
                    holder.binding.bossayisiTv.text=deneme1bos.toString()
                    notifyItemChanged(position)

                }
            }
            notifyDataSetChanged()
        }
        holder.binding.denemenameTv.text=denemelerlist.get(position).title
        holder.binding.questioncountTv.text="Soru Sayısı: ${denemelerlist.get(position).questioncount}"
        holder.binding.timeTv.text="Süre: ${denemelerlist.get(position).time}"

    }
}