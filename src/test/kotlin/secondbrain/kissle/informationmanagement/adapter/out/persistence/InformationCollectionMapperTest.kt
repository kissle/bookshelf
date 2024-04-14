package secondbrain.kissle.informationmanagement.adapter.out.persistence

import org.junit.jupiter.api.Test
import secondbrain.kissle.informationmanagement.adapter.out.persitence.InformationCollectionEntity
import secondbrain.kissle.informationmanagement.adapter.out.persitence.InformationCollectionMapper
import secondbrain.kissle.informationmanagement.domain.InformationCollection

class InformationCollectionMapperTest {

    @Test
    fun `should map InformationCollection to InformationCollectionEntity`() {
        val informationCollection = InformationCollection(1L, "Projects", true)
        val informationCollectionEntity = InformationCollectionMapper.toEntity(informationCollection)
        assert(informationCollectionEntity.id == 1L)
        assert(informationCollectionEntity.name == "Projects")
    }

    @Test
    fun `should map InformationCollection to InformationCollectionEntity with id null`() {
        val informationCollection = InformationCollection(null, "Projects", true)
        val informationCollectionEntity = InformationCollectionMapper.toEntity(informationCollection)
        assert(informationCollectionEntity.id == null)
        assert(informationCollectionEntity.name == "Projects")
    }

    @Test
    fun `should map InformationCollectionEntity to InformationCollection`() {
        val informationCollectionEntity = InformationCollectionEntity.withId(1L, "Projects")
        val informationCollection = InformationCollectionMapper.toDomain(informationCollectionEntity)
        assert(informationCollection.id == 1L)
        assert(informationCollection.name == "Projects")
        assert(!informationCollection.isPermanent)
    }
}