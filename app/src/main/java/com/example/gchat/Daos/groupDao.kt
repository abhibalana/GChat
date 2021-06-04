package com.example.gchat.Daos

import android.content.Context
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import com.example.gchat.Models.Group
import com.example.gchat.Models.Post
import com.example.gchat.Models.User
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.*
import com.google.firebase.ktx.Firebase
import com.google.firebase.platforminfo.GlobalLibraryVersionRegistrar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class groupDao {

    val gdb = FirebaseFirestore.getInstance()
    val groupCollections = gdb.collection("groups")
    fun createGroup(group: Group?){
        group?.let {
            GlobalScope.launch(Dispatchers.IO) {
                groupCollections.document(group.gPin).set(it)
            }
        }
    }

    fun addPersonToGroup(uid: String, gid: String) {
        GlobalScope.launch {
                val group = getGroupById(gid).await().toObject(Group::class.java)!!
                    if (group.users.contains(uid)) {
                        Log.d("ERROR", "something went wrong")
                    } else {
                        group.users.add(uid)
                        groupCollections.document(gid).set(group)
                    }
            }
    }

    fun getGroupById(gid: String): Task<DocumentSnapshot> {
        return groupCollections.document(gid).get()
    }




}






