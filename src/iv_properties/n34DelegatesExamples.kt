package iv_properties

import util.TODO
import util.doc34
import kotlin.reflect.KProperty

class LazyPropertyUsingDelegates(val initializer: () -> Int) {
    val lazyValue: Int by Delegate(initializer)
}

class Delegate(val initializer: () -> Int) {

    var initialized: Boolean = false
        private set

    var value: Int = 0
        private set

    operator fun getValue(lazyPropertyUsingDelegates: LazyPropertyUsingDelegates, property: KProperty<*>): Int {
        if (!initialized) {
            value = initializer()
            initialized = true
        }
        return value
    }

}

fun todoTask34(): Lazy<Int> = TODO(
    """
        Task 34.
        Read about delegated properties and make the property lazy by using delegates.
    """,
    documentation = doc34()
)
