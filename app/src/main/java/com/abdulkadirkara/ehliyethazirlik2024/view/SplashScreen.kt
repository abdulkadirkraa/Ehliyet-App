package com.abdulkadirkara.ehliyethazirlik2024.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.abdulkadirkara.ehliyethazirlik2024.R
import com.abdulkadirkara.ehliyethazirlik2024.databinding.ActivitySplashScreenBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class SplashScreen : AppCompatActivity() {

    private lateinit var binding: ActivitySplashScreenBinding
    lateinit var topanim:Animation
    lateinit var bottomanim:Animation
    private val SPLASH_SCREEN_MOVES=2500L
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivitySplashScreenBinding.inflate(layoutInflater)
        val view=binding.root
        setContentView(view)

        topanim=AnimationUtils.loadAnimation(this@SplashScreen, R.anim.top_animation)
        bottomanim=AnimationUtils.loadAnimation(this@SplashScreen, R.anim.bottom_animation)

        binding.imageView.animation = topanim
        binding.textView.animation=bottomanim

        auth= Firebase.auth
        val currentuser=auth.currentUser

        Handler(Looper.getMainLooper()).postDelayed({

            if (currentuser != null){
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }else{
                val intent=Intent(this, SignIn::class.java)
                startActivity(intent)
                finish()
            }
        }, SPLASH_SCREEN_MOVES)
    }
}