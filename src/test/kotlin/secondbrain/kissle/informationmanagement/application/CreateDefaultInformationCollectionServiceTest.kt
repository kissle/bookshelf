package secondbrain.kissle.informationmanagement.application

import io.quarkus.test.InjectMock
import io.quarkus.test.junit.QuarkusTest
import io.quarkus.test.vertx.RunOnVertxContext
import io.quarkus.test.vertx.UniAsserter
import io.smallrye.mutiny.Uni
import jakarta.inject.Inject
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.kotlin.anyOrNull
import secondbrain.kissle.informationmanagement.application.port.CreateDefaultInformationCollectionService
import secondbrain.kissle.informationmanagement.application.port.out.CreateInformationCollectionPort
import secondbrain.kissle.informationmanagement.domain.InformationCollection
import secondbrain.kissle.informationmanagement.domain.PARAEnum

@QuarkusTest
class CreateDefaultInformationCollectionServiceTest {

    @Inject
    lateinit var service: CreateDefaultInformationCollectionService

    @InjectMock
    lateinit var createInformationCollectionPort: CreateInformationCollectionPort

    val projectsCollection = InformationCollection(1L, PARAEnum.PROJECTS.label, true)

    @Test
    @RunOnVertxContext
    fun `can create PARA informationCollections`(asserter: UniAsserter) {
        asserter.execute {
            Mockito.`when`(createInformationCollectionPort.createInformationCollection(anyOrNull()))
                .thenReturn(
                    Uni.createFrom().item(projectsCollection)
                )
        }
        asserter.assertEquals({
            service.createDefault().onItem().transformToUni { informationCollections ->
                Uni.createFrom().item(informationCollections)}
        }, null)
    }
}