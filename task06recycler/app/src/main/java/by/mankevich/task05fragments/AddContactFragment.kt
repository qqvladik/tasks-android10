package by.mankevich.task05fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.gson.Gson

private const val TAG = "MyContactAdd"

class AddContactFragment : Fragment() {

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editName: EditText
    private lateinit var editSurname: EditText
    private lateinit var editNumber: EditText
    private lateinit var editPhoto: EditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_edit_contact, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        editName = view.findViewById(R.id.edit_name)
        editSurname = view.findViewById(R.id.edit_surname)
        editNumber = view.findViewById(R.id.edit_number)
        editPhoto = view.findViewById(R.id.edit_photo)

        val buttonSave: Button = view.findViewById(R.id.button_save)
        val buttonCancel: Button = view.findViewById(R.id.button_cancel)

        buttonSave.setOnClickListener {
            if (editName.text.toString().trim() != "") {
                saveData()
            } else {
                Toast.makeText(requireContext(), "Field \"Name\" is empty!", Toast.LENGTH_SHORT).show()
            }
        }

        buttonCancel.setOnClickListener {
            cancel()
        }
    }

    private fun saveData() {
        val contactTemp = createContactTemp()
        if (editName.text.toString() != "" || editSurname.text.toString() != ""
            || editNumber.text.toString() != "" || editPhoto.text.toString() != "") {
            Data.contacts.add(contactTemp)

            sharedPreferences =
                requireActivity().getSharedPreferences(KEY_JSON_CONTACTS, Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            val jsonStr = Gson().toJson(Data.contacts)//а потом этот список засовываем обратно в
            // дату при перезапуске приложения, если настройки уже были созданы
            editor.putString(KEY_JSON_CONTACTS, jsonStr)
            editor.apply()
            Data.isAdded = true
        }

        requireActivity().onBackPressed()
    }

    private fun cancel() {
        if (editName.text.toString() != "" || editSurname.text.toString() != ""
            || editNumber.text.toString() != "" || editPhoto.text.toString() != "") {
            //todo диалоговое окно
        }
        requireActivity().onBackPressed()
    }

    private fun createContactTemp(): Contact {
        val name = editName.text.toString()
        val surname = editSurname.text.toString()
        val number = editNumber.text.toString()
        val photo =  Photo(editPhoto.text.toString())
        return Contact(name, surname, number, photo)
    }

}