package com.stockbit.feature_watchlist.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.stockbit.common.utils.toCurrency
import com.stockbit.feature_watchlist.R
import com.stockbit.model.Watchlist

class WatchlistAdapter: PagingDataAdapter<Watchlist, WatchlistAdapter.ViewHolder>(DIFF_CALLBACK) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(
                R
                .layout.watchlist_item, parent, false)
        return ViewHolder(view)
    }

    class ViewHolder(private val view: View): RecyclerView.ViewHolder(view) {
        private val symbol: TextView = view.findViewById(R.id.txt_symbol)
        private val desc: TextView = view.findViewById(R.id.txt_description)
        private val price: TextView = view.findViewById(R.id.txt_price)
        private val change: TextView = view.findViewById(R.id.txt_percentage)

        fun bind(watchlist: Watchlist) {
            symbol.text = watchlist.symbol
            desc.text = watchlist.name
            price.text = watchlist.price
            change.text = "$${watchlist.change} (${watchlist.changepct})"
            if (watchlist.changeAmount < 0) {
                change.setTextColor(ContextCompat.getColor(view.context, R.color.red))
            } else if (watchlist.change != "-") {
                change.setTextColor(ContextCompat.getColor(view.context, R.color.light_green))
            } else {
                change.setTextColor(ContextCompat.getColor(view.context, R.color.onyx))
            }
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Watchlist>() {
            override fun areItemsTheSame(oldItem: Watchlist, newItem: Watchlist): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Watchlist, newItem: Watchlist): Boolean =
                oldItem == newItem
        }
    }
}