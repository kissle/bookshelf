package secondbrain.kissle.notes.domain

class Book(
    val id: Long? = null,
    val bookId: Long,
    val noteIds: MutableList<Long>
) {
    companion object {
        fun createWithoutId(bookId: Long, noteIds: List<Long>?): Book {
            return Book(
                bookId = bookId,
                noteIds = noteIds!!.toMutableList()
            )
        }

        fun createWithId(id: Long?, bookId: Long, noteIds: List<Long>?): Book {
            return Book(
                id = id,
                bookId = bookId,
                noteIds = noteIds!!.toMutableList()
            )
        }
    }
}