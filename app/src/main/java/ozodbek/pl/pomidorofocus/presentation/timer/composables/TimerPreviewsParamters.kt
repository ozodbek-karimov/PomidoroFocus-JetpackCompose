package ozodbek.pl.pomidorofocus.presentation.timer.composables

import androidx.compose.ui.tooling.preview.datasource.CollectionPreviewParameterProvider
import ozodbek.pl.pomidorofocus.domain.models.TimerModes
import ozodbek.pl.pomidorofocus.domain.stopwatch.TimerWatchStates

class TimerModesPreviewParams : CollectionPreviewParameterProvider<TimerModes>(
	collection = TimerModes.entries.toList()
)

class TimerPlayPausePreviewParams : CollectionPreviewParameterProvider<TimerWatchStates>(
	listOf(TimerWatchStates.PAUSED, TimerWatchStates.RUNNING)
)