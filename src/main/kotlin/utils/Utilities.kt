package utils

import models.Questions


/**
 * A utility object that provides various utility functions for common tasks.
 */
object Utilities {
    /**
     * Check if the given index is a valid index for a list.
     *
     * @param index The index to check for validity.
     * @param list The list to check against.
     * @return `true` if the index is valid for the list, `false` otherwise.
     */
    @JvmStatic
    fun isValidListIndex(index: Int, list: List<Any>): Boolean {
        return (index >= 0 && index < list.size)
    }

    /**
     * Check if a number is within a valid range (inclusive).
     *
     * @param numberToCheck The number to check.
     * @param min The minimum value of the range.
     * @param max The maximum value of the range.
     * @return `true` if the number is within the range, `false` otherwise.
     */
    @JvmStatic
    fun validRange(numberToCheck: Int, min: Int, max: Int): Boolean {
        return numberToCheck in min..max
    }

    /**
     * Check if the length of a note's content is valid.
     *
     * @param input The note's content to check.
     * @return `true` if the content length is within the valid range, `false` otherwise.
     */

    @JvmStatic
    fun formatListString(objectsToFormat: ArrayList<Any>): String =
        objectsToFormat
            .joinToString(separator = "\n") { any -> "$any" }
//I need 2 seperate list strings for rounds and questions, ArrayLIst<Any> wasn'tworking for me

    @JvmStatic
    fun formatSetString(itemsToFormat: ArrayList<Any>): String =
        itemsToFormat
            .joinToString(separator = "\n") { any -> "\t$any" }


    @JvmStatic
    fun emptyArrayList(): ArrayList<Questions?> {
        return ArrayList()
    }

}