package no.nav.modia.soknadsstatus.pdl

import no.nav.api.generated.pdl.hentadressebeskyttelse.Adressebeskyttelse
import no.nav.modia.soknadsstatus.MockData

class PdlOppslagServiceMock : PdlOppslagService {
    override suspend fun hentFnr(userToken: String, aktorId: String): String = MockData.bruker.fnr
    override suspend fun hentFnrMedSystemToken(aktorId: String): String = MockData.bruker.fnr
    override suspend fun hentAktorId(userToken: String, fnr: String): String = MockData.bruker.aktorId
    override suspend fun hentGeografiskTilknytning(userToken: String, fnr: String): String = MockData.bruker.geografiskTilknyttning
    override suspend fun hentAdresseBeskyttelse(userToken: String, fnr: String): List<Adressebeskyttelse> = mutableListOf()
}
