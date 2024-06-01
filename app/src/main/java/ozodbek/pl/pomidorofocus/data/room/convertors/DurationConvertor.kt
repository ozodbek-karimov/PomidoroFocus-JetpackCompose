package ozodbek.pl.pomidorofocus.data.room.convertors

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import ozodbek.pl.pomidorofocus.domain.models.DurationOption

@ProvidedTypeConverter
class DurationConvertor {

	@TypeConverter
	fun toDuration(option: DurationOption): String = option.name

	@TypeConverter
	fun fromDuration(name: String): DurationOption = DurationOption.valueOf(name)
}