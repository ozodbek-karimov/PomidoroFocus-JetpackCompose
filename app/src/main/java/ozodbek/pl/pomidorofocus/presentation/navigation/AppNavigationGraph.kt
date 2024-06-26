package ozodbek.pl.pomidorofocus.presentation.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import ozodbek.pl.pomidorofocus.data.services.SessionService
import ozodbek.pl.pomidorofocus.presentation.settings.SettingsScreen
import ozodbek.pl.pomidorofocus.presentation.settings.SettingsViewModel
import ozodbek.pl.pomidorofocus.presentation.statistics.StatisticsScreen
import ozodbek.pl.pomidorofocus.presentation.statistics.StatisticsViewModel
import ozodbek.pl.pomidorofocus.presentation.timer.TimerScreen
import ozodbek.pl.pomidorofocus.presentation.timer.TimerViewModel
import ozodbek.pl.pomidorofocus.presentation.utils.LocalSnackBarProvider
import ozodbek.pl.pomidorofocus.presentation.utils.UiEvents
import java.time.LocalTime

@Composable
fun AppNavigationGraph(
    service: SessionService,
    modifier: Modifier = Modifier,
    snackBarHostState: SnackbarHostState = LocalSnackBarProvider.current
) {
	val navController = rememberNavController()
	val navBackStackEntry by navController.currentBackStackEntryAsState()

	Scaffold(
		bottomBar = {
			AppBottomNavigationBar(
				onRouteSelected = { screen ->
					navController.navigate(screen.route) {
						val startDestination = navController.graph.findStartDestination()
						popUpTo(id = startDestination.id) {
							saveState = true
						}
						launchSingleTop = true
						restoreState = true
					}
				},
				isRouteSelected = { screen ->
					navBackStackEntry?.destination?.hierarchy?.any { destination ->
						destination.route == screen.route
					} == true
				}
			)
		}
	) { scPadding ->
		NavHost(
			navController = navController,
			startDestination = Screens.TimerRoute.route,
			modifier = modifier.padding(bottom = scPadding.calculateBottomPadding())
		) {
			composable(
				route = Screens.StatisticsRoute.route,
				enterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Left) },
				exitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Right) },
			) {

				val viewModel = hiltViewModel<StatisticsViewModel>()

				LaunchedEffect(Unit) {
					viewModel.uiEvents.collect { events ->
						when (events) {
							is UiEvents.ShowSnackBar -> snackBarHostState.showSnackbar(events.message)
						}
					}
				}

				val sessionHighLights by viewModel.sessionHighLight.collectAsStateWithLifecycle()
				val tabIndex by viewModel.tabIndex.collectAsStateWithLifecycle()
				val selectedModeGraph by viewModel.graphMode.collectAsStateWithLifecycle()
				val graphContent by viewModel.weeklyGraph.collectAsStateWithLifecycle()
				val deleteState by viewModel.deleteState.collectAsStateWithLifecycle()

				StatisticsScreen(
					selectedMode = selectedModeGraph,
					selectedTab = tabIndex,
					highlight = sessionHighLights,
					deleteState = deleteState,
					graphContent = graphContent,
					onTabIndexChanged = viewModel::onTabIndexChanged,
					onModeChanged = viewModel::onTimerModeChanged,
					onDeleteEvents = viewModel::onDeleteOptions
				)
			}
			composable(
				route = Screens.TimerRoute.route,
				enterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Left) },
				exitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Right) },
			) {

				val viewModel = hiltViewModel<TimerViewModel>()

				val mode by service.timerMode.collectAsStateWithLifecycle()

				val state by service.stopWatch.state
					.collectAsStateWithLifecycle()

				val elapsedTime by service.stopWatch.elapsedTime
					.collectAsStateWithLifecycle()

				val timerDuration by service.timerDuration
					.collectAsStateWithLifecycle(initialValue = LocalTime.of(0, 0, 1))

				TimerScreen(
					state = state,
					timerTime = elapsedTime,
					mode = mode,
					timerDuration = timerDuration,
					onTimerEvents = viewModel::onTimerEvents
				)
			}
			composable(
				route = Screens.SettingsRoute.route,
				enterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Left) },
				exitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Right) },
			) {
				val settingsViewModel = hiltViewModel<SettingsViewModel>()

				val focusDuration by settingsViewModel.focusDuration.collectAsStateWithLifecycle()
				val breakDuration by settingsViewModel.breakDuration.collectAsStateWithLifecycle()
				val sessionCount by settingsViewModel.sessionCount.collectAsStateWithLifecycle()
				val airplaneMode by settingsViewModel.isAirPlaneModeEnabled.collectAsStateWithLifecycle()
				val isAllowSave by settingsViewModel.isSaveSessionDataAllowed.collectAsStateWithLifecycle()
				val reminderTime by settingsViewModel.reminderNotificationTime.collectAsStateWithLifecycle()
				val isGoalsNotificationActive by settingsViewModel.isGoalsNotificationActive.collectAsStateWithLifecycle()

				SettingsScreen(
					focusDuration = focusDuration,
					breakDuration = breakDuration,
					sessionCountOption = sessionCount,
					isAirplaneModeEnabled = airplaneMode,
					isSaveAllowed = isAllowSave,
					reminderTime = reminderTime,
					isGoalsNotificationActive = isGoalsNotificationActive,
					onChangeSettings = settingsViewModel::onChangeSettingsEvent
				)
			}
		}
	}
}