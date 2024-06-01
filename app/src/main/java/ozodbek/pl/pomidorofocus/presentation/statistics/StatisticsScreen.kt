package ozodbek.pl.pomidorofocus.presentation.statistics

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import ozodbek.pl.pomidorofocus.R
import ozodbek.pl.pomidorofocus.domain.models.SessionHighlightModel
import ozodbek.pl.pomidorofocus.domain.models.SessionReportModel
import ozodbek.pl.pomidorofocus.domain.models.TimerModes
import ozodbek.pl.pomidorofocus.presentation.statistics.composables.DeleteStatisticsDialog
import ozodbek.pl.pomidorofocus.presentation.statistics.composables.SessionHighlights
import ozodbek.pl.pomidorofocus.presentation.statistics.composables.StatisticsGraphContainer
import ozodbek.pl.pomidorofocus.presentation.statistics.composables.StatisticsGraphHeading
import ozodbek.pl.pomidorofocus.presentation.statistics.composables.StatisticsTabRow
import ozodbek.pl.pomidorofocus.presentation.statistics.composables.StatisticsTopBarOptions
import ozodbek.pl.pomidorofocus.presentation.statistics.utils.DeleteEvents
import ozodbek.pl.pomidorofocus.presentation.statistics.utils.DeleteStatisticsState
import ozodbek.pl.pomidorofocus.presentation.statistics.utils.StatisticsType
import ozodbek.pl.pomidorofocus.presentation.utils.PreviewFakes
import ozodbek.pl.pomidorofocus.presentation.utils.ShowContent
import ozodbek.pl.pomidorofocus.ui.theme.PomidoroFocusAppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StatisticsScreen(
	selectedMode: TimerModes,
	selectedTab: StatisticsType,
	highlight: SessionHighlightModel,
	deleteState: DeleteStatisticsState,
	graphContent: ShowContent<List<SessionReportModel>>,
	onTabIndexChanged: (StatisticsType) -> Unit,
	onDeleteEvents: (DeleteEvents) -> Unit,
	onModeChanged: (TimerModes) -> Unit,
	modifier: Modifier = Modifier
) {

	if (deleteState.showDialog && deleteState.option != null) {
		DeleteStatisticsDialog(
			option = deleteState.option,
			onDelete = { type -> onDeleteEvents(DeleteEvents.OnConfirmDelete(type)) },
			onDismissRequest = { onDeleteEvents(DeleteEvents.OnUnSelect) })
	}

	Scaffold(
		topBar = {
			CenterAlignedTopAppBar(
				title = { Text(text = stringResource(id = R.string.navigation_route_statistics)) },
				actions = {
					StatisticsTopBarOptions(
						onOptionSelect = { type ->
							onDeleteEvents(DeleteEvents.OnSelect(type))
						},
					)
				}
			)
		},
		modifier = modifier
	) { scPadding ->
		Column(
			modifier = Modifier
				.padding(scPadding)
				.padding(horizontal = dimensionResource(id = R.dimen.scaffold_padding)),
		) {
			StatisticsTabRow(
				selectedIndex = selectedTab.tabIndex,
				onTabChanged = onTabIndexChanged
			)
			Spacer(
				modifier = Modifier
					.height(dimensionResource(id = R.dimen.statistics_item_spacing))
			)
			Text(
				text = stringResource(id = R.string.highlights_text),
				style = MaterialTheme.typography.titleMedium,
				color = MaterialTheme.colorScheme.onSurface
			)
			SessionHighlights(
				highlight = highlight,
				modifier = Modifier.fillMaxWidth()
			)
			Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.statistics_item_spacing)))
			StatisticsGraphHeading(
				selectedMode = selectedMode,
				onModeChange = onModeChanged,
				modifier = Modifier.fillMaxWidth()
			)
			StatisticsGraphContainer(
				showLoading = graphContent.isLoading,
				content = graphContent.content,
				shape = MaterialTheme.shapes.medium,
				modifier = Modifier
					.fillMaxSize()
					.padding(vertical = dimensionResource(R.dimen.statistics_graph_padding))
			)
		}
	}
}

@Preview(
	uiMode = Configuration.UI_MODE_NIGHT_NO or Configuration.UI_MODE_TYPE_NORMAL
)
@Preview(
	uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL
)
@Composable
fun StatisticsScreenPreview() = PomidoroFocusAppTheme {
	StatisticsScreen(
		selectedMode = TimerModes.FOCUS_MODE,
		selectedTab = StatisticsType.All,
		highlight = PreviewFakes.FAKE_HIGHLIGHT_MODE,
		graphContent = ShowContent(
			isLoading = false,
			content = PreviewFakes.FAKE_SESSION_REPORT_WEEKLY
		),
		deleteState = DeleteStatisticsState(),
		onModeChanged = {},
		onTabIndexChanged = { },
		onDeleteEvents = {}
	)
}