package com.example.known

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class userAdapter(val context:Context,val userList:ArrayList<user>):
    RecyclerView.Adapter<userAdapter.UserviewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserviewHolder {
        val view : View = LayoutInflater.from(context).inflate(R.layout.user_layout,parent,false)
        return UserviewHolder(view)
    }

    override fun onBindViewHolder(holder: UserviewHolder, position: Int) {
        val currentUser=userList[position]
        holder.txtName.text=currentUser.name
        holder.itemView.setOnClickListener {
            val intent=Intent(context,chatactivity::class.java)
            intent.putExtra("name",currentUser.name)
            intent.putExtra("uid",currentUser.uid)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    class UserviewHolder(itemView:View):RecyclerView.ViewHolder(itemView){
        val txtName = itemView.findViewById<TextView>(R.id.txt_name)
    }
}