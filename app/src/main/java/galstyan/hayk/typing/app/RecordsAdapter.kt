package galstyan.hayk.typing.app

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import galstyan.hayk.typing.R
import galstyan.hayk.typing.model.RecordModel

class MyAdapter(private val myDataset: List<RecordModel>) :
    RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
            = MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.records_item, parent, false))

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.bind(myDataset[position])
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = myDataset.size

    inner class MyViewHolder(itemView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) {
        private val record = itemView.findViewById<TextView>(R.id.text_view_record)
        fun bind(item: RecordModel) {
            record.text = item.record.toString()
        }
    }
}