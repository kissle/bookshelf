package secondbrain.kissle.informationmanagement.adapter.out.persistence

import org.junit.jupiter.api.Test
import secondbrain.kissle.informationmanagement.adapter.out.persitence.NoteEntity

class NoteEntityTest {

    @Test
    fun `can create NoteEntity`() {
        val noteEntity = NoteEntity()
        assert(noteEntity.id == null)
        assert(noteEntity.content == "")
    }
}