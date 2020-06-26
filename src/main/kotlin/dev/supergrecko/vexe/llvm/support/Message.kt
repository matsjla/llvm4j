package dev.supergrecko.vexe.llvm.support

import dev.supergrecko.vexe.llvm.internal.contracts.Disposable
import java.nio.ByteBuffer
import org.bytedeco.llvm.global.LLVM

/**
 * A glorified char*
 *
 * A [Message] is a wrapper around Java's ByteBuffer and Bytedeco's BytePointers
 * It contains a char buffer which will be decoded into a Kotlin [String].
 *
 * Because these are obtained from C++ they need to be de-allocated which is why
 * they provide the [dispose] method. Failing to call this method will leak
 * memory.
 *
 * Do not use this class for byte buffers which do not come from the LLVM
 */
public class Message(private val buffer: ByteBuffer) : Disposable,
    AutoCloseable {
    public override var valid: Boolean = true

    /**
     * Get a string representation of this [Message]
     *
     * For this method to be available the [Message] needs to be [valid].
     * If this was called on a de-allocated object, the JVM would crash.
     */
    public fun getString(): String {
        require(valid) { "Cannot use disposed memory" }

        val res = StringBuilder()

        for (i in 0 until buffer.capacity()) {
            res.append(buffer.get(i).toChar())
        }

        return res.toString()
    }

    public override fun dispose() {
        require(valid) { "Cannot dispose object twice" }

        valid = false

        LLVM.LLVMDisposeMessage(buffer)
    }

    public override fun close() = dispose()
}