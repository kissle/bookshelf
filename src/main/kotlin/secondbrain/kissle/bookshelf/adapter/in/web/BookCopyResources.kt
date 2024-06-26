package secondbrain.kissle.bookshelf.adapter.`in`.web

import jakarta.inject.Inject
import jakarta.ws.rs.GET
import jakarta.ws.rs.Path
import secondbrain.kissle.bookshelf.application.port.`in`.LoadBookCopyUseCase

@Path("/book-copies")
class BookCopyResources{

    @Inject
    private lateinit var loadBookCopyUseCase: LoadBookCopyUseCase

    @GET
    fun findAll() = loadBookCopyUseCase.findAll()

    @GET
    @Path("/{id}")
    fun findById(id: Long) = loadBookCopyUseCase.findById(id)
}