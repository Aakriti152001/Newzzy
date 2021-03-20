package com.aakriti.newzzy

import android.content.Context
import android.content.Intent
import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class NewsAdapter( val context:Context, val articles:List<Article>): RecyclerView.Adapter<NewsAdapter.ArticleViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
       val view=LayoutInflater.from(context).inflate(R.layout.item_layout,parent,false)
        return ArticleViewHolder(view)
    }

    override fun getItemCount(): Int {
    return articles.size
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
val article=articles[position]
        holder.newsTitle.text=article.title
        holder.newsDescription.text=article.description
        Glide.with(context).load(article.urlToImage).into(holder.newsImage)
        holder.itemView.setOnClickListener{
            val intent= Intent(context,DetailActivity::class.java)
            intent.putExtra("URL",article.url)
            context.startActivity(intent)
        }
        
    }
    class ArticleViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val newsImage=itemView.findViewById<ImageView>(R.id.imgNews)
        val newsTitle=itemView.findViewById<TextView>(R.id.txtTitle)
        val newsDescription=itemView.findViewById<TextView>(R.id.txtDescription)

    }


}