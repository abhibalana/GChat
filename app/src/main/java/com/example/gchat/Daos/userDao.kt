package com.example.gchat.Daos

import com.example.gchat.Models.User
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class userDao {
    private val db = FirebaseFirestore.getInstance()
    private val userCollection = db.collection("users")
    fun addUser( user: User?){
        user?.let {
            GlobalScope.launch(Dispatchers.IO) {
                userCollection.document(user.Uid).set(it)
            }
        }

    }

    fun getUserById(uId:String):Task<DocumentSnapshot>{
        return userCollection.document(uId).get()
    }

    fun setId(gPin:String){
        val userId  = Firebase.auth.currentUser!!
        GlobalScope.launch {
            val user = getUserById(userId.uid).await().toObject(User::class.java)!!
            user.gPin = gPin

            userCollection.document(userId.uid).set(user)

        }
    }



}