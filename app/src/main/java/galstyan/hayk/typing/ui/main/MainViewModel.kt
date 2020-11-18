package galstyan.hayk.typing.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import galstyan.hayk.typing.model.AppContainer
import galstyan.hayk.typing.model.TextMatch
import galstyan.hayk.typing.model.TypingResult
import galstyan.hayk.typing.repository.HistoryRepository
import galstyan.hayk.typing.repository.TextRepository
import galstyan.hayk.typing.ui.AppViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class MainViewModel(appContainer: AppContainer) : AppViewModel(appContainer) {

	val onNewTextObservable: LiveData<String> get() = _onNewTextObservable
	private val _onNewTextObservable: MutableLiveData<String> = MutableLiveData()

	val onTimeChangedObservable: LiveData<Long> get() = _onTimeChangedObservable
	private val _onTimeChangedObservable: MutableLiveData<Long> = MutableLiveData()

	val onFinishedObservable: LiveData<TypingResult> get() = _onFinishedObservable
	private val _onFinishedObservable: MutableLiveData<TypingResult> = MutableLiveData()

	private val repoText = appContainer.getRepository(TextRepository::class.java)
	private val repoHistory = appContainer.getRepository(HistoryRepository::class.java)

	private val limitMillis = appContainer.getTimeToFinish()

	private val timerListener = object : FinishTimer.TimerListener {
		override fun onTick(millisUntilFinished: Long) {
			_onTimeChangedObservable.value = millisUntilFinished
		}

		override fun onFinish() {
			finish()
		}
	}

	var progress: Int = 0

	private lateinit var text: String
	private lateinit var match: TextMatch
	private var timer: FinishTimer? = null


	init {
		loadNewText()
	}


	fun getHistory(): LiveData<List<TypingResult>> {
		val callback = MutableLiveData<List<TypingResult>>()
		viewModelScope.launch {
			callback.value = repoHistory.getResultList()
		}
		return callback
	}


	fun loadNewText(timeLimitMillis: Long = limitMillis) {
		disposeTimer()
		viewModelScope.launch {
			text = repoText.getText()
			match = appContainer.textMatcherOf(text)
			_onNewTextObservable.value = text
			timer = FinishTimer(timeLimitMillis)
			timer!!.setListener(timerListener)
			timer!!.start()
		}
	}


	fun onTextChanged(inputText: CharSequence) {
		progress = match.match(inputText)
		if (progress == text.length) finish()
	}


	private fun finish() {
		val timeLeft = timer?.stopGetTimeLeft() ?: 0
		val result = TypingResult(
			textSource = text,
			textInput = text.substring(0, progress),
			progress = progress,
			elapsedMillis = limitMillis - timeLeft
		)
		_onFinishedObservable.value = result

		persistResult(result)
	}


	private fun persistResult(result: TypingResult) {
		// using a non android scope since this is not a lifecycle dependant operation
		// and needs to finish anyway
		GlobalScope.launch {
			repoHistory.saveResult(result)
		}
	}


	private fun disposeTimer() {
		timer?.dispose()
	}


	override fun onCleared() {
		super.onCleared()
		disposeTimer()
	}
}
