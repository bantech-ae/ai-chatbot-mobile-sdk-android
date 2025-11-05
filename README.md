Bantech Chat Library

Android library for integrating Bantech Chat WebView into your applications with easy configuration and seamless integration.
📦 Installation

Method 1: Using JitPack (Recommended)

Add to your root build.gradle:

allprojects {
    repositories {
        maven { url 'https://jitpack.io' }
    }
}

 Add the dependency in your app's build.gradle:
 
 dependencies {
    implementation 'com.github.bantech:bantech-chat-library:1.0.0'
}

Method 2: Using Local AAR:

dependencies {
    implementation files('libs/bantech-chat-library-1.0.0.aar')
}


🚀 Quick Start
Basic Usage

// Create configuration
val config = BantechChatLibrary.config(
    customerId = "your_customer_id",
    customerToken = "your_customer_token"
)
// Create chat view
val chatView = BantechChatLibrary.createChatView(this, config)

// Add to your layout
val container = findViewById<FrameLayout>(R.id.container)

container?.addView(chatView)

Advanced Usage with Optional Parameters

val config = BantechChatLibrary.config(
    customerId = "a02026b80b3f4c04bce4eef8297863d8",
    customerToken = "708254251"
){
    wsAppKey = "your_pusher_app_key"      // Optional
    
    wsHost = "custom.bantech.ae"          // Optional (default: ws.bantech.ae)
    
    apiBaseUrl = "https://custom-api.bantech.ae/api/" // Optional (default: https://ai-chatbot.bantech.ae/api/)
    
}

val chatView = BantechChatLibrary.createChatView(this, config)
container.addView(chatView)




# ai-chatbot-mobile-sdk-android
