package com.example.cakmaflappy

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class SonucEkraniActivity : AppCompatActivity() {

    private lateinit var btnTekrar: Button
    private lateinit var tvSonskor: TextView
    private lateinit var tvRekor: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sonuc)

        btnTekrar = findViewById(R.id.btnTekrar)
        tvSonskor = findViewById(R.id.tvSonskor)
        tvRekor = findViewById(R.id.tvRekor)

        val skor = intent.getIntExtra("skor",0)
        tvSonskor.text = skor.toString()

        val sp = getSharedPreferences("Sonuc", Context.MODE_PRIVATE)
        val rekor = sp.getInt("rekor", 0)

        if(skor>rekor){
            val editor = sp.edit()
            editor.putInt("rekor",skor)
            editor.commit()
            tvRekor.text = skor.toString()
        }else{
            tvRekor.text = rekor.toString()

        }

        btnTekrar.setOnClickListener {
            startActivity(Intent(this@SonucEkraniActivity,MainActivity::class.java))
        }
    }
}