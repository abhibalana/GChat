package com.example.gchat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.gchat.Daos.groupDao
import com.example.gchat.Daos.postDao
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_post.*

class PostActivity : AppCompatActivity() {
    private lateinit var gPin:String
    private lateinit var postDao: postDao
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post)
        postDao= postDao()
        val uid = Firebase.auth.currentUser!!.uid
        val documentReference = FirebaseFirestore.getInstance().collection("users").document(uid)
        documentReference.get().addOnSuccessListener {
          gPin = it.getString("gpin")!!
        }.addOnFailureListener {
            Toast.makeText(this,"Something went wrong",Toast.LENGTH_LONG).show()
        }
      post_btn.setOnClickListener {
          val text = postInput.text.toString()
          if(text.isNotEmpty()){
              postDao.addPost(text,gPin)
              finish()
          }
          else{
              Toast.makeText(this,"Something went wrong",Toast.LENGTH_LONG).show()
          }
      }


    }
}