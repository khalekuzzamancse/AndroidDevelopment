@file:Suppress("unUsed","UNUSED_PARAMETER")
package paymentsave.terminalapp.core.language
import java.io.File
import java.io.FileWriter
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object Logger {
 //   private var logger: ILogger = AndroidLogger() // default to AndroidLogger
    private var logger: ILogger = PrintLogger() // Work in unit test as well
    private var writer:Writer?=null
    fun init(loggerImpl: ILogger) {
        logger = loggerImpl
    }


    fun on(context: String, subContext: String, key: Any, value: Any) {
        logger.on(context, subContext, key, value)
    }
    fun on(context: String,key: Any, value: Any) {
        logger.on(context, "", key, value)
    }
    fun off(context: String,key: Any, value: Any) {
    }

    fun off(context: String, subContext: String, key: Any, value: Any) {

    }
    fun error(tag: String, error: Throwable) {
        logger.error(tag, error)
    }

   fun initWriter(directory:File?){
       try {
           val logDir = File(directory, "logs")
           writer = Writer(logDir)
       }
       catch (e:Throwable) {
           error("Logger",e)
       }
    }
}

interface ILogger {
    fun on(context: String, subContext: String, key: Any, value: Any)
    fun off(tag: String, message: String)
    fun error(tag: String, error: Throwable)
}

class AndroidLogger : ILogger {
    private val id = "CustomLog"

    override fun on(context: String, subContext: String, key: Any, value: Any) {
        val tag = "$context:$subContext"
        val message = "$key=$value"
        android.util.Log.d("$id::$tag", message)
    }

    override fun off(tag: String, message: String) {
        android.util.Log.d("$id::$tag", "off: $message")
    }

    override fun error(tag: String, error: Throwable) {
        android.util.Log.d("$id::$tag", "error: $error")
    }
}

class PrintLogger : ILogger {
    private val id = "CustomLog"

    override fun on(context: String, subContext: String, key: Any, value: Any) {
        val tag = "$context:$subContext"
        val message = "$key=$value"
        println("$id::$tag -> $message")
    }

    override fun off(tag: String, message: String) {
        println("$id::$tag -> off: $message")
    }

    override fun error(tag: String, error: Throwable) {
        error.printStackTrace()
        println("$id::$tag -> error: $error,track:${error.stackTraceToString()}")
    }
}



private class Writer(logDir: File) {
    private val allLogFile: File
    private val errorLogFile: File
    private val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.US)

    init {
        logDir.mkdirs()
        allLogFile = File(logDir, "app.log")
        errorLogFile = File(logDir, "error.log")
    }

    private fun writeLine(file: File, line: String) {
        try {
            FileWriter(file, true).use { fw ->
                fw.write(line)
                fw.write("\n")
            }
        } catch (e: Throwable) {
           Logger.on("Writer","writeLine","failed to write log")
        }
    }

    fun on(context: String, subContext: String, key: Any, value: Any) {
        val logLine = "${timestamp()} [ON] [$context/$subContext] $key = $value"
        writeLine(allLogFile, logLine)
    }

    fun on(context: String, key: Any, value: Any) {
        val logLine = "${timestamp()} [ON] [$context] $key = $value"
        writeLine(allLogFile, logLine)
    }

    fun off(context: String, key: Any, value: Any) {
        val logLine = "${timestamp()} [OFF] [$context] $key = $value"
        writeLine(allLogFile, logLine)
    }

    fun off(context: String, subContext: String, key: Any, value: Any) {
        val logLine = "${timestamp()} [OFF] [$context/$subContext] $key = $value"
        writeLine(allLogFile, logLine)
    }

    fun error(tag: String, error: Throwable) {
        val logLine = "${timestamp()} [ERROR] [$tag] ${error.message ?: "Unknown error"}"
        writeLine(allLogFile, logLine)
        writeLine(errorLogFile, logLine)
        error.stackTrace.forEach { stack ->
            val stackLine = "\tat $stack"
            writeLine(allLogFile, stackLine)
            writeLine(errorLogFile, stackLine)
        }
    }

    private fun timestamp(): String = dateFormat.format(Date())
}