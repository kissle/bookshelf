package secondbrain.kissle.bookshelf.adapter.out.persistence

import jakarta.persistence.*
import secondbrain.kissle.bookshelf.domain.MediumType

@Entity
class BookCopyEntity {

    companion object {
        fun withId(id: Long, bookId: Long, mediumType: MediumType) = BookCopyEntity().apply {
            this.id = id
            this.bookId = bookId
            this.type = mediumType
        }

        fun withoutId(bookId: Long, mediumType: MediumType) = BookCopyEntity().apply {
            this.bookId = bookId
            this.type = mediumType
        }
    }

    @Id
    @GeneratedValue
    var id: Long? = null
    var bookId: Long? = null
    @Enumerated(EnumType.STRING)
    lateinit var type: MediumType
}