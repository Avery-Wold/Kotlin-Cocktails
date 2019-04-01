package com.example.averyw.cocktailmaker

import android.content.Intent
import android.os.Bundle
import android.os.Looper
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.widget.Toast
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
        println("Fetching data")
        val message = intent.getStringExtra(DRINK_MESSAGE)

        val drinkDetailUrl = "https://www.thecocktaildb.com/api/json/v1/1/search.php?s=" + message

        val client = OkHttpClient()
        val request = Request.Builder().url(drinkDetailUrl).build()
        client.newCall(request).enqueue(object: Callback {
            override fun onResponse(call: Call?, response: Response?) {
                val body = response?.body()?.string()
                val gson = GsonBuilder().create()

                val instructions = gson.fromJson(body, DrinkList::class.java)
                println(instructions.drinks)

                if(instructions.drinks == null){
                    Looper.prepare()
                    Toast.makeText(this@DetailActivity, "Please input valid drink name", Toast.LENGTH_LONG)
                    val error = "Please input valid drink name"
                    val intent = Intent(this@DetailActivity, MainActivity::class.java).apply {
                        putExtra(ERROR_MESSAGE, error)
                    }
                    startActivity(intent)
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
}
