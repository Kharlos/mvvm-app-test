package ve.com.cblanco1989.features.home.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ve.com.cblanco1989.databinding.LayoutMovieListItemBinding
import ve.com.cblanco1989.features.home.data.MovieModel

class MovieListAdapter(var adapterInterface: MovieListAdapterInterface) : ListAdapter<MovieModel, MovieListViewHolder>(MovieListDiffCalback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieListViewHolder {
        return MovieListViewHolder(
            LayoutMovieListItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ))
    }

    override fun onBindViewHolder(holder: MovieListViewHolder, position: Int) {
        holder.bind(getItem(position))
        holder.binding.cardView.setOnClickListener {
            adapterInterface.OnMovieSelected(getItem(position))
        }
    }
}

class MovieListViewHolder( var binding:LayoutMovieListItemBinding) :RecyclerView.ViewHolder(binding.root){

    fun bind(item: MovieModel) {
        binding.apply {
            movie = item
            executePendingBindings()
        }
    }

}

class MovieListDiffCalback : DiffUtil.ItemCallback<MovieModel>(){
    override fun areItemsTheSame(oldItem: MovieModel, newItem: MovieModel): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: MovieModel, newItem: MovieModel): Boolean {
        return oldItem == newItem
    }
}

interface MovieListAdapterInterface{
    fun OnMovieSelected(movie:MovieModel)
}