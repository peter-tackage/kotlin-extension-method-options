package com.moac.kotlinoptions

/**
 * Returns the non-null value or throws the given {@link Throwable}.
 */
fun <T> T?.getOrThrow(throwable: Throwable): T {
    return this ?: throw throwable
}

/**
 * Returns true if the value is non-null, false otherwise.
 */
fun <T : Any?> T?.isSome(): Boolean {
    return !isNone()
}

/**
 * Returns true if the value is null, false otherwise.
 */
fun <T : Any?> T?.isNone(): Boolean {
    return this == null;
}

/**
 * Performs the given action, if the value is non-null.
 */
inline fun <T : Any?> T?.ifSome(a: (T) -> Unit): Unit {
    this?.let(a)
}

/**
 * Performs the given action, if the value is null.
 */
inline fun <T : Any?> T?.ifNone(a: () -> Unit): Unit {
    this ?: a.invoke()
}

/**
 * If the value is non-null, and the value matches the given predicate, return the value, otherwise return a null value.
 */
inline fun <T : Any?> T?.filter(f: (T) -> Boolean): T? {
    return if (this != null) if (f.invoke(this)) this else null
    else null
}

/**
 * If the value is non-null, map to a non-nullable value according to the given function, otherwise return a null value.
 */
inline fun <T : Any?, R : Any> T?.map(f: (T) -> R): R? {
    return if (this != null) f.invoke(this) else null;
}

/**
 * If the value is non-null, map the value to a nullable value according to the given function, otherwise return a null value.
 */
inline fun <T : Any?, R : Any?> T?.flatMap(f: (T) -> R?): R? {
    return if (this != null) f.invoke(this) else null;
}

/**
 * Return the value if it is non-null, otherwise return a nullable value according to the given function.
 */
inline fun <T : Any?> T?.orOption(f: () -> T?): T? {
    return this ?: f.invoke()
}





