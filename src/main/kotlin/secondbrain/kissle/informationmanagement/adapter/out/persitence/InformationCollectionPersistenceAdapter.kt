package secondbrain.kissle.informationmanagement.adapter.out.persitence

import io.smallrye.mutiny.Uni
import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject
import jakarta.persistence.PersistenceException
import secondbrain.kissle.informationmanagement.application.port.out.CreateInformationCollectionPort
import secondbrain.kissle.informationmanagement.domain.InformationCollection

@ApplicationScoped
class InformationCollectionPersistenceAdapter(
    @Inject
    private var repository: InformationCollectionRepository
): CreateInformationCollectionPort {

    override fun createInformationCollection(collection: InformationCollection): Uni<InformationCollection> {
        return repository.persistAndFlush(InformationCollectionMapper.toEntity(collection))
            .onItem()
            .transformToUni { newEntity ->
                if (newEntity == null)
                    Uni.createFrom().nullItem()
                else
                    Uni.createFrom().item(InformationCollectionMapper.toDomain(newEntity))
            }
            .onItem()
            .ifNull()
            .failWith { PersistenceException("Information Collection could not be created.") }
    }
}