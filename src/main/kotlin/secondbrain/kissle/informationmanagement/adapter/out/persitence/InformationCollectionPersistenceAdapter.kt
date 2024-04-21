package secondbrain.kissle.informationmanagement.adapter.out.persitence

import io.smallrye.mutiny.Uni
import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject
import jakarta.persistence.PersistenceException
import org.hibernate.reactive.mutiny.Mutiny
import secondbrain.kissle.informationmanagement.application.port.out.CreateInformationCollectionPort
import secondbrain.kissle.informationmanagement.application.port.out.LoadCollectionPort
import secondbrain.kissle.informationmanagement.application.port.out.UpdateInformationCollectionPort
import secondbrain.kissle.informationmanagement.domain.InformationCollection
import secondbrain.kissle.informationmanagement.application.port.out.LoadComponentsOfCollectionPort

@ApplicationScoped
class InformationCollectionPersistenceAdapter(
    @Inject
    private var collectionRepository: InformationCollectionEntityRepository,
    @Inject
    private var loadComponentsOfCollectionPort: LoadComponentsOfCollectionPort
): CreateInformationCollectionPort,
    LoadCollectionPort,
    UpdateInformationCollectionPort {

    @Inject
    private lateinit var sessionFactory: Mutiny.SessionFactory

    override fun createInformationCollection(collection: InformationCollection): Uni<InformationCollection> {
        return collectionRepository.persistAndFlush(InformationCollectionMapper.toEntity(collection))
            .onItem()
            .transformToUni { newEntity ->
                if (newEntity == null)
                    Uni.createFrom().nullItem()
                else
                    Uni.createFrom().item(InformationCollectionMapper.toDomain(newEntity, emptyList()))
            }
            .onItem()
            .ifNull()
            .failWith { PersistenceException("InformationCollection could not be created.") }
    }

    override fun create(collections: List<InformationCollection>): Uni<Void> {
        return collectionRepository.persist(collections.map {InformationCollectionMapper.toEntity(it)})
    }

    override fun findById(id: Long): Uni<InformationCollection> {
        return sessionFactory.withSession { collectionRepository.findById(id) }
            .onItem().transformToUni {
            entity ->
            if (entity == null) {
                Uni.createFrom().nullItem()
            }
            else {
                loadComponentsOfCollectionPort.loadComponents(id).onItem().transform { elements ->
                    InformationCollectionMapper.toDomain(entity, elements)
                }
            }
        }
    }

    override fun update(collection: InformationCollection): Uni<InformationCollection> {
        if (collection.id == null)
            throw IllegalArgumentException("Collection must have an ID to be updated")

        val newCollectionEntity = InformationCollectionMapper.toEntity(collection)

        return sessionFactory.withTransaction { session ->
            session.merge(newCollectionEntity)
        }.onItem().transformToUni {
            entity ->
            if (entity == null) {
                Uni.createFrom().nullItem()
            } else {
                loadComponentsOfCollectionPort.loadComponents(entity.id!!).onItem().transform {
                    elements -> InformationCollectionMapper.toDomain(entity, elements)
                }
            }
        }
    }
}