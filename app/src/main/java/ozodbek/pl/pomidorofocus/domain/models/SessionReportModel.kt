package ozodbek.pl.pomidorofocus.domain.models

import java.time.LocalDate

data class SessionReportModel(
	val date: LocalDate,
	val sessionCount: Int,
)
