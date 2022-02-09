package dev.bahodir.rickandmorty.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingData
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.room.PrimaryKey
import com.bumptech.glide.Glide
import dev.bahodir.rickandmorty.R
import dev.bahodir.rickandmorty.databinding.RvItemBinding
import dev.bahodir.rickandmorty.model.Location
import dev.bahodir.rickandmorty.model.Model
import dev.bahodir.rickandmorty.model.Origin
import dev.bahodir.rickandmorty.model.Result
import dev.bahodir.rickandmorty.room.ResultDatabase

class RecyclerPagingAdapter(val context: Context, val listener: OnClick) :
    PagingDataAdapter<Result, RecyclerPagingAdapter.VH>(DU()) {

    interface OnClick {
        fun onClick(result: Result)
    }

    class DU : DiffUtil.ItemCallback<Result>() {
        override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem == newItem
        }

    }

    inner class VH(var binding: RvItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(result: Result) {
            binding.name.text = result.name
            Glide.with(context).load(result.image).placeholder(R.drawable.place_holder).into(binding.image)
            binding.status.text = " ${result.status} - "
            if (result.status == "Alive") {
                binding.circle.setBackgroundResource(R.drawable.green)
            } else {
                binding.circle.setBackgroundResource(R.drawable.red)
            }
            binding.species.text = result.species
            binding.locationName.text = result.location.name
            binding.originName.text = result.origin.name

            val results = dev.bahodir.rickandmorty.room.Result(
                created = result.created,
                gender = result.gender,
                id = result.id,
                image = result.image,
                name = result.name,
                species = result.species,
                status = result.status,
                type = result.type,
                url = result.url
            )

            itemView.setOnClickListener {
                listener.onClick(result)
            }

            ResultDatabase.getInstance(context).getResultDao().insertResult(results)
        }
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(RvItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }
}