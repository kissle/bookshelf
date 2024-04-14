package secondbrain.kissle.informationmanagement.domain

class InformationCollection(
    val id: Long?,
    var name: String,
    val isPermanent: Boolean
) : Component() {

    override fun addElement(element: Component) {
        elements.add(element)
    }

    override fun removeElement(element: Component) {
        elements.remove(element)
    }

    override fun getElements(): List<Component> {
        return elements
    }
}