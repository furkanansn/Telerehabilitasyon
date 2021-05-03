package com.safehouse.fizyoterapim.ui.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import com.safehouse.fizyoterapim.Firebase.Model.Form
import com.safehouse.fizyoterapim.R
import kotlinx.android.synthetic.main.activity_web_view.*

class WebViewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view)

        val data : Form = intent.getSerializableExtra("url") as Form


        webView.settings.javaScriptEnabled = true
        webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                view?.loadUrl(url.toString())
                return true
            }
        }
        toolbar.title = data.name.toString()
        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
        webView.loadUrl(data.url.toString())
    }
}