package ch.ost.mge.steamr.adapter

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import ch.ost.mge.steamr.R

class SteamIdAdapter(private val onClick: (String) -> Unit) :
    ListAdapter<String, SteamIdAdapter.ViewHolder>(SteamIdDiffCallback) {

    class ViewHolder(view: View, val onClick: (String) -> Unit) : RecyclerView.ViewHolder(view) {
        private val textView: TextView = view.findViewById(R.id.textView)
        private var currentSteamId: String? = null

        init {
            textView.setOnClickListener {
                currentSteamId?.let { onClick(it) }
            }
        }

        fun bind(steamId: String) {
            currentSteamId = steamId
            textView.text = currentSteamId
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.steam_id_row_item, viewGroup, false)

        return ViewHolder(view, onClick)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val steamId = getItem(position)
        holder.bind(steamId)
    }
}

object SteamIdDiffCallback : DiffUtil.ItemCallback<String>() {
    override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem === newItem
    }

    override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }
}

