package by.mankevich.task05fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

private const val TAG = "MyContactMainActivity"

class MainActivity : AppCompatActivity(), ListContactsFragment.Listener,
    DetailContactFragment.Listener {

    private var listContactsFragment: ListContactsFragment? = null
    private var detailContactFragment: DetailContactFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG, "onCreate ")
        super.onCreate(savedInstanceState)
        val sharedPreferences = getSharedPreferences(KEY_JSON_CONTACTS, MODE_PRIVATE)
        val jsonStr = sharedPreferences.getString(KEY_JSON_CONTACTS, null)
        if (jsonStr != null) {
            Data.contacts = fromJsonToList(jsonStr)
        }
        listContactsFragment = ListContactsFragment()
        supportFragmentManager.beginTransaction()
            .add(R.id.list_fragment_container, listContactsFragment!!)
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            .commit()
        setContentView(R.layout.activity_main)
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume ")
        if (Data.isEdited) {
            Log.d(TAG, "onResume: refreshing..")
            refreshFragmentUI(detailContactFragment!!)
            if (Data.isTablet) {
                refreshFragmentUI(listContactsFragment!!)
            }
            Data.isEdited = false
        }
        if (Data.isAdded) {
            Log.d(TAG, "onResume: refreshing..")
            refreshFragmentUI(listContactsFragment!!)
            Data.isAdded = false
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_add -> {
                startActivity(Intent(this, AddContactActivity::class.java))
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun itemClicked(contactId: Int) {
        detailContactFragment = DetailContactFragment()
        detailContactFragment!!.contactId = contactId
        val frameDetail = findViewById<View>(R.id.detail_fragment_container) as? FrameLayout
        val containerId = if (frameDetail == null) {
            R.id.list_fragment_container
        } else {
            Data.isTablet = true
            R.id.detail_fragment_container
        }
        supportFragmentManager.beginTransaction()
            .replace(containerId, detailContactFragment!!)
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            .addToBackStack(null)
            .commit()
    }

    private fun fromJsonToList(jsonStr: String): MutableList<Contact> {
        val medicineListType: Type = object : TypeToken<List<Contact?>?>() {}.type
        return Gson().fromJson(jsonStr, medicineListType)
    }

    override fun editClicked(contactId: Int) {
        val intent = Intent(this, EditContactActivity::class.java)
        intent.putExtra(EXTRA_EDIT_CONTACT_ID, contactId)
        startActivity(intent)
    }

    private fun refreshFragmentUI(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .detach(fragment)//кажется работает только с верхним элементом в стеке
            .commit()
        supportFragmentManager
            .beginTransaction()
            .attach(fragment)
            .commit()
    }

    companion object {
        const val EXTRA_EDIT_CONTACT_ID = "contactId"
    }
}