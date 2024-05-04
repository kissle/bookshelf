package secondbrain.kissle.bookshelf.domain

import org.junit.jupiter.api.Test

class BookShelfTest {

    @Test
    fun `should create bookshelf`() {
        val copy = BookCopy(1L, Book(1L, "title", "subTitle"), MediumType.HARDCOPY)
        val bookShelf = BookShelf(1L, mutableListOf(copy))
        assert(bookShelf.id == 1L)
        assert(bookShelf.bookCopies.size == 1)
        assert(bookShelf.bookCopies[0] == copy)
    }
}