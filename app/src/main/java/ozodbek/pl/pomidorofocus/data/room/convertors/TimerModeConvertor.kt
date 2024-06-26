package ozodbek.pl.pomidorofocus.data.room.convertors

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import ozodbek.pl.pomidorofocus.domain.models.TimerModes

@ProvidedTypeConverter
class TimerModeConvertor {

	@TypeConverter
	fun toTimerMode(mode: String): TimerModes = TimerModes.valueOf(mode)

	@TypeConverter
	fun fromTimerMode(mode: TimerModes): String = mode.name

}