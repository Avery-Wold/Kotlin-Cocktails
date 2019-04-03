package com.example.averyw.cocktailmaker

import android.app.AlertDialog
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_drink__list_.*
import okhttp3.*
import java.io.IOException

class DrinkListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_drink__list_)
        recyclerView_drinks.layoutManager = LinearLayoutManager(this)
        //we'll change nav bar title
        val spiritName = intent.getStringExtra(SPIRIT_MESSAGE)
        supportActionBar?.title = spiritName + " " + "cocktails"
        fetchData()
    }

    private fun fetchData() {
        val message = intent.getStringExtra(SPIRIT_MESSAGE)
        val url = "https://www.thecocktaildb.com/api/json/v1/1/filter.php?i=" + message
        val request = Request.Builder().url(url).build()
        val client = OkHttpClient()

        client.newCall(request).enqueue(object: Callback {
            override fun onResponse(call: Call?, response: Response?) {
                val body = response?.body()?.string()
                val gson = GsonBuilder().create()
                val drinkList = gson.fromJson(body, DrinkList::class.java)
                if (drinkList == null){
                    runOnUiThread {
                        showDialog()
                    }
                }
                else {
                    runOnUiThread {
                        // acts like table view delegate (adapter)
                        recyclerView_drinks.adapter = DrinksAdapter(drinkList)
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
        builder.setMessage(R.string.spirit_dialog)
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