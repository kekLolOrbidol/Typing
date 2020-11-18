package galstyan.hayk.typing.app

import galstyan.hayk.typing.model.TypingResult
import galstyan.hayk.typing.repository.HistoryRepository
import io.paperdb.Paper


/**
 * Just a fast and dirty implementation, sorry I don't have much time :)
 */
class HistoryLocalRepositoryImplement : HistoryRepository() {

	private val key = "typing_result_list"


	override suspend fun saveResult(typingResult: TypingResult) {
		val list = Paper.book().read<ArrayList<TypingResult>>(key, ArrayList())
		list.add(typingResult)
		Paper.book().write(key, list)
	}


	override suspend fun getResultList(): List<TypingResult> {
		return Paper.book().read(key, ArrayList())
	}
}