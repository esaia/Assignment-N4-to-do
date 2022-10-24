package com.example.recivlerview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast

class NoteDetail : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note_detail)

        val detailtext = findViewById<TextView>(R.id.detailText)

        val text = intent.getStringExtra("text" )
        detailtext.text = text.toString()

        Toast.makeText(this, "item text: ${text}", Toast.LENGTH_SHORT).show()


    }
}