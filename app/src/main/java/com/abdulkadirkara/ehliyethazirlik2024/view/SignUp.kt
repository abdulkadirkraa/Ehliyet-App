package com.abdulkadirkara.ehliyethazirlik2024.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.abdulkadirkara.ehliyethazirlik2024.databinding.ActivitySignUpBinding

class SignUp : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivitySignUpBinding.inflate(layoutInflater)
        val view=binding.root
        setContentView(view)

        binding.btnnext.setOnClickListener {
            signUpCheck()
        }
    }

    private fun signUpCheck() {
        if (binding.emailET.text.toString().isEmpty()){
            binding.emaillayout.error="Mailinizi Giriniz"
        }else if (binding.passwordET.text.toString().isEmpty()){
            binding.passwordlayout.error="Şifrenizi Giriniz"
        }else if (binding.confirmpasswordET.text.toString().isEmpty()){
            binding.confirmpasswordlayout.error="Şifrenizi Tekrar Giriniz"
        }else if (!(binding.passwordET.text.toString().equals(binding.confirmpasswordET.text.toString()))){
            binding.confirmpasswordlayout.error="Şifreniz aynı olmak zorunda"
        }else{
            val intent=Intent(this@SignUp, OTP::class.java)
            intent.putExtra("email",binding.emailET.text.toString())
            intent.putExtra("pass",binding.passwordET.text.toString())
            intent.putExtra("username",binding.usernameET.text.toString())
            startActivity(intent)
        }
    }
}