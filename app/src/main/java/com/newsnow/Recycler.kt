package com.newsnow
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.newsnow.R

class RecyclerAdapter(
    private var titles: List<String>,
    private var details: List<String>,
    private var images: List<String>,
    private var links: List<String>
) :
    RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {

    //Organizes DTO attributes to UI card
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val itemTitle: TextView = itemView.findViewById(R.id.tv_title)
        val itemDetail: TextView = itemView.findViewById(R.id.tv_description)
        val itemPicture: ImageView = itemView.findViewById(R.id.iv_image)

        //If a card is selected, the URL that is associated with the article will open in default browser
        init {
            itemView.setOnClickListener { v: View ->
                val position: Int = absoluteAdapterPosition

                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(links[position])
                startActivity(itemView.context, intent,null)
            }
        }
    }

    //Initializes organization of the cards together
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.article_tile_layout, parent, false)
        return ViewHolder(v)
    }

    //Function for displaying number of cards specified in XML file
    override fun getItemCount(): Int {
        return titles.size
    }

    //Function that allow the various cards to slide up and down
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemTitle.text = titles[position]
        holder.itemDetail.text = details[position]

        Glide.with(holder.itemPicture)
            .load(images[position])
            .into(holder.itemPicture)
    }
}