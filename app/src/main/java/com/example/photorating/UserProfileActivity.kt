package com.example.photorating

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView

class UserProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_profile)

        val username = intent.getStringExtra("username") ?: return

        // Here you would load the user's profile and recent photos
        // using your API service
        setupProfile(username)
        loadRecentPhotos(username)
    }

    private fun setupProfile(username: String) {
        val usernameTv = findViewById<TextView>(R.id.username)
        usernameTv.text = username
    }

    private fun loadRecentPhotos(username: String) {
        val recyclerView = findViewById<RecyclerView>(R.id.photosRecyclerView)
        // Implement loading recent photos using your API service
    }
}