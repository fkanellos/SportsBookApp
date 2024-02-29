package gr.sportsbook.domain.errorHandling

data class ErrorState(
    val message: String? = null,
    val errorType: ErrorType = ErrorType.GENERAL,
    val action: ErrorAction? = null
)

enum class ErrorType {
    NETWORK,
    SERVER,
    GENERAL,
    NONE
}

data class ErrorAction(
    val label: String,
    val action: () -> Unit
)
