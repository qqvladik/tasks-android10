package by.mankevich.task05fragments

const val KEY_JSON_CONTACTS = "Contacts"

object Data {
    var contacts: MutableList<Contact> = mutableListOf(
        Contact(
            "Vlad",
            "Mankevich",
            "+375445313884",
            Photo("https://picsum.photos/id/200/200/300")
        ),
        Contact(
            "Eugene",
            "Mankevich",
            "80291111111",
            Photo("https://picsum.photos/id/201/200/300")
        ),
        Contact("Mommy", "", "80123456789", Photo("https://picsum.photos/id/202/200/300")),
//        Contact("Dad", "", "80987654321", Photo("https://picsum.photos/id/203/200/300"))
    )

    var isEdited = false
    var isAdded = false
    var isTablet = false

    fun fillContacts(count: Int) {
        var charPool: List<Char>
        var randomString: String
        var digitPool: List<Char>
        var randomNumber: String
        for (i in 0..count) {
            charPool = ('a'..'z') + ('A'..'Z')
            randomString = (1..6)
                .map { kotlin.random.Random.nextInt(0, charPool.size) }
                .map(charPool::get)
                .joinToString("")

            digitPool = ('0'..'9') + ('0'..'9')
            randomNumber = (1..9)
                .map { kotlin.random.Random.nextInt(0, digitPool.size) }
                .map(digitPool::get)
                .joinToString("")
            contacts.add(
                Contact(
                    randomString,
                    randomString,
                    "80".plus(randomNumber),
                    Photo("https://picsum.photos/id/".plus(i.toString()).plus("/200/300"))
                )
            )
        }
    }
}