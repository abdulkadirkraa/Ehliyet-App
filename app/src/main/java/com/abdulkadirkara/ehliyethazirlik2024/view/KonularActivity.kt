package com.abdulkadirkara.ehliyethazirlik2024.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.abdulkadirkara.ehliyethazirlik2024.adapter.KonuAdapter
import com.abdulkadirkara.ehliyethazirlik2024.adapter.OnItemClickListener
import com.abdulkadirkara.ehliyethazirlik2024.databinding.ActivityKonularBinding
import com.abdulkadirkara.ehliyethazirlik2024.model.KonuModel
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore

class KonularActivity : AppCompatActivity(), OnItemClickListener {
    private lateinit var binding: ActivityKonularBinding
    private lateinit var db:FirebaseFirestore
    private lateinit var konulist:ArrayList<KonuModel>
    private lateinit var adapter: KonuAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityKonularBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db=Firebase.firestore
        konulist= ArrayList()

        getData()

        binding.recyclerView2.layoutManager=LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
        adapter= KonuAdapter(konulist,this)
        binding.recyclerView2.adapter=adapter
    }

    private fun getData() {
        db.collection("Ehliyetİçerik").document("Konular").collection("konus").orderBy("order")
            .addSnapshotListener { value, error ->
                if (error!=null){
                    error.printStackTrace()
                }else{
                    if (value!= null && !value.isEmpty){
                        val docs=value.documents
                        konulist.clear()

                        for (doc in docs){
                            var title=doc.get("title") as String
                            var subtitle=doc.get("subtitle") as String
                            var order=doc.get("order") as Long

                            var konu=KonuModel(title,subtitle,order)
                            konulist.add(konu)

                        }
                        adapter.notifyDataSetChanged()
                    }
                }
            }
    }

    override fun onItemClickListener(position: Int) {
        val konu=konulist[position]
        val intent= Intent(this,UniteActivity::class.java)
        intent.putExtra("konukategori",konu.title)
        startActivity(intent)
    }
}