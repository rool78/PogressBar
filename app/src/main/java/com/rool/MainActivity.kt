package com.rool

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.rool.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding) {
            progressBar.setOnProgressChangedListener {
                progressBar.labelText =  "${it}%"
            }
            progressBar1.setOnProgressChangedListener {
                progressBar1.labelText =  "${it}%"
            }
            progressBar2.setOnProgressChangedListener {
                progressBar2.labelText =  "${it}%"
            }

            progressBar.progress = 25
            progressBar1.progress = 50
            progressBar2.progress = 80

            button.setOnClickListener {
                progressBar.progress += 5
                progressBar1.progress += 20
                progressBar2.progress += 5
            }
        }
    }

}