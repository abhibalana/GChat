package com.example.gchat

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.BoringLayout
import android.widget.Toast
import com.example.gchat.Daos.groupDao
import com.example.gchat.Daos.userDao
import com.example.gchat.Models.Group
import com.example.gchat.Models.User
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_create_group.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.lang.Exception
import kotlin.properties.Delegates

class CreateGroupActivity : AppCompatActivity() {
    private lateinit var userList:ArrayList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_group)
        create_btn.setOnClickListener {
            val gDao = groupDao()
            val gid = Groupid.text.toString()
            val gName = GroupName.text.toString()


            val user = Firebase.auth.currentUser!!.uid


            if (gid.isNotEmpty() && gName.isNotEmpty()) {
                try {
                    GlobalScope.launch {
                        if (!gDao.getGroupById(gid).await().exists()) {
                            withContext(Dispatchers.Main) {
                                userList = ArrayList()
                                userList.add(user)

                                val group = Group(gid, gName, userList)
                                gDao.createGroup(group)

                                val udao = userDao()
                                udao.setId(gid)
                                changeActivity(gid)
                            }
                        }



                    }
                }
                catch (ex:Exception){
                  finish()
                    Toast.makeText(this,"Group Pin Exists",Toast.LENGTH_LONG).show()
                }
                      }




            else{
                Toast.makeText(this, "All fields are required", Toast.LENGTH_LONG).show()
            }
        }




    }



    private fun changeActivity(gid:String) {
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("gPin", gid)
        startActivity(intent)
        finish()
    }
}