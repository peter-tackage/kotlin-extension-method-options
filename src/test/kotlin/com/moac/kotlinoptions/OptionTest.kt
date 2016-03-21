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
        val predicate: TestPredicate<Any?> = TestPredicate(false)

        val result: Any? = obj.filter(predicate)

        assertFalse(predicate.wasInvoked())
        assertEquals(obj, result)
    }

    @Test
    fun test_filter_returnsInput_whenNotNullAndPredicateReturnsTrue() {
        val obj: Any? = Any()
        val predicate: TestPredicate<Any?> = TestPredicate(true)

        val result: Any? = obj.filter(predicate)

        assertTrue(predicate.wasInvoked())
        assertEquals(obj, result)
    }

    @Test
    fun test_filter_returnsNull_whenNotNullAndPredicateReturnsFalse() {
        val obj: Any? = Any()
        val predicate: TestPredicate<Any?> = TestPredicate(false)

        val result: Any? = obj.filter(predicate)

        assertTrue(predicate.wasInvoked())
        assertNull(result)
    }


    @Test
    fun test_map_returnsNull_whenNull() {
        val obj: Any? = null
        val mapper: TestMapper<Any, Any> = TestMapper(Any())

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
        val flatMapper: TestFlatMapper<Any, Any?> = TestFlatMapper(Any())

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
    fun test_combine_returnsCombinedResult_whenNoNull() {
        val obj: Any? = Any()
        val obj2: Any? = Any()
        val result: Any = Any()

        assertEquals(result, obj.combine(obj2, { v1, v2 -> result }))
    }

    // Helpers

    data class Dummy(val nullable: Any?) {
    }

    class TestPredicate<T>(expectedResult: Boolean) : (T) -> Boolean {

        private var isInvoked: Boolean = false
        private val expectedResult: Boolean = expectedResult

        override fun invoke(p1: T): Boolean {
            isInvoked = true
            return expectedResult
        }

        fun wasInvoked(): Boolean {
            return this.isInvoked
        }
    }

    class TestMapper<T, R : Any>(expectedResult: R) : (T) -> R {

        private var isInvoked: Boolean = false
        private val expectedResult: R = expectedResult

        override fun invoke(p1: T): R {
            isInvoked = true
            return expectedResult
        }

        fun wasInvoked(): Boolean {
            return this.isInvoked
        }
    }

    class TestFlatMapper<T : Any, R>(expectedResult: R) : (T) -> R {

        private var isInvoked: Boolean = false
        private val expectedResult: R = expectedResult

        override fun invoke(p1: T): R {
            isInvoked = true
            return expectedResult
        }

        fun wasInvoked(): Boolean {
            return this.isInvoked
        }
    }


}