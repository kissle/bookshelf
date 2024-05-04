package secondbrain.kissle.informationmanagement.adapter.`in`.web

import com.fasterxml.jackson.annotation.JsonCreator

data class NoteDto @JsonCreator constructor(
    val content: String
) : ComponentDto()
