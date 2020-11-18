package galstyan.hayk.typing.model


/**
 * Since the length or duration of words is clearly variable,
 * for the purpose of measurement of text entry,
 * the definition of each "word" is often standardized to be five characters
 * or keystrokes long in English
 *
 * [Source](https://en.wikipedia.org/wiki/Words_per_minute#Alphanumeric_entry)
 */
const val WORD_LENGTH = 5


/**
 * Word per minute analyser
 */
class WpmAnalyzer : ResultAnalyzer<Float> {

	override fun analyze(typingResult: TypingResult): Float =
		with(typingResult) {
			progress.toFloat() / WORD_LENGTH.toFloat() /
					(elapsedMillis.toFloat() / (1000 * 60).toFloat())
		}
}