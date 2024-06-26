package ozodbek.pl.pomidorofocus.presentation.settings.composables

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import ozodbek.pl.pomidorofocus.R
import ozodbek.pl.pomidorofocus.domain.models.SessionNumberOption
import ozodbek.pl.pomidorofocus.presentation.settings.ChangeSettingsEvent
import java.time.LocalTime

fun LazyListScope.setNotificationSettings(
	reminderTime: LocalTime,
	isGoalReminderActive: Boolean,
	sessionCountOption: SessionNumberOption,
	onChangeSettings: (ChangeSettingsEvent) -> Unit,
) {
	item(key = R.string.session_notification_title) {
		Text(
			text = stringResource(id = R.string.session_notification_title),
			style = MaterialTheme.typography.titleLarge,
			color = MaterialTheme.colorScheme.onSurface,
			modifier = Modifier
				.padding(vertical = dimensionResource(id = R.dimen.settings_heading_padding))
		)
	}
	item(key = R.string.daily_goal_title) {
		SessionOptionNumber(
			title = stringResource(id = R.string.daily_goal_title),
			selected = sessionCountOption,
			onSessionNumberChange = { number ->
				onChangeSettings(ChangeSettingsEvent.OnSessionCountChange(number))
			},
		)
	}
	item(key = R.string.reminder_time) {
		SettingsReminderOption(
			title = stringResource(id = R.string.reminder_time),
			subtitle = stringResource(id = R.string.reminder_time_desc),
			time = reminderTime,
			onTimeChanged = {
				onChangeSettings(ChangeSettingsEvent.OnReminderTimeChanged(it))
			},
		)
	}
	item(key = R.string.notification_active) {
		SettingsSwitchOptions(
			title = stringResource(id = R.string.notification_active),
			subtitle = stringResource(id = R.string.notification_active_subtitle),
			isChecked = isGoalReminderActive,
			onCheckChange = { onChangeSettings(ChangeSettingsEvent.ToggleReminderNotification) },
		)
	}
}