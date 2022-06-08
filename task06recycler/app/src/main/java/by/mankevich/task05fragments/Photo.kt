package by.mankevich.task05fragments

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Photo(val url: String) : Parcelable
