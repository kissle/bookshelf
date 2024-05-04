package secondbrain.kissle.bookshelf.adapter.out.persistence

import org.junit.jupiter.api.Test
import secondbrain.kissle.bookshelf.domain.Book
import secondbrain.kissle.bookshelf.domain.MediumType


class BookCopyTest {

    @Test
    fun `should generate Object with id equals null`() {
        val book = Book(1L, "title", "subTitle")
        val bookCopy = BookCopyEntity.withoutId(book.id!!, MediumType.HARDCOPY)

        assert(bookCopy.id == null)
        assert(bookCopy.bookId == book.id)
    }

    @Test
    fun `should generate Object with id equals 1L`() {
        val book = Book(1L, "title", "subTitle")
        val bookCopy = BookCopyEntity.withId(1L, book.id!!, MediumType.HARDCOPY)

        assert(bookCopy.id == 1L)
        assert(bookCopy.bookId == book.id)
    }
}