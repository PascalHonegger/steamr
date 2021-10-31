package ch.ost.mge.steamr.util

import kotlinx.serialization.ExperimentalSerializationApi
import nl.adaptivity.xmlutil.ExperimentalXmlUtilApi
import org.junit.Test

import org.junit.Assert.*

@ExperimentalSerializationApi
@ExperimentalXmlUtilApi
class SteamProfileUtilTest {

    @Test
    fun `given valid xml parseProfile returns valid profile`() {
        val xml = ClassLoader.getSystemResource("full_profile.xml").readText()

        val profile = parseProfile(xml)

        assertNotNull(profile)
        assertNotNull(profile.inGameInfo)
        println(profile)
    }

    @Test
    fun `given minimal valid xml parseProfile returns valid profile`() {
        val xml = ClassLoader.getSystemResource("minimal_profile.xml").readText()

        val profile = parseProfile(xml)

        assertNotNull(profile)
        println(profile)
    }
}