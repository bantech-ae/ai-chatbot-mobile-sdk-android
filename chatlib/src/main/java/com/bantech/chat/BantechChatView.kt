package com.bantech.chat

import android.content.Context
import android.util.AttributeSet
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.FrameLayout

class BantechChatView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private lateinit var webView: WebView
    private var config: BantechChatConfig? = null

    init {
        setupWebView()
    }

    private fun setupWebView() {
        webView = WebView(context).apply {
            layoutParams = LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT
            )
            settings.apply {
                javaScriptEnabled = true
                domStorageEnabled = true
                loadWithOverviewMode = true
                useWideViewPort = true
                builtInZoomControls = false
                displayZoomControls = false
                setSupportZoom(false)
                cacheMode = android.webkit.WebSettings.LOAD_DEFAULT
                mediaPlaybackRequiresUserGesture = false
            }
            webViewClient = object : WebViewClient() {
                override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                    url?.let {
                        if (it.startsWith("http")) {
                            view?.loadUrl(it)
                            return true
                        }
                    }
                    return false
                }
            }
            webChromeClient = WebChromeClient()
        }

        addView(webView)
    }

    /**
     * Initialize the chat view with a configuration
     */
    fun initialize(config: BantechChatConfig) {
        this.config = config
        loadChatUrl()
    }

    private fun loadChatUrl() {
        val currentConfig = config ?: throw IllegalStateException(
            "BantechChatView must be initialized with config before loading chat"
        )

        val baseUrl = currentConfig.apiBaseUrl.removeSuffix("/")

        val url = buildString {
            append(baseUrl)
            append("?uuid=").append(currentConfig.customerId)
            append("&token=").append(currentConfig.customerToken)
            currentConfig.wsAppKey?.let { append("&wsAppKey=").append(it) }
            currentConfig.wsHost?.let { append("&wsHost=").append(it) }
        }

        webView.loadUrl(url)
    }

    fun reload() {
        if (config != null) loadChatUrl()
    }

    fun canGoBack(): Boolean = webView.canGoBack()

    fun goBack() {
        if (webView.canGoBack()) webView.goBack()
    }

    fun cleanup() {
        webView.stopLoading()
        webView.webViewClient = WebViewClient()
        webView.webChromeClient = WebChromeClient()
    }
}
