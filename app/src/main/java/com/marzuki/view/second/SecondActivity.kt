package com.marzuki.view.second

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.marzuki.suitmedia.R
import com.marzuki.view.third.ThirdActivity

class SecondActivity : AppCompatActivity() {

    private lateinit var userNameLabel: TextView
    private lateinit var selectedUserLabel: TextView
    private lateinit var chooseUserButton: Button
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        userNameLabel = findViewById(R.id.userNameLabel)
        selectedUserLabel = findViewById(R.id.selectedUser)
        chooseUserButton = findViewById(R.id.chooseUserButton)
        sharedPreferences = getSharedPreferences("MyPreferences", Context.MODE_PRIVATE)

        val welcomeText: TextView = findViewById(R.id.welcomeText)
        welcomeText.text = "Welcome"
        val name = sharedPreferences.getString("name", "Guest")
        userNameLabel.text = "$name"

        val selected = intent.getStringExtra("selected_user_name")
        if (selected==null) {
            selectedUserLabel.text = "Selected Username"
        } else {
            selectedUserLabel.text = "$selected"
        }


        val chooseUserButton: Button = findViewById(R.id.chooseUserButton)
        chooseUserButton.setOnClickListener {
            val intent = Intent(this, ThirdActivity::class.java)
            startActivity(intent)
        }
    }
}