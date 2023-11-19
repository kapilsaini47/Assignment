package com.dating.assignment.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.navArgs
import com.dating.assignment.R
import com.dating.assignment.databinding.FragmentArticleBinding
import com.dating.assignment.databinding.FragmentHomeBinding
class ArticleFragment : Fragment() {

    // data binding for the fragment layout
    private var _binding: FragmentArticleBinding? = null
    private val binding get() = _binding!!

    // Retrieve arguments using the Safe Args Gradle plugin
    private val args: ArticleFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment using data binding
        _binding = FragmentArticleBinding.inflate(inflater, container, false)

        // Retrieve the article URL from the fragment arguments
        val article = args.urlArgument

        // Show progress bar while loading the web article
        showProgressBar()

        // Configure and load the web article in the WebView
        binding.webView.apply {
            // Hide the progress bar once the WebView has finished loading
            hideProgressBar()

            // Set a WebViewClient to handle page navigation within the WebView
            webViewClient = WebViewClient()

            // Load the article URL in the WebView
            loadUrl(article)
        }

        return binding.root
    }

    private fun showProgressBar() {
        binding.artProgressBar.visibility = View.VISIBLE
        binding.webView.visibility = View.GONE
    }

    private fun hideProgressBar() {
        binding.artProgressBar.visibility = View.GONE
        binding.webView.visibility = View.VISIBLE
    }

    // Clean up the data binding and webView instance when the fragment is destroyed
    override fun onDestroy() {
        super.onDestroy()
        binding.webView.destroy()

        _binding = null
    }
}
