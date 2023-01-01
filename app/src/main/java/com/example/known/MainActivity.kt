package com.example.known

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class MainActivity : AppCompatActivity() {
    private lateinit var userRecyclerView: RecyclerView
    private lateinit var userlist:ArrayList<user>
    private lateinit var adapter: userAdapter
    private lateinit var mAuth: FirebaseAuth
    private lateinit var mDbref:DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mAuth = FirebaseAuth.getInstance()
        mDbref=FirebaseDatabase.getInstance().getReference()
        userlist=ArrayList()
        adapter=userAdapter(this,userlist)
        userRecyclerView = findViewById(R.id.userrecyclerview)
        userRecyclerView.layoutManager=LinearLayoutManager(this)
        userRecyclerView.adapter=adapter
        mDbref.child("user").addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                      userlist.clear()
                      for(postSnapshot in snapshot.children){
                          val currentuser=postSnapshot.getValue(user::class.java)
                          if(mAuth.currentUser?.uid!=currentuser?.uid) {
                              userlist.add(currentuser!!)
                          }
                      }
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId==R.id.logout){
            mAuth.signOut()
            val intent = Intent(this@MainActivity,Login::class.java)
            finish()
            startActivity(intent)
            return true
        }
        return true
    }
}