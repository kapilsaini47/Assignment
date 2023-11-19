package com.dating.assignment.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.dating.assignment.model.Article
import com.dating.assignment.R
import com.dating.assignment.model.NewsResponse
import com.squareup.picasso.Picasso

class HomeAdapter : RecyclerView.Adapter<HomeAdapter.HomeViewHolder>() {

    // Interface to define item click behavior
    interface OnItemClickListener {
        fun onItemClick(article: Article)
    }

    // Listener variable to hold the click listener
    private var onItemClickListener: OnItemClickListener? = null

    // Public method to set the OnItemClickListener
    fun setOnItemClickListener(listener: OnItemClickListener) {
        onItemClickListener = listener
    }

    // ViewHolder class for the RecyclerView
    inner class HomeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // Views within the item layout
        private val source = itemView.findViewById<TextView>(R.id.tvSource)
        private val publishedAt = itemView.findViewById<TextView>(R.id.tvPublishedAt)
        private val title = itemView.findViewById<TextView>(R.id.tvTitle)
        private val articleImage = itemView.findViewById<ImageView>(R.id.ivArticleImage)

        // Bind method to populate the views with data
        fun bind(article: Article) {
            source.text = article.source?.name
            publishedAt.text = article.publishedAt
            title.text = article.title

            // Using Picasso library to load the article image
            Picasso.get().load(article.urlToImage)
                .error(R.drawable.ic_launcher_background).into(articleImage)
        }
    }

    // Callback for calculating the difference between two non-null items
    private val differCallback = object : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            // Check if the items have the same URL (considered as a unique identifier)
            return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            // Check if the contents of the items are the same
            return oldItem == newItem
        }
    }

    // AsyncListDiffer to compute the differences between the old and new lists
    val diffCallBack = AsyncListDiffer(this, differCallback)

    // Inflates the item layout and returns the ViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        return HomeViewHolder(LayoutInflater.from(parent.context).inflate(
            R.layout.single_item_news_layout, parent, false))
    }

    // Returns the total number of items in the data set
    override fun getItemCount(): Int {
        return diffCallBack.currentList.size
    }

    // Binds the data to the views in the ViewHolder
    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        holder.bind(diffCallBack.currentList[position])

        // Set click listener for the item
        holder.itemView.setOnClickListener {
            // Notify the listener when an item is clicked
            onItemClickListener?.onItemClick(diffCallBack.currentList[position])
        }
    }
}
