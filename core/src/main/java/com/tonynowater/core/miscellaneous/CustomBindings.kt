package com.tonynowater.core.miscellaneous

import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.github.florent37.glidepalette.BitmapPalette
import com.github.florent37.glidepalette.GlidePalette
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.shape.CornerFamily
import com.tonynowater.core.R
import com.tonynowater.core.changeStatusBarColor
import com.tonynowater.core.utils.LoadMoreInterface
import com.tonynowater.core.utils.RecyclerViewLoadMoreListener
import com.tonynowater.core.views.BorderBarView

@BindingAdapter("isLoading")
fun isLoading(view: View, loading: Boolean) {
    view.isVisible = loading
}

@BindingAdapter("inVisible")
fun isInvisible(view: View, inVisible: Boolean) {
    view.isInvisible = inVisible
}

@BindingAdapter("barValue")
fun bindBarValue(view: BorderBarView, barValue: Int) {
    view.currentValue = barValue
}

/**
 * 注意：在XML裡要bind的view ID不能有底線...
 * e.g.
 * Correct：
 * android:id="@+id/ivRounded"
 * app:paletteView="@{ivRounded}"
 *
 * Failure：
 * android:id="@+id/iv_rounded"
 * app:paletteView="@{iv_rounded}"
 */
@BindingAdapter("imageUrl", "paletteView", "fullRounded", "isPortrait", "changeStatusBarColor")
fun loadImageUrlAndPaletteView(
    imageView: ImageView,
    url: String?,
    paletteView: ShapeableImageView,
    fullRounded: Boolean,
    isPortrait: Boolean,
    changeStatusBarColor: Boolean
) {

    url?.let {

        val context = imageView.context

        Glide.with(context)
            .load(url)
            .listener(
                GlidePalette
                    .with(url)
                    .use(BitmapPalette.Profile.MUTED_DARK)
                    .intoCallBack {
                        it?.dominantSwatch?.rgb?.let { rgb ->
                            imageView.setBackgroundColor(rgb)
                            paletteView.setBackgroundColor(rgb)
                            paletteView.shapeAppearanceModel =
                                if (fullRounded) {
                                    paletteView.shapeAppearanceModel.toBuilder()
                                        .setAllCorners(
                                            CornerFamily.ROUNDED,
                                            imageView.resources.getDimension(
                                                R.dimen.image_corner_radius
                                            )
                                        )
                                        .build()
                                } else {
                                    if (isPortrait) {
                                        paletteView.shapeAppearanceModel.toBuilder()
                                            .setAllCorners(CornerFamily.ROUNDED, 0F)
                                            .setBottomLeftCorner(
                                                CornerFamily.ROUNDED,
                                                imageView.resources.getDimension(
                                                    R.dimen.image_corner_radius
                                                )
                                            )
                                            .setBottomRightCorner(
                                                CornerFamily.ROUNDED,
                                                imageView.resources.getDimension(
                                                    R.dimen.image_corner_radius
                                                )
                                            ).build()
                                    } else {
                                        paletteView.shapeAppearanceModel.toBuilder()
                                            .setAllCorners(CornerFamily.ROUNDED, 0F)
                                            .setBottomLeftCorner(
                                                CornerFamily.ROUNDED,
                                                imageView.resources.getDimension(
                                                    R.dimen.image_corner_radius
                                                )
                                            ).build()
                                    }
                                }

                            if (changeStatusBarColor && context is AppCompatActivity) {
                                context.changeStatusBarColor(color = rgb)
                            }
                        }
                    }
                    .crossfade(true)
            )
            .into(imageView)
    }
}

@BindingAdapter("imageUrl")
fun loadImageUrl(imageView: ImageView, url: String?) {

    url?.let {

        val context = imageView.context

        Glide.with(context)
            .load(url)
            .listener(
                GlidePalette
                    .with(url)
                    .use(BitmapPalette.Profile.MUTED_DARK)
                    .intoCallBack {
                        it?.dominantSwatch?.rgb?.let { rgb ->
                            if (imageView.parent is ConstraintLayout) {
                                (imageView.parent as ConstraintLayout).setBackgroundColor(rgb)
                            }
                        }
                    }
                    .crossfade(true)
            )
            .into(imageView)
    }
}

@BindingAdapter("loadRemoteImage")
fun loadRemoteImage(imageView: ImageView, url: String) {
    Glide.with(imageView)
        .load(url)
        .into(imageView)
}

@BindingAdapter("setLoadMore")
fun setLoadMoreListener(recyclerView: RecyclerView, loadMoreImpl: LoadMoreInterface) {
    RecyclerViewLoadMoreListener(recyclerView, loadMoreImpl)
}