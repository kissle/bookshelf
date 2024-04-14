package secondbrain.kissle.informationmanagement.domain

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class InformationCollectionTest {

     @Test
     fun `should add element to collection`() {
         val collection = InformationCollection("Projects", true)
         val note = Note()

         collection.addElement(note)

         assertEquals(listOf(note), collection.getElements())
     }

     @Test
     fun `should remove element from collection`() {
         val collection = InformationCollection("Projects", true)
         val note1 = Note()
         val note2 = Note()
         collection.elements = mutableListOf(note1, note2)

         collection.removeElement(note1)

         assertEquals(1, collection.getElements().size)
         assertEquals(listOf(note2), collection.getElements())
     }

    @Test
    fun `should add collection to collection`() {
        val collection = InformationCollection("Projects", true)
        val note = Note()
        val anotherCollection = InformationCollection("Projects", true)
        anotherCollection.addElement(note)

        collection.addElement(anotherCollection)

        assertEquals(listOf(anotherCollection), collection.getElements())
    }

    @Test
    fun `should remove collection from collection`() {
        val collection = InformationCollection("Projects", true)
        val note = Note()
        val anotherCollection = InformationCollection("Projects", true)
        anotherCollection.addElement(note)
        collection.elements = mutableListOf(anotherCollection)

        collection.removeElement(anotherCollection)

        assertTrue(collection.getElements().isEmpty())
    }

    @Test
    fun `should change name`(){
        val collection = InformationCollection("Projects", true)
        collection.name = "New Name"
        assertEquals("New Name", collection.name)
    }
}