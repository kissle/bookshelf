package secondbrain.kissle.informationmanagement.adapter.out.persitence

import io.smallrye.mutiny.Uni
import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject
import jakarta.persistence.PersistenceException
import secondbrain.kissle.informationmanagement.application.port.out.CreateInformationCollectionPort
import secondbrain.kissle.informationmanagement.application.port.out.LoadCollectionPort
import secondbrain.kissle.informationmanagement.application.port.out.UpdateInformationCollectionPort
import secondbrain.kissle.informationmanagement.domain.Component
import secondbrain.kissle.informationmanagement.domain.InformationCollection

@ApplicationScoped
class InformationCollectionPersistenceAdapter(
    @Inject
    private var repository: InformationCollectionRepository
): CreateInformationCollectionPort,
    LoadCollectionPort,
    UpdateInformationCollectionPort {

    override fun createInformationCollection(collection: InformationCollection): Uni<InformationCollection> {
        return repository.persistAndFlush(InformationCollectionMapper.toEntity(collection))
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
        return repository.persist(collections.map {InformationCollectionMapper.toEntity(it)})
    }

    override fun findById(id: Long): Uni<InformationCollection> {
        return repository.findById(id).onItem().transformToUni {
            entity ->
            if (entity == null) {
                Uni.createFrom().nullItem()
            }
            else {
                loadElements(entity.elements).onItem().transformToUni {
                    elements -> Uni.createFrom().item(InformationCollectionMapper.toDomain(entity, elements))
                }
            }
        }.onItem().ifNull()
            .failWith { NoSuchElementException("Collection with id $id could not be found") }
    }

    override fun update(collection: InformationCollection): Uni<InformationCollection> {
        if (collection.id == null)
            throw IllegalArgumentException("Collection must have an ID to be updated")

        val newCollectionEntity = InformationCollectionMapper.toEntity(collection)

        return repository.findById(collection.id).onItem().transformToUni {
            existingEntity ->
            existingEntity.name = newCollectionEntity.name
            existingEntity.elements = newCollectionEntity.elements
            repository.persistAndFlush(existingEntity)
                .onItem().transformToUni { newEntity ->
                    if (newEntity == null) {
                        Uni.createFrom().nullItem()
                    }
                    else {
                        loadElements(newEntity.elements).onItem().transformToUni {
                            elements -> Uni.createFrom().item(InformationCollectionMapper.toDomain(newEntity, elements))
                        }
                    }
                }
                .onItem()
                .ifNull()
                .failWith { NoSuchElementException("Collection with id ${collection.id} could not be found") }
        }
    }

    private fun loadElements(elements: List<ComponentEntity>): Uni<List<Component>> {
        if (elements.isEmpty())
            return Uni.createFrom().item(emptyList())

        val unis = elements.map { element ->
//            when (element.componentType) {
//                ComponentTypes.INFORMATION_COLLECTION -> repository.findById(element.componentId)
//                else -> Uni.createFrom().nullItem()
//            }
            repository.findById(element.componentId).onItem().transformToUni { entity ->
                Uni.createFrom().item(ComponentMapper().toDomain(entity))
            }
        }

        val builder = Uni.join().builder<Component>()

        unis.forEach { itemUni -> builder.add(itemUni) }

        return builder.joinAll().andFailFast()
    }
}