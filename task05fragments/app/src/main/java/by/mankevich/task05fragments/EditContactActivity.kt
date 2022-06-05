package by.mankevich.task05fragments

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.FrameLayout
import androidx.fragment.app.FragmentTransaction

class EditContactActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_contact)

        val editContactFragment = EditContactFragment()
        editContactFragment.contactId=intent.getIntExtra(MainActivity.EXTRA_EDIT_CONTACT_ID, -1)
        supportFragmentManager.beginTransaction()
                .replace(R.id.edit_fragment_container, editContactFragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit()
    }
}