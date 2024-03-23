package secondbrain.bookshelf.application

import io.quarkus.hibernate.reactive.panache.common.WithSession
import io.smallrye.mutiny.Uni
import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject
import secondbrain.bookshelf.domain.BookCopy
import secondbrain.bookshelf.port.`in`.LoadBookCopyUseCase
import secondbrain.bookshelf.port.out.LoadBookCopyPort

@ApplicationScoped
class LoadBookCopyService: LoadBookCopyUseCase {

    @Inject
    private lateinit var loadPort: LoadBookCopyPort
    @WithSession
    override fun findAll(): Uni<List<BookCopy>> {
        return loadPort.findAll()
    }
    @WithSession
    override fun findById(id: Long): Uni<BookCopy?> {
        return loadPort.findById(id).onItem()
            .ifNull().failWith { NoSuchElementException("Book Copy with id $id could not be found.") }
    }
}