package com.abdulkadirkara.ehliyethazirlik2024.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.abdulkadirkara.ehliyethazirlik2024.databinding.ActivityMainBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore

class MainActivity : AppCompatActivity() {
    private lateinit var db:FirebaseFirestore
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        val view=binding.root
        setContentView(view)
        auth=FirebaseAuth.getInstance()

        if (intent.getStringExtra("username")==null){
            db=Firebase.firestore

            db.collection("kullanicilar").document(auth.currentUser!!.uid).get().addOnSuccessListener {
                if (it.exists()){
                    binding.textView.text=it.get("username").toString()
                }
            }
        }else{
            binding.textView.text=intent.getStringExtra("username").toString()
        }

        var intent:Intent

        binding.logoutImageview.setOnClickListener {
            MaterialAlertDialogBuilder(this@MainActivity)
                .setTitle("Uygulamadan Çıkış Yapmak")
                .setMessage("Uygulamadan çıkış yapmak üzeresin. Emin misin?")
                .setPositiveButton("Evet"){dialog,which->
                    auth.signOut()
                    intent=Intent(this@MainActivity,SignIn::class.java)
                    startActivity(intent)
                    finish()
                    Intent.FLAG_ACTIVITY_CLEAR_TOP
                }
                .setNegativeButton("Hayır") { dialog, which ->
                    // Respond to negative button press
                }
                .show()


        }

        binding.denemelerCv.setOnClickListener {
            intent=Intent(this@MainActivity,DenemelerActivity::class.java)
            startActivity(intent)
        }

        binding.aracgostergepaneliCv.setOnClickListener {
            intent=Intent(this@MainActivity, PanelVeLevhaActivity::class.java)
            intent.putExtra("detail","panel").toString()
            startActivity(intent)
        }

        binding.trafikisaretlevhalariCv.setOnClickListener {
            intent=Intent(this@MainActivity, PanelVeLevhaActivity::class.java)
            intent.putExtra("detail","levha").toString()
            startActivity(intent)
        }

        binding.konularCv.setOnClickListener {
            intent=Intent(this@MainActivity,KonularActivity::class.java)
            startActivity(intent)
        }

        binding.testlerCv.setOnClickListener {
            intent=Intent(this@MainActivity,TestlerActivity::class.java)
            startActivity(intent)
        }

        binding.hizsinirlariCv.setOnClickListener {
            intent=Intent(this@MainActivity,HizVeEhliyetActivity::class.java)
            intent.putExtra("detay","hiz").toString()
            startActivity(intent)
        }

        binding.ehliyetsiniflariCv.setOnClickListener {
            intent=Intent(this@MainActivity,HizVeEhliyetActivity::class.java)
            intent.putExtra("detay","ehliyet").toString()
            startActivity(intent)
        }
    }
}