package secondbrain.kissle.informationmanagement.application.port.out

import io.smallrye.mutiny.Uni
import secondbrain.kissle.informationmanagement.domain.Note

interface CreateNotePort {
    fun createNote(note: Note): Uni<Note>
}