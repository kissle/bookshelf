package secondbrain.kissle.informationmanagement.adapter.out.persitence

import jakarta.persistence.*

@Entity
class InformationCollectionEntity {

    companion object {
        fun withId(id: Long, name: String, elements: List<ComponentEntity>) = InformationCollectionEntity().apply {
            this.id = id
            this.name = name
            this.elements = elements
        }

        fun withoutId(name: String) = InformationCollectionEntity().apply {
            this.name = name
        }
    }

    @Id
    @GeneratedValue
    var id: Long? = null
    var name: String = "New Collection"

    @ElementCollection(fetch = FetchType.EAGER)
    var elements: List<ComponentEntity> = emptyList()
}