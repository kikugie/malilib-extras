package dev.kikugie.malilib_extras.api.restriction

/**
 * Result of testing [Condition]s in a [Restriction].
 *
 * @property success Whenever the check succeeded.
 * @property reasons A list of translated reasons for this result.
 * Translations should be evaluated in the config GUI.
 */
data class TestResult(
    val success: Boolean,
    val reasons: List<() -> String> = listOf()
) {
    constructor(success: Boolean, vararg reasons: () -> String) : this(success, reasons.toList())
}
