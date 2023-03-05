package com.rool.progressbar

import android.animation.ValueAnimator
import android.content.Context
import android.content.res.TypedArray
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.animation.BounceInterpolator
import android.widget.FrameLayout
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.core.content.ContextCompat
import com.rool.R
import com.rool.progressbar.listeners.OnProgressChangedListener

class ProgressBar : FrameLayout {

    var maxProgress: Int = 100

    var progress: Int = 0
        set(value) {
            field = if (progress >= maxProgress) {
                maxProgress
            } else {
                value
            }
            updateProgress()
            onProgressListener?.onProgressChanged(field)
        }

    @ColorInt
    var colorProgress: Int = Color.MAGENTA

    @ColorInt
    var colorBackground: Int = Color.WHITE
        set(value) {
            field = value
            updateBackground()
        }

    var labelText: CharSequence? = ""
        set(value) {
            field = value
            this.progressText.text = value
        }

    @ColorInt
    var labelColor: Int = Color.BLACK
        set(value) {
            field = value
            progressText.setTextColor(labelColor)
        }

    var isAnimated: Boolean = true

    var animationDuration: Int = 1000

    var onProgressListener: OnProgressChangedListener? = null

    private val progressBarView = View(context)
    private val progressText = TextView(context)

    constructor(context: Context) : super(context)

    constructor(
        context: Context,
        attributeSet: AttributeSet
    ) : this(context, attributeSet, 0)

    constructor(
        context: Context,
        attributeSet: AttributeSet,
        defStyle: Int
    ) : super(
        context,
        attributeSet,
        defStyle
    ) {
        getAttrs(attributeSet, defStyle)
    }

    fun setOnProgressChangedListener(block: (Int) -> Unit) {
        this.onProgressListener = object : OnProgressChangedListener {
            override fun onProgressChanged(currentProgress: Int) {
                block(currentProgress)
            }
        }
    }

    private fun getAttrs(
        attributeSet: AttributeSet,
        defStyleAttr: Int
    ) {
        val typedArray =
            context.obtainStyledAttributes(attributeSet, R.styleable.ProgressBar, defStyleAttr, 0)
        obtainAttrs(typedArray)
        typedArray.recycle()
    }

    private fun obtainAttrs(typedArray: TypedArray) {
        this.labelText = typedArray.getString(R.styleable.ProgressBar_progressBar_labelText)
        this.labelColor =
            typedArray.getColor(R.styleable.ProgressBar_progressBar_colorLabelText, labelColor)
        this.colorProgress =
            typedArray.getColor(R.styleable.ProgressBar_progressBar_colorProgress, colorProgress)
        this.colorBackground =
            typedArray.getColor(
                R.styleable.ProgressBar_progressBar_colorBackground,
                colorBackground
            )
        this.isAnimated =
            typedArray.getBoolean(R.styleable.ProgressBar_progressBar_isAnimated, isAnimated)
        this.animationDuration = typedArray.getInt(
            R.styleable.ProgressBar_progressBar_animationDuration,
            animationDuration
        )
    }

    private fun updateProgress() {
        updateProgressBar()
        updateLabel()
        if (this.isAnimated) {
            animateProgress()
        } else {
            setProgress()
        }
    }

    private fun updateProgressBar() {
        progressBarView.background = ColorDrawable(colorProgress)
        removeView(progressBarView)
        addView(progressBarView)
    }

    private fun animateProgress() {
        ValueAnimator.ofFloat(0f, 1f)
            .apply {
                interpolator = BounceInterpolator()
                duration = this@ProgressBar.animationDuration.toLong()
                addUpdateListener {
                    val value = it.animatedValue as Float
                    val animatedWidth = getProportionalProgress() * value
                    progressBarView.layoutParams =
                        LayoutParams(animatedWidth.toInt(), ViewGroup.LayoutParams.MATCH_PARENT)
                }
            }.also { it.start() }
    }

    private fun setProgress() {
        post {
            progressBarView.layoutParams = LayoutParams(getProportionalProgress(), ViewGroup.LayoutParams.MATCH_PARENT)
        }
    }


    private fun updateLabel() {
        val params = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
        progressText.layoutParams = params
        progressText.gravity = Gravity.END
        removeView(progressText)
        addView(progressText)
    }

    private fun getProportionalProgress(): Int =
        (this.progress * this.width) / maxProgress

    private fun updateBackground() {
        background = ColorDrawable(colorBackground)
    }
}
