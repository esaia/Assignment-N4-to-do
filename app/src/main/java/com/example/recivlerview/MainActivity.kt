package com.example.recivlerview

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class MainActivity : AppCompatActivity() , onItemClickListener  {
    private lateinit var recycleVew: RecyclerView
    private lateinit var inputText : EditText
    private lateinit var submitButton : Button
    private lateinit var clear : Button
    private lateinit var notesArray: ArrayList<Notes>
    private lateinit var sharedPreferences: SharedPreferences


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        this.init()

        sharedPreferences = getSharedPreferences("MY_SHARED_PREF", MODE_PRIVATE)
//        sharedPreferences.edit().remove("NOTE").commit()

        this.loadData()

        // Recycle Adapter
        recycleVew = findViewById(R.id.recycleView)
        recycleVew.layoutManager = LinearLayoutManager(this)
        var adapter = NotesRecycleAdapter(notesArray, this)
        recycleVew.adapter = adapter


        submitButton.setOnClickListener {
            this.addTodo()
             adapter = NotesRecycleAdapter(notesArray, this)
            recycleVew.adapter = adapter
        }


        clear.setOnClickListener {
            sharedPreferences.edit().remove("NOTE").apply()
            Toast.makeText( this,"Please Restart Application", Toast.LENGTH_SHORT).show()
        }


    }

    private fun init(){
        inputText = findViewById(R.id.inputText)
        submitButton = findViewById(R.id.submitButton)
        clear = findViewById(R.id.clear)

        notesArray = ArrayList<Notes>()

    }

    private fun loadData() {
        val gson = Gson()
        val json = sharedPreferences.getString("NOTE", null)
        val NotesArrayaType = object : TypeToken<ArrayList<Notes>>() {}.type
        if(json != null){
            val cachArray: ArrayList<Notes> = gson.fromJson(json, NotesArrayaType)
            notesArray = cachArray
        }
    }


    private fun addTodo(): List<Notes>{

        val note = inputText.text.toString()
        notesArray.add(Notes(1, note))

        val gson = Gson()
        val json = gson.toJson(notesArray)
        sharedPreferences.edit().putString("NOTE", json).apply()

        inputText.setText("")


        // Hide keyboard
        val imm: InputMethodManager =
            getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)

        return notesArray
    }

    override fun onItemClick(position: Int) {
        val intent = Intent(this, NoteDetail::class.java)
        intent.putExtra("text", notesArray[position].text )
        startActivity(intent)
    }

    override fun onItemRemove(position: String) {
        val filteredarr = notesArray.filter {
            it.text != position
        }
        notesArray = filteredarr as ArrayList<Notes>
        var adapter = NotesRecycleAdapter(notesArray, this)
        recycleVew.adapter = adapter
        val gson = Gson()
        val json = gson.toJson(notesArray)
        sharedPreferences.edit().putString("NOTE", json).apply()
    }

}
