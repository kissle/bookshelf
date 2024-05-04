package secondbrain.kissle.informationmanagement.application.port.out

import io.smallrye.mutiny.Uni
import secondbrain.kissle.informationmanagement.domain.Note

interface LoadNotePort {
    fun findById(id: Long): Uni<Note>
}