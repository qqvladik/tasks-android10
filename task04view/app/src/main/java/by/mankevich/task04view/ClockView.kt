package by.mankevich.task04view

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.Handler
import android.os.Message
import android.util.AttributeSet
import android.view.View
import java.util.*
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.sin


class ClockView : View {
    companion object{
        private const val DEFAULT_WIDTH = 200 // default width
        private const val CLOCK_VIEW_TAG = "ClockView"
    }

    private var paintOuterLine = Paint()
    private var paintScale = Paint()
    private var paintSecond = Paint()
    private var paintMinute = Paint()
    private var paintHour = Paint()

    private var arrowSecondColor: Int = Color.BLACK
    private var arrowMinuteColor: Int = Color.BLACK
    private var arrowHourColor: Int = Color.BLACK

    private var distToCenter: Float = 390f

    private var arrowSecondLengthPercent: Int = 40
    private var arrowMinuteLengthPercent: Int = 50
    private var arrowHourLengthPercent: Int = 60
    private var arrowSecondWidth: Float = 5f
    private var arrowMinuteWidth: Float = 10f
    private var arrowHourWidth: Float = 15f

    private var outerCircleWidth = distToCenter/26//15f
    private var scaleWidth = distToCenter/15//25
    private var scaleLength = (distToCenter/6.5).toFloat()//60

    private var hour: Int? = null
    private var minute: Int? = null
    private var second: Int? = null
    private var refreshThread: Thread? = null
    private var mHandler = @SuppressLint("HandlerLeak")
    object : Handler() {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            when (msg.what) {
                0 -> {
                    invalidate()
                }
            }
        }
    }

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs){
        val typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.ClockView)
        arrowSecondColor = typedArray.getColor(R.styleable.ClockView_arrowSecondColor, 0)
        arrowMinuteColor = typedArray.getColor(R.styleable.ClockView_arrowMinuteColor, 0)
        arrowHourColor = typedArray.getColor(R.styleable.ClockView_arrowHourColor, 0)
        arrowSecondLengthPercent = correctArrowLengthValues(typedArray
            .getInt(R.styleable.ClockView_arrowSecondLengthPercent, 0))
        arrowMinuteLengthPercent = correctArrowLengthValues(typedArray
            .getInt(R.styleable.ClockView_arrowMinuteLengthPercent, 0))
        arrowHourLengthPercent = correctArrowLengthValues(typedArray
            .getInt(R.styleable.ClockView_arrowHourLengthPercent, 0))
        arrowSecondWidth = correctArrowWidthValues(typedArray
            .getDimension(R.styleable.ClockView_arrowSecondWidth, 0f))
        arrowMinuteWidth = correctArrowWidthValues(typedArray
            .getDimension(R.styleable.ClockView_arrowMinuteWidth, 0f))
        arrowHourWidth = correctArrowWidthValues(typedArray
            .getDimension(R.styleable.ClockView_arrowHourWidth, 0f))
        typedArray.recycle()
    }
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        refreshThread=Thread{
            while (true) {
                try {
                    Thread.sleep(500)
                    mHandler.sendEmptyMessage(0)
                } catch (e: InterruptedException) {
                    break
                }
            }
        }
        refreshThread?.start()
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        mHandler.removeCallbacksAndMessages(null)
        refreshThread?.interrupt()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        val widthSpecMode = MeasureSpec.getMode(widthMeasureSpec)
        //val widthSpecSize = MeasureSpec.getSize(widthMeasureSpec)
        val heightSpecMode = MeasureSpec.getMode(heightMeasureSpec)
        //val heightSpecSize = MeasureSpec.getSize(heightMeasureSpec)
        val result = if (widthSpecMode == MeasureSpec.AT_MOST && heightSpecMode == MeasureSpec.AT_MOST) {
            DEFAULT_WIDTH
        } else {
            min(measuredWidth, measuredHeight)
        }

        setMeasuredDimension(result, result)
        distToCenter=result/2.toFloat()
        outerCircleWidth = distToCenter/26//15f
        scaleWidth = distToCenter/15//25
        scaleLength = (distToCenter/6.5).toFloat()//60

        with(paintOuterLine){
            color=Color.BLACK
            strokeWidth = outerCircleWidth
            isAntiAlias=true
            style=Paint.Style.STROKE
        }

        with(paintScale){
            color=Color.BLACK
            strokeWidth = scaleWidth
            isAntiAlias=true
            style=Paint.Style.STROKE
        }

        with(paintSecond){
            color=arrowSecondColor
            strokeWidth = arrowSecondWidth
            isAntiAlias=true
            style=Paint.Style.STROKE
        }

        with(paintMinute){
            color=arrowMinuteColor
            strokeWidth = arrowMinuteWidth
            isAntiAlias=true
            style=Paint.Style.STROKE
        }

        with(paintHour){
            color=arrowHourColor
            strokeWidth = arrowHourWidth
            isAntiAlias=true
            style=Paint.Style.STROKE
        }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        getCurrentTime()
        drawOuterCircle(canvas)
        drawScale(canvas)
        drawHands(canvas)
    }

    private fun getCurrentTime(){
        val calendar = Calendar.getInstance()
        hour = calendar.get(Calendar.HOUR)
        minute = calendar.get(Calendar.MINUTE)
        second = calendar.get(Calendar.SECOND)
    }

    private fun drawOuterCircle(canvas: Canvas?){
        canvas?.drawCircle(distToCenter, distToCenter,
            distToCenter-(outerCircleWidth/2), paintOuterLine)
    }

    private fun drawScale(canvas: Canvas?){
        canvas?.save()
        for (i in 0..11){
            canvas?.drawLine(distToCenter, outerCircleWidth/2, distToCenter,
                outerCircleWidth+scaleLength, paintScale)
            canvas?.rotate(360/12.toFloat(), distToCenter, distToCenter)
        }
        canvas?.restore()//восстанавливает исходное состояние
    }

    private fun drawHands(canvas: Canvas?) {
        drawSecond(canvas)
        drawMinute(canvas)
        drawHour(canvas)
    }

    private fun drawSecond(canvas: Canvas?) {
        val longR = distToCenter*arrowSecondLengthPercent/100
        val shortR = (distToCenter/6.5*arrowSecondLengthPercent/100).toFloat()
        val angle = second!!.times(Math.PI / 30).toFloat()
        drawHand(angle, longR, shortR, canvas, paintSecond)
    }

    private fun drawMinute(canvas: Canvas?) {
        val longR = distToCenter*arrowMinuteLengthPercent/100
        val shortR = (distToCenter/7.8*arrowMinuteLengthPercent/100).toFloat()
        val angle = (60*minute!!+second!!).times(Math.PI / 1800).toFloat()
        drawHand(angle, longR, shortR, canvas, paintMinute)
    }

    private fun drawHour(canvas: Canvas?) {
        val longR = distToCenter*arrowHourLengthPercent/100
        val shortR = (distToCenter/9.75*arrowHourLengthPercent/100).toFloat()
        val angle = ((60*hour!!+(minute!!)).times(Math.PI / 360)).toFloat()
        drawHand(angle, longR, shortR, canvas, paintHour)
    }

    private fun drawHand(angle: Float, longR: Float, shortR: Float, canvas: Canvas?, paint: Paint) {
        val startX = (distToCenter - shortR * sin(angle))
        val startY = (distToCenter + shortR * cos(angle))
        val endX = (distToCenter + longR * sin(angle))
        val endY = (distToCenter - longR * cos(angle))
        canvas?.drawLine(startX, startY, endX, endY, paint)
    }

    private fun correctArrowLengthValues(lengthPercent: Int): Int{
        if(lengthPercent>100){
            return 100
        }else if(lengthPercent<0){
            return 0
        }
        return lengthPercent
    }

    private fun correctArrowWidthValues(width: Float): Float{
        if(width>distToCenter/5){
            return (distToCenter/5)
        }else if(width<0){
            return 0f
        }
        return width
    }
}