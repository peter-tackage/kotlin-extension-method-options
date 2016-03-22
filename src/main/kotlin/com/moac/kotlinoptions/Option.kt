package com.moac.kotlinoptions

/*
 * A collection of extension methods that add a monad behavior to Kotlin nullable types.
 */

/**
 * Returns the non-null value or throws the given {@link Throwable}.
 */
fun <T> T?.getOrThrow(throwable: Throwable): T {
    return this ?: throw throwable
}

/**
 * Returns true if the value is non-null, false otherwise.
 */
fun <T> T?.isSome(): Boolean {
    return !isNone()
}

/**
 * Returns true if the value is null, false otherwise.
 */
fun <T> T?.isNone(): Boolean {
    return this == null
}

/**
 * Performs the given action, if the value is non-null.
 */
inline fun <T> T?.ifSome(action: (T) -> Unit): Unit {
    this?.let(action)
}

/**
 * Performs the given action, if the value is null.
 */
inline fun <T> T?.ifNone(action: () -> Unit): Unit {
    this ?: action.invoke()
}

/**
 * If the value is non-null, and the value matches the given predicate, return the value, otherwise return a null value.
 */
inline fun <T> T?.filter(filtering: (T) -> Boolean): T? {
    return this?.let { value -> if (filtering.invoke(value)) this else null }
}

/**
 * If the value is non-null, map to a non-nullable value according to the given function, otherwise return a null value.
 */
inline fun <T, R : Any> T?.map(mapping: (T) -> R): R? {
    return this?.let { mapping.invoke(it) }
}

/**
 * If the value is non-null, map the value to a nullable value according to the given function, otherwise return a null value.
 */
inline fun <T, R> T?.flatMap(flatmapping: (T) -> R?): R? {
    return this?.let { flatmapping.invoke(it) }
}

/**
 * Returns the value if it is non-null, otherwise return a nullable value according to the given function.
 */
inline fun <T> T?.orNullable(func: () -> T?): T? {
    return this ?: func.invoke()
}

/**
 * Returns the value if it non-null, otherwise return the non-null value produced by the given function.
 */
inline fun <T> T?.orElse(func: () -> T): T {
    return this ?: func.invoke()
}

/**
 * Returns the result of the combination function, if this and the given values are non-null.
 */
inline fun <T : Any, R : Any> T?.combine(other: T?, bothSome: (T, T) -> R): R? {
    return this?.let { it1 -> other.map { it2 -> bothSome.invoke(it1, it2) } }
}

/**
 * If non-null, map using the given function, otherwise return the value from the alternate function.
 */
inline fun <T, R : Any> T?.match(some: (T) -> R, none: () -> R): R {
    return this?.map(some) ?: none.invoke()
}

/**
 * If non-null, perform the given action, otherwise perform the alternate action.
 */
inline fun <T> T?.matchAction(some: (T) -> Unit, none: () -> Unit) {
    this?.map(some) ?: none.invoke()
}
