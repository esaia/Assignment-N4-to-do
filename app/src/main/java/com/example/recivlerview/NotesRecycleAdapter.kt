package com.example.recivlerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class NotesRecycleAdapter  (private val Notes: List<Notes>, private val onItemClickListener: onItemClickListener): RecyclerView.Adapter<NotesRecycleAdapter.NotesViewHolder>() {

    override fun getItemCount() = Notes.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.layout, parent, false)
        return NotesViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        val notes = Notes[position]
        holder.setData(notes)
        val showbtn = holder.itemView.findViewById<Button>(R.id.openBtn)
        val removebtn = holder.itemView.findViewById<Button>(R.id.removeItem)
        showbtn.setOnClickListener{
            onItemClickListener.onItemClick(position)
        }
        removebtn.setOnClickListener {
            onItemClickListener.onItemRemove(notes.text)
        }

    }


    inner class NotesViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        private val textView = itemView.findViewById<TextView>(R.id.textview)


        fun setData(notes: Notes){
            textView.text = notes.text
        }


    }


}