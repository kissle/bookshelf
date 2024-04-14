package secondbrain.kissle.informationmanagement.adapter.out.persistence

import org.junit.jupiter.api.Test
import secondbrain.kissle.informationmanagement.adapter.out.persitence.InformationCollectionEntity


class InformationCollectionEntityTest {

    @Test
    fun `should generate Object with id equals null`() {
        val informationCollectionEntity = InformationCollectionEntity.withoutId("title")
        assert(informationCollectionEntity.id == null)
        assert(informationCollectionEntity.name == "title")
    }

    @Test
    fun `should generate Object with id equals 1L`() {
        val informationCollectionEntity = InformationCollectionEntity.withId(1L, "title")
        assert(informationCollectionEntity.id == 1L)
        assert(informationCollectionEntity.name == "title")
    }
}