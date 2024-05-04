package secondbrain.kissle.informationmanagement.adapter.out.persitence

import secondbrain.kissle.informationmanagement.domain.Note

class NoteEntityMapper {

    fun toEntity(note: Note): NoteEntity {
        return NoteEntity().apply {
            id = note.id
            content = note.content
        }
    }

    fun toDomain(noteEntity: NoteEntity): Note {
        return Note(
            id = noteEntity.id,
            content = noteEntity.content
        )
    }
}