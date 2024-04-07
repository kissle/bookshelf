package secondbrain.kissle.bookshelf.adapter.out.persistence

import org.junit.jupiter.api.Test
import secondbrain.kissle.bookshelf.domain.Book

class BookMapperTest {

    @Test
    fun `should map from entity to domain`() {
        val bookEntity = BookEntity.withId(1L, "title", "subtitle")
        val mapper = BookMapper()
        val book = mapper.toDomain(bookEntity)

        assert(book.javaClass == Book::class.java)
        assert(book.id == 1L)
        assert(book.title == "title")
        assert(book.subTitle == "subtitle")
    }

    @Test
    fun `should map from domain to entity`() {
        val book = Book(1L, "title", "subtitle")
        val mapper = BookMapper()
        val bookEntity = mapper.toEntity(book)

        assert(bookEntity.javaClass == BookEntity::class.java)
        assert(bookEntity.id == 1L)
        assert(bookEntity.title == "title")
        assert(bookEntity.subTitle == "subtitle")
    }
}