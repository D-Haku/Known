package com.example.known

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth

class messageadapter(val context: Context,val messageList:ArrayList<Message>):
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val item_receive =1
    val item_sent=2
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
         if(viewType==1){
             val view : View = LayoutInflater.from(context).inflate(R.layout.receive,parent,false)
             return receiveviewholder(view)
         }
        else{
             val view : View = LayoutInflater.from(context).inflate(R.layout.sent,parent,false)
             return sentviewholder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val Currentmessage = messageList[position]

        if(holder.javaClass==sentviewholder::class.java){
               //sent messages
          val viewHolder=holder as sentviewholder
          holder.sentmessage.text=Currentmessage.message
      }
        else{
            //received messages
          val viewHolder=holder as receiveviewholder
          holder.receivemessage.text=Currentmessage.message
        }
    }

    override fun getItemViewType(position: Int): Int {
        val currentmessage=messageList[position]
        if(FirebaseAuth.getInstance().currentUser?.uid.equals(currentmessage.senderId)){
            return item_sent
        }
        else{
            return item_receive
        }
    }

    override fun getItemCount(): Int {
      return messageList.size
    }

    class sentviewholder(itemView: View) : RecyclerView.ViewHolder(itemView) {
         val sentmessage=itemView.findViewById<TextView>(R.id.txt_sent_message)
    }

    class receiveviewholder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val receivemessage=itemView.findViewById<TextView>(R.id.txt_receive_message)
    }
}