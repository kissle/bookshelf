package secondbrain.kissle.informationmanagement.adapter.`in`.web

import secondbrain.kissle.informationmanagement.domain.Note

class NoteDtoMapper {

    fun toDomain(noteDto: NoteDto): Note {
        return Note(
            content = noteDto.content
        )
    }
}