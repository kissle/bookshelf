package secondbrain.kissle.informationmanagement.adapter.out.persistence

import org.junit.jupiter.api.Test
import secondbrain.kissle.informationmanagement.adapter.out.persitence.NoteEntity
import secondbrain.kissle.informationmanagement.adapter.out.persitence.NoteEntityMapper
import secondbrain.kissle.informationmanagement.domain.Note

class NoteEntityMapperTest {

    @Test
    fun `can map Note to NoteEntity`() {
        val note = Note(
            id = 1,
            content = "content"
        )
        val noteEntity = NoteEntityMapper().toEntity(note)
        assert(noteEntity.id == 1L)
        assert(noteEntity.content == "content")
    }

    @Test
    fun `can map entity to Note`() {
        val noteEntity = NoteEntity().apply {
            id = 1
            content = "content"
        }
        val note = NoteEntityMapper().toDomain(noteEntity)
        assert(note.id == 1L)
        assert(note.content == "content")
    }
}