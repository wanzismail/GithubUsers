package id.wanztudio.githubusers.extensions

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.transition.DrawableCrossFadeFactory
import id.wanztudio.githubusers.R

/**
 * @author Ridwan Ismail  (iwanz@pm.me)
 * @version Source, v 0.1 26/12/2021 by Ridwan Ismail
 */

fun ImageView.load(imgResource : Any?) {
    Glide.with(context.applicationContext)
        .load(imgResource)
        .circleCrop()
        .transition(DrawableTransitionOptions.withCrossFade(getDrawableFactory()))
        .placeholder(R.drawable.photo_placeholder)
        .into(this)
}

private fun getDrawableFactory() : DrawableCrossFadeFactory {
    return DrawableCrossFadeFactory.Builder().setCrossFadeEnabled(true).build()
}