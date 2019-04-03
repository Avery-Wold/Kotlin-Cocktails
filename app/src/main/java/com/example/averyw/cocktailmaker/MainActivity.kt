package com.example.averyw.cocktailmaker

import android.app.AlertDialog
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

    fun getDrink(view: View){
        val editText = findViewById<EditText>(R.id.editText_drinkname)
        var drink = editText.text.toString()
        drink = drink.replace("\\s".toRegex(), "+")
        val intent = Intent(this, DetailActivity::class.java).apply {
            putExtra(DRINK_MESSAGE, drink)
        }
        if (drink.isEmpty()){
            val builder = AlertDialog.Builder(this, R.style.AlertDialogCustom)
            builder.setTitle(R.string.title_dialog)
            builder.setMessage(R.string.drink_dialog)
            val dialog: AlertDialog = builder.create()
            builder.setPositiveButton(R.string.try_again){_,_ ->
                dialog.dismiss()
            }
            dialog.show()
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
            val builder = AlertDialog.Builder(this, R.style.AlertDialogCustom)
            builder.setTitle(R.string.title_dialog)
            builder.setMessage(R.string.spirit_dialog)
            val dialog: AlertDialog = builder.create()
            builder.setPositiveButton(R.string.try_again){_,_ ->
                dialog.dismiss()
            }
            dialog.show()
        }
        else {
            startActivity(intent)
        }
        editText.text.clear()
    }
}
