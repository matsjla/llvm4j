package dev.supergrecko.vexe.llvm.unit.internal

import dev.supergrecko.vexe.llvm.internal.util.fromLLVMBool
import dev.supergrecko.vexe.llvm.internal.util.toLLVMBool
import dev.supergrecko.vexe.llvm.utils.VexeLLVMTestCase
import kotlin.test.assertEquals
import org.junit.jupiter.api.Test

internal class ConversionsTest : VexeLLVMTestCase() {
    @Test
    fun `Conversion from Int to Bool via extension`() {
        assertEquals(true, 1.fromLLVMBool())
        assertEquals(false, 0.fromLLVMBool())
    }

    @Test
    fun `Conversion from Bool to Int via extension`() {
        assertEquals(1, true.toLLVMBool())
        assertEquals(0, false.toLLVMBool())
    }

    @Test
    fun `Negative number is false`() {
        assertEquals(false, (-100).fromLLVMBool())
    }

    @Test
    fun `Any positive number is true`() {
        assertEquals(true, 1238182.fromLLVMBool())
    }
}
