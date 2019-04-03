package com.example.averyw.cocktailmaker

import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.Window
import android.widget.TextView
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_drink__list_.*
import okhttp3.*
import java.io.IOException


/**
 * Created by averyw on 3/28/2019.
 */

const val ERROR_MESSAGE = "ERROR_MESSAGE"
class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_drink__list_)
        recyclerView_drinks.layoutManager = LinearLayoutManager(this)
//        recyclerView_drinks.adapter = DrinkDetailAdapter()
        fetchData()
    }

    private fun fetchData() {
        val message = intent.getStringExtra(DRINK_MESSAGE)
        val drinkDetailUrl = "https://www.thecocktaildb.com/api/json/v1/1/search.php?s=" + message
        val client = OkHttpClient()
        val request = Request.Builder().url(drinkDetailUrl).build()

        client.newCall(request).enqueue(object: Callback {
            override fun onResponse(call: Call?, response: Response?) {
                val body = response?.body()?.string()
                val gson = GsonBuilder().create()
                val instructions = gson.fromJson(body, DrinkList::class.java)
                if(instructions.drinks == null){
                    runOnUiThread {
                        showDialog()
                    }
                }
                else {
                    runOnUiThread {
                        // acts like table view delegate (adapter)
                        recyclerView_drinks.adapter = DrinkDetailAdapter(instructions)
                    }
                }
            }

            override fun onFailure(call: Call?, e: IOException?) {
                println("Failed to execute request")
            }
        })
    }

    private fun showDialog() {
        val builder = AlertDialog.Builder(this, R.style.AlertDialogCustom)
        builder.setTitle(R.string.title_dialog)
        builder.setMessage(R.string.drink_dialog)
        builder.setPositiveButton(R.string.try_again){_,_ ->
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        val dialog: AlertDialog = builder.create()
        dialog.setCancelable(false)
        dialog.setCanceledOnTouchOutside(false)
        dialog.show()
    }
}
