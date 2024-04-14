package secondbrain.kissle.informationmanagement.adapter.out.persitence

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id

@Entity
class InformationCollectionEntity {

    companion object {
        fun withId(id: Long, name: String) = InformationCollectionEntity().apply {
            this.id = id
            this.name = name
        }

        fun withoutId(name: String) = InformationCollectionEntity().apply {
            this.name = name
        }
    }

    @Id
    @GeneratedValue
    var id: Long? = null
    var name: String = "New Collection"
}