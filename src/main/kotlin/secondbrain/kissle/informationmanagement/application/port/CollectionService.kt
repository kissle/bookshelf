package secondbrain.kissle.informationmanagement.application.port

import io.smallrye.mutiny.Uni
import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject
import org.hibernate.reactive.mutiny.Mutiny.SessionFactory
import secondbrain.kissle.informationmanagement.adapter.`in`.web.ComponentDto
import secondbrain.kissle.informationmanagement.adapter.`in`.web.InformationCollectionDto
import secondbrain.kissle.informationmanagement.adapter.`in`.web.NoteDto
import secondbrain.kissle.informationmanagement.adapter.`in`.web.NoteDtoMapper
import secondbrain.kissle.informationmanagement.application.port.`in`.AddNewCollectionToCollectionUseCase
import secondbrain.kissle.informationmanagement.application.port.`in`.AddNewComponentToCollectionUseCase
import secondbrain.kissle.informationmanagement.application.port.`in`.AddNewNoteToCollectionUseCase
import secondbrain.kissle.informationmanagement.application.port.`in`.LoadComponentsOfCollectionUseCase
import secondbrain.kissle.informationmanagement.application.port.out.*
import secondbrain.kissle.informationmanagement.domain.Component
import secondbrain.kissle.informationmanagement.domain.InformationCollection
import secondbrain.kissle.informationmanagement.domain.Note

@ApplicationScoped
class CollectionService(
    @Inject
    private var loadCollectionPort: LoadCollectionPort,
    @Inject
    private var createInformationCollectionPort: CreateInformationCollectionPort,
    @Inject
    private var updateInformationCollectionPort: UpdateInformationCollectionPort,
    @Inject
    private var createNotePort: CreateNotePort,
    @Inject
    private var loadComponentsOfCollectionPort: LoadComponentsOfCollectionPort
): AddNewCollectionToCollectionUseCase, AddNewNoteToCollectionUseCase, AddNewComponentToCollectionUseCase, LoadComponentsOfCollectionUseCase {

    private val noteDtoMapper = NoteDtoMapper()
    @Inject
    private lateinit var sessionFactory: SessionFactory

    override fun addComponent(id: Long, component: ComponentDto): Uni<InformationCollection> {
        return when (component) {
            is NoteDto -> addNote(id, noteDtoMapper.toDomain(component))
            is InformationCollectionDto -> addCollection(id, component.name)
            else -> throw IllegalArgumentException("Unknown component type")
        }
    }

    override fun addCollection(id: Long, name: String): Uni<InformationCollection> {
        return createNewCollection(name)
            .onItem().transformToUni { newCollection ->
                loadCollectionPort.findById(id)
                .onItem().transformToUni { collection ->
                collection.addElement(newCollection)
                updateCollection(collection)
            }
        }
    }

    override fun addNote(id: Long, note: Note): Uni<InformationCollection> {
        return createNewNote(note)
            .onItem().transformToUni { newNote ->
            loadCollectionPort.findById(id).onItem().transformToUni { collection ->
                collection.addElement(newNote)
                updateCollection( collection)
            }
        }
    }

    private fun createNewCollection(name: String): Uni<InformationCollection> {
        val newCollection = InformationCollection(null, name, false)
        return sessionFactory.withSession { createInformationCollectionPort.createInformationCollection(newCollection) }
    }

    private fun createNewNote(note: Note): Uni<Note> {
        return sessionFactory.withSession { createNotePort.createNote(note) }
    }

    private fun updateCollection(collection: InformationCollection): Uni<InformationCollection> {
        return updateInformationCollectionPort.update(collection)
    }

    override fun loadComponents(id: Long): Uni<List<Component>> {
        return loadComponentsOfCollectionPort.loadComponents(id)
    }
}