package galstyan.hayk.typing.repository

import galstyan.hayk.typing.model.TypingResult


abstract class HistoryRepository : Repository {

	abstract suspend fun saveResult(typingResult: TypingResult)

	abstract suspend fun getResultList(): List<TypingResult>

}