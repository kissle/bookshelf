package secondbrain.kissle.bookshelf.adapter.out.persistence

import jakarta.persistence.ElementCollection
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id

@Entity
class BookShelfEntity {

    @Id
    @GeneratedValue
    var id: Long? = null

    @ElementCollection
    var bookCopyIds: List<Long> = mutableListOf()
}