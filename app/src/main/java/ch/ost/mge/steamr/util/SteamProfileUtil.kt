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
@SerialName("inGameInfo")
data class InGameInfo(
    @SerialName("gameLogo") val bannerUrl: String?
) : Parcelable {
    constructor(parcel: Parcel) : this(parcel.readString())

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(bannerUrl)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<InGameInfo> {
        override fun createFromParcel(parcel: Parcel): InGameInfo {
            return InGameInfo(parcel)
        }

        override fun newArray(size: Int): Array<InGameInfo?> {
            return arrayOfNulls(size)
        }
    }

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
    @SerialName("vacBanned") private val vacBanned: Int?,
    @SerialName("inGameInfo") val inGameInfo: InGameInfo?
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
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readParcelable(InGameInfo::class.java.classLoader)
    )

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
        parcel.writeParcelable(inGameInfo, flags)
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
