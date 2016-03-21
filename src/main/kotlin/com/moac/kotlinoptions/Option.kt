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
inline fun <T> T?.ifSome(a: (T) -> Unit): Unit {
    this?.let(a)
}

/**
 * Performs the given action, if the value is null.
 */
inline fun <T> T?.ifNone(a: () -> Unit): Unit {
    this ?: a.invoke()
}

/**
 * If the value is non-null, and the value matches the given predicate, return the value, otherwise return a null value.
 */
inline fun <T> T?.filter(f: (T) -> Boolean): T? {
    return this?.let { value -> if (f.invoke(value)) this else null }
}

/**
 * If the value is non-null, map to a non-nullable value according to the given function, otherwise return a null value.
 */
inline fun <T, R : Any> T?.map(f: (T) -> R): R? {
    return this?.let { f.invoke(it) }
}

/**
 * If the value is non-null, map the value to a nullable value according to the given function, otherwise return a null value.
 */
inline fun <T, R> T?.flatMap(f: (T) -> R?): R? {
    return this?.let { f.invoke(it) }
}

/**
 * Returns the value if it is non-null, otherwise return a nullable value according to the given function.
 */
inline fun <T> T?.orNullable(f: () -> T?): T? {
    return this ?: f.invoke()
}

/**
 * Returns the value if it non-null, otherwise return the non-null value produced by the given function.
 */
inline fun <T> T?.orValue(f: () -> T): T {
    return this ?: f.invoke()
}

/**
 * Returns the result of the combination function, if this and the given values are non-null.
 */
inline fun <T, R : Any> T?.combine(n1: T, f2: (T, T) -> R): R? {
    return this?.let { it1 -> n1.map { it2 -> f2.invoke(it1, it2) } }
}
