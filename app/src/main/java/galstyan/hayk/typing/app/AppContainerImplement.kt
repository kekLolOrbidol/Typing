package galstyan.hayk.typing.app

import galstyan.hayk.typing.model.AppContainer
import galstyan.hayk.typing.model.TextMatch
import galstyan.hayk.typing.model.TextMatcherFactory
import galstyan.hayk.typing.repository.Repository


class AppContainerImplement(
	private val repositories: Map<Class<out Repository>, Repository>,
	private val textMatcherFactory: TextMatcherFactory,
	private val timeToFinish: Long
) : AppContainer {


	@Suppress("UNCHECKED_CAST")
	override fun <T : Repository> getRepository(repositoryClass: Class<T>): T =
		repositories[repositoryClass] as T


	override fun textMatcherOf(text: String): TextMatch = textMatcherFactory.create(text)


	override fun getTimeToFinish(): Long = timeToFinish
}