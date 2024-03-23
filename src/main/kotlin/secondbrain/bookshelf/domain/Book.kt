package secondbrain.bookshelf.domain

import io.quarkus.hibernate.reactive.panache.kotlin.PanacheCompanion
import io.quarkus.hibernate.reactive.panache.kotlin.PanacheEntity
import jakarta.persistence.Cacheable
import jakarta.persistence.Entity

@Entity
@Cacheable
class Book: PanacheEntity() {
    companion object : PanacheCompanion<Book> {}

    var title: String? = null
    var subTitle: String? = null
}