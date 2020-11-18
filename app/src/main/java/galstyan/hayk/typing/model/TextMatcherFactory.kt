package galstyan.hayk.typing.model


interface TextMatcherFactory {

	fun create(text: CharSequence): TextMatch
}