package ozodbek.pl.pomidorofocus.presentation.timer

import androidx.lifecycle.ViewModel
import ozodbek.pl.pomidorofocus.data.services.UIServiceController
import ozodbek.pl.pomidorofocus.domain.models.TimerModes
import dagger.hilt.android.lifecycle.HiltViewModel
import ozodbek.pl.pomidorofocus.presentation.timer.TimerEvents
import javax.inject.Inject

@HiltViewModel
class TimerViewModel @Inject constructor(
	private val serviceHelper: UIServiceController
) : ViewModel() {

	fun onTimerEvents(event: TimerEvents) = when (event) {
		TimerEvents.OnPause -> serviceHelper.pauseTimer()
		TimerEvents.OnResume -> serviceHelper.resumeTimer()
		TimerEvents.OnFocusModeStart -> serviceHelper.startTimer(TimerModes.FOCUS_MODE)
		TimerEvents.OnBreakModeStart -> serviceHelper.startTimer(TimerModes.BREAK_MODE)
		TimerEvents.OnStopSession -> serviceHelper.stopTimer()
	}

}