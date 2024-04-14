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
import secondbrain.kissle.informationmanagement.adapter.out.persitence.InformationCollectionEntity
import secondbrain.kissle.informationmanagement.adapter.out.persitence.InformationCollectionPersistenceAdapter
import secondbrain.kissle.informationmanagement.adapter.out.persitence.InformationCollectionRepository
import secondbrain.kissle.informationmanagement.domain.InformationCollection

@QuarkusTest
class InformationCollectionPersistenceAdapterTest {

    @Inject
    lateinit var adapter: InformationCollectionPersistenceAdapter

    @InjectMock
    lateinit var repository: InformationCollectionRepository

    @Test
    @RunOnVertxContext
    fun `should create a new information collection`(asserter: UniAsserter) {
        asserter.execute {
            val collectionEntity = InformationCollectionEntity.withId(1L, "New Collection")
            Mockito.`when`(repository.persistAndFlush(anyOrNull())).thenReturn(Uni.createFrom().item(collectionEntity))
        }
        asserter.assertNotNull {
            val collection = InformationCollection(1, "New Collection", false)
            adapter.createInformationCollection(collection)
        }
    }

    @Test
    @RunOnVertxContext
    fun `should throw Exception of persistence error`(asserter: UniAsserter) {
        asserter.execute {
            Mockito.`when`(repository.persistAndFlush(anyOrNull())).thenReturn(Uni.createFrom().nullItem())
        }
        asserter.assertFailedWith({
            try {
                return@assertFailedWith adapter.createInformationCollection(InformationCollection(1, "New Collection", false))
            } catch (e: Exception) {
                return@assertFailedWith Uni.createFrom().failure(e)
            }
        }, { assertEquals(PersistenceException::class.java, it.javaClass)})
    }
}