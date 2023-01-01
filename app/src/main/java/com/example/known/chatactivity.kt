package com.example.known

import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class chatactivity : AppCompatActivity() {
    private lateinit var chatrecyclerview:RecyclerView
    private lateinit var messagebox:EditText
    private lateinit var sendbutton:ImageView
    private lateinit var messageadapter: messageadapter
    private lateinit var messageList: ArrayList<Message>
    private lateinit var mDbref:DatabaseReference
    var receiverRoom:String?=null
    var senderRoom:String?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chatactivity)
        val name=intent.getStringExtra("name")
        val receiverUid=intent.getStringExtra("uid")
        val senderUid=FirebaseAuth.getInstance().currentUser?.uid
        mDbref=FirebaseDatabase.getInstance().getReference()
        senderRoom=receiverUid+senderUid
        receiverRoom=senderUid+receiverUid
        supportActionBar?.title=name
        chatrecyclerview=findViewById(R.id.chat_recycler)
        messagebox=findViewById(R.id.message_box)
        sendbutton=findViewById(R.id.send_button)
        messageList=ArrayList()
        messageadapter=messageadapter(this,messageList)
        chatrecyclerview.layoutManager=LinearLayoutManager(this)
        chatrecyclerview.adapter=messageadapter
        mDbref.child("chats").child(senderRoom!!).child("messages")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    messageList.clear()
                    for (postSnapshot in snapshot.children){
                        val message=postSnapshot.getValue(Message::class.java)
                        messageList.add(message!!)
                    }
                    messageadapter.notifyDataSetChanged()
                }
                override fun onCancelled(error: DatabaseError) {

                }
            })
        sendbutton.setOnClickListener{
           val message=messagebox.text.toString()
           val messageObject=Message(message,senderUid)
            mDbref.child("chats").child(senderRoom!!).child("messages").push()
                .setValue(messageObject).addOnSuccessListener {
                    mDbref.child("chats").child(receiverRoom!!).child("messages").push()
                        .setValue(messageObject)
                }
            messagebox.setText("")
        }
    }
}