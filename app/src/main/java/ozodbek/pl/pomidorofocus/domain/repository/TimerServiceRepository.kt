package ozodbek.pl.pomidorofocus.domain.repository

import ozodbek.pl.pomidorofocus.domain.models.DurationOption
import ozodbek.pl.pomidorofocus.domain.models.TimerModes
import ozodbek.pl.pomidorofocus.utils.Resource
import java.time.LocalDate

interface TimerServiceRepository {

	suspend fun addTimerSession(
        sessionDate: LocalDate = LocalDate.now(),
        option: DurationOption,
        mode: TimerModes
	): Resource<Boolean>

}