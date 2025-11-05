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
    private var wsAppKey: String? = null
    private var wsHost: String = "ws.bantech.ae"
    private var apiBaseUrl: String = "https://ai-chatbot.bantech.ae/api/"

    fun wsAppKey(appKey: String) = apply { this.wsAppKey = appKey }
    fun wsHost(host: String) = apply { this.wsHost = host }
    fun apiBaseUrl(baseUrl: String) = apply { this.apiBaseUrl = baseUrl }

    fun build(): BantechChatConfig {
        return BantechChatConfig(
            customerId = customerId,
            customerToken = customerToken,
            wsAppKey = wsAppKey,
            wsHost = wsHost,
            apiBaseUrl = apiBaseUrl
        )
    }
}