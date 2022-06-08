package by.mankevich.task05fragments

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.isGone
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

private const val TAG = "MyContactMainActivity"

class MainActivity : AppCompatActivity(), ListContactsFragment.Listener,
    DetailContactFragment.Listener, Adapter.Listener {

    private var contactsFragment: RecyclerContactsFragment? = null
    private var detailContactFragment: DetailContactFragment? = null
    private var searchItem: MenuItem? = null//todo filter
    private lateinit var searchView: SearchView

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG, "onCreate ")
        if(Data.contacts.size<4){
            Data.fillContacts(100)
        }
        super.onCreate(savedInstanceState)
        val sharedPreferences = getSharedPreferences(KEY_JSON_CONTACTS, MODE_PRIVATE)
        val jsonStr = sharedPreferences.getString(KEY_JSON_CONTACTS, null)
        if (jsonStr != null) {
            Data.contacts = fromJsonToList(jsonStr)
        }
        contactsFragment = RecyclerContactsFragment()
        supportFragmentManager.beginTransaction()
            .add(R.id.list_fragment_container, contactsFragment!!)
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            .commit()
        setContentView(R.layout.activity_main)
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume ")
        if (Data.isEdited) {
            Log.d(TAG, "onResume: refreshing..")
            //todo update filteredContacts if was added
            refreshFragmentUI(detailContactFragment!!)
            if (Data.isTablet) {
                refreshFragmentUI(contactsFragment!!)
            }
            Data.isEdited = false
        }
        //todo feature add
        /*if (Data.isAdded) {
            Log.d(TAG, "onResume: refreshing..")
            //todo update filteredContacts if was added
            //refreshFragmentUI(contactsFragment!!)//походу если через recycler,
                            // то не нужно обновлять фрагмент
            Data.isAdded = false
        }*/
    }

    //todo feature add
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)

        searchItem = menu?.findItem(R.id.item_search)
        searchView = searchItem!!.actionView as SearchView
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        searchView.setSearchableInfo(
            searchManager
                .getSearchableInfo(componentName)
        )
        searchView.maxWidth = Int.MAX_VALUE

        /*// listening to search query text change//todo filter
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                // filter recycler view when query submitted
                return false
            }

            override fun onQueryTextChange(query: String?): Boolean {
                // filter recycler view when text is changed
                filter(query.toString());
                return false
            }
        })*/
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        /*val b: Boolean
        when (item.itemId) {
            R.id.item_add -> {//todo feature add
                b = true
                startActivity(Intent(this, AddContactActivity::class.java))
            }
            R.id.item_search -> b = true//todo filter
            else -> b = super.onOptionsItemSelected(item)
        }
        return b
        */
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        //hideSearch(false)//todo filter
    }

    /*private fun filter(text: String) {//todo filter
        //new array list that will hold the filtered data
        val filteredContactList: ArrayList<Contact> = ArrayList()
        //looping through existing elements
        for (c in Data.contacts) {
            //if the existing elements contains the search input
            if (c.getFullName().lowercase().contains(text.lowercase())) {
                //adding the element to filtered list
                filteredContactList.add(c)
            }
        }
        //calling a method of the adapter class and passing the filtered list
        contactsFragment!!.adapter.filterList(filteredContactList)//todo
    }*/

    override fun listItemClicked(contactId: Int) {
        itemClicked(contactId)
    }

    override fun recyclerItemClicked(contactId: Int) {
        itemClicked(contactId)
    }

    private fun itemClicked(contactId: Int) {
        detailContactFragment = DetailContactFragment()
        detailContactFragment!!.contactId = contactId
        val frameDetail = findViewById<View>(R.id.detail_fragment_container) as? FrameLayout
        val containerId: Int
        if (frameDetail == null) {
            //hideSearch(true)//todo filter
            containerId = R.id.list_fragment_container
        } else {
            Data.isTablet = true
            containerId = R.id.detail_fragment_container
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

    /*private fun hideSearch(isHide: Boolean){//todo filter
        if(isHide){
            if(searchView.isShown){
                searchView.isGone = true
            }
            searchItem!!.isVisible = false
        }else{
            searchItem!!.isVisible = true
            searchView.isGone = false
        }
    }*/

    companion object {
        const val EXTRA_EDIT_CONTACT_ID = "contactId"
    }
}