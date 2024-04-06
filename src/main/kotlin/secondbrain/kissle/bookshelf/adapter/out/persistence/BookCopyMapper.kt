package secondbrain.kissle.bookshelf.adapter.out.persistence

import secondbrain.kissle.bookshelf.domain.Book
import secondbrain.kissle.bookshelf.domain.BookCopy
import secondbrain.kissle.bookshelf.domain.MediumType

class BookCopyMapper {

    fun toEntity(id: Long?, bookId: Long, type: MediumType): BookCopyEntity {
        if (id == null) {
            return BookCopyEntity.withoutId(bookId, type)
        }
        return BookCopyEntity.withId(id, bookId, type)
    }

    fun toDomain(id: Long?, book: Book, type: MediumType): BookCopy {
        return BookCopy(id, book, type)
    }
}