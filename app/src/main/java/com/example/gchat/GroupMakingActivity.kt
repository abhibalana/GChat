package com.example.gchat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_group_making.*

class GroupMakingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_group_making)
        joinGroupBtn.setOnClickListener {
            startActivity(Intent(this,JoinGroupActivity::class.java))
        }
        CreateGroupBtn.setOnClickListener {
            startActivity(Intent(this,CreateGroupActivity::class.java))
        }
    }
}