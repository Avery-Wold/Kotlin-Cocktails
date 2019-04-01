package com.example.averyw.cocktailmaker

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

const val SPIRIT_MESSAGE = "SPIRIT_MESSAGE"
const val DRINK_MESSAGE = "DRINK_MESSAGE"

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

//    override fun onResume() {
//        super.onResume()
//        val error = intent.getStringExtra(ERROR_MESSAGE)
//
//        if(error != null){
//            Toast.makeText(this, "Please input valid drink name", Toast.LENGTH_SHORT)
//        }
//    }

    fun getDrink(view: View){
        val editText = findViewById<EditText>(R.id.editText_drinkname)
        var drink = editText.text.toString()
        drink = drink.replace("\\s".toRegex(), "+")
        val intent = Intent(this, DetailActivity::class.java).apply {
            putExtra(DRINK_MESSAGE, drink)
        }
        if (drink.isEmpty()){
            Toast.makeText(this, "Enter a valid drink name", Toast.LENGTH_SHORT).show()
        }
        else {
            startActivity(intent)
        }

        editText.text.clear()
    }

    fun getSpiritList(view: View) {
        val editText = findViewById<EditText>(R.id.editText_spiritname)
        val spirit = editText.text.toString()
        val intent = Intent(this, DrinkListActivity::class.java).apply {
            putExtra(SPIRIT_MESSAGE, spirit)
        }
        if (spirit.isEmpty()){
            Toast.makeText(this, "Enter a valid spirit name", Toast.LENGTH_SHORT).show()
        }
        else {
            startActivity(intent)
        }

        editText.text.clear()
    }
}
