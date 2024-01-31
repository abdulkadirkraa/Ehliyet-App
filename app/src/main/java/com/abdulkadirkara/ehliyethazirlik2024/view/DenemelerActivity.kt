package com.abdulkadirkara.ehliyethazirlik2024.view

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.abdulkadirkara.ehliyethazirlik2024.R
import com.abdulkadirkara.ehliyethazirlik2024.adapter.DenemeAdapter
import com.abdulkadirkara.ehliyethazirlik2024.adapter.OnItemClickListener
import com.abdulkadirkara.ehliyethazirlik2024.databinding.ActivityDenemelerBinding
import com.abdulkadirkara.ehliyethazirlik2024.model.DenemeModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore

class DenemelerActivity : AppCompatActivity(), OnItemClickListener {

    private lateinit var binding:ActivityDenemelerBinding
    private lateinit var db:FirebaseFirestore
    private lateinit var denemelerlist:ArrayList<DenemeModel>
    private lateinit var adapter: DenemeAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityDenemelerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        denemelerlist= ArrayList()
        db=Firebase.firestore
        getData()

        binding.recyclerView2.layoutManager=LinearLayoutManager(this@DenemelerActivity,LinearLayoutManager.VERTICAL,false)
        adapter= DenemeAdapter(denemelerlist,this)
        binding.recyclerView2.adapter=adapter
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun getData() {
        db.collection("Ehliyetİçerik").document("Denemeler").collection("denemes")
            .addSnapshotListener { value, error ->
                if (error != null){
                    error.printStackTrace()
                }else{
                    if (value != null && !value.isEmpty){
                        val docs=value.documents
                        denemelerlist.clear()
                        for (doc in docs){
                            var title=doc.get("title") as String
                            var questions=doc.get("questioncount") as String
                            var time=doc.get("time") as String

                            val deneme=DenemeModel(title,questions,time)
                            denemelerlist.add(deneme)
                        }
                        adapter.notifyDataSetChanged()
                    }
                }
            }
    }

    override fun onItemClickListener(position: Int) {
        val deneme= denemelerlist[position]
        if (deneme.title.equals("Deneme 1")){
            val intent=Intent(this@DenemelerActivity,DenemeSorulariActivity::class.java)
            startActivity(intent)
            finish()
            Intent.FLAG_ACTIVITY_CLEAR_TOP
        }
    }
}