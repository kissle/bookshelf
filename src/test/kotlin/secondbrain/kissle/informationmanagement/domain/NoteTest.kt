package secondbrain.kissle.informationmanagement.domain

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class NoteTest {

     @Test
     fun `should throw exception when adding element to note`() {
         val note = Note(1L, "content")
         val anotherNote = Note(2L, "another content")

         assertThrows<UnsupportedOperationException> {
             note.addElement(anotherNote)
         }
     }

     @Test
     fun `should throw exception when removing element from note`() {
         val note = Note(1L, "content")
         val anotherNote = Note(2L, "another content")

         assertThrows<UnsupportedOperationException> {
             note.removeElement(anotherNote)
         }
     }

     @Test
     fun `should throw exception when getting elements from note`() {
         val note = Note(1L, "content")

         assertThrows<UnsupportedOperationException> {
             note.getElements()
         }
     }
}