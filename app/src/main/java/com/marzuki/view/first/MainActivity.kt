package com.marzuki.view.first

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.marzuki.suitmedia.R
import com.marzuki.view.second.SecondActivity

class MainActivity : AppCompatActivity() {
    private lateinit var nameInput: EditText
    private lateinit var sentenceInput: EditText
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        nameInput = findViewById(R.id.nameInput)
        sentenceInput = findViewById(R.id.sentenceInput)
        sharedPreferences = getSharedPreferences("MyPreferences", Context.MODE_PRIVATE)

        val checkButton: Button = findViewById(R.id.checkButton)
        checkButton.setOnClickListener {
            checkPalindrome()
        }

        val nextButton: Button = findViewById(R.id.nextButton)
        nextButton.setOnClickListener {
            val intent = Intent(this, SecondActivity::class.java)
            val nameToSave = nameInput.text.toString()
            saveNameToSharedPreferences(nameToSave)
            startActivity(intent)
        }
    }

    private fun checkPalindrome() {
        val name = nameInput.text.toString()
        val sentence = sentenceInput.text.toString()

        val isPalindrome = isPalindrome(sentence)

        val message = if (isPalindrome) "isPalindrome" else "not palindrome"
        AlertDialog.Builder(this)
            .setMessage(message)
            .show()
    }

    private fun isPalindrome(s: String): Boolean {
        val cleanString = s.replace("\\s+".toRegex(), "")
        val reversedString = cleanString.reversed()
        return cleanString.equals(reversedString, ignoreCase = true)
    }

    private fun saveNameToSharedPreferences(name: String) {
        val editor = sharedPreferences.edit()
        editor.putString("name", name)
        editor.apply()
    }
}
