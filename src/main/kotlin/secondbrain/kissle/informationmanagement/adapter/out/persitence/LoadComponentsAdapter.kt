package secondbrain.kissle.informationmanagement.adapter.out.persitence

import io.smallrye.mutiny.Uni
import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject
import org.hibernate.reactive.mutiny.Mutiny
import secondbrain.kissle.informationmanagement.application.port.out.LoadComponentsOfCollectionPort
import secondbrain.kissle.informationmanagement.domain.Component
import secondbrain.kissle.informationmanagement.domain.ComponentTypes
import secondbrain.kissle.informationmanagement.domain.InformationCollection
import secondbrain.kissle.informationmanagement.domain.Note

@ApplicationScoped
class LoadComponentsAdapter(
    @Inject private var noteRepository: NoteEntityRepository,
    @Inject private var collectionRepository: InformationCollectionEntityRepository
    ) : LoadComponentsOfCollectionPort {

    @Inject
    private lateinit var sessionFactory: Mutiny.SessionFactory

    override fun loadComponents(id: Long): Uni<List<Component>> {
        return loadCollection(id).onItem().transformToUni { collectionEntity ->
            val collectionIds = collectionEntity.elements.filter { it.componentType == ComponentTypes.INFORMATION_COLLECTION }.map { it.componentId }
            val noteIds = collectionEntity.elements.filter { it.componentType == ComponentTypes.NOTE }.map { it.componentId }
            val collectionsUni: Uni<List<InformationCollection>> = loadCollections(collectionIds)
            val noteUni = loadNotes(noteIds)

            Uni.combine().all().unis(collectionsUni, noteUni).asTuple().onItem()
                .transformToUni { tuple ->
                    val collections = tuple.item1
                    val notes = tuple.item2
                    val result: MutableList<Component> = mutableListOf()
                    result.addAll(collections)
                    result.addAll(notes)
                    Uni.createFrom().item(result)
                }
        }
    }

    private fun loadCollection(id: Long): Uni<InformationCollectionEntity> {
        return sessionFactory.withSession {
                collectionRepository.findById(id)
        }
    }

    private fun loadCollections(ids: List<Long>): Uni<List<InformationCollection>> {
        return sessionFactory.withSession {
            collectionRepository.list("id IN ?1", ids).onItem().transform {
                elements -> elements.map { InformationCollectionMapper.toDomain(it, emptyList()) }
            }
        }
    }

    private fun loadNotes(ids: List<Long>): Uni<List<Note>> {
        return sessionFactory.withSession {
            noteRepository.list("id IN ?1", ids).onItem().transform { items ->
                items.map { NoteEntityMapper().toDomain(it) }
            }
        }
    }
}
