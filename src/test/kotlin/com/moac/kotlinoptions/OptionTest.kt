package com.moac.kotlinoptions

import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNull
import kotlin.test.assertTrue

class TestSource {

    @Test(expected = IllegalArgumentException::class)
    fun test_getOrThrow_throws_whenNull() {
        val obj: Any? = null

        obj.getOrThrow(IllegalArgumentException())
    }

    @Test
    fun test_getOrThrow_doesNotThrow_whenNotNull() {
        val obj: Any? = Any()

        assertEquals(obj, obj.getOrThrow(Throwable()))
    }

    @Test
    fun test_filter_doesNothing_whenNull() {
        val obj: Any? = null
        val predicate: Func1<Any, Boolean> = Func1(false)

        val result: Any? = obj.filter(predicate)

        assertFalse(predicate.wasInvoked())
        assertEquals(obj, result)
    }

    @Test
    fun test_filter_returnsInput_whenNotNullAndPredicateReturnsTrue() {
        val obj: Any? = Any()
        val predicate: Func1<Any, Boolean> = Func1(true)

        val result: Any? = obj.filter(predicate)

        assertTrue(predicate.wasInvoked())
        assertEquals(obj, result)
    }

    @Test
    fun test_filter_returnsNull_whenNotNullAndPredicateReturnsFalse() {
        val obj: Any? = Any()
        val predicate: Func1<Any, Boolean> = Func1(false)

        val result: Any? = obj.filter(predicate)

        assertTrue(predicate.wasInvoked())
        assertNull(result)
    }


    @Test
    fun test_map_returnsNull_whenNull() {
        val obj: Any? = null
        val mapper: Func1<Any, Any> = Func1(Any())

        val result: Any? = obj.map(mapper)

        assertFalse(mapper.wasInvoked())
        assertNull(result)
    }

    @Test
    fun test_map_returnsMappedInput_whenNotNull() {
        val obj: Int? = 1

        val result: Int? = obj.map { it -> it * 2 }

        assertEquals(2, result)
    }

    @Test
    fun test_flatMap_returnsNull_whenNull() {
        val obj: Any? = null
        val flatMapper: Func1<Any, Any?> = Func1(Any())

        val result: Any? = obj.flatMap(flatMapper)

        assertFalse(flatMapper.wasInvoked())
        assertNull(result)
    }

    @Test
    fun test_flatMap_returnsFlatmappedInput_whenNotNull() {
        val inner: Any? = Any()
        val obj: Dummy? = Dummy(inner)

        val result: Any? = obj.flatMap { it -> it.nullable }

        assertEquals(inner, result)
    }

    @Test
    fun test_isSome_returnTrue_whenNotNull() {
        assertTrue(Any().isSome())
    }

    @Test
    fun test_isSome_returnFalse_whenNull() {
        val obj: Any? = null

        assertFalse(obj.isSome())
    }

    @Test
    fun test_isNone_returnTrue_whenNull() {
        val obj: Any? = null

        assertTrue(obj.isNone())
    }

    @Test
    fun test_isNone_returnFalse_whenNotNull() {
        assertFalse(Any().isNone())
    }

    @Test
    fun test_orNullable_returnInput_whenNotNull() {
        val obj: Any? = Any()

        assertEquals(obj, obj.orNullable { Any() })
    }

    @Test
    fun test_orNullable_returnAlternative_whenNull() {
        val obj: Any? = null
        val obj2: Any? = Any()

        assertEquals(obj2, obj.orNullable { obj2 })
    }

    @Test
    fun test_ifSome_doesNotInvoke_whenNull() {
        val obj: Any? = null
        var invoked: Boolean = false

        obj.ifSome { invoked = true }
        assertFalse(invoked)
    }

    @Test
    fun test_ifSome_invokes_whenNotNull() {
        val obj: Any? = Any()
        var invoked: Boolean = false

        obj.ifSome { invoked = true }
        assertTrue(invoked)
    }


    @Test
    fun test_ifNone_invokes_whenNull() {
        val obj: Any? = null
        var invoked: Boolean = false

        obj.ifNone { invoked = true }
        assertTrue(invoked)
    }

    @Test
    fun test_ifNone_doesNotInvoke_whenNotNull() {
        val obj: Any? = Any()
        var invoked: Boolean = false

        obj.ifNone { invoked = true }
        assertFalse(invoked)
    }

    @Test
    fun test_orValue_returnsInput_whenNotNull() {
        val obj: Any? = Any()

        assertEquals(obj, obj.orValue { Any() })
    }

    @Test
    fun test_orValue_returnsAlternative_whenNotNull() {
        val obj: Any? = null
        val obj2: Any? = Any()

        assertEquals(obj2, obj.orValue { obj2 })
    }

    @Test
    fun test_combine_returnsNull_whenFirstNull() {
        val obj: Any? = null
        val obj2: Any? = Any()

        assertNull(obj.combine(obj2, { v1, v2 -> }))
    }

    @Test
    fun test_combine_returnsNull_whenSecondNull() {
        val obj: Any? = Any()
        val obj2: Any? = null

        assertNull(obj.combine(obj2, { v1, v2 -> }))
    }

    @Test
    fun test_combine_returnsCombinedResult_whenNeitherNull() {
        val obj: Any? = Any()
        val obj2: Any? = Any()
        val result: Any = Any()

        assertEquals(result, obj.combine(obj2, { v1, v2 -> result }))
    }

    @Test
    fun test_match_returnsSomeFunctionResult_whenNotNull() {
        val obj: Any? = Any()
        val expected: Any = Any()
        val someFunc = Func1<Any, Any>(expected)
        val noneFunc = Func0(Any())

        assertEquals(expected, obj.match(someFunc, noneFunc))
        assertTrue(someFunc.wasInvoked())
        assertFalse(noneFunc.wasInvoked())
    }

    @Test
    fun test_match_returnsNoneFunctionResult_whenNull() {
        val obj: Any? = null
        val expected: Any = Any()
        val someFunc = Func1<Any, Any>(Any())
        val noneFunc = Func0(expected)

        assertEquals(expected, obj.match(someFunc, noneFunc))
        assertFalse(someFunc.wasInvoked())
        assertTrue(noneFunc.wasInvoked())
    }

    @Test
    fun test_matchAction_performsSomeAction_whenNotNull() {
        val obj: Any? = Any()
        val someAction = Func1<Any, Unit>(Unit)
        val noneAction = Func0(Unit)

        obj.matchAction(someAction, noneAction)

        assertTrue(someAction.wasInvoked())
        assertFalse(noneAction.wasInvoked())
    }

    @Test
    fun test_matchAction_performsNoneAction_whenNull() {
        val obj: Any? = null
        val someAction = Func1<Any, Unit>(Unit)
        val noneAction = Func0(Unit)

        obj.matchAction(someAction, noneAction)

        assertFalse(someAction.wasInvoked())
        assertTrue(noneAction.wasInvoked())
    }

    // Helpers

    private data class Dummy(val nullable: Any?) {}

    private class Func0<R>(result: R) : Call(), () -> R {
        private val result: R = result

        override fun invoke(): R {
            isInvoked = true
            return result
        }
    }

    private class Func1<T, R>(result: R) : Call(), (T) -> R {
        private val result: R = result

        override fun invoke(p1: T): R {
            isInvoked = true
            return result
        }
    }

    private abstract class Call {
        protected var isInvoked: Boolean = false

        fun wasInvoked(): Boolean {
            return this.isInvoked
        }
    }

}