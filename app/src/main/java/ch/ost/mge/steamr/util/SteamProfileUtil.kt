package ch.ost.mge.steamr.util

import android.os.Parcel
import android.os.Parcelable
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import nl.adaptivity.xmlutil.ExperimentalXmlUtilApi
import nl.adaptivity.xmlutil.serialization.XML


@ExperimentalSerializationApi
@ExperimentalXmlUtilApi
fun parseProfile(xmlData: String): Profile {
    val xml = XML {
        policy = JacksonPolicy
    }

    return xml.decodeFromString(xmlData)
}

@Serializable
@SerialName("profile")
data class Profile(
    @SerialName("steamID64") val steamId: Long,
    @SerialName("steamID") val username: String?,
    val onlineState: String?,
    @SerialName("avatarFull") val avatarUrl: String?,
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readLong(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(steamId)
        parcel.writeString(username)
        parcel.writeString(onlineState)
        parcel.writeString(avatarUrl)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Profile> {
        override fun createFromParcel(parcel: Parcel): Profile {
            return Profile(parcel)
        }

        override fun newArray(size: Int): Array<Profile?> {
            return arrayOfNulls(size)
        }
    }
}
