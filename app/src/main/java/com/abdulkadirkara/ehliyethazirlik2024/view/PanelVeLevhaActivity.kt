package com.abdulkadirkara.ehliyethazirlik2024.view

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.abdulkadirkara.ehliyethazirlik2024.adapter.PanelVeLevhaAdapter
import com.abdulkadirkara.ehliyethazirlik2024.databinding.ActivityPanelVeLevhaBinding
import com.abdulkadirkara.ehliyethazirlik2024.model.PanelVeLevhaModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class PanelVeLevhaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPanelVeLevhaBinding
    private lateinit var db: FirebaseFirestore
    private var detail:String?=""
    private lateinit var panelvelevhaArrayList:ArrayList<PanelVeLevhaModel>
    private lateinit var panelVeLevhaAdapter: PanelVeLevhaAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityPanelVeLevhaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db= Firebase.firestore
        panelvelevhaArrayList=ArrayList<PanelVeLevhaModel>()
        detail=intent.getStringExtra("detail").toString()

        if (detail.equals("panel")){
            binding.textView.text = "Araç Gösterge Paneli"
            getpanel()

        }else{
            binding.textView.text="Trafik İşaret levhaları"
            getlevha()
        }

        binding.recyclerView.layoutManager=LinearLayoutManager(this@PanelVeLevhaActivity,LinearLayoutManager.VERTICAL,false)
        panelVeLevhaAdapter= PanelVeLevhaAdapter(panelvelevhaArrayList)
        binding.recyclerView.adapter=panelVeLevhaAdapter

    }

    private fun getlevha() {
        db.collection("Ehliyetİçerik").document("Trafik İşaretleri").collection("levhalar")
            .orderBy("order")
            .addSnapshotListener { value, error ->
                if (error != null){
                    error.printStackTrace()
                }else{
                    if (value != null && !value.isEmpty){
                        val docs=value.documents
                        panelvelevhaArrayList.clear()
                        for (doc in docs){
                            val title=doc.get("title") as String
                            val description=doc.get("description") as String
                            val imgurl=doc.get("imgurl") as String
                            val order=doc.get("order") as Long

                            val levha= PanelVeLevhaModel(title,description,imgurl,order)
                            panelvelevhaArrayList.add(levha)
                        }
                        panelVeLevhaAdapter.notifyDataSetChanged()
                    }
                }
            }
    }

    private fun getpanel() {
        db.collection("Ehliyetİçerik").document("AraçGöstergePaneli").collection("paneller")
            .orderBy("order", Query.Direction.DESCENDING)
            .addSnapshotListener { value, error ->

                if (error !=null){
                    Toast.makeText(this@PanelVeLevhaActivity,error.localizedMessage, Toast.LENGTH_LONG).show()

                }else{

                    if (value != null && !value.isEmpty){
                        val docs=value.documents
                        panelvelevhaArrayList.clear()


                        for (doc in docs){
                            val title=doc.get("title") as String
                            val description=doc.get("description") as String
                            val imgurl=doc.get("imgurl") as String
                            val order=doc.get("order") as Long

                            val panel= PanelVeLevhaModel(title,description,imgurl,order)
                            panelvelevhaArrayList.add(panel)
                        }

                        panelVeLevhaAdapter.notifyDataSetChanged()
                    }
                }
            }
    }
}