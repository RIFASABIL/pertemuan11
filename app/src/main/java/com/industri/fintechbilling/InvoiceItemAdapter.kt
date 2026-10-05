package com.industri.fintechbilling

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.text.NumberFormat
import java.util.Locale

class InvoiceItemAdapter(
    private val items: List<InvoiceItem>
) : RecyclerView.Adapter<InvoiceItemAdapter.ViewHolder>() {

    private val currencyFormat: NumberFormat = NumberFormat.getCurrencyInstance(Locale.forLanguageTag("id-ID"))

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvItemName: TextView = itemView.findViewById(R.id.tvItemName)
        val tvItemSubtotal: TextView = itemView.findViewById(R.id.tvItemSubtotal)
        val tvItemQtyPrice: TextView = itemView.findViewById(R.id.tvItemQtyPrice)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_invoice_product, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.tvItemName.text = item.itemName
        holder.tvItemQtyPrice.text = "${item.qty} x @ ${currencyFormat.format(item.unitPrice)}"
        holder.tvItemSubtotal.text = currencyFormat.format(item.subtotal)
    }

    override fun getItemCount(): Int = items.size
}
