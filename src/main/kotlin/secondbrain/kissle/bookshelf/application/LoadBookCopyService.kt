package secondbrain.kissle.bookshelf.application

import io.quarkus.hibernate.reactive.panache.common.WithSession
import io.smallrye.mutiny.Uni
import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject
import secondbrain.kissle.bookshelf.domain.BookCopy
import secondbrain.kissle.bookshelf.port.`in`.LoadBookCopyUseCase
import secondbrain.kissle.bookshelf.port.out.LoadBookCopyPort

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
        return loadPort.findById(id)
    }
}