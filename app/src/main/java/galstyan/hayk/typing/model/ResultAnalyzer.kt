package galstyan.hayk.typing.model


interface ResultAnalyzer<R> {

	fun analyze(typingResult: TypingResult): R

}