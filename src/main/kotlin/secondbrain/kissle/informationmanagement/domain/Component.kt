package secondbrain.kissle.informationmanagement.domain

abstract class Component{
    internal var elements: MutableList<Component> = mutableListOf()


    open fun addElement(element: Component) {
        throw UnsupportedOperationException("This component cannot handle this operation")
    }

    open fun removeElement(element: Component) {
        throw UnsupportedOperationException("This component cannot handle this operation")
    }

    open fun getElements(): List<Component> {
        throw UnsupportedOperationException("This component cannot handle this operation")
    }
}