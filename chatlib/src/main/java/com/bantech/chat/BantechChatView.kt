package com.bantech.chat

import android.content.Context
import android.util.AttributeSet
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.FrameLayout
import java.util.UUID

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

            setupWebSettings()
            setupWebClients()
        }

        addView(webView)
    }

    private fun WebView.setupWebSettings() {
        with(settings) {
            javaScriptEnabled = true
            domStorageEnabled = true
            loadWithOverviewMode = true
            useWideViewPort = true
            builtInZoomControls = false
            displayZoomControls = false
            setSupportZoom(false)

            // Enhanced settings for better performance
            cacheMode = android.webkit.WebSettings.LOAD_DEFAULT
            mediaPlaybackRequiresUserGesture = false
        }
    }

    private fun WebView.setupWebClients() {
        webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(
                view: WebView?,
                url: String?
            ): Boolean {
                url?.let {
                    if (it.startsWith("http")) {
                        view?.loadUrl(it)
                        return true
                    }
                }
                return false
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                // يمكنك إضافة أي كود بعد تحميل الصفحة هنا
            }
        }

        webChromeClient = WebChromeClient()
    }

    /**
     * تهيئة الـ WebView مع بيانات العميل
     */
    fun initialize(config: BantechChatConfig) {
        this.config = config
        loadChatUrl()
    }

    /**
     * تحميل رابط الدردشة
     */
    private fun loadChatUrl() {
        val currentConfig = config ?: throw IllegalStateException(
            "BantechChatView must be initialized with config before loading chat"
        )

        val chatUrl = buildChatUrl(currentConfig)
        webView.loadUrl(chatUrl)
    }

    /**
     * بناء رابط الدردشة
     */
    private fun buildChatUrl(config: BantechChatConfig): String {
//        val uuid = UUID.randomUUID().toString()

        val baseUrl = config.apiBaseUrl.removeSuffix("/")

        return StringBuilder().apply {
            append(baseUrl).append("/")
            append("?uuid=").append("${config.customerId}")
            append("&token=").append("${config.customerToken}")

            config.wsAppKey?.let {
                append("&wsAppKey=").append(it)
            }

            config.wsHost?.let {
                append("&wsHost=").append(it)
            }
        }.toString()
    }


    /**
     * إعادة تحميل الدردشة
     */
    fun reload() {
        if (config != null) {
            loadChatUrl()
        }
    }

    /**
     * التحقق مما إذا كان يمكن العودة في الـ WebView
     */
    fun canGoBack(): Boolean = webView.canGoBack()

    /**
     * العودة للصفحة السابقة في الـ WebView
     */
    fun goBack() {
        if (webView.canGoBack()) {
            webView.goBack()
        }
    }

    /**
     * تنظيف الموارد
     */
    fun cleanup() {
        webView.stopLoading()
        webView.webViewClient = WebViewClient()
        webView.webChromeClient = WebChromeClient()
    }
}