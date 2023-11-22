package persistence


import com.thoughtworks.xstream.XStream
import com.thoughtworks.xstream.io.json.JettisonMappedXmlDriver
import java.io.File
import java.io.FileReader
import java.io.FileWriter

//we use the abstract interface seralizer, need a read and write
/**
 * JSONSerializer is a class that implements the Serializer interface and is used for
 * reading and writing data in JSON format. It uses the XStream library with the JettisonMappedXmlDriver
 * for JSON serialization and deserialization.
 *
 * @param file The file to read from or write to.
 */
class JSONSerializer (private val file: File):Serializer {

    /**
     * Read data from a JSON file and deserialize it into an object.
     *
     * @return The deserialized object.
     * @throws Exception if there is an error during deserialization.
     */
    @Throws(Exception::class)
    override fun read(): Any {
        val xStream = XStream(JettisonMappedXmlDriver())
        xStream.allowTypes(arrayOf(Any::class.java))
        val inputStream = xStream.createObjectInputStream(FileReader(file))
        val obj = inputStream.readObject() as Any
        inputStream.close()
        return obj
    }

    /**
     * Write data to a JSON file by serializing the provided object.
     *
     * @param obj The object to be serialized and written to the file.
     * @throws Exception if there is an error during serialization and writing.
     */
    @Throws(Exception::class)
    override fun write(obj: Any?) {
        val xStream = XStream(JettisonMappedXmlDriver())
        val outputStream = xStream.createObjectOutputStream(FileWriter(file))
        outputStream.writeObject(obj)
        outputStream.close()
    }

}