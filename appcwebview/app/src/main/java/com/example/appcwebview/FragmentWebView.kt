package com.example.appcwebview

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebSettings
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import com.example.appcwebview.databinding.FragmentWebviewBinding

class FragmentWebView : Fragment() {
    lateinit var thisParent: MainActivity
    lateinit var wbSet: WebSettings
    lateinit var b: FragmentWebviewBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        thisParent = activity as MainActivity
        b = FragmentWebviewBinding.inflate(inflater, container, false)
        // Initialize wbSet
        wbSet = b.webV.settings
        return b.root
    }

    override fun onStart() {
        super.onStart()
        webSettings()
        b.webV.loadUrl(GlobalVariables.url)
    }

    @SuppressLint("SetJavaScriptEnabled")
    fun webSettings() {
        // Check if wbSet is initialized before using it
        if (!::wbSet.isInitialized) {
            // Handle the case where wbSet is not initialized
            return
        }

        // Rest of your webSettings function
        wbSet.setDomStorageEnabled(true)
        wbSet.setDisplayZoomControls(false)
        wbSet.setUseWideViewPort(true)
        wbSet.setJavaScriptEnabled(true)
        wbSet.setSavePassword(true)
        wbSet.setCacheMode(WebSettings.LOAD_DEFAULT)
        wbSet.setGeolocationEnabled(true)
        wbSet.setAllowFileAccess(true)
        wbSet.setAllowContentAccess(true)
        wbSet.setLoadsImagesAutomatically(true)
        b.webV.webViewClient = WebViewClient()
    }
}
