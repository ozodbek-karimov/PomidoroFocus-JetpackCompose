package ozodbek.pl.pomidorofocus.presentation.timer.composables

import android.content.Context
import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import ozodbek.pl.pomidorofocus.R
import ozodbek.pl.pomidorofocus.domain.stopwatch.TimerWatchStates
import ozodbek.pl.pomidorofocus.ui.theme.PomidoroFocusAppTheme
import java.time.LocalTime

@Composable
fun TimerClockStyle(
    currentTime: LocalTime,
    timerTime: LocalTime,
    state: TimerWatchStates,
    modifier: Modifier = Modifier,
    context: Context = LocalContext.current
) {
	val angle by remember(currentTime, timerTime) {
		derivedStateOf {
			val currentTimeSeconds = currentTime.toNanoOfDay().toFloat()

			val timerTimeSeconds = timerTime.toNanoOfDay()
				.coerceAtLeast(1L).toFloat()

			val angle = (timerTimeSeconds - currentTimeSeconds) / timerTimeSeconds * 360f
			angle
		}
	}

	val showTimerState by remember(state) {
		derivedStateOf { state != TimerWatchStates.IDLE }
	}

	Box(
		modifier = modifier.aspectRatio(1f),
		contentAlignment = Alignment.Center
	) {
		TimerClockDial(
			coveredAngle = angle,
			modifier = Modifier.fillMaxSize(),
			primaryDialColor = MaterialTheme.colorScheme.primaryContainer,
			coverDialColor = MaterialTheme.colorScheme.primary,
			shadowColor = MaterialTheme.colorScheme.onPrimaryContainer
		)
		AnimatedVisibility(
			visible = showTimerState,
			enter = fadeIn(),
			exit = fadeOut(),
			modifier = Modifier
				.align(Alignment.BottomCenter)
				.offset(y = (-40).dp),
		) {
			Text(
				text = when (state) {
					TimerWatchStates.RUNNING -> context.getString(R.string.timer_running)
					TimerWatchStates.PAUSED -> context.getString(R.string.timer_paused)
					TimerWatchStates.COMPLETED -> context.getString(R.string.timer_completed)
					else -> ""
				},
				style = MaterialTheme.typography.titleLarge,
				color = MaterialTheme.colorScheme.onBackground,
				fontFamily = FontFamily.Monospace
			)
		}
		TimerClockFace(
			time = currentTime,
			modifier = Modifier.align(Alignment.Center)
		)
	}
}


@Preview(
	uiMode = Configuration.UI_MODE_NIGHT_NO or Configuration.UI_MODE_TYPE_NORMAL
)
@Preview(
	uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL
)
@Composable
fun TimerClockStylePreview(
	@PreviewParameter(TimerPlayPausePreviewParams::class)
	state: TimerWatchStates
) = PomidoroFocusAppTheme {
	Surface(color = MaterialTheme.colorScheme.background) {
		TimerClockStyle(
			currentTime = LocalTime.of(0, 5),
			timerTime = LocalTime.of(0, 10),
			modifier = Modifier
				.padding(40.dp)
				.fillMaxWidth()
				.aspectRatio(1f),
			state = state
		)
	}
}