package com.example.gchat

import android.app.DownloadManager
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gchat.Daos.groupDao
import com.example.gchat.Daos.postDao
import com.example.gchat.Models.Post
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.Query


import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), IPostAdapter {
    private lateinit var postDao: postDao
    private lateinit var adapter: PostAdapter
    private lateinit var id:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
       id = intent.getStringExtra("gPin").toString()
        setSupportActionBar(findViewById(R.id.toolbar_main))
        create_post_btn.setOnClickListener{
            startActivity(Intent(this,PostActivity::class.java))
        }
        meme_btn.setOnClickListener{
            startActivity(Intent(this,MemeActivity::class.java))
        }
        setUpRecyclerView()

    }

    private fun setUpRecyclerView() {
         postDao = postDao()
        val postCollection = postDao.postCollection
        val query = postCollection.whereEqualTo("gpin",id)
        val recyclerViewOptions = FirestoreRecyclerOptions.Builder<Post>().setQuery(query,Post::class.java).build()
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = PostAdapter(recyclerViewOptions,this)
        recyclerView.adapter = adapter


    }

    override fun onStart() {

        super.onStart()
        adapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        adapter.stopListening()
    }




    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater:MenuInflater = menuInflater
        inflater.inflate(R.menu.menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {

            R.id.logout -> {
                logout()
                true

            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun logout() {
        Firebase.auth.signOut()
        startActivity(Intent(this,LoginActivity::class.java))
        finish()
    }

    override fun onLikeClicked(postId: String) {
        postDao.updateLikes(postId)
    }
}