package ozodbek.pl.pomidorofocus.presentation.settings

import android.content.res.Configuration
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ozodbek.pl.pomidorofocus.R
import ozodbek.pl.pomidorofocus.domain.models.DurationOption
import ozodbek.pl.pomidorofocus.domain.models.SessionNumberOption
import ozodbek.pl.pomidorofocus.presentation.settings.composables.otherSettings
import ozodbek.pl.pomidorofocus.presentation.settings.composables.setNotificationSettings
import ozodbek.pl.pomidorofocus.presentation.settings.composables.setSessionSettings
import ozodbek.pl.pomidorofocus.ui.theme.PomidoroFocusAppTheme
import java.time.LocalTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
	focusDuration: DurationOption,
	breakDuration: DurationOption,
	sessionCountOption: SessionNumberOption,
	isSaveAllowed: Boolean,
	isAirplaneModeEnabled: Boolean,
	reminderTime: LocalTime,
	isGoalsNotificationActive: Boolean,
	onChangeSettings: (ChangeSettingsEvent) -> Unit,
	modifier: Modifier = Modifier
) {

	val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

	Scaffold(
		topBar = {
			CenterAlignedTopAppBar(
				title = { Text(text = stringResource(id = R.string.navigation_route_settings)) },
				scrollBehavior = scrollBehavior
			)
		},
		modifier = modifier
	) { scPadding ->
		LazyColumn(
			contentPadding = scPadding,
			verticalArrangement = Arrangement.spacedBy(8.dp),
			modifier = Modifier
				.fillMaxSize()
				.nestedScroll(scrollBehavior.nestedScrollConnection)
				.padding(horizontal = dimensionResource(id = R.dimen.scaffold_padding))
		) {
			setSessionSettings(
				focusDuration = focusDuration,
				breakDuration = breakDuration,
				isSaveAllowed = isSaveAllowed,
				onChangeSettings = onChangeSettings
			)
			setNotificationSettings(
				isGoalReminderActive = isGoalsNotificationActive,
				reminderTime = reminderTime,
				sessionCountOption = sessionCountOption,
				onChangeSettings = onChangeSettings
			)
			otherSettings(
				isAirplaneModeEnabled = isAirplaneModeEnabled
			)
		}
	}
}


@RequiresApi(Build.VERSION_CODES.O)
@Preview(
	uiMode = Configuration.UI_MODE_NIGHT_NO or Configuration.UI_MODE_TYPE_NORMAL
)
@Preview(
	uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL
)
@Composable
fun SettingsScreenPreview() = PomidoroFocusAppTheme {
	SettingsScreen(
		focusDuration = DurationOption.TEN_MINUTES,
		breakDuration = DurationOption.TEN_MINUTES,
		sessionCountOption = SessionNumberOption.TWO_TIMES,
		isAirplaneModeEnabled = false,
		reminderTime = LocalTime.of(1, 0),
		onChangeSettings = {},
		isSaveAllowed = true,
		isGoalsNotificationActive = false
	)
}