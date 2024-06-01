package ozodbek.pl.pomidorofocus.domain.repository

import ozodbek.pl.pomidorofocus.domain.models.SessionReportModel
import ozodbek.pl.pomidorofocus.domain.models.TimerModes
import ozodbek.pl.pomidorofocus.utils.Resource
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

interface StatisticsRepository {


	fun sessionAvgMinutes(
		mode: TimerModes,
		start: LocalDate?,
		end: LocalDate = LocalDate.now()
	): Flow<Float>

	fun totalSessionCount(
		mode: TimerModes,
		start: LocalDate?,
		end: LocalDate = LocalDate.now()
	): Flow<Int>

	fun weeklyReport(
		mode: TimerModes,
		start: LocalDate,
		end: LocalDate = LocalDate.now()
	): Flow<List<SessionReportModel>>

	suspend fun deleteStatisticsData(
		start: LocalDate?,
		end: LocalDate = LocalDate.now()
	): Resource<Unit>

}