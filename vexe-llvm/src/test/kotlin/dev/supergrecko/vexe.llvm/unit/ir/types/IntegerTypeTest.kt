package dev.supergrecko.vexe.llvm.unit.ir.types

import dev.supergrecko.vexe.llvm.ir.Context
import dev.supergrecko.vexe.llvm.ir.TypeKind
import dev.supergrecko.vexe.llvm.ir.types.IntType
import dev.supergrecko.vexe.llvm.utils.VexeLLVMTestCase
import dev.supergrecko.vexe.llvm.utils.runAll
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import org.junit.jupiter.api.Test

internal class IntegerTypeTest : VexeLLVMTestCase() {
    @Test
    fun `Creation from user-land constructor`() {
        val type = IntType(64)

        assertEquals(TypeKind.Integer, type.getTypeKind())
    }

    @Test
    fun `Creation via LLVM reference`() {
        val type = IntType(1)
        val second = IntType(type.ref)

        assertEquals(type.ref, second.ref)
    }

    @Test
    fun `Type width does not change across modules`() {
        val ctx = Context()

        runAll(1, 8, 16, 32, 64, 128) { it, _ ->
            val contextType = IntType(it, ctx)
            val globalType = IntType(it)

            assertEquals(contextType.getTypeWidth(), globalType.getTypeWidth())
        }
    }

    @Test
    fun `Type width matches returned value`() {
        val ctx = Context()

        runAll(1, 8, 16, 32, 64, 128) { it, _ ->
            val type = IntType(it, ctx)

            assertEquals(it, type.getTypeWidth())
        }
    }

    @Test
    fun `Creation with negative size fails`() {
        assertFailsWith<IllegalArgumentException> {
            IntType(-1)
        }
    }

    @Test
    fun `Creation with size larger than 8388606 fails`() {
        assertFailsWith<IllegalArgumentException> {
            IntType(1238234672)
        }
    }

    @Test
    fun `The type is sized`() {
        val type = IntType(192)

        assertEquals(true, type.isSized())
    }
}
