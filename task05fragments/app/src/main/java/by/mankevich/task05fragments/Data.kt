package by.mankevich.task05fragments

const val KEY_JSON_CONTACTS = "Contacts"

object Data {
    var contacts: MutableList<Contact> = mutableListOf(
        Contact("Vlad", "Mankevich", "+375445313884"),
        Contact("Eugene", "Mankevich", "80291111111"),
        Contact("Mommy", "", "80123456789"),
        Contact("Dad", "", "80987654321")
    )

    var isEdited = false
    var isAdded = false
    var isTablet = false
}