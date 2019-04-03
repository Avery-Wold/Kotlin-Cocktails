package com.example.averyw.cocktailmaker

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.widget.TextView
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_drink__list_.*
import okhttp3.*
import java.io.IOException

/**
 * Created by averyw on 3/27/2019.
 */
class DrinkDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_drink__list_)
        recyclerView_drinks.layoutManager = LinearLayoutManager(this)
//        recyclerView_drinks.adapter = DrinkDetailAdapter()

        fetchData()

    }

    private fun fetchData() {
        val drinkId = intent.getStringExtra(CustomViewHolder.DRINK_NAME_ID)

        val drinkDetailUrl = "https://www.thecocktaildb.com/api/json/v1/1/lookup.php?i=" + drinkId

        val client = OkHttpClient()
        val request = Request.Builder().url(drinkDetailUrl).build()
        client.newCall(request).enqueue(object: Callback {
            override fun onResponse(call: Call?, response: Response?) {
                val body = response?.body()?.string()

                val gson = GsonBuilder().create()

                val instructions = gson.fromJson(body, DrinkList::class.java)
                runOnUiThread{
                    // acts like table view delegate (adapter)
                    recyclerView_drinks.adapter = DrinkDetailAdapter(instructions)
                }
            }

            override fun onFailure(call: Call?, e: IOException?) {
                println("Failed to execute request")
            }
        })
    }
}