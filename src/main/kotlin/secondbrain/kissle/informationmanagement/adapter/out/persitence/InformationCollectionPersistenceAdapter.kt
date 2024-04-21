package secondbrain.kissle.informationmanagement.adapter.out.persitence

import io.smallrye.mutiny.Uni
import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject
import jakarta.persistence.PersistenceException
import secondbrain.kissle.informationmanagement.application.port.out.CreateInformationCollectionPort
import secondbrain.kissle.informationmanagement.application.port.out.LoadCollectionPort
import secondbrain.kissle.informationmanagement.application.port.out.UpdateInformationCollectionPort
import secondbrain.kissle.informationmanagement.domain.Component
import secondbrain.kissle.informationmanagement.domain.ComponentTypes
import secondbrain.kissle.informationmanagement.domain.InformationCollection
import secondbrain.kissle.informationmanagement.application.port.`in`.LoadComponentsOfCollectionUseCase

@ApplicationScoped
class InformationCollectionPersistenceAdapter(
    @Inject
    private var collectionRepository: InformationCollectionRepository,
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
                loadComponentsOfCollectionUseCase.loadComponents(id).onItem().transform { elements ->
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
                loadComponentsOfCollectionUseCase.loadComponents(entity.id!!).onItem().transform {
                    elements -> InformationCollectionMapper.toDomain(entity, elements)
                }
            }
        }
    }

    private fun loadElements(elements: List<ComponentEntity>): Uni<MutableList<Component>> {
        if (elements.isEmpty())
            return Uni.createFrom().item(mutableListOf())

        val unis: MutableList<Uni<Component>> = mutableListOf()

        elements.forEach {
            element ->
            unis.add(loadComponent(element))
        }

        val builder = Uni.join().builder<Component>()

        unis.forEach { itemUni -> builder.add(itemUni) }

        return builder.joinAll().andFailFast()
    }

    fun loadComponent(component: ComponentEntity): Uni<Component> {
        return when (component.componentType) {
            ComponentTypes.INFORMATION_COLLECTION -> loadInformationCollection(component)
            ComponentTypes.NOTE -> loadNote(component)
        }
    }

    fun loadInformationCollection(component: ComponentEntity): Uni<Component> {
        log.debug("Loading InformationCollection with id ${component.componentId}")
        return Uni.createFrom().item(InformationCollection(component.componentId, "", false))
    }

    fun loadNote(component: ComponentEntity): Uni<Component> {
        log.debug("Loading Note with id ${component.componentId}")
        return Uni.createFrom().item(Note(component.componentId, ""))
    }
}