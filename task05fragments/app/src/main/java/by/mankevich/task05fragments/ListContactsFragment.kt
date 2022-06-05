package by.mankevich.task05fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.fragment.app.ListFragment

private const val TAG = "MyContactList"

class ListContactsFragment : ListFragment() {
    private lateinit var listener: Listener

    interface Listener {
        fun itemClicked(contactId: Int)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = activity as Listener
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val nameList = mutableListOf<String>()
        Data.contacts.sortBy { it.name }
        Data.contacts.forEach {
            nameList.add(it.name + " " + it.surname)
        }
        val listContactsAdapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, nameList)
        listAdapter = listContactsAdapter
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onListItemClick(l: ListView, v: View, position: Int, id: Long) {
        super.onListItemClick(l, v, position, id)
        listener.itemClicked(position)
    }

}