package com.example.gchat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.gchat.Daos.groupDao
import com.example.gchat.Daos.userDao
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_join_group.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.lang.Exception

class JoinGroupActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

            setContentView(R.layout.activity_join_group)
        val gDao = groupDao()
        join_joinActivity.setOnClickListener {
           val  gid = join_id.text.toString()
            if(gid.isNotEmpty()) {
                try{
                GlobalScope.launch {
                    if (gDao.getGroupById(gid).await().exists()) {
                        withContext(Dispatchers.Main){
                        val uid = Firebase.auth.currentUser!!.uid
                        val udao = userDao()
                        udao.setId(gid)
                        gDao.addPersonToGroup(uid, gid)

                        changeActivity(gid)
                          }
                    }
                }


                }
                catch(Ex:Exception){
                    finish()
                    Toast.makeText(this,"Group Pin not existed",Toast.LENGTH_LONG).show()
                }
        }
            else{
                Toast.makeText(this,"Something Went Wrong",Toast.LENGTH_LONG).show()
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