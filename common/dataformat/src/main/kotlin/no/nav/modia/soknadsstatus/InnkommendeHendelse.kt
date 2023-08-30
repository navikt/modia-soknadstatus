package no.nav.modia.soknadsstatus

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

@Serializable
data class InnkommendeHendelse(
    val aktoerer: List<String>,
    val identer: List<String>,
    val ansvarligEnhet: String? = null,
    val behandlingsId: String,
    val behandlingsTema: String,
    val behandlingsType: String,
    val hendelsesType: SoknadsstatusDomain.HendelseType,
    val hendelsesId: String,
    val hendelsesTidspunkt: LocalDateTime,
    val hendelsesProdusent: String,
    val sakstema: String,
    val status: SoknadsstatusDomain.Status,
    val primaerBehandling: String? = null,
)
