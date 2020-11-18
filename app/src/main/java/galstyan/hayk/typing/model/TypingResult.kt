package galstyan.hayk.typing.model


data class TypingResult(
	val textSource: String,
	val textInput: String,
	val progress: Int,
	val elapsedMillis: Long
)