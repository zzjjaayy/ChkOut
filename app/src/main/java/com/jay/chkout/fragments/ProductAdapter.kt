package com.jay.chkout.fragments

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jay.chkout.R
import com.jay.chkout.databinding.ProductItemBinding
import com.jay.chkout.network.Product

class ProductAdapter(private val onProductClicked : (Product) -> Unit): ListAdapter<Product,
        ProductAdapter.ProductViewHolder>(DiffCallback) {

    class ProductViewHolder(private var binding: ProductItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(product: Product) {
            Glide.with(binding.productImage.context)
                .load(product.imageUrl)
                .into(binding.productImage)
            binding.productTitle.text = product.productTitle
            binding.productPrice.text = binding.productPrice.context.resources
                .getString(R.string.price_template, product.price.toString())
            when(product.category) {
                "men's clothing" -> binding.categoryIcon.setImageResource(R.drawable.ic_category_clothing)
                "women's clothing" -> binding.categoryIcon.setImageResource(R.drawable.ic_category_clothing)
                "jewelery" -> binding.categoryIcon.setImageResource(R.drawable.ic_category_jewelery)
                "electronics" -> binding.categoryIcon.setImageResource(R.drawable.ic_category_electronics)
                else -> binding.categoryIcon.visibility = View.GONE
            }
        }
    }

    companion object DiffCallback: DiffUtil.ItemCallback<Product>(){
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.id == newItem.id
        }
        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.imageUrl == newItem.imageUrl &&
                    oldItem.productTitle == newItem.productTitle &&
                    oldItem.description == newItem.description &&
                    oldItem.price == newItem.price &&
                    oldItem.productTitle == newItem.productTitle &&
                    oldItem.category == newItem.category
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProductViewHolder {
        val viewHolder = ProductViewHolder(ProductItemBinding.
            inflate(LayoutInflater.from(parent.context), parent, false))
        viewHolder.itemView.setOnClickListener {
            onProductClicked(getItem(viewHolder.adapterPosition))
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}