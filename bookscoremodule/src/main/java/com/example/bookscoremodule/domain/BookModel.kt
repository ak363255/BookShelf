package com.example.bookscoremodule.domain

import android.os.Parcel
import android.os.Parcelable

data class BookModel(
    var alias: String?,
    var hits: Int?,
    var id: String?,
    var image: String?,
    var lastChapterDate: Int?,
    var title: String?,
    var isFav:Boolean = false
):Parcelable{
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString(),
        parcel.readString(),
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString(),
        parcel.readByte() != 0.toByte()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(alias)
        parcel.writeValue(hits)
        parcel.writeString(id)
        parcel.writeString(image)
        parcel.writeValue(lastChapterDate)
        parcel.writeString(title)
        parcel.writeByte(if (isFav) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<BookModel> {
        override fun createFromParcel(parcel: Parcel): BookModel {
            return BookModel(parcel)
        }

        override fun newArray(size: Int): Array<BookModel?> {
            return arrayOfNulls(size)
        }
    }

}