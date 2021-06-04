package com.example.gchat.Daos

import com.example.gchat.Models.Post
import com.example.gchat.Models.User
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore


import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
class  postDao {

    val db = FirebaseFirestore.getInstance()
    val postCollection = db.collection("posts")

     fun addPost(text: String, gPin: String) {
        val userId = Firebase.auth.currentUser!!.uid
        GlobalScope.launch {
            val uDao = userDao()
            val user = uDao.getUserById(userId).await().toObject(User::class.java)!!
            val time = System.currentTimeMillis()
            val post = Post(text, user, time, gPin)
            postCollection.document().set(post)

        }
    }
    fun getPostById(postId: String) : Task<DocumentSnapshot> {
        return postCollection.document(postId).get()
    }

    fun updateLikes(postId:String) {
        val currentUser = Firebase.auth.currentUser!!.uid
        GlobalScope.launch {
            val post = getPostById(postId).await().toObject(Post::class.java)!!
            if(post.likedBy.contains(currentUser)){
                post.likedBy.remove(currentUser)
            }
            else{
                post.likedBy.add(currentUser)
            }
            postCollection.document(postId).set(post)
        }
    }
}