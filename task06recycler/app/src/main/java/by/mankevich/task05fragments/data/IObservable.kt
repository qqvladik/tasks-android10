package by.mankevich.task05fragments.data

interface IObservable {
    val observers: ArrayList<IObserver>

    fun addObserver(observer: IObserver) {
        observers.add(observer)
    }

    fun removeObserver(observer: IObserver) {
        observers.remove(observer)
    }

    fun sendUpdateEvent() {
        if(observers!= null && !observers.isEmpty()) {
            observers.forEach { it.update() }
        }
    }
}