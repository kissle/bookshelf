package secondbrain.kissle.informationmanagement.adapter.`in`.web

import com.fasterxml.jackson.annotation.JsonCreator

data class InformationCollectionDto @JsonCreator constructor(
    val name: String
): ComponentDto()