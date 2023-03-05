package com.rool.progressbar

import android.animation.ValueAnimator
import android.content.Context
import android.content.res.TypedArray
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.AccelerateInterpolator
import android.view.animation.AnticipateInterpolator
import android.view.animation.BounceInterpolator
import android.view.animation.Interpolator
import android.view.animation.LinearInterpolator
import android.widget.FrameLayout
import android.widget.TextView

public class ProgressBar : FrameLayout {

    public var maxProgress: Int = 100

    public var progress: Int = 0
        set(value) {
            field = if (value >= maxProgress) {
                maxProgress
            } else {
                value
            }
            updateProgress()
            onProgressListener?.onProgressChanged(field)
        }

    public var colorProgress: Int = Color.MAGENTA

    public var colorBackground: Int = Color.WHITE
        set(value) {
            field = value
            updateBackground()
        }

    public var labelText: CharSequence? = ""
        set(value) {
            field = value
            this.progressText.text = value
        }

    public var labelColor: Int = Color.BLACK
        set(value) {
            field = value
            progressText.setTextColor(labelColor)
        }

    public var isAnimated: Boolean = true

    public var animationDuration: Int = 1000

    public var animationType: AnimationType = AnimationType.BOUNCE

    private var onProgressListener: OnProgressChangedListener? = null
    private val progressBarView = View(context)
    private val progressText = TextView(context)

    public constructor(context: Context) : super(context)

    public constructor(
        context: Context,
        attributeSet: AttributeSet
    ) : this(context, attributeSet, 0)

    public constructor(
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

    public fun setOnProgressChangedListener(block: (Int) -> Unit) {
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
        this.animationType = typedArray.getInt(
                R.styleable.ProgressBar_progressBar_animationType,
                animationType.value
            ).mapToAnimationType()
        this.progress = typedArray.getInt(R.styleable.ProgressBar_progressBar_progress, progress)
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
                interpolator = this@ProgressBar.animationType.getInterpolator()
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
            progressBarView.layoutParams =
                LayoutParams(getProportionalProgress(), ViewGroup.LayoutParams.MATCH_PARENT)
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

    public interface OnProgressChangedListener {
        public fun onProgressChanged(currentProgress: Int)
    }

    public enum class AnimationType(public val value: Int) {
        LINEAR(0), ANTICIPATE(1), ACCELERATE(2), BOUNCE(3), ACCELERATE_DECELERATE(4);

        public fun getInterpolator(): Interpolator =
            when (value) {
                LINEAR.value -> LinearInterpolator()
                ANTICIPATE.value -> AnticipateInterpolator()
                ACCELERATE.value -> AccelerateInterpolator()
                BOUNCE.value -> BounceInterpolator()
                ACCELERATE_DECELERATE.value -> AccelerateDecelerateInterpolator()
                else -> {
                    LinearInterpolator()
                }
            }
    }

    private fun Int.mapToAnimationType() : AnimationType = when {
            this == AnimationType.LINEAR.value -> AnimationType.LINEAR
            this == AnimationType.ANTICIPATE.value -> AnimationType.ANTICIPATE
            this == AnimationType.ACCELERATE.value -> AnimationType.ACCELERATE
            this == AnimationType.BOUNCE.value -> AnimationType.BOUNCE
            this == AnimationType.ACCELERATE_DECELERATE.value -> AnimationType.ACCELERATE_DECELERATE
            else -> {
                AnimationType.LINEAR
            }
        }
}
