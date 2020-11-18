package galstyan.hayk.typing.app

import galstyan.hayk.typing.model.TextMatch
import galstyan.hayk.typing.model.TextMatcherFactory


class TextMatcherFactoryImplement : TextMatcherFactory {

	override fun create(text: CharSequence): TextMatch {
		return TextMatcherImplement(text)
	}
}