package secondbrain.kissle.bookshelf.adapter.out.persistence

import io.quarkus.hibernate.reactive.panache.kotlin.PanacheRepository
import jakarta.enterprise.context.ApplicationScoped
import secondbrain.kissle.bookshelf.domain.BookShelf

@ApplicationScoped
class BookShelfRepository: PanacheRepository<BookShelf> {
}