package secondbrain.kissle.bookshelf.adapter.out.persistence

import io.quarkus.hibernate.reactive.panache.kotlin.PanacheRepository
import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject
import org.hibernate.reactive.mutiny.Mutiny
import secondbrain.kissle.bookshelf.domain.Book

@ApplicationScoped
class BookRepository: PanacheRepository<Book> {}