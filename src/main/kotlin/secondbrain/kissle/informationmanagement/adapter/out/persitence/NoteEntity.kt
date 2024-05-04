package secondbrain.kissle.informationmanagement.adapter.out.persitence

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id

@Entity
class NoteEntity {
    @Id
    @GeneratedValue
    var id: Long? = null
    var content: String = ""
}