package com.schoolteam.educationmanager.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.schoolteam.educationmanager.R
import kotlinx.android.synthetic.main.activity_news_detail.*
import ru.noties.markwon.Markwon
import ru.noties.markwon.html.HtmlPlugin
import ru.noties.markwon.image.ImagesPlugin

class NewsDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_detail)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)

        tv_title.text = intent.getStringExtra("title")
        tv_summary.text = intent.getStringExtra("summary")
        Glide.with(this).load(intent.getStringExtra("image_url")).into(img_news)
        Markwon.builder(this).usePlugin(ImagesPlugin.create(this)).usePlugin(HtmlPlugin.create()).build()
            .setMarkdown(tv_content, intent.getStringExtra("content"))
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
