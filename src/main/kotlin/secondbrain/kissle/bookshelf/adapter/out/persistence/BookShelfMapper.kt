package secondbrain.kissle.bookshelf.adapter.out.persistence

import secondbrain.kissle.bookshelf.domain.BookCopy
import secondbrain.kissle.bookshelf.domain.BookShelf

class BookShelfMapper {

    fun toEntity(id: Long?, bookCopyIds: List<Long>): BookShelfEntity {
        val entity = BookShelfEntity()
        entity.id = id
        entity.bookCopyIds = bookCopyIds
        return entity
    }

    fun toDomain(id: Long?, bookCopies: List<BookCopy>): BookShelf {
        return BookShelf(id, bookCopies.toMutableList())
    }
}