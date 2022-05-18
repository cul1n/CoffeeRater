package com.example.coffeerater

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CS_RecyclerViewAdapter(private val coffeeShopList: MutableList<CoffeeShopModel>) : RecyclerView.Adapter<CS_RecyclerViewAdapter.MyViewHolder>(), Filterable {
    var coffeeShopListFiltered: MutableList<CoffeeShopModel> = coffeeShopList

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CS_RecyclerViewAdapter.MyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_view_card, parent, false)

        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: CS_RecyclerViewAdapter.MyViewHolder, position: Int) {
        val itemsViewModel = coffeeShopListFiltered[position]

        holder.imageView.setImageBitmap(itemsViewModel.image)

        holder.textViewName.text = itemsViewModel.coffeeShopName

        holder.textViewLocation.text = itemsViewModel.coffeeShopLocation
    }

    override fun getItemCount(): Int {
        return coffeeShopListFiltered.size
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imageViewCamera)
        val textViewName: TextView = itemView.findViewById(R.id.textView)
        val textViewLocation: TextView = itemView.findViewById(R.id.textView2)
    }

    override fun getFilter(): Filter {
        return customFilter
    }

    private val customFilter = object: Filter() {

        override fun performFiltering(constraint: CharSequence?): FilterResults {
            val charString = constraint?.toString() ?: ""
            if (charString.isEmpty()) {
                coffeeShopListFiltered = coffeeShopList
            }
            else {
                val filteredList = ArrayList<CoffeeShopModel>()
                coffeeShopList
                    .filter {
                        (it.coffeeShopName.lowercase().contains(constraint!!.toString().lowercase()))

                    }.forEach { filteredList.add(it) }
                coffeeShopListFiltered = filteredList

            }
            return FilterResults().apply { values = coffeeShopListFiltered }
        }

        override fun publishResults(constraints: CharSequence?, results: FilterResults?) {
            coffeeShopListFiltered = if (results?.values == null)
                ArrayList()
            else
                results.values as ArrayList<CoffeeShopModel>

            notifyDataSetChanged()
        }
    }
}
