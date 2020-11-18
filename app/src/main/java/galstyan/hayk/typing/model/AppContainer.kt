package galstyan.hayk.typing.model

import galstyan.hayk.typing.repository.Repository


/**
 * Container for domain layer dependencies
 */
interface AppContainer {

	/**
	 * Get a Repository marked by the [Repository] interface
	 * Using [Class] so this can be used by java code if needed
	 */
	fun <T : Repository> getRepository(repositoryClass: Class<T>): T


	/**
	 * Get a text matcher for new text
	 */
	fun textMatcherOf(text: String): TextMatch


	/**
	 * Get time in milliseconds to finish typing
	 */
	fun getTimeToFinish(): Long

}