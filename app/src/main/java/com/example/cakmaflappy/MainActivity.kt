package com.example.cakmaflappy

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {

    private lateinit var btnBasla: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnBasla = findViewById(R.id.btnBasla)
        btnBasla.setOnClickListener {
            startActivity(Intent(this@MainActivity,OyunEkrani::class.java))
        }
    }
}