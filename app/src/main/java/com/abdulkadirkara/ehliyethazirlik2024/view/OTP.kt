package com.abdulkadirkara.ehliyethazirlik2024.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import com.abdulkadirkara.ehliyethazirlik2024.databinding.ActivityOtpBinding
import com.abdulkadirkara.ehliyethazirlik2024.model.User
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import papaya.`in`.sendmail.SendMail

class OTP : AppCompatActivity() {

    private lateinit var binding: ActivityOtpBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore
    var email: String=""
    var pass: String=""
    var username: String=""
    var random:Int=0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityOtpBinding.inflate(layoutInflater)
        val view=binding.root
        setContentView(view)

        random()

        binding.showEmail.setText(email)

        binding.tvreset.setOnClickListener {
            random()
        }

        email=intent.getStringExtra("email").toString()
        pass=intent.getStringExtra("pass").toString()
        username=intent.getStringExtra("username").toString()
        auth=FirebaseAuth.getInstance()
        db= Firebase.firestore

        otpcheck()

    }

    private fun otpcheck() {
        binding.otp1.doOnTextChanged { text, start, before, count ->
            if (!binding.otp1.text.toString().isEmpty()){
                binding.otp2.requestFocus()
            }
            if (!binding.otp2.text.toString().isEmpty()){
                binding.otp2.requestFocus()
            }
        }
        binding.otp2.doOnTextChanged { text, start, before, count ->
            if (!binding.otp2.text.toString().isEmpty()){
                binding.otp3.requestFocus()
            }else{
                binding.otp1.requestFocus()
            }
        }
        binding.otp3.doOnTextChanged { text, start, before, count ->
            if (!binding.otp3.text.toString().isEmpty()){
                binding.otp4.requestFocus()
            }else{
                binding.otp2.requestFocus()
            }
        }
        binding.otp4.doOnTextChanged { text, start, before, count ->
            if (!binding.otp4.text.toString().isEmpty()){
                binding.otp5.requestFocus()
            }else{
                binding.otp3.requestFocus()
            }
        }
        binding.otp5.doOnTextChanged { text, start, before, count ->
            if (!binding.otp5.text.toString().isEmpty()){
                binding.otp6.requestFocus()
            }else{
                binding.otp4.requestFocus()
            }
        }
        binding.otp6.doOnTextChanged { text, start, before, count ->
            if (binding.otp6.text.toString().isEmpty()){
                binding.otp5.requestFocus()
            }

            binding.btndone.setOnClickListener {
                var otp1=binding.otp1.text.toString()
                var otp2=binding.otp2.text.toString()
                var otp3=binding.otp3.text.toString()
                var otp4=binding.otp4.text.toString()
                var otp5=binding.otp5.text.toString()
                var otp6=binding.otp6.text.toString()

                var otp="$otp1$otp2$otp3$otp4$otp5$otp6"

                if (binding.otp1.text.toString().isEmpty() ||
                    binding.otp2.text.toString().isEmpty() ||
                    binding.otp3.text.toString().isEmpty() ||
                    binding.otp4.text.toString().isEmpty() ||
                    binding.otp5.text.toString().isEmpty() ||
                    binding.otp6.text.toString().isEmpty()){

                    Toast.makeText(this@OTP,"Doğrulama Kodu Girin",Toast.LENGTH_SHORT).show()
                } else if (!otp.equals(random.toString())){
                    Toast.makeText(this@OTP,"Yanlış Doğrulama Kodu",Toast.LENGTH_SHORT).show()
                }else{
                    createUser()
                }
            }

        }
    }

    private fun createUser() {
        auth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener {
            if (it.isSuccessful){

                val user=auth.currentUser
                user?.let {
                    val uid=user.uid

                    val newUser=User(user.email.toString(),username)

                    db.collection("kullanicilar").document(uid).set(newUser).addOnSuccessListener {
                        val intent=Intent(this@OTP, MainActivity::class.java)
                        intent.putExtra("username",username)
                        startActivity(intent)
                        finish()
                    }.addOnFailureListener {
                        Toast.makeText(this@OTP,it.localizedMessage,Toast.LENGTH_SHORT).show()
                    }
                }
            }else{
                Toast.makeText(this@OTP, it.exception?.message.toString(),Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun random(){
        random= (100000..999999).random()
        var mail=SendMail("ehliyethazirlik2024@gmail.com","puzh csaq nffb jgmd",email,"Ehliyet " +
                "Hazırlık 2024 Uygulaması Email Doğrulama","Merhaba $username \nUygulamamıza kayıt olurken email adresinizi " +
                "doğrulamak için $random kodunu kullanabilirisiniz. \n\n\n Saygılarımızla,\n Ehliyet Hazırlık 2024 Ekibi")
        mail.execute()
    }
}