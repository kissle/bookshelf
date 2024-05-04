package secondbrain.kissle.bookshelf.adapter.out.persistence

import org.junit.jupiter.api.Test

class BookEntityTest {

    @Test
    fun `should generate Object with id equals null`() {
        val bookEntity = BookEntity.withoutId("title", "subtitle")
        assert(bookEntity.id == null)
        assert(bookEntity.subTitle == "subtitle")
        assert(bookEntity.title == "title")
    }

    @Test
    fun `should generate Object with id equals 1L`() {
        val bookEntity = BookEntity.withId(1L, "title", "subtitle")
        assert(bookEntity.id == 1L)
        assert(bookEntity.subTitle == "subtitle")
        assert(bookEntity.title == "title")
    }
}