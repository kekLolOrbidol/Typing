package galstyan.hayk.typing.app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import galstyan.hayk.typing.R
import galstyan.hayk.typing.model.RecordModel
import galstyan.hayk.typing.model.TypingResult
import galstyan.hayk.typing.repository.HistoryRepository
import io.paperdb.Paper
import kotlinx.android.synthetic.main.activity_records.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RecordsActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_records)
        GlobalScope.launch(Dispatchers.IO) {
            val records = Paper.book().read("typing_result_list", ArrayList<TypingResult>())
            val newRecords = mutableListOf<RecordModel>()
            for (record in records) {
                Log.e("Record", record.toString())
                newRecords.add(RecordModel(record.elapsedMillis))
            }
            withContext(Dispatchers.Main){
                recv_records.layoutManager = LinearLayoutManager(this@RecordsActivity)
                recv_records.adapter = MyAdapter(newRecords)
            }
        }
    }
}