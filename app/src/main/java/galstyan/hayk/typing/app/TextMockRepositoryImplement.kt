package galstyan.hayk.typing.app

import galstyan.hayk.typing.repository.TextRepository


class TextMockRepositoryImplement : TextRepository() {

	private val texts = listOf(
		"Many desktop publishing packages and web page editors now use Lorem Ipsum as their default model text",
		"All the Lorem Ipsum generators on the Internet tend to repeat predefined chunks as necessary",
		"The standard chunk of Lorem Ipsum used since the 1500s is reproduced below for those interested",
		"If you are going to use a passage of Lorem Ipsum you need to be sure there isn't anything embarrassing hidden in the middle of text",
		"Contrary to popular belief, Lorem Ipsum is not simply random text"
	)

	override suspend fun getText() = texts.random()
}