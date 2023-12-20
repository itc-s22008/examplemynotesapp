package jp.ac.it_college.std.s22008.examplemynotesapp


import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.EditText
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var notesListView: ListView
    private lateinit var notesEditText: EditText
    private lateinit var notesList: MutableList<String>
    private lateinit var notesAdapter: NotesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        notesListView = findViewById(R.id.notesListView)
        notesEditText = findViewById(R.id.notesEditText)

        // 保存されたメモをロードする
        val sharedPreferences = getPreferences(Context.MODE_PRIVATE)
        notesList = sharedPreferences.getStringSet("notes", mutableSetOf())?.toMutableList() ?: mutableListOf()

        notesAdapter = NotesAdapter(notesList)
        notesListView.adapter = notesAdapter

    }

    fun onAddButtonClick(view: View) {
        val note = notesEditText.text.toString()
        if (note.isNotEmpty()) {
            notesList.add(note)
            notesAdapter.notifyDataSetChanged()

            // メモを保存する
            val sharedPreferences = getPreferences(Context.MODE_PRIVATE)
            sharedPreferences.edit().putStringSet("notes", notesList.toSet()).apply()

            notesEditText.text.clear()
        }
    }

    inner class NotesAdapter(private val notes: List<String>) : BaseAdapter() {

        override fun getCount(): Int {
            return notes.size
        }

        override fun getItem(position: Int): Any {
            return notes[position]
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val view = convertView ?: LayoutInflater.from(this@MainActivity).inflate(R.layout.note_item, parent, false)
            val noteTextView = view.findViewById<TextView>(R.id.noteTextView)
            noteTextView.text = notes[position]
            return view
        }
    }
}
