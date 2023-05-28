package com.example.cakmaflappy

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import kotlin.concurrent.schedule
import java.util.*
import kotlin.math.floor

class OyunEkrani : AppCompatActivity() {

    private lateinit var clOyunekrani: ConstraintLayout
    private lateinit var ivHero: ImageView
    private lateinit var ivOyunbiter: ImageView
    private lateinit var ivKayisi50P: ImageView
    private lateinit var ivKalp20P: ImageView
    private lateinit var tvOyunaBasla: TextView
    private lateinit var tvSkor: TextView

    //pozisyonlar
    private var heroX = 0.0f
    private var heroY = 0.0f
    private var oyunBiterY = 0.0f
    private var oyunBiterX = 0.0f
    private var kayisiX = 0.0f
    private var kayisiY = 0.0f
    private var kalpX = 0.0f
    private var kalpY = 0.0f

    //boyutlar
    private var ekranGenisligi = 0
    private var ekranYuksekligi = 0
    private var heroGenisligi = 0
    private var heroYuksekligi = 0

    //kontroller
    private var dokunmaKontrol = false
    private var baslangicKontrol = false

    private val timer = Timer()
    private var skor = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_oyun_ekrani)

        clOyunekrani = findViewById(R.id.clOyunekrani)
        ivHero = findViewById(R.id.ivHero)
        ivOyunbiter = findViewById(R.id.ivOyunbiter)
        ivKalp20P = findViewById(R.id.ivKalp20P)
        ivKayisi50P = findViewById(R.id.ivKayisi50P)
        tvOyunaBasla = findViewById(R.id.tvOyunaBasla)
        tvSkor = findViewById(R.id.tvSkor)

        ivOyunbiter.x = -800f
        ivOyunbiter.y = -800f
        ivKalp20P.x = -800f
        ivKalp20P.y = -800f
        ivKayisi50P.x = -800f
        ivKayisi50P.y = -800f

        clOyunekrani.run {
            setOnTouchListener(object : View.OnTouchListener {
                override fun onTouch(v: View?, event: MotionEvent?): Boolean {

                    if (baslangicKontrol) {
                        if (event?.action == MotionEvent.ACTION_DOWN) {
                            Log.e("motionevent", "ekrana dokundu")
                            dokunmaKontrol = true
                        }
                        if (event?.action == MotionEvent.ACTION_UP) {
                            Log.e("motionevent", "ekranı bıraktı")
                            dokunmaKontrol = false
                        }
                    } else {
                        baslangicKontrol = true

                        tvOyunaBasla.visibility = View.INVISIBLE

                        heroX = ivHero.x
                        heroY = ivHero.y

                        heroGenisligi = ivHero.width
                        heroYuksekligi = ivHero.height
                        ekranGenisligi = clOyunekrani.width
                        ekranYuksekligi = clOyunekrani.height

                        timer.schedule(0, 20) {
                            Handler(Looper.getMainLooper()).post {
                                heroHareketi()
                                cisimHareketi()
                                carpismaKontrol()
                            }
                        }

                    }

                    return true
                }
            })
        }
    }

    fun heroHareketi() {
        val heroHiz = ekranYuksekligi / 60.0f

        if (dokunmaKontrol) {
            heroY -= heroHiz
        } else {
            heroY += heroHiz
        }
        if (heroY <= 0.0f) {
            heroY = 0.0f
        }
        if (heroY >= ekranYuksekligi - heroYuksekligi) {
            heroY = (ekranYuksekligi - heroYuksekligi).toFloat()
        }
        ivHero.y = heroY
    }

    fun cisimHareketi() {
        oyunBiterX -= ekranGenisligi / 44.0f
        kayisiX -= ekranGenisligi / 36.0f
        kalpX -= ekranGenisligi / 54.0f
        if (oyunBiterX < 0.0f) {
            oyunBiterX = ekranGenisligi + 20.0f
            oyunBiterY = floor(Math.random() * ekranYuksekligi).toFloat()
        }
        ivOyunbiter.x = oyunBiterX
        ivOyunbiter.y = oyunBiterY
        if (kayisiX < 0.0f) {
            kayisiX = ekranGenisligi + 20.0f
            kayisiY = floor(Math.random() * ekranYuksekligi).toFloat()
        }
        ivKayisi50P.x = kayisiX
        ivKayisi50P.y = kayisiY
        if (kalpX < 0.0f) {
            kalpX = ekranGenisligi + 20.0f
            kalpY = floor(Math.random() * ekranYuksekligi).toFloat()
        }
        ivKalp20P.x = kalpX
        ivKalp20P.y = kalpY
    }

    fun carpismaKontrol() {
        val kalpMerkezX = kalpX + ivKalp20P.width / 2.0f
        val kalpMerkezY = kalpY + ivKalp20P.height / 2.0f

        if (0.0f <= kalpMerkezX && kalpMerkezX <= heroGenisligi && heroY <= kalpMerkezY
            && kalpMerkezY <= heroY + heroYuksekligi
        ) {
            skor += 20
            kalpX = -80.0f
        }
        val kayisiMerkezX = kayisiX + ivKayisi50P.width / 2.0f
        val kayisiMerkezY = kayisiY + ivKayisi50P.height / 2.0f

        if (0.0f <= kayisiMerkezX && kayisiMerkezX <= heroGenisligi && heroY <= kayisiMerkezY
            && kayisiMerkezY <= heroY + heroYuksekligi
        ) {
            skor += 50
            kayisiX = -80.0f
        }
        val oyunbiterMerkezX = oyunBiterX + ivOyunbiter.width / 2.0f
        val oyunbiterMerkezY = oyunBiterY + ivOyunbiter.height / 2.0f

        if (0.0f <= oyunbiterMerkezX && oyunbiterMerkezX <= heroGenisligi && heroY <= oyunbiterMerkezY
            && oyunbiterMerkezY <= heroY + heroYuksekligi
        ) {
            oyunBiterX = -80.0f
            timer.cancel()

            val intent = Intent(this@OyunEkrani,SonucEkraniActivity::class.java)
            intent.putExtra("skor",skor)
            startActivity(intent)
            finish()
        }
        tvSkor.text = skor.toString()
    }
}