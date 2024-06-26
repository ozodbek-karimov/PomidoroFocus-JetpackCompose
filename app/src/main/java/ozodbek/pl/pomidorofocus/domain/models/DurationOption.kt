package ozodbek.pl.pomidorofocus.domain.models

enum class DurationOption(val minutes: Int) {
	ONE_MINUTE(1),
	FIVE_MINUTES(5),
	TEN_MINUTES(10),
	FIFTEEN_MINUTES(15),
	THIRTY_MINUTES(30),
	ONE_HOUR(60);

	companion object {

		fun fromNumber(minutes: Int): DurationOption = when (minutes) {
			ONE_MINUTE.minutes -> ONE_MINUTE
			FIVE_MINUTES.minutes -> FIVE_MINUTES
			TEN_MINUTES.minutes -> TEN_MINUTES
			FIFTEEN_MINUTES.minutes -> FIFTEEN_MINUTES
			THIRTY_MINUTES.minutes -> THIRTY_MINUTES
			else -> ONE_HOUR
		}
	}
}