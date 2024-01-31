package com.abdulkadirkara.ehliyethazirlik2024.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.abdulkadirkara.ehliyethazirlik2024.adapter.UniteAdapter
import com.abdulkadirkara.ehliyethazirlik2024.databinding.ActivityUniteBinding
import com.abdulkadirkara.ehliyethazirlik2024.model.UniteModel
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore

class UniteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUniteBinding
    private lateinit var db:FirebaseFirestore
    private lateinit var unitelist:ArrayList<UniteModel>
    private lateinit var adapter: UniteAdapter
    private var doc=""
    private var col=""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityUniteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db=Firebase.firestore
        unitelist= ArrayList()

        when(intent.getStringExtra("konukategori").toString()){
            "Trafik Adabı Konuları"->{
                binding.actTexview.text="Trafik Adabı Konuları"
                doc="adabkonu"
                col="adabkonular"
            }
            "Motor Tekniği Konuları"->{
                binding.actTexview.text="Motor Tekniği Konuları"
                doc="motorkonu"
                col="motorkonular"
            }
            "Trafik ve Çevre Konuları"->{
                binding.actTexview.text="Trafik ve Çevre Konuları"
                doc="trafikkonu"
                col="trafikkonuları"
            }
            else->{
                binding.actTexview.text="İlk Yardım Konuları"
                doc="yardimkonu"
                col="yardimkonular"
            }
        }

        getData(doc,col)

        binding.recyclerView2.layoutManager=LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
        adapter= UniteAdapter(unitelist)
        binding.recyclerView2.adapter=adapter

    }

    private fun getData(doc: String, col: String) {
        db.collection("Ehliyetİçerik").document("Konular").collection("konus")
            .document(doc).collection(col).orderBy("order").addSnapshotListener { value, error ->
                if (error != null){
                    error.printStackTrace()
                }else{
                    if (value != null && !value.isEmpty){
                        val docs=value.documents
                        unitelist.clear()

                        for (doc in docs){
                            var title=doc.get("title") as String
                            var order=doc.get("order") as Long

                            var unite=UniteModel(title,order)
                            unitelist.add(unite)
                        }
                        adapter.notifyDataSetChanged()
                    }
                }
            }
    }
}