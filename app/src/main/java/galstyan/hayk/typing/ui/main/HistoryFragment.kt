package galstyan.hayk.typing.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.RecyclerView
import galstyan.hayk.typing.R
import galstyan.hayk.typing.model.PercentageAnalyz
import galstyan.hayk.typing.model.ResultAnalyzer
import galstyan.hayk.typing.model.TypingResult
import galstyan.hayk.typing.model.WpmAnalyzer
import kotlinx.android.synthetic.main.history_fragment.*


class HistoryFragment : DialogFragment() {


	private val percentageAnalyzer: ResultAnalyzer<Int> = PercentageAnalyz()
	private val wpmAnalyzer: ResultAnalyzer<Float> = WpmAnalyzer()

	private lateinit var listProvider: ListProvider


	companion object {
		@JvmStatic
		fun newInstance() = HistoryFragment()
	}


	interface ListProvider {
		fun provideList(): List<TypingResult>
	}


	fun setListProvider(listProvider: ListProvider) {
		this.listProvider = listProvider
	}


	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		return inflater.inflate(R.layout.history_fragment, container)
	}


	class VH(itemView: View) : RecyclerView.ViewHolder(itemView) {
		val label: TextView by lazy { itemView.findViewById<TextView>(R.id.item_history_label) }
	}


	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		val list = listProvider.provideList()
		recycler.adapter = object : RecyclerView.Adapter<VH>() {
			override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
				return VH(
					LayoutInflater.from(parent.context)
						.inflate(R.layout.item_history, parent, false)
				)
			}

			override fun onBindViewHolder(holder: VH, position: Int) {
				val result = list[position]
				val percentage = percentageAnalyzer.analyze(result)
				val wpm = wpmAnalyzer.analyze(result)
				val body = getString(R.string.result_stats_body, percentage, wpm)
				holder.label.text = body
			}

			override fun getItemCount(): Int = list.size
		}
	}
}