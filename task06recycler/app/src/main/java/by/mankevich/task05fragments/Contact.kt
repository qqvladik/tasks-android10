package by.mankevich.task05fragments


data class Contact(var name: String, var surname: String, var number: String, var photo: Photo) {

    fun copyPropertiesFrom(contact: Contact){
        this.name=contact.name
        this.surname=contact.surname
        this.number=contact.number
        this.photo=contact.photo
    }

    fun getFullName(): String {
        return name.plus(" ").plus(surname)
    }
}
