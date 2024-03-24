package secondbrain.kissle.bookshelf.adapter.`in`.web

import jakarta.inject.Inject
import jakarta.ws.rs.GET
import jakarta.ws.rs.POST
import jakarta.ws.rs.Path
import secondbrain.kissle.bookshelf.port.`in`.BookCopyDto
import secondbrain.kissle.bookshelf.port.`in`.CreateBookCopyUseCase
import secondbrain.kissle.bookshelf.port.`in`.LoadBookCopyUseCase

@Path("/book-copies")
class BookCopyResources{

    @Inject
    private lateinit var createBookCopyUseCase: CreateBookCopyUseCase

    @Inject
    private lateinit var loadBookCopyUseCase: LoadBookCopyUseCase

    @GET
    fun findAll() = loadBookCopyUseCase.findAll()

    @GET
    @Path("/{id}")
    fun findById(id: Long) = loadBookCopyUseCase.findById(id)

    @POST
    fun create(bookCopy: BookCopyDto) = createBookCopyUseCase.create(bookCopy)
}