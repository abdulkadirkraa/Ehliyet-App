package com.abdulkadirkara.ehliyethazirlik2024.view

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.abdulkadirkara.ehliyethazirlik2024.R
import com.abdulkadirkara.ehliyethazirlik2024.databinding.ActivitySonucBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore

class SonucActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySonucBinding
    private lateinit var db:FirebaseFirestore
    private lateinit var auth: FirebaseAuth

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivitySonucBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db=Firebase.firestore
        auth=FirebaseAuth.getInstance()

        val dogrusayisi=intent.getStringExtra("dogrusayisi").toString()
        val yanlissayisi=intent.getStringExtra("yanlissayisi").toString()
        val bossayisi=intent.getStringExtra("bossayisi").toString()
        val kullanilanzaman=intent.getStringExtra("kalansure").toString()

        binding.dogrusayisitv.text=dogrusayisi
        binding.yanlissayisitv.text=yanlissayisi
        binding.bossayisitv.text=bossayisi
        binding.kalansure.text="$kullanilanzaman dakika"

        if (dogrusayisi.toInt()*2>=70){
            binding.lottieanimation.resources.openRawResource(R.raw.success)
            binding.puan.text="${dogrusayisi.toInt()*2} Puan"
            binding.status.text="Tebrikler,Sınavı Geçtin"
            binding.puan.setTextColor(ContextCompat.getColor(this, R.color.dark_green))

        }else{
            binding.lottieanimation.resources.openRawResource(R.raw.fail)
            binding.puan.text="${dogrusayisi.toInt()*2} Puan"
            binding.status.text="Maalesef Sınavdan Kaldın"
            binding.puan.setTextColor(ContextCompat.getColor(this, R.color.dark_red))

        }

        binding.bitirBtn.setOnClickListener {

            val userid=auth.currentUser!!.uid
            val kullaniciBelgesiRef = db.collection("kullanicilar").document(userid)

            kullaniciBelgesiRef.update(
                "deneme1dogru", dogrusayisi,
                "deneme1yanlis", yanlissayisi,
                "deneme1bos", bossayisi,
                "deneme1sure",kullanilanzaman
            ).addOnSuccessListener {

                Toast.makeText(this@SonucActivity,"Sonuç Kaydedildi",Toast.LENGTH_SHORT).show()
                val intent=Intent(this@SonucActivity,MainActivity::class.java)
                startActivity(intent)
                finish()
                Intent.FLAG_ACTIVITY_CLEAR_TOP
            }
        }
    }
}