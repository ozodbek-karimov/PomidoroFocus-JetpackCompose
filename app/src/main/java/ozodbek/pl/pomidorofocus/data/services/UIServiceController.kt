package ozodbek.pl.pomidorofocus.data.services

import android.content.Context
import android.content.Intent
import ozodbek.pl.pomidorofocus.domain.models.TimerModes
import ozodbek.pl.pomidorofocus.utils.ServiceConstants
import ozodbek.pl.pomidorofocus.utils.SessionServiceActions

/**
 * A controller class to control the service from the ui
 */
class UIServiceController(
	private val context: Context
) {
	private val serviceIntent by lazy { Intent(context, SessionService::class.java) }

	fun startTimer(mode: TimerModes) {
		serviceIntent.apply {
			action = SessionServiceActions.START_TIMER.action

			when (mode) {
				TimerModes.FOCUS_MODE -> putExtra(
					ServiceConstants.START_TIMER_MODE,
					ServiceConstants.START_TIMER_FOCUS_MODE
				)

				TimerModes.BREAK_MODE -> putExtra(
					ServiceConstants.START_TIMER_MODE,
					ServiceConstants.START_TIMER_BREAK_MODE
				)
			}

			context.startService(this)
		}
	}

	fun pauseTimer() {
		serviceIntent.apply {
			action = SessionServiceActions.PAUSE_TIMER.action
			context.startService(this)
		}
	}

	fun resumeTimer() {
		serviceIntent.apply {
			action = SessionServiceActions.RESUME_TIMER.action
			context.startService(this)
		}
	}

	fun stopTimer() {
		serviceIntent.apply {
			action = SessionServiceActions.STOP_TIMER.action
			context.startService(this)
		}
	}

}