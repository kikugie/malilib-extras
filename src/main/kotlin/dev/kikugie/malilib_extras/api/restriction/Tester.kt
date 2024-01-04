package dev.kikugie.malilib_extras.api.restriction

/**
 * Interface that must be implemented by a custom checker used in [Condition].
 * The implementation must be either a singleton object or have a constructor with no arguments.
 */
interface Tester {
    /**
     * Runs the tester.
     *
     * For options the check is executed whenever it is registered (recommended to be in the mod initializer).
     * The same tester may be called multiple times if passed to more than one option.
     *
     * @return Test result.
     */
    fun test(): Boolean
}