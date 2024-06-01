package ozodbek.pl.pomidorofocus.data.repository

import android.database.sqlite.SQLiteConstraintException
import ozodbek.pl.pomidorofocus.data.room.dao.DaySessionDao
import ozodbek.pl.pomidorofocus.data.room.dao.SessionInfoDao
import ozodbek.pl.pomidorofocus.data.room.entity.DaySessionEntry
import ozodbek.pl.pomidorofocus.data.room.entity.SessionInfoEntity
import ozodbek.pl.pomidorofocus.domain.models.DurationOption
import ozodbek.pl.pomidorofocus.domain.models.TimerModes
import ozodbek.pl.pomidorofocus.domain.repository.TimerServiceRepository
import ozodbek.pl.pomidorofocus.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.time.LocalDate

class TimerServiceRepoImpl(
	private val dailySessionDao: DaySessionDao,
	private val sessionDao: SessionInfoDao,
) : TimerServiceRepository {

	override suspend fun addTimerSession(
		sessionDate: LocalDate,
		option: DurationOption,
		mode: TimerModes
	): Resource<Boolean> = withContext(Dispatchers.IO) {
		try {
			val entity = dailySessionDao.fetchDaysEntryIfExists(sessionDate)
			val sessionId = entity?.id ?: kotlin.run {
				val newEntity = DaySessionEntry(date = sessionDate)
				dailySessionDao.insertDayEntry(newEntity)
			}
			val session = SessionInfoEntity(
				option = option,
				mode = mode,
				sessionId = sessionId
			)
			sessionDao.insertSessionEntry(session)
			Resource.Success(true)
		} catch (e: SQLiteConstraintException) {
			e.printStackTrace()
			Resource.Error(errorMessage = e.message ?: "")
		} catch (e: Exception) {
			e.printStackTrace()
			Resource.Error(errorMessage = e.message ?: "")
		}
	}

}