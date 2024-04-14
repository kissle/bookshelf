package secondbrain.kissle.informationmanagement.domain

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class NoteTest {

     @Test
     fun `should throw exception when adding element to note`() {
         val note = Note()
         val anotherNote = Note()

         assertThrows<UnsupportedOperationException> {
             note.addElement(anotherNote)
         }
     }

     @Test
     fun `should throw exception when removing element from note`() {
         val note = Note()
         val anotherNote = Note()

         assertThrows<UnsupportedOperationException> {
             note.removeElement(anotherNote)
         }
     }

     @Test
     fun `should throw exception when getting elements from note`() {
         val note = Note()

         assertThrows<UnsupportedOperationException> {
             note.getElements()
         }
     }
}