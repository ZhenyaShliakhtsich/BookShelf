package com.example.bookshelf.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.bookshelf.R
import com.example.bookshelf.databinding.ActivityMainBinding
import com.example.bookshelf.db.DatabaseRepository

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        DatabaseRepository.initDatabase(this)
        supportFragmentManager.beginTransaction()
            .replace(R.id.container_view_activity, MainFragment())
            .commit()

    }
}