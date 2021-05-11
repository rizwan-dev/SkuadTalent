package com.skuad.talent.ui.main.dashboard

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.skuad.talent.R
import com.skuad.talent.data.model.Cards
import com.skuad.talent.extension.setSafeOnClickListener

class DashboardAdapter(
    private val context: Context,
    private val cardList: List<Cards>,
    private val listener: (Cards) -> Unit
) :
    RecyclerView.Adapter<DashboardAdapter.ViewHolder>() {
    class ViewHolder(
        listItemView: View
    ) :
        RecyclerView.ViewHolder(listItemView) {
        val cardName = itemView.findViewById<TextView>(R.id.tvCardName)
        val activeProfiles = itemView.findViewById<TextView>(R.id.tvActiveProfiles)
        val cardIcon = itemView.findViewById<ImageView>(R.id.ivCardLogo)

        fun bind(cards: Cards) {
            cardName.text = cards.titleCard
            activeProfiles.text = cards.activeCard
            cardIcon.setImageResource(cards.imageCard)

        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DashboardAdapter.ViewHolder {
        val inflater = LayoutInflater.from(context)
        val candidateView = inflater.inflate(R.layout.item_dashbord_cards, parent, false)
        return ViewHolder(candidateView)
    }

    override fun onBindViewHolder(holder: DashboardAdapter.ViewHolder, position: Int) {
        val cards = cardList.get(position)
        holder.bind(cards)
        holder.itemView.setOnClickListener { listener(cards) }

    }

    override fun getItemCount(): Int {
        return cardList.size
    }

}
