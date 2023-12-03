package com.example.sampleroulette

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {

    private lateinit var roulette: Roulette

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        roulette = findViewById(R.id.roulette)

        val items = listOf(
            RouletteItem("옵션1", 1),
            RouletteItem("옵션2", 2),
            RouletteItem("옵션3", 3),
            RouletteItem("옵션4", 4),
        )
        roulette.items = items

        roulette.rotateToOption("옵션1")
    }
}