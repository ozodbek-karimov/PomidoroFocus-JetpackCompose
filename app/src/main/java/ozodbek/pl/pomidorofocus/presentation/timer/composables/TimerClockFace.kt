package ozodbek.pl.pomidorofocus.presentation.timer.composables

import android.content.res.Configuration
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.ContentTransform
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ozodbek.pl.pomidorofocus.ui.theme.PromptFontFamily
import ozodbek.pl.pomidorofocus.ui.theme.PomidoroFocusAppTheme
import java.text.NumberFormat
import java.time.LocalTime

@Composable
fun TimerClockFace(
	time: LocalTime,
	modifier: Modifier = Modifier,
	hourTextStyle: TextStyle = MaterialTheme.typography.displayLarge,
	minuteTextStyle: TextStyle = MaterialTheme.typography.displayLarge,
	separatorStyle: TextStyle = MaterialTheme.typography.displaySmall,
	secondsTextStyle: TextStyle = MaterialTheme.typography.displayLarge,
	hourColor: Color = MaterialTheme.colorScheme.onPrimaryContainer,
	minutesColor: Color = MaterialTheme.colorScheme.onSecondaryContainer,
	secondsColor: Color = MaterialTheme.colorScheme.onTertiaryContainer,
	separatorColor: Color = MaterialTheme.colorScheme.tertiary,
	fontFamily: FontFamily = PromptFontFamily,
) {
	val numberFormatter = remember { NumberFormat.getInstance() }

	val paddingCharacter = remember { numberFormatter.format(0).first() }

	Row(
		modifier = modifier,
		horizontalArrangement = Arrangement.Center,
		verticalAlignment = Alignment.CenterVertically
	) {
		AnimatedContent(
			targetState = time.hour,
			label = "Hour Text Animation",
			transitionSpec = { incrementAnimation() },
		) { hour ->
			Text(
				text = numberFormatter.format(hour).padStart(2, paddingCharacter),
				style = hourTextStyle,
				color = hourColor,
				fontFamily = fontFamily,
			)
		}
		Text(
			text = ":",
			style = separatorStyle,
			color = separatorColor,
			modifier = Modifier.padding(horizontal = 2.dp)
		)
		AnimatedContent(
			targetState = time.minute,
			label = "Minute Text Animation",
			transitionSpec = { incrementAnimation() },
		) { minutes ->
			Text(
				text = numberFormatter.format(minutes).padStart(2, paddingCharacter),
				style = minuteTextStyle,
				color = minutesColor,
				fontFamily = fontFamily,
			)
		}
		Text(
			text = ":",
			style = separatorStyle,
			color = separatorColor,
			modifier = Modifier.padding(horizontal = 2.dp)
		)
		AnimatedContent(
			targetState = time.second,
			label = "Seconds Text Animation",
			transitionSpec = { incrementAnimation() },
		) { seconds ->
			Text(
				text = numberFormatter.format(seconds).padStart(2, paddingCharacter),
				style = secondsTextStyle,
				color = secondsColor,
				fontFamily = fontFamily,
			)
		}
	}

}

private fun AnimatedContentTransitionScope<Int>.incrementAnimation(): ContentTransform {
	return if (targetState > initialState) {
		slideInVertically { height -> height } + fadeIn() togetherWith slideOutVertically { height -> -height } + fadeOut()
	} else {
		slideInVertically { height -> -height } + fadeIn() togetherWith slideOutVertically { height -> height } + fadeOut()
	}
}

@Preview(
	uiMode = Configuration.UI_MODE_NIGHT_NO or Configuration.UI_MODE_TYPE_NORMAL
)
@Preview(
	uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL,
)
@Composable
fun TimerClockFacePreview() = PomidoroFocusAppTheme {
	Surface(color = MaterialTheme.colorScheme.background) {
		TimerClockFace(
			time = LocalTime.of(1, 10),
			modifier = Modifier.padding(10.dp),
		)
	}
}