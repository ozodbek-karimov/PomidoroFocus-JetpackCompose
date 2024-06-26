package ozodbek.pl.pomidorofocus.presentation.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ozodbek.pl.pomidorofocus.domain.facade.SessionReminderFacade
import ozodbek.pl.pomidorofocus.domain.facade.SettingsInfoFacade
import ozodbek.pl.pomidorofocus.domain.facade.SettingsPreferences
import ozodbek.pl.pomidorofocus.domain.models.DurationOption
import ozodbek.pl.pomidorofocus.domain.models.SessionNumberOption
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import ozodbek.pl.pomidorofocus.presentation.settings.ChangeSettingsEvent
import java.time.LocalTime
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
	private val settingsPreferences: SettingsPreferences,
	private val reminderFacade: SessionReminderFacade,
	airPlaneSettingsInfo: SettingsInfoFacade,
) : ViewModel() {

	val focusDuration = settingsPreferences.focusDuration
		.stateIn(
			scope = viewModelScope,
			started = SharingStarted.WhileSubscribed(2000L),
			initialValue = DurationOption.TEN_MINUTES
		)

	val breakDuration = settingsPreferences.breakDuration
		.stateIn(
			scope = viewModelScope,
			started = SharingStarted.WhileSubscribed(2000L),
			initialValue = DurationOption.TEN_MINUTES
		)

	val sessionCount = settingsPreferences.sessionCount.stateIn(
		scope = viewModelScope,
		started = SharingStarted.WhileSubscribed(2000L),
		initialValue = SessionNumberOption.TWO_TIMES
	)

	val isSaveSessionDataAllowed = settingsPreferences.isSaveSessions.stateIn(
		scope = viewModelScope,
		started = SharingStarted.WhileSubscribed(2000L),
		initialValue = false
	)

	val reminderNotificationTime = settingsPreferences.reminderTime.stateIn(
		scope = viewModelScope,
		started = SharingStarted.WhileSubscribed(200L),
		initialValue = LocalTime.of(0, 0)
	)

	val isGoalsNotificationActive = settingsPreferences.isGoalReminderActive.stateIn(
		viewModelScope,
		SharingStarted.WhileSubscribed(2000L),
		initialValue = false
	)

	val isAirPlaneModeEnabled = airPlaneSettingsInfo.status
		.stateIn(
			scope = viewModelScope,
			started = SharingStarted.Lazily,
			initialValue = airPlaneSettingsInfo.initialValue
		)

	fun onChangeSettingsEvent(event: ChangeSettingsEvent) {
		when (event) {
			is ChangeSettingsEvent.OnFocusDurationChange -> viewModelScope.launch {
				settingsPreferences.setFocusDuration(event.duration)
			}

			is ChangeSettingsEvent.OnSessionCountChange -> viewModelScope.launch {
				settingsPreferences.setSessionCount(event.number)
			}

			is ChangeSettingsEvent.OnBreakDurationChange -> viewModelScope.launch {
				settingsPreferences.setBreakDuration(event.duration)
			}

			is ChangeSettingsEvent.IsSaveSessionAllowed -> viewModelScope.launch {
				settingsPreferences.setIsSaveSessions(event.isAllowed)
			}

			is ChangeSettingsEvent.OnReminderTimeChanged -> viewModelScope.launch {
				settingsPreferences.setReminderTime(event.time)
				val currentValue = isGoalsNotificationActive.value
				if (currentValue) reminderFacade.setGoalReminderAlarm(event.time)

			}

			ChangeSettingsEvent.ToggleReminderNotification -> viewModelScope.launch {
				// Toggled
				val isActive = isGoalsNotificationActive.value
				settingsPreferences.setIsGoalsReminderActiveness(!isActive)
				// Set ot canceled the timer
				val currentTime = reminderNotificationTime.value
				if (isActive) reminderFacade.stopAlarm()
				else reminderFacade.setGoalReminderAlarm(currentTime)
			}
		}
	}
}