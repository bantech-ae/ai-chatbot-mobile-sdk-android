package com.bantech.chat

import android.content.Context

object BantechChatLibrary {

    /**
     * Create the chat view with a given configuration
     */
    fun createChatView(context: Context, config: BantechChatConfig): BantechChatView {
        return BantechChatView(context).apply {
            initialize(config)
        }
    }

    /**
     * Kotlin DSL style config builder
     */
    fun config(
        customerId: String,
        customerToken: String,
        init: BantechChatConfigBuilder.() -> Unit = {}
    ): BantechChatConfig {
        return BantechChatConfigBuilder(customerId, customerToken)
            .apply(init)
            .build()
    }
}
