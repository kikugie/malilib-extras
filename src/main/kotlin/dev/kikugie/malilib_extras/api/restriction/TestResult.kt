package dev.kikugie.malilib_extras.api.restriction

data class TestResult(
    val success: Boolean,
    val reasons: List<() -> String> = listOf()
) {
    constructor(success: Boolean, vararg reasons: () -> String) : this(success, reasons.toList())
}
