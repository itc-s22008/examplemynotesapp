package jp.ac.it_college.std.s22008.examplemynotesapp

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.PersistableBundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import jp.ac.it_college.std.s22008.examplemynotesapp.Memo.kt.Memo
import jp.ac.it_college.std.s22008.examplemynotesapp.MemoManager.sortMemoList



class MainActivity : AppCompatActivity() {

    private lateinit var editTextTitle: EditText
    private lateinit var editTextContent: EditText
    private lateinit var listViewMemo: ListView
    private lateinit var memoAdapter: ArrayAdapter<Memo>

/*
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val button:Button = findViewById(R.id.button)

        button.setOnClickListener {
            clickHandlerFunction(it)
        }
    }

 */



    //ボタンの処理
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val button:Button = findViewById(R.id.buttonAddMemo)

        button.setOnClickListener{
            memoAdapter.notifyDataSetChanged()
            true
            showAddMemoDialog()


        }


        //onclick
        editTextTitle = findViewById(R.id.editTextTitle)
        editTextContent = findViewById(R.id.editTextContent)
        listViewMemo = findViewById(R.id.listViewMemo)
        R.id.dialogEditTextTitle
        memoAdapter = ArrayAdapter<Memo>(
                this,
                android.R.layout.simple_list_item_1,
                //受け渡し
                MemoManager.getMemoList()
        )

        listViewMemo.adapter = memoAdapter



        listViewMemo.setOnItemClickListener { _, _, position, _ ->
            showMemoOptionsDialog(memoAdapter.getItem(position))
            /*
            memoAdapter.notifyDataSetChanged()
            true
             */

            listViewMemo.adapter = memoAdapter

        }
    }

            /*
            class MainActivity : AppCompatActivity() {

                override fun onCreate(savedInstanceState: Bundle?) {
                    super.onCreate(savedInstanceState)
                    setContentView(R.layout.activity_main)

                    fun getMemoList(): List<Memo> {
                   return memoList.toList()
    }
             */


    //ボタンの処理
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_add_memo -> {
                showAddMemoDialog()

                val button:Button = findViewById(R.id.buttonAddMemo)

                button.setOnClickListener{
                    memoAdapter.notifyDataSetChanged()
                    true
                    showAddMemoDialog()


                }
                    //return true
            }




            //sortMemo
            R.id.menu_sort_list -> {
               sortMemoList()
                val button:Button = findViewById(R.id.buttonSortList)

                button.setOnClickListener{
                    memoAdapter.notifyDataSetChanged()
                    true


                }
                    // return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showAddMemoDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Add Memo")

        val inputLayout = layoutInflater.inflate(R.layout.dialog_add_memo, null)
        val dialogEditTextTitle = inputLayout.findViewById<EditText>(R.id.dialogEditTextTitle)
        val dialogEditTextContent = inputLayout.findViewById<EditText>(R.id.dialogEditTextContent)

        builder.setView(inputLayout)

        builder.setPositiveButton("Add") { _, _ ->
            val title = dialogEditTextTitle.text.toString()
            val content = dialogEditTextContent.text.toString()
            MemoManager.addMemo(title, content)
            memoAdapter.notifyDataSetChanged()
            true
        }

        builder.setNegativeButton("Cancel") { dialog, _ ->
            dialog.cancel()
        }

        builder.show()
    }

    private fun showMemoOptionsDialog(selectedMemo: Memo?) {
        val options = arrayOf("Edit Title", "Delete")
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Memo Options")

        builder.setItems(options) { _, which ->
            when (which) {
                0 ->
                    showEditTitleDialog(selectedMemo)
                1 -> {
                    selectedMemo?.let {
                        MemoManager.deleteMemo(it.id)
                        memoAdapter.notifyDataSetChanged()
                    }
                }
            }
        }

        builder.show()
    }

    @SuppressLint("MissingInflatedId")
    private fun showEditTitleDialog(selectedMemo: Memo?) {
        selectedMemo?.let {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Edit Title")

            val inputLayout = layoutInflater.inflate(R.layout.dialog_edit_title, null)
            val dialogEditTextTitle = inputLayout.findViewById<EditText>(R.id.dialogEditTextTitle)
            dialogEditTextTitle.setText(selectedMemo.title)
            memoAdapter.notifyDataSetChanged()
            true

            builder.setView(inputLayout)

            builder.setPositiveButton("Save") { _, _ ->
                memoAdapter.notifyDataSetChanged()
                true
                showAddMemoDialog()
                showAddMemoDialog()
                val newTitle = dialogEditTextTitle.text.toString()
                MemoManager.updateMemoTitle(selectedMemo.id, newTitle)

            }

            builder.setNegativeButton("Cancel") { dialog, _ ->
                dialog.cancel()
            }

            builder.show()
        }
    }
}
