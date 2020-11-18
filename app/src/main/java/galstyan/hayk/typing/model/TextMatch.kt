package galstyan.hayk.typing.model


abstract class TextMatch(protected val text: CharSequence) {

	/**
	 * @return index of the matching [input] end in [text]
	 */
	abstract fun match(input: CharSequence): Int

}