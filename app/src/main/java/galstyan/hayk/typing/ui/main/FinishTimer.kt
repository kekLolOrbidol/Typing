package galstyan.hayk.typing.ui.main

import android.os.CountDownTimer


class FinishTimer(millisToFinish: Long) {

	private val timer = InternalTimer(millisToFinish)


	interface TimerListener {
		fun onTick(millisUntilFinished: Long)
		fun onFinish()
	}


	fun start() {
		timer.start()
	}


	fun stopGetTimeLeft(): Long {
		dispose()
		return timer.millisToFinish
	}


	fun setListener(listener: TimerListener) {
		timer.setListener(listener)
	}


	fun dispose() {
		timer.setListener(null)
		timer.cancel()
	}


	/**
	 * Why use composition over inheritance?
	 * Changing behaviour/encapsulating without violating LSP
	 */
	private class InternalTimer(
		var millisToFinish: Long
	) : CountDownTimer(millisToFinish, 1000) {
		private var _listener: TimerListener? = null

		fun setListener(listener: TimerListener?) {
			_listener = listener
		}

		override fun onFinish() {
			_listener?.onFinish()
		}

		override fun onTick(millisUntilFinished: Long) {
			millisToFinish = millisUntilFinished / 1000 * 1000 // normalize
			_listener?.onTick(millisToFinish)
		}
	}
}