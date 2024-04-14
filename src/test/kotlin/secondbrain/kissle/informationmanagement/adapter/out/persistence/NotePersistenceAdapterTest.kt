package secondbrain.kissle.informationmanagement.adapter.out.persistence

import io.quarkus.test.InjectMock
import io.quarkus.test.junit.QuarkusTest
import io.quarkus.test.vertx.RunOnVertxContext
import io.quarkus.test.vertx.UniAsserter
import io.smallrye.mutiny.Uni
import jakarta.inject.Inject
import jakarta.persistence.PersistenceException
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.kotlin.anyOrNull
import secondbrain.kissle.informationmanagement.adapter.out.persitence.NoteEntity
import secondbrain.kissle.informationmanagement.adapter.out.persitence.NoteEntityRepository
import secondbrain.kissle.informationmanagement.adapter.out.persitence.NotePersistenceAdapter
import secondbrain.kissle.informationmanagement.domain.Note

@QuarkusTest
class NotePersistenceAdapterTest {

    @Inject
    private lateinit var adapter: NotePersistenceAdapter

    @InjectMock
    private lateinit var repository: NoteEntityRepository

    @Test
    @RunOnVertxContext
    fun `can persist NoteEntity`(asserter: UniAsserter) {
        asserter.execute {
            val noteEntity = NoteEntity().apply {
                id = 1L
                content = "content"
            }
            Mockito.`when`(repository.persistAndFlush(anyOrNull())).thenReturn(
                Uni.createFrom().item(noteEntity)
            )
        }
        asserter.assertNotNull { adapter.createNote(Note(1L, "content")) }
        asserter.assertEquals({ adapter.createNote(Note(1L, "content")).onItem().transformToUni { note ->
            Uni.createFrom().item(note.id)
        }}, 1L)
    }

    @Test
    @RunOnVertxContext
    fun `should throw Exception of persistence error`(asserter: UniAsserter) {
        asserter.execute {
            Mockito.`when`(repository.persistAndFlush(anyOrNull())).thenReturn(Uni.createFrom().nullItem())
        }
        asserter.assertFailedWith({
            try {
                return@assertFailedWith adapter.createNote(Note(1, "content"))
            } catch (e: Exception) {
                return@assertFailedWith Uni.createFrom().failure(e)
            }
        }, { assertEquals(PersistenceException::class.java, it.javaClass) })
    }
}