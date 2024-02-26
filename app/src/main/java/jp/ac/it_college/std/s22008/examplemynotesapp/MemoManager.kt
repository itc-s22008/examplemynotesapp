package jp.ac.it_college.std.s22008.examplemynotesapp

import jp.ac.it_college.std.s22008.examplemynotesapp.Memo.kt.Memo

object MemoManager {
    private val memoList = mutableListOf<Memo>()

    fun addMemo(title: String, content: String) {
        val newId = memoList.size + 1
        val newMemo = Memo(newId, title, content)
        memoList.add(newMemo)
    }

    fun getMemoList(): List<Memo> {
//      return memoList.toList()
        return memoList
    }

    fun updateMemoTitle(id: Int, newTitle: String) {
        val memo = memoList.find { it.id == id }
        memo?.title = newTitle
    }

    fun deleteMemo(id: Int) {
        memoList.removeAll { it.id == id }
    }

    fun sortMemoList() {
        memoList.sortBy { it.title }
    }
}
