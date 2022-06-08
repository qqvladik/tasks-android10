package by.mankevich.task05fragments.data

import androidx.recyclerview.widget.DiffUtil
import by.mankevich.task05fragments.Contact

class ContactsDiffUtilCallback (
    private val oldList: List<Contact>,
    private val newList: List<Contact>): DiffUtil.Callback() {

    override fun getOldListSize() = oldList.size

    override fun getNewListSize() = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].name == newList[newItemPosition].name
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val isSame = oldList[oldItemPosition].name == newList[newItemPosition].name &&
                oldList[oldItemPosition].surname == newList[newItemPosition].surname &&
                oldList[oldItemPosition].number == newList[newItemPosition].number &&
                oldList[oldItemPosition].photo == newList[newItemPosition].photo
        return isSame
    }

}