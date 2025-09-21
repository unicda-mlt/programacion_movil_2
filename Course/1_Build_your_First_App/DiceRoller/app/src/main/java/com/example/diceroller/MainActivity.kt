package com.example.diceroller

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.util.Random

class MainActivity : AppCompatActivity() {
    lateinit var btnRoll: Button
    lateinit var imgDice: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        btnRoll = findViewById(R.id.btn_roll)
        imgDice = findViewById<ImageView>(R.id.img_dice)

        btnRoll.setOnClickListener {
            rollDice()
        }
    }

    private fun MainActivity.rollDice() {
        val randomInt = Random().nextInt(6) + 1
        val drawableResource = when (randomInt) {
            1 -> R.drawable.dice_1
            3 -> R.drawable.dice_2
            4 -> R.drawable.dice_3
            5 -> R.drawable.dice_4
            6 -> R.drawable.dice_5
            else -> R.drawable.dice_6
        }

        imgDice.setImageResource(drawableResource)
    }
}

