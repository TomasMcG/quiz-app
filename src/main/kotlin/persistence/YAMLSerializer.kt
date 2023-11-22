package persistence

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import java.io.File

/**
 * A serializer implementation that uses YAML for object serialization and deserialization.
 *
 * This class implements the [Serializer] interface and provides the ability to read
 * and write objects to/from a YAML format. It uses the Jackson library for YAML
 * serialization/deserialization.
 *
 * @param file The file to read from or write to during serialization and deserialization.
 */
class YAMLSerializer(private val file: File) : Serializer {

    /**
     * Read objects from the YAML file and return them as an ArrayList of [Note] objects.
     *
     * @return An ArrayList of deserialized [Note] objects.
     * @throws Exception if there is an error during deserialization.
     */
    @Throws(Exception::class)
    override fun read(): ArrayList<Any> {
        val objectMapper = ObjectMapper(YAMLFactory())
        objectMapper.findAndRegisterModules()
        val noteType = objectMapper.typeFactory.constructParametricType(ArrayList::class.java, Any::class.java)
        val obj = objectMapper.readValue(file, noteType) as ArrayList<Any>
        return obj
    }

    /**
     * Write the provided object to the YAML file.
     *
     * @param obj The object to be serialized and written to the file.
     * @throws Exception if there is an error during serialization and writing.
     */
    @Throws(Exception::class)
    override fun write(obj: Any?) {
        val objectMapper = ObjectMapper(YAMLFactory())
        objectMapper.findAndRegisterModules()
        objectMapper.writeValue(file, obj)
    }
}
