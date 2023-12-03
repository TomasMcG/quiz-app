package utils

import models.Questions
import models.Rounds


/**
 * A utility object that provides various utility functions for common tasks.
 */
object Utilities {

 /*

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
*/


    /**
     * Formats a list of objects into a string representation.
     *
     * @param objectsToFormat The list of objects to format.
     * @return A formatted string containing the details of each object in the list.
     */
    @JvmStatic
    fun formatListString(objectsToFormat: ArrayList<Rounds>): String =
        objectsToFormat.joinToString(separator = "\n") { any -> "$any" }


    /**
     * Formats a list of items into a string representation with indentation.
     *
     * @param itemsToFormat The list of items to format.
     * @return A formatted string containing the indented details of each item in the list.
     */
    @JvmStatic
    fun formatSetString(itemsToFormat: ArrayList<Questions?>): String =
        itemsToFormat.joinToString(separator = "\n") { any -> "\t$any" }


}
