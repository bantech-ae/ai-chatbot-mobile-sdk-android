package com.bantech.chat

data class BantechChatConfig(
    val customerId: String,
    val customerToken: String,
    val wsAppKey: String? = null,
    val wsHost: String = "ws.bantech.ae",
    val apiBaseUrl: String = "https://ai-chatbot.bantech.ae/api/"
) {
    init {
        require(customerId.isNotEmpty()) { "Customer ID cannot be empty" }
        require(customerToken.isNotEmpty()) { "Customer Token cannot be empty" }
    }
}

class BantechChatConfigBuilder(
    private val customerId: String,
    private val customerToken: String
) {
    private var _wsAppKey: String? = null
    private var _wsHost: String = "ws.bantech.ae"
    private var _apiBaseUrl: String = "https://ai-chatbot.bantech.ae/api/"

    fun wsAppKey(appKey: String) = apply { _wsAppKey = appKey }
    fun wsHost(host: String) = apply { _wsHost = host }
    fun apiBaseUrl(baseUrl: String) = apply { _apiBaseUrl = baseUrl }

    fun build(): BantechChatConfig {
        return BantechChatConfig(
            customerId = customerId,
            customerToken = customerToken,
            wsAppKey = _wsAppKey,
            wsHost = _wsHost,
            apiBaseUrl = _apiBaseUrl
        )
    }
}
