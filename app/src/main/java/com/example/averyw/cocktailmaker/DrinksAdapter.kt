package com.example.averyw.cocktailmaker

import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.drink_row.view.*

/**
 * Created by averyw on 3/26/2019.
 */


class DrinksAdapter(val drinkList: DrinkList) : RecyclerView.Adapter<CustomViewHolder>() {

    // number of items
    override fun getItemCount(): Int {
        return drinkList.drinks.count()
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): CustomViewHolder {
        // create the view
        val layoutInflator = LayoutInflater.from(parent?.context)
        val cellForRow = layoutInflator.inflate(R.layout.drink_row, parent, false)
        return CustomViewHolder(cellForRow)
    }

    override fun onBindViewHolder(holder: CustomViewHolder?, position: Int) {
        val drink = drinkList.drinks.get(position)
        holder?.view?.textView_title?.text = drink.strDrink

        val thumbnailImageView = holder?.view?.imageView_Drink
        Picasso.get().load(drink.strDrinkThumb).into(thumbnailImageView)

        holder?.drink = drink
    }
}

class CustomViewHolder(val view: View, var drink: Drinks? = null): RecyclerView.ViewHolder(view) {

    companion object {
        val DRINK_NAME_KEY = "DRINK_NAME"
        val DRINK_NAME_ID = "DRINK_ID"
    }

    init {
        view.setOnClickListener {
            val intent = Intent(view.context, DrinkDetailActivity::class.java).apply {
                putExtra(DRINK_NAME_KEY, drink?.strDrink)
                putExtra(DRINK_NAME_ID, drink?.idDrink)
            }
            view.context.startActivity(intent)
        }
    }
}