package com.abyte.core.widgets

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.abyte.core.R
import com.abyte.core.ext.otherwise
import com.abyte.core.ext.yes
import com.abyte.core.utils.ResourceUtil

/**
 * 自定义点赞View
 */
class LikeView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var likeCount = 0
    private var bitmapPaint: Paint
    private var textPaint: Paint
    private var previousTextPaint: Paint
    private var circlePaint: Paint
    private var textRange: Rect
    private var numbers = FloatArray(8)  // 位数
    private lateinit var likeBitmap: Bitmap
    private lateinit var unLikeBitmap: Bitmap
    private lateinit var shineBitmap: Bitmap
    private var hasLike = false  // 是否已经点赞
    private var shineAlpha = 0f
    private var shineCircleAlpha = 0f
    private var textAlpha = 0f

    private var bitmapScale = 1f
    private var shineScale = 0f
    private var shineCircleScale = 0f

    private var isFirst = true
    private var textMove = 0f  // 文字上下移动的最大距离
    private var textTranslate = 0f  // 文字上下移动的距离


    init {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.LikeView)
        likeCount = typedArray.getInt(R.styleable.LikeView_like_count, 1024)
        typedArray.recycle()

        bitmapPaint = Paint(Paint.ANTI_ALIAS_FLAG)  // 绘制图片
        circlePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = Color.RED
            style = Paint.Style.STROKE
            strokeWidth = ResourceUtil.dp2Px(2f).toFloat()
            //设置模糊效果
            setShadowLayer(
                ResourceUtil.dp2Px(1f).toFloat(),   // 模糊半径，越大越模糊
                ResourceUtil.dp2Px(1f).toFloat(),   // 阴影的横向偏移距离，正值向下偏移 负值向上偏移
                ResourceUtil.dp2Px(1f).toFloat(),   // 纵向偏移距离，正值向下偏移，负值向上偏移
                Color.RED   // 画笔的颜色
            )
        }

        textPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = Color.GRAY
            textSize = ResourceUtil.sp2px(14f)
        }

        previousTextPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = Color.GRAY
            textSize = ResourceUtil.sp2px(14f)
        }

        textRange = Rect()  // 文字的展示范围
    }

    // 这个方法，每个View只会执行一次，而且是在Activity的resume方法之后执行，可以在这里做一些初始化操作
    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        likeBitmap = BitmapFactory.decodeResource(resources, R.drawable.ic_like)
        unLikeBitmap = BitmapFactory.decodeResource(resources, R.drawable.ic_unlike)
        shineBitmap = BitmapFactory.decodeResource(resources, R.drawable.ic_like_shining)
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        likeBitmap.recycle()
        unLikeBitmap.recycle()
        shineBitmap.recycle()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {

        val textNum = likeCount.toString()

        val textNumWidth = textPaint.measureText(textNum, 0, textNum.length) // 点赞数宽度

        val widthMeasureSpecTemp =
            MeasureSpec.makeMeasureSpec(
                (unLikeBitmap.width + textNumWidth + ResourceUtil.dp2Px(3 * 10f)).toInt(),
                MeasureSpec.EXACTLY
            )

        val heightMeasureSpecTemp = MeasureSpec.makeMeasureSpec(
            unLikeBitmap.height + ResourceUtil.dp2Px(2 * 10f),
            MeasureSpec.EXACTLY
        )

        super.onMeasure(widthMeasureSpecTemp, heightMeasureSpecTemp)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        val centerY = height / 2
        val bitmap = if (hasLike) likeBitmap else unLikeBitmap
        val bitmapWidth = bitmap.width
        val bitmapHeight = bitmap.height

        val bitmapMarginTop =
            ((height - bitmapHeight) / 2).toFloat()     // height = bitmapHeight + 2 * MARGIN_TOP = bitmapHeight + 2 * 10
        canvas?.save()  // 先保存画布状态，等下再恢复，不然后面做了缩放操作的话，会对后续绘制造成影响
        canvas?.scale(bitmapScale, bitmapScale, (bitmapWidth / 2).toFloat(), centerY.toFloat())
        canvas?.drawBitmap(
            bitmap,
            ResourceUtil.dp2Px(10f).toFloat(),   // 左上角坐标
            bitmapMarginTop,   // 顶部坐标
            bitmapPaint
        )
        canvas?.restore()

        // 绘制4个点
        val shineMarginTop =
            bitmapMarginTop - shineBitmap.height + ResourceUtil.dp2Px(6f)  // 这个数值这个需要自己慢慢适配
        bitmapPaint.alpha = (255 * shineAlpha.toInt())
        canvas?.save()
        canvas?.scale(shineScale, shineScale, (bitmapWidth / 2).toFloat(), bitmapMarginTop)
        canvas?.restore()
        bitmapPaint.alpha = 255
        hasLike.yes {
            canvas?.drawBitmap(
                shineBitmap,
                ResourceUtil.dp2Px(10 + 2f).toFloat(),  // 这个2f和上述的shineMarginTop一样，也需要自己去慢慢适配
                shineMarginTop,
                bitmapPaint
            )
        }.otherwise {
            canvas?.save()
            bitmapPaint.setAlpha(0)
            canvas?.drawBitmap(
                shineBitmap,
                ResourceUtil.dp2Px(10 + 5f).toFloat(),
                shineMarginTop,
                bitmapPaint
            )
            canvas?.restore()
            bitmapPaint.setAlpha(255)
        }

        // 绘制文字
        var textValue = likeCount.toString()
        var previousTextValue =
            hasLike.yes {
                (likeCount - 1).toString()
            }.otherwise {
                if (isFirst) {
                    isFirst = !isFirst
                    likeCount.toString()
                } else {
                    (likeCount + 1).toString()
                }
            }

        textPaint.getTextBounds(textValue, 0, textValue.length, textRange)  // 获取绘制文字的坐标，返回所有文本的边界

        var textX = (bitmapWidth + ResourceUtil.dp2Px(2 * 10f)).toFloat()

        // textRange.top + textRange.bottom的结果是文字区域高度
        val textY = (height / 2 - (textRange.top + textRange.bottom) / 2).toFloat()

        // 不同位数，整体变换
        if (textValue.length != previousTextValue.length || textMove == 0f) {
            if (hasLike) {
                circlePaint.alpha = (255 * shineCircleAlpha).toInt()
                canvas?.drawCircle(
                    (bitmapWidth / 2 + ResourceUtil.dp2Px(10f)).toFloat(),
                    (bitmapHeight / 2 + ResourceUtil.dp2Px(10f)).toFloat(),
                    (bitmapHeight / 2 + ResourceUtil.dp2Px(2f)) * shineCircleScale,
                    circlePaint
                )
                previousTextPaint.alpha = (255 * (1 - textAlpha)).toInt()
                canvas?.drawText(
                    previousTextValue,
                    textX,
                    textY - textMove + textTranslate,
                    previousTextPaint
                )
                textPaint.alpha = (255 * textAlpha).toInt()
                canvas?.drawText(textValue, textX, textY + textTranslate, textPaint)
            } else {
                previousTextPaint.alpha = (255 * (1 - textAlpha)).toInt()
                canvas?.drawText(
                    previousTextValue,
                    textX,
                    textY + textMove + textTranslate,
                    previousTextPaint
                )
                textPaint.alpha = (255 * textAlpha).toInt()
                canvas?.drawText(textValue, textX, textY + textTranslate, textPaint)
            }
            return
        }
        // 相同位数变换，将文字拆分成一个一个的字符
        textPaint.getTextWidths(textValue, numbers)  // 获取每个字符的宽度，输出到numbers中

        val chars = textValue.toCharArray()
        val previousChars = previousTextValue.toCharArray()
        for (i in chars.indices) {
            if (chars[i] == previousChars[i]) {
                textPaint.alpha = 255
                canvas?.drawText(chars[i].toString(), textX, textY, textPaint)
            } else {
                // 不等
                if (hasLike) {
                    circlePaint.alpha = (255 * shineCircleAlpha).toInt()
                    canvas?.drawCircle(
                        (bitmapWidth / 2 + ResourceUtil.dp2Px(10f)).toFloat(),
                        (bitmapHeight / 2 + ResourceUtil.dp2Px(10f)).toFloat(),
                        (bitmapHeight / 2 + ResourceUtil.dp2Px(2f)) * shineCircleScale,
                        circlePaint
                    )
                    previousTextPaint.alpha = (255 * (1 - textAlpha)).toInt()
                    canvas?.drawText(
                        previousChars[i].toString(),
                        textX, textY - textMove + textTranslate, previousTextPaint
                    )
                    textPaint.alpha = (255 * textAlpha).toInt()
                    canvas?.drawText(chars[i].toString(), textX, textY + textTranslate, textPaint)
                } else {
                    previousTextPaint.alpha = (255 * (1 - textAlpha)).toInt()
                    canvas?.drawText(
                        previousChars[i].toString(),
                        textX,
                        textY + textMove + textTranslate,
                        previousTextPaint
                    )
                    textPaint.alpha = (255 * textAlpha).toInt()
                    canvas?.drawText(chars[i].toString(), textX, textY + textTranslate, textPaint)
                }
            }
            textX += numbers[i]
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when (event?.action) {
            MotionEvent.ACTION_DOWN -> click()
        }
        return true
    }

    private fun click() {
        hasLike = !hasLike
        hasLike.yes {
            likeCount++
            setLikeCount()

            val bitmapScaleAnim = ObjectAnimator.ofFloat(this, "bitmapScale", 1f, 0.8f, 1f)
            bitmapScaleAnim.duration = DURATION

            val shineAlphaAnim = ObjectAnimator.ofFloat(this, "shineAlpha", 0f, 1f)
            shineAlphaAnim.duration = DURATION

            val shineScaleAnim = ObjectAnimator.ofFloat(this, "shineScale", 0f, 1f)


            val shineCircleAlphaAnim = ObjectAnimator.ofFloat(this, "shineCircleAlpha", 0.3f, 0f)
            val shineCircleAnim = ObjectAnimator.ofFloat(this, "shineCircleScale", 0.6f, 1f)


            val animatorSet = AnimatorSet()
            animatorSet.playTogether(
                bitmapScaleAnim,
                shineAlphaAnim,
                shineScaleAnim,
                shineCircleAnim,
                shineCircleAlphaAnim
            )
            animatorSet.start()
        }.otherwise {
            likeCount--
            setLikeCount()
            val bitmapScaleAnim = ObjectAnimator.ofFloat(this, "bitmapScale", 1f, 0.8f, 1f)
            bitmapScaleAnim.duration = DURATION
            bitmapScaleAnim.start()
            setShineAlpha(0f)
        }
//        listener?.clickLike(hasLike)
    }

    private fun setLikeCount() {
        textMove = ResourceUtil.dp2Px(20f).toFloat()
        val startY = if (hasLike) textMove else -textMove   // 如果点赞，从下往上移动

        val textAlphaAnim = ObjectAnimator.ofFloat(this, "textAlpha", 0f, 1f)
            .apply {
                duration = DURATION
            }

        val textMoveAnim = ObjectAnimator.ofFloat(this, "textTranslate", startY, 0f)
            .apply {
                duration = DURATION
            }

        AnimatorSet().apply {
            playTogether(textAlphaAnim, textMoveAnim)
            start()
        }
    }


    fun setTextAlpha(textAlpha: Float) {
        this.textAlpha = textAlpha
        invalidate()
    }

    fun setTextTranslate(textTranslate: Float) {
        this.textTranslate = textTranslate
        invalidate()
    }

    fun setBitmapScale(bitmapScale: Float) {
        this.bitmapScale = bitmapScale
        invalidate()
    }

    fun setShineAlpha(shineAlpha: Float) {
        this.shineAlpha = shineAlpha
        invalidate()
    }

    fun setShineScale(shineScale: Float) {
        this.shineScale = shineScale
        invalidate()
    }

    fun setShineCircleAlpha(shineCircleAlpha: Float) {
        this.shineCircleAlpha = shineCircleAlpha
        invalidate()
    }

    fun setShineCircleScale(shineCircleScale: Float) {
        this.shineCircleScale = shineCircleScale
        invalidate()
    }

    fun setLikeCount(likeCount: Int) {
        this.likeCount = likeCount
    }

    fun setLike(like: Boolean) {
        this.hasLike = like
    }

    fun isLike() = hasLike

    fun setOnItemClickLikeListener(onItemClickLikeListener: OnItemClickLikeListener) {
        this.listener = onItemClickLikeListener
    }

    private var listener: OnItemClickLikeListener? = null

    interface OnItemClickLikeListener {
        fun clickLike(isLike: Boolean)
    }


    companion object {

        private const val DURATION = 250L
    }
}