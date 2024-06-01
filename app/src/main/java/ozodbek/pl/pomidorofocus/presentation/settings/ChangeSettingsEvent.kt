package ozodbek.pl.pomidorofocus.presentation.settings

import ozodbek.pl.pomidorofocus.domain.models.DurationOption
import ozodbek.pl.pomidorofocus.domain.models.SessionNumberOption
import java.time.LocalTime

sealed interface ChangeSettingsEvent {
	data class OnFocusDurationChange(val duration: DurationOption) : ChangeSettingsEvent
	data class OnBreakDurationChange(val duration: DurationOption) : ChangeSettingsEvent
	data class OnSessionCountChange(val number: SessionNumberOption) : ChangeSettingsEvent
	data class IsSaveSessionAllowed(val isAllowed: Boolean) : ChangeSettingsEvent
	data class OnReminderTimeChanged(val time: LocalTime) : ChangeSettingsEvent
	data object ToggleReminderNotification : ChangeSettingsEvent
}