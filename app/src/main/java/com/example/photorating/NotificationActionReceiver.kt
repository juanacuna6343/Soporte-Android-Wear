package com.example.photorating

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NotificationActionReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        when (intent.action) {
            "FOLLOW_ACTION" -> {
                val username = intent.getStringExtra("username") ?: return
                handleFollowAction(context, username)
            }
        }
    }

    private fun handleFollowAction(context: Context, username: String) {
        val apiService = ApiClient.create()
        apiService.toggleFollow(username).enqueue(object : Callback<FollowResponse> {
            override fun onResponse(call: Call<FollowResponse>, response: Response<FollowResponse>) {
                if (response.isSuccessful) {
                    val message = response.body()?.message ?: "Action completed"
                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "Failed to perform action", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<FollowResponse>, t: Throwable) {
                Toast.makeText(context, "Network error", Toast.LENGTH_SHORT).show()
            }
        })
    }
}