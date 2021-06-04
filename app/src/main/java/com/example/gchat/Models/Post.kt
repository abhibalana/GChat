package com.example.gchat.Models

import android.content.pm.LauncherApps

data class Post(
        val text:String = "",
        val createdBy:User = User(),
        val createdAt:Long = 0L,
        var gPin:String="",
        val likedBy:ArrayList<String> = ArrayList()
)