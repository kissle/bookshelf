package secondbrain.kissle.informationmanagement.adapter.out.persitence

import io.smallrye.mutiny.Uni
import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject
import jakarta.persistence.PersistenceException
import secondbrain.kissle.informationmanagement.application.port.out.CreateNotePort
import secondbrain.kissle.informationmanagement.domain.Note

@ApplicationScoped
class NotePersistenceAdapter(
    @Inject
    private var repository: NoteEntityRepository
): CreateNotePort {

    val mapper: NoteEntityMapper = NoteEntityMapper()

    override fun createNote(note: Note): Uni<Note> {
        return repository.persistAndFlush(mapper.toEntity(note))
            .onItem()
            .transformToUni { entity ->
                if (entity == null)
                    Uni.createFrom().nullItem()
                else
                    Uni.createFrom().item(mapper.toDomain(entity))
            }.onItem()
            .ifNull()
            .failWith { PersistenceException("Note could not be created")}
    }
}