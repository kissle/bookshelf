package secondbrain.kissle.neo4j

import jakarta.inject.Inject
import jakarta.ws.rs.POST
import jakarta.ws.rs.Path

@Path("/booknodes")
class BookNodeResource {

    @Inject
    lateinit var bookNodeRepository: BookNodeRepository

    @POST
    fun create() {
        val book = BookNode().apply { title = "Der Herr der Ringe" }
        bookNodeRepository.create(book)
    }
}