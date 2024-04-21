package secondbrain.kissle.informationmanagement.adapter.`in`.web

import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes(
    JsonSubTypes.Type(value = NoteDto::class, name = "NoteDto"),
    JsonSubTypes.Type(value = InformationCollectionDto::class, name = "InformationCollectionDto")
)
open class ComponentDto {
}