package com.example.test.view.adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.core.net.toUri
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.test.R
import com.example.test.databinding.ActivityMainBinding
import com.example.test.databinding.ItemBinding
import com.example.test.model.Animation
import com.example.test.view.ShowActivity
import java.util.concurrent.TimeUnit

class MainAdapter(private val list: MutableList<Animation>) :
    RecyclerView.Adapter<MainAdapter.MainAdapterViewHolder>() {

    class MainAdapterViewHolder(private val binding: ItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(animation: Animation) {
            with(binding) {
                item.setOnClickListener {
                    val intent = Intent(binding.root.context, ShowActivity::class.java)
                    intent.putExtra("ani", animation)
                    binding.root.context.startActivity(intent)
                }
                if (animation.type != "video") {
                    img.setImageResource(animation.path.toInt())
                } else {
                    Glide.with(binding.root)
                        .asBitmap()
                        .load(animation.path)
                        .frame(TimeUnit.SECONDS.toMicros(1.toLong()))
                        .into(img)

                }
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
      fun notify(list1: MutableList<Animation>) {
        list.clear()
        list.addAll(list1)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainAdapterViewHolder {
        return MainAdapterViewHolder(
            ItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: MainAdapterViewHolder, position: Int) {
        holder.bind(list[position])
    }
}