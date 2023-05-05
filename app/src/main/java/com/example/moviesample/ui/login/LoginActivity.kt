package com.example.moviesample.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.moviesample.R
import com.example.moviesample.databinding.ActivityLoginBinding
import com.example.moviesample.databinding.ActivitySingleBinding
import com.example.moviesample.ui.movie_details.SingleActivity
import com.example.moviesample.ui.popular_movie.MainActivity

class LoginActivity : AppCompatActivity() {
    private lateinit var binding:ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btLogin.setOnClickListener {
            navigateToMainPage()
        }
    }
    // I did not get login api response so here dummy navigate

    private fun navigateToMainPage(){
        binding.apply {
            val intent = Intent(this@LoginActivity, MainActivity::class.java)
            startActivity(intent)
        }
    }


}