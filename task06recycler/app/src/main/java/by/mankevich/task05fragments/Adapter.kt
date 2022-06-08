package by.mankevich.task05fragments

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.MemoryPolicy
import com.squareup.picasso.NetworkPolicy
import com.squareup.picasso.Picasso

private const val TAG = "AdapterContacts"

class Adapter: RecyclerView.Adapter<Adapter.ViewHolder>() {

    lateinit var listener: Listener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.contact_card, parent, false)
        val viewHolder = ViewHolder(itemView)
        itemView.setOnClickListener{//слушатель лучше назначать при создании
            val adapterPosition = viewHolder.adapterPosition
            if (adapterPosition != RecyclerView.NO_POSITION) {
                listener.recyclerItemClicked(adapterPosition)
            }
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textContactCard.text = Data.contacts[position].getFullName()//можно сделать отдельный метод bind в классе viewholder
        val photo = Data.contacts[position].photo
        Picasso.get()//todo можно в отдельную логику закинуть
            .load(photo.url)
            .error(android.R.drawable.ic_menu_report_image)
            .fit()
            .into(holder.imageContactCard)
    }

    override fun getItemCount(): Int {
        return Data.contacts.size
    }

    fun filterList(filteredContactList: ArrayList<Contact>) {
        //Data.filteredContacts=filteredContactList
        //this.contactList = filteredContactList
        notifyDataSetChanged()
    }

    interface Listener{
        fun recyclerItemClicked(contactId: Int)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var textContactCard: TextView
        var imageContactCard: ImageView
        init {
            textContactCard = itemView.findViewById(R.id.text_contact_card)
            imageContactCard = itemView.findViewById(R.id.image_contact_card)
        }
    }
}