package ozodbek.pl.pomidorofocus.presentation.statistics.utils

import androidx.annotation.StringRes
import ozodbek.pl.pomidorofocus.R

sealed class StatisticsType(
	@StringRes val label: Int,
	val tabIndex: Int
) {

	data object All : StatisticsType(label = R.string.statistics_all, tabIndex = 0)

	data object Weekly : StatisticsType(label = R.string.statistics_weekly, tabIndex = 1)

	data object Today : StatisticsType(label = R.string.statistics_today, tabIndex = 2)
}

