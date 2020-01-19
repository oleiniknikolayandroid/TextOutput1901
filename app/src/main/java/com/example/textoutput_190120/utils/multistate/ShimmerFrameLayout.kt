package com.example.textoutput_190120.utils.multistate

import android.animation.Animator
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Paint
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.graphics.RadialGradient
import android.graphics.Shader
import android.util.AttributeSet
import android.util.Log
import android.view.ViewTreeObserver
import android.widget.FrameLayout
import com.example.textoutput_190120.R


class ShimmerFrameLayout @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyle: Int = 0) :
    FrameLayout(context, attrs, defStyle) {

    private val mAlphaPaint: Paint
    private val mMaskPaint: Paint

    private val mMask: Mask
    private var mMaskTranslation: MaskTranslation? = null

    private var mRenderMaskBitmap: Bitmap? = null
    private var mRenderUnmaskBitmap: Bitmap? = null

    var isAutoStart: Boolean = false
        set(autoStart) {
            field = autoStart
            resetAll()
        }

    var duration: Int = 0
        set(duration) {
            field = duration
            resetAll()
        }

    var repeatCount: Int = 0
        set(repeatCount) {
            field = repeatCount
            resetAll()
        }

    var repeatDelay: Int = 0
        set(repeatDelay) {
            field = repeatDelay
            resetAll()
        }

    var repeatMode: Int = 0
        set(repeatMode) {
            field = repeatMode
            resetAll()
        }

    private var mMaskOffsetX: Int = 0
    private var mMaskOffsetY: Int = 0

    var isAnimationStarted: Boolean = false
        private set
    private var mGlobalLayoutListener: ViewTreeObserver.OnGlobalLayoutListener? = null

    protected var mAnimator: ValueAnimator? = null
    protected var mMaskBitmap: Bitmap? = null

    var baseAlpha: Float
        get() = mAlphaPaint.alpha.toFloat() / 0xff
        set(alpha) {
            mAlphaPaint.alpha = (clamp(0f, 1f, alpha) * 0xff).toInt()
            resetAll()
        }

    var maskShape: MaskShape?
        get() = mMask.shape
        set(shape) {
            mMask.shape = shape
            resetAll()
        }

    var angle: MaskAngle?
        get() = mMask.angle
        set(angle) {
            mMask.angle = angle
            resetAll()
        }

    var dropoff: Float
        get() = mMask.dropoff
        set(dropoff) {
            mMask.dropoff = dropoff
            resetAll()
        }

    var fixedWidth: Int
        get() = mMask.fixedWidth
        set(fixedWidth) {
            mMask.fixedWidth = fixedWidth
            resetAll()
        }

    var fixedHeight: Int
        get() = mMask.fixedHeight
        set(fixedHeight) {
            mMask.fixedHeight = fixedHeight
            resetAll()
        }

    var intensity: Float
        get() = mMask.intensity
        set(intensity) {
            mMask.intensity = intensity
            resetAll()
        }

    val relativeWidth: Float
        get() = mMask.relativeWidth

    val relativeHeight: Float
        get() = mMask.relativeHeight

    var tilt: Float
        get() = mMask.tilt
        set(tilt) {
            mMask.tilt = tilt
            resetAll()
        }

    private val layoutListener: ViewTreeObserver.OnGlobalLayoutListener
        get() = ViewTreeObserver.OnGlobalLayoutListener {
            val animationStarted = isAnimationStarted
            resetAll()
            if (isAutoStart || animationStarted) {
                startShimmerAnimation()
            }
        }

    private val maskBitmap: Bitmap?
        get() {
            if (mMaskBitmap != null) {
                return mMaskBitmap
            }

            val width = mMask.maskWidth(width)
            val height = mMask.maskHeight(height)

            mMaskBitmap = createBitmapAndGcIfNecessary(width, height)
            val canvas = Canvas(mMaskBitmap!!)
            val gradient: Shader
            when (mMask.shape) {
                MaskShape.LINEAR -> {
                    val x1: Int
                    val y1: Int
                    val x2: Int
                    val y2: Int
                    when (mMask.angle) {
                        ShimmerFrameLayout.MaskAngle.CW_0 -> {
                            x1 = 0
                            y1 = 0
                            x2 = width
                            y2 = 0
                        }
                        ShimmerFrameLayout.MaskAngle.CW_90 -> {
                            x1 = 0
                            y1 = 0
                            x2 = 0
                            y2 = height
                        }
                        ShimmerFrameLayout.MaskAngle.CW_180 -> {
                            x1 = width
                            y1 = 0
                            x2 = 0
                            y2 = 0
                        }
                        ShimmerFrameLayout.MaskAngle.CW_270 -> {
                            x1 = 0
                            y1 = height
                            x2 = 0
                            y2 = 0
                        }
                        else -> {
                            x1 = 0
                            y1 = 0
                            x2 = width
                            y2 = 0
                        }
                    }
                    gradient = LinearGradient(
                        x1.toFloat(), y1.toFloat(),
                        x2.toFloat(), y2.toFloat(),
                        mMask.gradientColors,
                        mMask.gradientPositions,
                        Shader.TileMode.REPEAT
                    )
                }
                MaskShape.RADIAL -> {
                    val x = width / 2
                    val y = height / 2
                    gradient = RadialGradient(
                        x.toFloat(),
                        y.toFloat(),
                        (Math.max(width, height) / Math.sqrt(2.0)).toFloat(),
                        mMask.gradientColors,
                        mMask.gradientPositions,
                        Shader.TileMode.REPEAT
                    )
                }
                else -> {
                    val x1: Int
                    val y1: Int
                    val x2: Int
                    val y2: Int
                    when (mMask.angle) {
                        ShimmerFrameLayout.MaskAngle.CW_0 -> {
                            x1 = 0
                            y1 = 0
                            x2 = width
                            y2 = 0
                        }
                        ShimmerFrameLayout.MaskAngle.CW_90 -> {
                            x1 = 0
                            y1 = 0
                            x2 = 0
                            y2 = height
                        }
                        ShimmerFrameLayout.MaskAngle.CW_180 -> {
                            x1 = width
                            y1 = 0
                            x2 = 0
                            y2 = 0
                        }
                        ShimmerFrameLayout.MaskAngle.CW_270 -> {
                            x1 = 0
                            y1 = height
                            x2 = 0
                            y2 = 0
                        }
                        else -> {
                            x1 = 0
                            y1 = 0
                            x2 = width
                            y2 = 0
                        }
                    }
                    gradient = LinearGradient(
                        x1.toFloat(),
                        y1.toFloat(),
                        x2.toFloat(),
                        y2.toFloat(),
                        mMask.gradientColors,
                        mMask.gradientPositions,
                        Shader.TileMode.REPEAT
                    )
                }
            }
            canvas.rotate(mMask.tilt, (width / 2).toFloat(), (height / 2).toFloat())
            val paint = Paint()
            paint.shader = gradient
            val padding = (Math.sqrt(2.0) * Math.max(width, height)).toInt() / 2
            canvas.drawRect(
                (-padding).toFloat(),
                (-padding).toFloat(),
                (width + padding).toFloat(),
                (height + padding).toFloat(),
                paint
            )

            return mMaskBitmap
        }

    private val shimmerAnimation: Animator
        get() {
            if (mAnimator != null) {
                return mAnimator as ValueAnimator
            }
            val width = width
            val height = height
            when (mMask.shape) {
                MaskShape.LINEAR -> when (mMask.angle) {
                    MaskAngle.CW_0 -> mMaskTranslation!![-width, 0, width] = 0
                    MaskAngle.CW_90 -> mMaskTranslation!![0, -height, 0] = height
                    MaskAngle.CW_180 -> mMaskTranslation!![width, 0, -width] = 0
                    MaskAngle.CW_270 -> mMaskTranslation!![0, height, 0] = -height
                    else -> mMaskTranslation!![-width, 0, width] = 0
                }
                else -> when (mMask.angle) {
                    MaskAngle.CW_0 -> mMaskTranslation!![-width, 0, width] = 0
                    MaskAngle.CW_90 -> mMaskTranslation!![0, -height, 0] = height
                    MaskAngle.CW_180 -> mMaskTranslation!![width, 0, -width] = 0
                    MaskAngle.CW_270 -> mMaskTranslation!![0, height, 0] = -height
                    else -> mMaskTranslation!![-width, 0, width] = 0
                }
            }
            mAnimator = ValueAnimator.ofFloat(0.0f, 1.0f + repeatDelay.toFloat() / duration)
            mAnimator!!.duration = (duration + repeatDelay).toLong()
            mAnimator!!.repeatCount = repeatCount
            mAnimator!!.repeatMode = repeatMode
            mAnimator!!.addUpdateListener { animation ->
                val value = Math.max(0.0f, Math.min(1.0f, animation.animatedValue as Float))
                setMaskOffsetX((mMaskTranslation!!.fromX * (1 - value) + mMaskTranslation!!.toX * value).toInt())
                setMaskOffsetY((mMaskTranslation!!.fromY * (1 - value) + mMaskTranslation!!.toY * value).toInt())
            }
            return mAnimator as ValueAnimator
        }

    enum class MaskShape {
        LINEAR,
        RADIAL
    }

    enum class MaskAngle {
        CW_0,
        CW_90,
        CW_180,
        CW_270
    }

    private class Mask {

        var angle: MaskAngle? = null
        var tilt: Float = 0.toFloat()
        var dropoff: Float = 0.toFloat()
        var fixedWidth: Int = 0
        var fixedHeight: Int = 0
        var intensity: Float = 0.toFloat()
        var relativeWidth: Float = 0.toFloat()
        var relativeHeight: Float = 0.toFloat()
        var shape: MaskShape? = null

        val gradientColors: IntArray
            get() {
                when (shape) {
                    MaskShape.LINEAR -> return intArrayOf(
                        Color.TRANSPARENT,
                        Color.BLACK,
                        Color.BLACK,
                        Color.TRANSPARENT
                    )
                    MaskShape.RADIAL -> return intArrayOf(
                        Color.BLACK,
                        Color.BLACK,
                        Color.TRANSPARENT
                    )
                    else -> return intArrayOf(Color.TRANSPARENT, Color.BLACK, Color.BLACK, Color.TRANSPARENT)
                }
            }

        val gradientPositions: FloatArray
            get() {
                when (shape) {
                    MaskShape.LINEAR -> return floatArrayOf(
                        Math.max(
                            (1.0f - intensity - dropoff) / 2,
                            0.0f
                        ),
                        Math.max((1.0f - intensity) / 2, 0.0f),
                        Math.min((1.0f + intensity) / 2, 1.0f),
                        Math.min((1.0f + intensity + dropoff) / 2, 1.0f)
                    )
                    MaskShape.RADIAL -> return floatArrayOf(
                        0.0f,
                        Math.min(intensity, 1.0f),
                        Math.min(intensity + dropoff, 1.0f)
                    )
                    else -> return floatArrayOf(
                        Math.max((1.0f - intensity - dropoff) / 2, 0.0f),
                        Math.max((1.0f - intensity) / 2, 0.0f),
                        Math.min((1.0f + intensity) / 2, 1.0f),
                        Math.min((1.0f + intensity + dropoff) / 2, 1.0f)
                    )
                }
            }

        fun maskWidth(width: Int): Int {
            return if (fixedWidth > 0) fixedWidth else (width * relativeWidth).toInt()
        }

        fun maskHeight(height: Int): Int {
            return if (fixedHeight > 0) fixedHeight else (height * relativeHeight).toInt()
        }
    }

    private class MaskTranslation {

        var fromX: Int = 0
        var fromY: Int = 0
        var toX: Int = 0
        var toY: Int = 0

        operator fun set(fromX: Int, fromY: Int, toX: Int, toY: Int) {
            this.fromX = fromX
            this.fromY = fromY
            this.toX = toX
            this.toY = toY
        }
    }

    init {

        setWillNotDraw(false)

        mMask = Mask()
        mAlphaPaint = Paint()
        mMaskPaint = Paint()
        mMaskPaint.isAntiAlias = true
        mMaskPaint.isDither = true
        mMaskPaint.isFilterBitmap = true
        mMaskPaint.xfermode = DST_IN_PORTER_DUFF_XFERMODE

        useDefaults()

        if (attrs != null) {
            val a = context.obtainStyledAttributes(attrs, R.styleable.ShimmerFrameLayout, 0, 0)
            try {
                if (a.hasValue(R.styleable.ShimmerFrameLayout_auto_start)) {
                    isAutoStart = a.getBoolean(R.styleable.ShimmerFrameLayout_auto_start, false)
                }
                if (a.hasValue(R.styleable.ShimmerFrameLayout_base_alpha)) {
                    baseAlpha = a.getFloat(R.styleable.ShimmerFrameLayout_base_alpha, 0f)
                }
                if (a.hasValue(R.styleable.ShimmerFrameLayout_duration)) {
                    duration = a.getInt(R.styleable.ShimmerFrameLayout_duration, 0)
                }
                if (a.hasValue(R.styleable.ShimmerFrameLayout_repeat_count)) {
                    repeatCount = a.getInt(R.styleable.ShimmerFrameLayout_repeat_count, 0)
                }
                if (a.hasValue(R.styleable.ShimmerFrameLayout_repeat_delay)) {
                    repeatDelay = a.getInt(R.styleable.ShimmerFrameLayout_repeat_delay, 0)
                }
                if (a.hasValue(R.styleable.ShimmerFrameLayout_repeat_mode)) {
                    repeatMode = a.getInt(R.styleable.ShimmerFrameLayout_repeat_mode, 0)
                }

                if (a.hasValue(R.styleable.ShimmerFrameLayout_angle)) {
                    val angle = a.getInt(R.styleable.ShimmerFrameLayout_angle, 0)
                    when (angle) {
                        0 -> mMask.angle = MaskAngle.CW_0
                        90 -> mMask.angle = MaskAngle.CW_90
                        180 -> mMask.angle = MaskAngle.CW_180
                        270 -> mMask.angle = MaskAngle.CW_270
                        else -> mMask.angle = MaskAngle.CW_0
                    }
                }

                if (a.hasValue(R.styleable.ShimmerFrameLayout_shape)) {
                    val shape = a.getInt(R.styleable.ShimmerFrameLayout_shape, 0)
                    when (shape) {
                        0 -> mMask.shape = MaskShape.LINEAR
                        1 -> mMask.shape = MaskShape.RADIAL
                        else -> mMask.shape = MaskShape.LINEAR
                    }
                }

                if (a.hasValue(R.styleable.ShimmerFrameLayout_dropoff)) {
                    mMask.dropoff = a.getFloat(R.styleable.ShimmerFrameLayout_dropoff, 0f)
                }
                if (a.hasValue(R.styleable.ShimmerFrameLayout_fixed_width)) {
                    mMask.fixedWidth = a.getDimensionPixelSize(R.styleable.ShimmerFrameLayout_fixed_width, 0)
                }
                if (a.hasValue(R.styleable.ShimmerFrameLayout_fixed_height)) {
                    mMask.fixedHeight = a.getDimensionPixelSize(R.styleable.ShimmerFrameLayout_fixed_height, 0)
                }
                if (a.hasValue(R.styleable.ShimmerFrameLayout_intensity)) {
                    mMask.intensity = a.getFloat(R.styleable.ShimmerFrameLayout_intensity, 0f)
                }
                if (a.hasValue(R.styleable.ShimmerFrameLayout_relative_width)) {
                    mMask.relativeWidth = a.getFloat(R.styleable.ShimmerFrameLayout_relative_width, 0f)
                }
                if (a.hasValue(R.styleable.ShimmerFrameLayout_relative_height)) {
                    mMask.relativeHeight = a.getFloat(R.styleable.ShimmerFrameLayout_relative_height, 0f)
                }
                if (a.hasValue(R.styleable.ShimmerFrameLayout_tilt)) {
                    mMask.tilt = a.getFloat(R.styleable.ShimmerFrameLayout_tilt, 0f)
                }
            } finally {
                a.recycle()
            }
        }
    }

    fun useDefaults() {
        // Set defaults
        isAutoStart = false
        duration = 1000
        repeatCount = ObjectAnimator.INFINITE
        repeatDelay = 0
        repeatMode = ObjectAnimator.RESTART

        mMask.angle = MaskAngle.CW_0
        mMask.shape = MaskShape.LINEAR
        mMask.dropoff = 0.5f
        mMask.fixedWidth = 0
        mMask.fixedHeight = 0
        mMask.intensity = 0.0f
        mMask.relativeWidth = 1.0f
        mMask.relativeHeight = 1.0f
        mMask.tilt = 20f

        mMaskTranslation = MaskTranslation()

        baseAlpha = 0.3f

        resetAll()
    }

    fun setRelativeWidth(relativeWidth: Int) {
        mMask.relativeWidth = relativeWidth.toFloat()
        resetAll()
    }

    fun setRelativeHeight(relativeHeight: Int) {
        mMask.relativeHeight = relativeHeight.toFloat()
        resetAll()
    }

    fun startShimmerAnimation() {
        if (isAnimationStarted) {
            return
        }
        val animator = shimmerAnimation
        animator.start()
        isAnimationStarted = true
    }

    fun stopShimmerAnimation() {
        if (mAnimator != null) {
            mAnimator!!.end()
            mAnimator!!.removeAllUpdateListeners()
            mAnimator!!.cancel()
        }
        mAnimator = null
        isAnimationStarted = false
    }

    private fun setMaskOffsetX(maskOffsetX: Int) {
        if (mMaskOffsetX == maskOffsetX) {
            return
        }
        mMaskOffsetX = maskOffsetX
        invalidate()
    }

    private fun setMaskOffsetY(maskOffsetY: Int) {
        if (mMaskOffsetY == maskOffsetY) {
            return
        }
        mMaskOffsetY = maskOffsetY
        invalidate()
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        if (mGlobalLayoutListener == null) {
            mGlobalLayoutListener = layoutListener
        }
        viewTreeObserver.addOnGlobalLayoutListener(mGlobalLayoutListener)
    }

    override fun onDetachedFromWindow() {
        stopShimmerAnimation()
        if (mGlobalLayoutListener != null) {
            viewTreeObserver.removeGlobalOnLayoutListener(mGlobalLayoutListener)
            mGlobalLayoutListener = null
        }
        super.onDetachedFromWindow()
    }

    override fun dispatchDraw(canvas: Canvas) {
        if (!isAnimationStarted || width <= 0 || height <= 0) {
            super.dispatchDraw(canvas)
            return
        }
        dispatchDrawUsingBitmap(canvas)
    }

    private fun dispatchDrawUsingBitmap(canvas: Canvas): Boolean {
        val unmaskBitmap = tryObtainRenderUnmaskBitmap()
        val maskBitmap = tryObtainRenderMaskBitmap()
        if (unmaskBitmap == null || maskBitmap == null) {
            return false
        }
        // First draw a desaturated version
        drawUnmasked(Canvas(unmaskBitmap))
        canvas.drawBitmap(unmaskBitmap, 0f, 0f, mAlphaPaint)

        // Then draw the masked version
        drawMasked(Canvas(maskBitmap))
        canvas.drawBitmap(maskBitmap, 0f, 0f, null)

        return true
    }

    private fun tryObtainRenderUnmaskBitmap(): Bitmap? {
        if (mRenderUnmaskBitmap == null) {
            mRenderUnmaskBitmap = tryCreateRenderBitmap()
        }
        return mRenderUnmaskBitmap
    }

    private fun tryObtainRenderMaskBitmap(): Bitmap? {
        if (mRenderMaskBitmap == null) {
            mRenderMaskBitmap = tryCreateRenderBitmap()
        }
        return mRenderMaskBitmap
    }

    private fun tryCreateRenderBitmap(): Bitmap? {
        val width = width
        val height = height
        try {
            return createBitmapAndGcIfNecessary(width, height)
        } catch (e: OutOfMemoryError) {
            val logMessage = "ShimmerFrameLayout failed to create working bitmap"
            val logMessageStringBuilder = StringBuilder(logMessage)
            logMessageStringBuilder.append(" (width = ")
            logMessageStringBuilder.append(width)
            logMessageStringBuilder.append(", height = ")
            logMessageStringBuilder.append(height)
            logMessageStringBuilder.append(")\n\n")
            for (stackTraceElement in Thread.currentThread().stackTrace) {
                logMessageStringBuilder.append(stackTraceElement.toString())
                logMessageStringBuilder.append("\n")
            }
        }

        return null
    }

    private fun drawUnmasked(renderCanvas: Canvas) {
        renderCanvas.drawColor(0, PorterDuff.Mode.CLEAR)
        super.dispatchDraw(renderCanvas)
    }

    private fun drawMasked(renderCanvas: Canvas) {
        val maskBitmap = maskBitmap ?: return

        renderCanvas.clipRect(
            mMaskOffsetX,
            mMaskOffsetY,
            mMaskOffsetX + maskBitmap.width,
            mMaskOffsetY + maskBitmap.height
        )
        renderCanvas.drawColor(0, PorterDuff.Mode.CLEAR)
        super.dispatchDraw(renderCanvas)

        renderCanvas.drawBitmap(maskBitmap, mMaskOffsetX.toFloat(), mMaskOffsetY.toFloat(), mMaskPaint)
    }

    private fun resetAll() {
        stopShimmerAnimation()
        resetMaskBitmap()
        resetRenderedView()
    }

    private fun resetMaskBitmap() {
        if (mMaskBitmap != null) {
            mMaskBitmap!!.recycle()
            mMaskBitmap = null
        }
    }

    private fun resetRenderedView() {
        if (mRenderUnmaskBitmap != null) {
            mRenderUnmaskBitmap!!.recycle()
            mRenderUnmaskBitmap = null
        }

        if (mRenderMaskBitmap != null) {
            mRenderMaskBitmap!!.recycle()
            mRenderMaskBitmap = null
        }
    }

    companion object {

        private val DST_IN_PORTER_DUFF_XFERMODE = PorterDuffXfermode(PorterDuff.Mode.DST_IN)

        private fun clamp(min: Float, max: Float, value: Float): Float {
            return Math.min(max, Math.max(min, value))
        }

        protected fun createBitmapAndGcIfNecessary(width: Int, height: Int): Bitmap {
            try {
                return Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
            } catch (e: OutOfMemoryError) {
                System.gc()
                return Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
            }

        }
    }
}
