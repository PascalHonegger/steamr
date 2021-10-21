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
        val xml = ClassLoader.getSystemResource("example.xml").readText()

        val profile = parseProfile(xml)

        assertNotNull(profile)
        println(profile)
    }
}