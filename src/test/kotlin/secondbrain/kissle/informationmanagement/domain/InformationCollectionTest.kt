package secondbrain.kissle.informationmanagement.domain

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class InformationCollectionTest {

     @Test
     fun `should add element to collection`() {
         val collection = InformationCollection(null , "Projects", true)
         val note = Note(1L, "content")

         collection.addElement(note)

         assertEquals(listOf(note), collection.elements)
     }

     @Test
     fun `should remove element from collection`() {
         val collection = InformationCollection(1L,"Projects", true)
         val note1 = Note(1L, "content")
         val note2 = Note(2L, "content")
         collection.elements = mutableListOf(note1, note2)

         collection.removeElement(note1)

         assertEquals(1, collection.elements.size)
         assertEquals(listOf(note2), collection.elements)
     }

    @Test
    fun `should add collection to collection`() {
        val collection = InformationCollection(1L,"Projects", true)
        val note = Note(1L, "content")
        val anotherCollection = InformationCollection(1L,"Projects", true)
        anotherCollection.addElement(note)

        collection.addElement(anotherCollection)

        assertEquals(listOf(anotherCollection), collection.elements)
    }

    @Test
    fun `should remove collection from collection`() {
        val collection = InformationCollection(1L,"Projects", true)
        val note = Note(1L, "content")
        val anotherCollection = InformationCollection(1L,"Projects", true)
        anotherCollection.addElement(note)
        collection.elements = mutableListOf(anotherCollection)

        collection.removeElement(anotherCollection)

        assertTrue(collection.elements.isEmpty())
    }

    @Test
    fun `should change name`(){
        val collection = InformationCollection(1L,"Projects", true)
        collection.name = "New Name"
        assertEquals("New Name", collection.name)
    }
}