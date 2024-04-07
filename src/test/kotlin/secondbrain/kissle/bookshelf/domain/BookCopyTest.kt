package secondbrain.kissle.bookshelf.domain

import org.junit.jupiter.api.Test

class BookCopyTest {

    @Test
    fun `should generate Object with properties`() {
        val book = Book(1L, "title", "subTitle")
        val bookCopy = BookCopy(1L, book, MediumType.HARDCOPY)

        assert(bookCopy.id == 1L)
        assert(bookCopy.book == book)
        assert(bookCopy.mediumType == MediumType.HARDCOPY)
    }
}