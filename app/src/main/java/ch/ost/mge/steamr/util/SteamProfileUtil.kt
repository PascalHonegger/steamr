package ch.ost.mge.steamr.util

import android.os.Parcel
import android.os.Parcelable
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import nl.adaptivity.xmlutil.ExperimentalXmlUtilApi
import nl.adaptivity.xmlutil.serialization.XML
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

private val datePattern = DateTimeFormatter.ofPattern("MMMM d, y", Locale.ENGLISH)

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
    @SerialName("stateMessage") val onlineState: String?,
    @SerialName("avatarFull") val avatarUrl: String?,
    @SerialName("summary") val summary: String?,
    @SerialName("realname") val realName: String?,
    @SerialName("location") val location: String?,
    @SerialName("memberSince") private val memberSince: String?,
    @SerialName("vacBanned") private val vacBanned: Int?
) : Parcelable {
    val creationDate by lazy { memberSince?.let { LocalDate.parse(it, datePattern) } }
    val isVacBanned by lazy { vacBanned != null && vacBanned != 0 }

    constructor(parcel: Parcel) : this(
        parcel.readLong(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readValue(Int::class.java.classLoader) as? Int
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(steamId)
        parcel.writeString(username)
        parcel.writeString(onlineState)
        parcel.writeString(avatarUrl)
        parcel.writeString(summary)
        parcel.writeString(realName)
        parcel.writeString(location)
        parcel.writeString(memberSince)
        parcel.writeValue(vacBanned)
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
