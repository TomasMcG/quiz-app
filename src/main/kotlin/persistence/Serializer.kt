package persistence

/**
 * A serializer interface for encoding and decoding objects.
 *
 * This interface provides methods for writing an object to a serialized format and
 * reading it back from that format. Implementations of this interface should handle
 * the serialization and deserialization of objects.
 *
 * @throws Exception if there is an error during serialization or deserialization.
 */
interface Serializer {

    /**
     * Write the given object to a serialized format.
     *
     * @param obj The object to be serialized.
     * @throws Exception if an error occurs during serialization.
     */
    @Throws(Exception::class)
    fun write(obj: Any?)

    /**
     * Read and return an object from the serialized format.
     *
     * @return The deserialized object.
     * @throws Exception if an error occurs during deserialization.
     */
    @Throws(Exception::class)
    fun read(): Any?
}