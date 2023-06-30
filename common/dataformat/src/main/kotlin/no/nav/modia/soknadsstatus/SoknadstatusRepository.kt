package no.nav.modia.soknadsstatus

import kotlinx.datetime.toJavaInstant
import kotlinx.datetime.toKotlinInstant
import no.nav.modia.soknadsstatus.SqlDsl.execute
import no.nav.modia.soknadsstatus.SqlDsl.executeQuery
import java.sql.Timestamp
import javax.sql.DataSource

interface SoknadsstatusRepository {
    fun get(ident: String): Result<List<SoknadsstatusDomain.SoknadsstatusOppdatering>>
    fun upsert(state: SoknadsstatusDomain.SoknadsstatusOppdatering): Result<Boolean>
}

class SoknadsstatusRepositoryImpl(private val dataSource: DataSource) : SoknadsstatusRepository {
    private object Tabell {
        override fun toString(): String = "soknadsstatus"
        const val ident = "ident"
        const val behandlingsId = "behandlingsId"
        const val systemRef = "systemRef"
        const val tema = "tema"
        const val status = "status"
        const val tidspunkt = "tidspunkt"
    }

    override fun get(ident: String): Result<List<SoknadsstatusDomain.SoknadsstatusOppdatering>> {
        return dataSource.executeQuery("SELECT * from $Tabell where ${Tabell.ident} = ?", ident).map { rows ->
            rows.map {
                SoknadsstatusDomain.SoknadsstatusOppdatering(
                    ident = it.rs.getString(Tabell.ident),
                    behandlingsId = it.rs.getString(Tabell.behandlingsId),
                    systemRef = it.rs.getString(Tabell.systemRef),
                    tema = it.rs.getString(Tabell.tema),
                    status = SoknadsstatusDomain.Status.valueOf(it.rs.getString(Tabell.status)),
                    tidspunkt = it.rs.getTimestamp(Tabell.tidspunkt).toInstant().toKotlinInstant()
                )
            }.toList()
        }
    }

    override fun upsert(status: SoknadsstatusDomain.SoknadsstatusOppdatering): Result<Boolean> {
        val tidspunkt = Timestamp.from(status.tidspunkt.toJavaInstant())
        return dataSource.execute(
            """
            INSERT INTO $Tabell
            VALUES (?, ?, ?, ?, ?::statusenum, ?)
            ON CONFLICT (${Tabell.ident}, ${Tabell.behandlingsId}) DO
            UPDATE SET ${Tabell.status} = ?::statusenum, ${Tabell.tidspunkt} = ? WHERE $Tabell.${Tabell.tidspunkt} < ?
            """.trimIndent(),
            status.ident,
            status.behandlingsId,
            status.systemRef,
            status.tema,
            status.status.name,
            tidspunkt,
            status.status.name,
            tidspunkt,
            tidspunkt,
        )
    }
}
