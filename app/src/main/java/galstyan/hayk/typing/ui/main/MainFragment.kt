package galstyan.hayk.typing.ui.main

import android.os.Bundle
import android.text.Spannable
import android.text.style.CharacterStyle
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import galstyan.hayk.typing.R
import galstyan.hayk.typing.model.PercentageAnalyz
import galstyan.hayk.typing.model.ResultAnalyzer
import galstyan.hayk.typing.model.TypingResult
import galstyan.hayk.typing.model.WpmAnalyzer
import galstyan.hayk.typing.ui.AppBaseFragment
import galstyan.hayk.typing.ui.getColor
import kotlinx.android.synthetic.main.main_fragment.*
import java.text.SimpleDateFormat
import java.util.*


class MainFragment(viewModelFactory: ViewModelProvider.Factory) :
	AppBaseFragment(viewModelFactory) {

	private val timeFormat = SimpleDateFormat("mm:ss", Locale.getDefault())

	private val progressStyle by lazy { ForegroundColorSpan(getColor(R.color.colorAccent)) }
	private val errorStyle by lazy { ForegroundColorSpan(getColor(R.color.colorError)) }

	private val viewModel by viewModels<MainViewModel> { viewModelFactory }

	private val percentageAnalyzer: ResultAnalyzer<Int> = PercentageAnalyz()
	private val wpmAnalyzer: ResultAnalyzer<Float> = WpmAnalyzer()


	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		return inflater.inflate(R.layout.main_fragment, container, false)
	}


	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		input.requestFocus()

		history.setOnClickListener {
			viewModel.getHistory().observe(viewLifecycleOwner, Observer {
				showHistory(it)
			})
		}

		viewModel.onNewTextObservable.observe(viewLifecycleOwner, Observer {
			time.setTextColor(getColor(R.color.colorPrimary))
			output.setText(it, TextView.BufferType.SPANNABLE)

			input.doOnTextChanged { s, _, _, _ ->
				run {
					viewModel.onTextChanged(s ?: "")
					applyStyleSpan(s, errorStyle, start = viewModel.progress)
					applyStyleSpan(output.text, progressStyle, end = viewModel.progress)
				}
			}
		})

		viewModel.onTimeChangedObservable.observe(viewLifecycleOwner, Observer {
			time?.text = timeFormat.format(it)
		})

		viewModel.onFinishedObservable.observe(viewLifecycleOwner, Observer {
			time?.setTextColor(getColor(R.color.colorError))
			this@MainFragment.onFinish(it)
		})
	}

	private fun showHistory(list: List<TypingResult>) {
		val fragment = HistoryFragment.newInstance()
		fragment.show(childFragmentManager, "")
		fragment.setListProvider(object : HistoryFragment.ListProvider {
			override fun provideList(): List<TypingResult> {
				return list
			}
		})
	}


	private fun onFinish(result: TypingResult) {
		val context = context ?: return

		val percentage = percentageAnalyzer.analyze(result)
		val wpm = wpmAnalyzer.analyze(result)

		val body = getString(R.string.result_stats_body, percentage, wpm)
		val head = resources.getStringArray(R.array.result_stats_head)[0]

		AlertDialog.Builder(context).apply {
			setTitle(head)
			setMessage(body)
			setPositiveButton(android.R.string.ok, null)
			setOnDismissListener { viewModel.loadNewText() }
			create()
			show()
		}

		input.setText("")
	}


	private fun applyStyleSpan(
		chars: CharSequence?,
		span: CharacterStyle,
		start: Int = 0,
		end: Int = chars?.length ?: 0
	) {
		(chars as Spannable).apply {
			getSpans(start, end, span::class.java).forEach { removeSpan(it) }
			setSpan(span, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
		}
	}
}

