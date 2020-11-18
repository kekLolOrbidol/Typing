package galstyan.hayk.typing.model

import kotlin.math.floor


/**
 * Simple percentage analyzer
 */
class PercentageAnalyz : ResultAnalyzer<Int> {

	override fun analyze(typingResult: TypingResult): Int =
		with(typingResult) {
			floor(progress.toDouble() / textSource.length.toDouble() * 100).toInt()
		}

}