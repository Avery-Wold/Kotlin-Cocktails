package com.example.averyw.cocktailmaker

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.drink_detail.view.*

/**
 * Created by averyw on 3/27/2019.
 */
class  DrinkDetailAdapter(val drinkList: DrinkList): RecyclerView.Adapter<DrinkInstructionViewHolder>(){

    override fun getItemCount(): Int {
        return 1
//        return drinkList.drinks.count()
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int):DrinkInstructionViewHolder {
        val layoutInflator = LayoutInflater.from(parent?.context)
        val customView = layoutInflator.inflate(R.layout.drink_detail, parent, false)
        return DrinkInstructionViewHolder(customView)
    }

    override fun onBindViewHolder(holder: DrinkInstructionViewHolder?, position: Int) {
        val drink = drinkList.drinks.get(position)

        holder?.customView?.textView_title?.text = drink.strDrink
        holder?.customView?.textView_glass?.text = drink.strGlass
        holder?.customView?.textView_instructions1?.text = drink.strMeasure1 + drink.strIngredient1
        holder?.customView?.textView_instructions2?.text = drink.strMeasure2 + drink.strIngredient2
        holder?.customView?.textView_instructions3?.text = drink.strMeasure3 + drink.strIngredient3
        holder?.customView?.textView_instructions4?.text = drink.strMeasure4 + drink.strIngredient4
        holder?.customView?.textView_instructions5?.text = drink.strMeasure5 + drink.strIngredient5
        holder?.customView?.textView_howTo?.text = drink.strInstructions

        val thumbnailImageView = holder?.customView?.imageView_Drink
        Picasso.get().load(drink.strDrinkThumb).into(thumbnailImageView)

        holder?.drink = drink
    }
}

class DrinkInstructionViewHolder(val customView: View, var drink: Drinks? = null): RecyclerView.ViewHolder(customView) {

}