package com.jay.chkout.fragments

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jay.chkout.databinding.ProductItemBinding
import com.jay.chkout.network.Product

class ProductAdapter(): ListAdapter<Product,
        ProductAdapter.ProductViewHolder>(DiffCallback) {

    class ProductViewHolder(private var binding: ProductItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(product: Product) {
            binding.productTitle.text = product.productTitle
//            binding.executePendingBindings() // this is for updating immediately
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
        val adapterLayout = ProductItemBinding.inflate(LayoutInflater.from(parent.context))
        return ProductViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}