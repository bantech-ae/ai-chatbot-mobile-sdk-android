package com.bantech.chat

import android.content.Context

object BantechChatLibrary {
    fun createChatView(
        context: Context,
        config: BantechChatConfig
    ): BantechChatView {
        return BantechChatView(context).apply {
            initialize(config)
        }
    }

    fun config(
        cid: String,
        token: String,
        init: BantechChatConfigBuilder.() -> Unit = {}
    ): BantechChatConfig {
        return BantechChatConfigBuilder(cid, token)
            .apply(init)
            .build()
    }
}