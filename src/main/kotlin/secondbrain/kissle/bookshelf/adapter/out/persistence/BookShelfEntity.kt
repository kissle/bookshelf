package secondbrain.kissle.bookshelf.adapter.out.persistence

import jakarta.persistence.ElementCollection
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id

@Entity
class BookShelfEntity {

    companion object {
        fun withoutId(bookCopyIds: List<Long>): BookShelfEntity {
            val entity = BookShelfEntity()
            entity.bookCopyIds = bookCopyIds
            return entity
        }

        fun withId(id: Long, bookCopyIds: List<Long>): BookShelfEntity {
            val entity = BookShelfEntity()
            entity.id = id
            entity.bookCopyIds = bookCopyIds
            return entity
        }
    }

    @Id
    @GeneratedValue
    var id: Long? = null

    @ElementCollection
    var bookCopyIds: List<Long> = mutableListOf()
}