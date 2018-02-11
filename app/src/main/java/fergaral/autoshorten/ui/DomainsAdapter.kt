package fergaral.autoshorten.ui

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import fergaral.autoshorten.R
import fergaral.autoshorten.data.Domain
import kotlinx.android.synthetic.main.domain_item.view.*

/**
 * Created by Fer on 10/02/2018.
 */
class DomainsAdapter(private var items: List<Domain>, private val onRemoveListener: (Domain) -> Unit): RecyclerView.Adapter<DomainsAdapter.DomainViewHolder>() {

    override fun onBindViewHolder(holder: DomainViewHolder?, position: Int) {
        holder?.bind(items[position], onRemoveListener)
    }

    override fun getItemCount(): Int = items.count()

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): DomainViewHolder {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.domain_item, parent, false)
        return DomainViewHolder(view, items)
    }

    fun setItems(items: List<Domain>?) {
        if (items != null) {
            this.items = items
            notifyDataSetChanged()
        }
    }

    class DomainViewHolder(view: View, private val items: List<Domain>): RecyclerView.ViewHolder(view) {
        val btnDelete: ImageButton
        val tvDomain: TextView

        init {
            btnDelete = view.btnDeleteDomain
            tvDomain = view.tvDomain
        }

        fun bind(domain: Domain, onRemoveListener: (Domain) -> Unit) {
            tvDomain.text = domain.url
            btnDelete.setOnClickListener {
                onRemoveListener(items[adapterPosition])
            }
        }
    }
}