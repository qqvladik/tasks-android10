package by.mankevich.task05fragments

import android.content.Context
import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.text.util.Linkify
import android.util.Log
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

private const val KEY_CONTACT_ID = "contactId"
private const val TAG = "MyContactDetail"

class DetailContactFragment : Fragment() {
    var contactId: Int? = null
    private lateinit var contact: Contact
    private lateinit var listener: Listener

    interface Listener {
        fun editClicked(contactId: Int)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.d(TAG, "onAttach: ")
        listener = requireActivity() as Listener
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d(TAG, "onCreateView: ")
        contact = Data.contacts[contactId!!]
        return inflater.inflate(R.layout.fragment_detail_contact, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "onViewCreated: ")
        val textName: TextView = view.findViewById(R.id.text_name)
        val textNumber: TextView = view.findViewById(R.id.text_number)

        textName.text = contact.name.plus(" ").plus(contact.surname)
        textNumber.text = contact.number
        Linkify.addLinks(
            textNumber,
            Patterns.PHONE,
            "tel:",
            Linkify.sPhoneNumberMatchFilter,
            Linkify.sPhoneNumberTransformFilter
        )
        textNumber.movementMethod = LinkMovementMethod.getInstance()

        val imgButtonEditContact: ImageView = view.findViewById(R.id.img_button_edit_contact)
        imgButtonEditContact.setOnClickListener {
            listener.editClicked(contactId!!)
        }
    }
}