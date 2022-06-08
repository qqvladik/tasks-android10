package by.mankevich.task05fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class RecyclerContactsFragment : Fragment() {

    private lateinit var listener: Adapter.Listener
    lateinit var adapter: Adapter
        private set

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as Adapter.Listener
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_recycler_contacts, container, false)
        val recyclerView: RecyclerView = view.findViewById(R.id.recycler_contacts)
        recyclerView.layoutManager = LinearLayoutManager(requireActivity())
        adapter = Adapter()
        adapter.listener = listener
        recyclerView.adapter = adapter
        return view
    }

}