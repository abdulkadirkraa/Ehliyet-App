package com.abdulkadirkara.ehliyethazirlik2024.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.abdulkadirkara.ehliyethazirlik2024.databinding.ActivitySignInBinding
import com.google.firebase.auth.FirebaseAuth

class SignIn : AppCompatActivity() {
    private lateinit var binding: ActivitySignInBinding
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivitySignInBinding.inflate(layoutInflater)
        val view=binding.root
        setContentView(view)

        auth= FirebaseAuth.getInstance()

        binding.signupTv.setOnClickListener {
            val intent= Intent(this@SignIn, SignUp::class.java)
            startActivity(intent)
        }
        binding.btnLogin.setOnClickListener {
            login()
        }
    }

    private fun login() {
        if (binding.emailET.text.toString().isEmpty()){
            binding.emaillayout.error="Mailinizi Giriniz"
        }else if (binding.passwordET.text.toString().isEmpty()){
            binding.passwordlayout.error="Şifrenizi Giriniz"
        }else{
            auth.signInWithEmailAndPassword(binding.emailET.text.toString(),binding.passwordET.text.toString()).addOnCompleteListener {
                if (it.isSuccessful){
                    val intent=Intent(this@SignIn, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
        }
    }
}