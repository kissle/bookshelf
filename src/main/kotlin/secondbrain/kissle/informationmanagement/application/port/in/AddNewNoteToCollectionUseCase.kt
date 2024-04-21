package secondbrain.kissle.informationmanagement.application.port.`in`

import io.smallrye.mutiny.Uni
import secondbrain.kissle.informationmanagement.domain.InformationCollection
import secondbrain.kissle.informationmanagement.domain.Note

interface AddNewNoteToCollectionUseCase {
    fun addNote(id: Long, note: Note): Uni<InformationCollection>
}