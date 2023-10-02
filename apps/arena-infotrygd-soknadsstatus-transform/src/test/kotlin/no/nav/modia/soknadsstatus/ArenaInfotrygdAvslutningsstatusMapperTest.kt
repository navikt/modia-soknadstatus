package no.nav.modia.soknadsstatus

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class ArenaInfotrygdAvslutningsstatusMapperTest {
    @Test
    fun `test at infotrygd mapping status filen blir lest`() {
        val status = ArenaInfotrygdAvslutningsstatusMapper.getAvslutningsstatus(INFOTRYGD, "T")
        assertEquals(status, SoknadsstatusDomain.Status.FERDIG_BEHANDLET)
    }
}
