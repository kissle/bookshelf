package secondbrain.kissle.informationmanagement.domain

class Note(
    override val id: Long? = null,
    val content: String
): Component() {
    override fun getComponentType(): ComponentTypes {
        return ComponentTypes.NOTE
    }
}