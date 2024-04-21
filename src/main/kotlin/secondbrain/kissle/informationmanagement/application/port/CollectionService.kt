package secondbrain.kissle.informationmanagement.application.port

import io.smallrye.mutiny.Uni
import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject
import secondbrain.kissle.informationmanagement.application.port.`in`.AddNewCollectionToCollectionUseCase
import secondbrain.kissle.informationmanagement.application.port.`in`.AddNewNoteToCollectionUseCase
import secondbrain.kissle.informationmanagement.application.port.out.CreateInformationCollectionPort
import secondbrain.kissle.informationmanagement.application.port.out.CreateNotePort
import secondbrain.kissle.informationmanagement.application.port.out.LoadCollectionPort
import secondbrain.kissle.informationmanagement.application.port.out.UpdateInformationCollectionPort
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
    private var createNotePort: CreateNotePort
): AddNewCollectionToCollectionUseCase, AddNewNoteToCollectionUseCase {

    override fun addCollection(id: Long, name: String): Uni<InformationCollection> {
        return createNewCollection(name)
            .onItem().transformToUni { newCollection ->
            loadCollectionPort.findById(id).onItem().transformToUni { collection ->
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
                updateCollection(collection)
            }
        }
    }

    private fun createNewCollection(name: String): Uni<InformationCollection> {
        val newCollection = InformationCollection(null, name, false)
        return createInformationCollectionPort.createInformationCollection(newCollection)
    }

    private fun createNewNote(note: Note): Uni<Note> {
        return createNotePort.createNote(note)
            .onItem().ifNull().failWith { IllegalArgumentException("Note could not be created") }
    }

    private fun updateCollection(collection: InformationCollection): Uni<InformationCollection> {
        return updateInformationCollectionPort.update(collection)
    }
}