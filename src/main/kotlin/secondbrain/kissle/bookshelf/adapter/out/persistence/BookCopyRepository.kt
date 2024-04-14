package secondbrain.kissle.bookshelf.adapter.out.persistence

import io.quarkus.hibernate.reactive.panache.kotlin.PanacheRepository
import jakarta.enterprise.context.ApplicationScoped

@ApplicationScoped
class BookCopyRepository: PanacheRepository<BookCopyEntity>