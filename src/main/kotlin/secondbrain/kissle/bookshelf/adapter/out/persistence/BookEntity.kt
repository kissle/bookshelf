package secondbrain.kissle.bookshelf.adapter.out.persistence

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id

@Entity
class BookEntity {

    companion object{
         fun withId(id: Long, title: String, subtitle: String?) = BookEntity().apply {
            this.id = id
            this.title = title
            this.subTitle = subtitle
         }

        fun withoutId(title: String, subtitle: String?) = BookEntity().apply {
            this.title = title
            this.subTitle = subtitle
        }
    }

    @Id
    @GeneratedValue
    var id: Long? = null
    lateinit var title: String
    var subTitle: String? = null
}