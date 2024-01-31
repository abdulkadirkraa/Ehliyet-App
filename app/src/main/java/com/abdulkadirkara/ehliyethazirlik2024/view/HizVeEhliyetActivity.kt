package com.abdulkadirkara.ehliyethazirlik2024.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.abdulkadirkara.ehliyethazirlik2024.databinding.ActivityHizVeEhliyetBinding
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import com.squareup.picasso.Picasso

class HizVeEhliyetActivity : AppCompatActivity(){
    private lateinit var binding: ActivityHizVeEhliyetBinding
    private lateinit var db: FirebaseFirestore
    private var detay:String?=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityHizVeEhliyetBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db= Firebase.firestore
        detay=intent.getStringExtra("detay").toString()

        if (detay.equals("hiz")){
            getHiz()
        }else{
            getEhliyet()
        }
    }

    private fun getEhliyet() {
        db.collection("Ehliyetİçerik").document("Ehliyet Sınıfları").collection("ehliyet")
            .document("siniflari").get().addOnSuccessListener {document->
                if (document.exists()){
                    val imgurl=document.getString("imgurl")
                    Picasso.get().load(imgurl).into(binding.image)
                }
            }
    }

    private fun getHiz() {
        db.collection("Ehliyetİçerik").document("Hız Sınırları").collection("hiz")
            .document("siniri").get().addOnSuccessListener {document->
                if (document.exists()){
                    val imgurl=document.getString("imgurl")
                    Picasso.get().load(imgurl).into(binding.image)
                }
            }
    }
}