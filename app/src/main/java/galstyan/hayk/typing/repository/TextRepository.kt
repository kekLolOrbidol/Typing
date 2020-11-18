package galstyan.hayk.typing.repository


abstract class TextRepository : Repository {

	abstract suspend fun getText(): String
}