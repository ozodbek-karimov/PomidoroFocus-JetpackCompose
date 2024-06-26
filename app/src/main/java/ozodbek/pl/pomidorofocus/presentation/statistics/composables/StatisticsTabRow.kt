package ozodbek.pl.pomidorofocus.presentation.statistics.composables

import android.content.res.Configuration
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ozodbek.pl.pomidorofocus.presentation.statistics.utils.StatisticsType
import ozodbek.pl.pomidorofocus.ui.theme.PomidoroFocusAppTheme

@Composable
fun StatisticsTabRow(
	selectedIndex: Int,
	onTabChanged: (StatisticsType) -> Unit,
	modifier: Modifier = Modifier,
	indicatorColor: Color = MaterialTheme.colorScheme.primaryContainer,
	selectedItemColor: Color = MaterialTheme.colorScheme.onPrimaryContainer,
	unSelectedItemColor: Color = MaterialTheme.colorScheme.primary
) {
	val isSystemThemeDark = isSystemInDarkTheme()

	val options = remember {
		listOf(
			StatisticsType.All,
			StatisticsType.Weekly,
			StatisticsType.Today
		)
	}

	val transition = updateTransition(
		targetState = selectedIndex,
		label = "Tab Indicator Animation"
	)

	TabRow(
		selectedTabIndex = selectedIndex,
		modifier = modifier,
		divider = { Divider(color = MaterialTheme.colorScheme.outline) },
		indicator = { tabPositions ->
			if (selectedIndex < tabPositions.size) {
				val indicatorEnd by transition.animateDp(
					label = "Indicator End",
					transitionSpec = {
						if (initialState < targetState) {
							spring(
								dampingRatio = Spring.DampingRatioLowBouncy,
								stiffness = Spring.StiffnessLow
							)
						} else {
							spring(
								dampingRatio = Spring.DampingRatioLowBouncy,
								stiffness = Spring.StiffnessVeryLow
							)
						}
					}
				) { tabPositions[it].right }

				val indicatorStart by transition.animateDp(
					label = "Indicator Start",
					transitionSpec = {
						if (initialState > targetState) {
							spring(
								dampingRatio = Spring.DampingRatioLowBouncy,
								stiffness = Spring.StiffnessLow
							)
						} else {
							spring(
								dampingRatio = Spring.DampingRatioLowBouncy,
								stiffness = Spring.StiffnessVeryLow
							)
						}
					}
				) { tabPositions[it].left }

				val boxRadius = 20.dp.value

				val customTabOffset = Modifier
					.fillMaxWidth()
					.wrapContentWidth(align = Alignment.Start)
					.offset(x = indicatorStart)
					.width(width = indicatorEnd - indicatorStart)

				Box(
					modifier = Modifier
						.padding(bottom = 4.dp)
						.then(customTabOffset)
						.drawBehind {
							drawRoundRect(
								color = indicatorColor,
								cornerRadius = CornerRadius(boxRadius, boxRadius),
								blendMode = if (isSystemThemeDark) BlendMode.Screen
								else BlendMode.Multiply,
							)
						},
				)
			}
		},
		tabs = {
			options.forEach { option ->
				Tab(
					selected = selectedIndex == option.tabIndex,
					onClick = { onTabChanged(option) },
					text = {
						Text(
							text = stringResource(id = option.label),
							style = MaterialTheme.typography.titleSmall,
							maxLines = 2,
							overflow = TextOverflow.Ellipsis,
						)
					},
					selectedContentColor = selectedItemColor,
					unselectedContentColor = unSelectedItemColor
				)
			}
		}
	)
}


@Preview(
	uiMode = Configuration.UI_MODE_NIGHT_NO or Configuration.UI_MODE_TYPE_NORMAL
)
@Preview(
	uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL
)
@Composable
fun StatisticsTabRowPreview() = PomidoroFocusAppTheme {
	StatisticsTabRow(
		selectedIndex = 0,
		onTabChanged = { },
		modifier = Modifier.padding(4.dp)
	)
}