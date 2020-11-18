package galstyan.hayk.typing.app

import galstyan.hayk.typing.model.TextMatch


class TextMatcherImplement(text: CharSequence) : TextMatch(text) {

	override fun match(input: CharSequence): Int {
		var m = 0
		if (input.isNotEmpty())
			while (m < input.length && text[m] == input[m]) m++

		return m
	}
}