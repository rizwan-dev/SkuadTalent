package net.zenpro.data.extensions

import com.google.android.gms.tasks.Task
import kotlinx.coroutines.CancellationException
import java.net.UnknownHostException
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

suspend fun <T> Task<T>.await(): T {
    // fast path
    if (isComplete) {
        val e = exception
        return if (e == null) {
            if (isCanceled) {
                throw CancellationException("Task $this was cancelled normally.")
            } else {
                result!!
            }
        } else {
            throw e
        }
    }

    return kotlinx.coroutines.suspendCancellableCoroutine { cont ->
        addOnCompleteListener {
            val e = exception
            if (e == null) {
                if (isCanceled) cont.cancel() else cont.resume(result!!)
            } else {
                if (e.cause is UnknownHostException) {
                    cont.resumeWithException(UnknownHostException())
                    return@addOnCompleteListener
                }
                cont.resumeWithException(e)
            }
        }
    }
}
