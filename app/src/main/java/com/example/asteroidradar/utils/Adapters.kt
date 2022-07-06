package com.example.asteroidradar.utils

import android.R.attr.radius
import android.graphics.*
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.example.asteroidradar.R
import com.squareup.picasso.Picasso
import com.squareup.picasso.Transformation
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation


val transformation: Transformation = RoundedCornersTransformation(150, 50)

@BindingAdapter("imageUrl")
fun setImageUrl(imageView: ImageView, url: String?) {
    Picasso.get().load(url).placeholder(R.drawable.ic_loading)
        .error(R.drawable.ic_no_wifi).transform(transformation).into(imageView);
}
@BindingAdapter("statusIcon")
fun bindAsteroidStatusImage(imageView: ImageView, isHazardous: Boolean) {
    val context = imageView.context
    if (isHazardous) {
        imageView.setImageResource(R.drawable.ic_danger)
        imageView.contentDescription = String.format(context.getString(R.string.potentially_hazardous_asteroid_image))
    } else {
        imageView.setImageResource(R.drawable.ic_good)
        imageView.contentDescription = String.format(context.getString(R.string.not_hazardous_asteroid_image))
    }
}

@BindingAdapter("asteroidStatusImage")
fun bindDetailsStatusImage(imageView: ImageView, isHazardous: Boolean) {
    val context = imageView.context
    if (isHazardous) {
        imageView.setImageResource(R.drawable.ic_asteroid_danger)
        imageView.contentDescription = String.format(context.getString(R.string.potentially_hazardous_asteroid_image))
    } else {
        imageView.setImageResource(R.drawable.ic_asteroid_normal)
        imageView.contentDescription = String.format(context.getString(R.string.not_hazardous_asteroid_image))
    }
}

@BindingAdapter("asteroidStatusTxt")
fun bindDetailsStatusImage(textView: TextView, isHazardous: Boolean) {
    val context = textView.context
    if (isHazardous) {
        textView.text = String.format(context.getString(R.string.danger))
        textView.setTextColor(ContextCompat.getColor(context, R.color.orange))
    } else {
        textView.text = String.format(context.getString(R.string.safe))
        textView.setTextColor(ContextCompat.getColor(context, R.color.green))
    }
}


@BindingAdapter("astronomicalUnitText")
fun bindTextViewToAstronomicalUnit(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = String.format(context.getString(R.string.astronomical_unit_format), number)
}

@BindingAdapter("kmUnitText")
fun bindTextViewToKmUnit(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = String.format(context.getString(R.string.km_unit_format), number)
}

@BindingAdapter("velocityText")
fun bindTextViewToDisplayVelocity(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = String.format(context.getString(R.string.km_s_unit_format), number)
}


