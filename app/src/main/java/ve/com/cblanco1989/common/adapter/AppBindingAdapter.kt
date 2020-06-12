package ve.com.cblanco1989.common.adapter

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import ve.com.cblanco1989.R

@BindingAdapter("imageFromUrl")
fun bindImageFromUrl(view: AppCompatImageView, imageUrl: String?) {

    if (!imageUrl.isNullOrEmpty()) {
        Glide.with(view.context)
            .load(imageUrl)
            .placeholder(ColorDrawable(view.context.getColor(R.color.place_holder)))
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(view)
    }
}