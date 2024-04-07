package secondbrain.kissle.bookshelf.domain

import org.junit.jupiter.api.Test

class BookTest {

    @Test
    fun `should create book`() {
        val book = Book(1L, "title", "subTitle")
        assert(book.id == 1L)
        assert(book.title == "title")
        assert(book.subTitle == "subTitle")
    }
}