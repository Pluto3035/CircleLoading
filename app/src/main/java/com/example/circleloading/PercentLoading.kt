package com.example.circleloading

import android.animation.ValueAnimator
import android.content.Context
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View



/**
 * @Description
 * 代码高手
 */
class PercentLoading : View {


    //下载的进度
    var progress=0f
     set(value) {
         field=value
         invalidate()
     }

    //设置圆环背景颜色
    var progressBgColor = Color.CYAN
        //一开始并不会直接调用此set方法
       set(value) {
           field = value
           progressBgPaint.color = value
       }

    //设置进度条颜色
   var progressFgColor=Color.MAGENTA
    set(value) {
        field=value
        progressFgPaint.color=value
    }
    //字体颜色
    var textColor = Color.BLACK

    //画笔的宽度 mstrokeWidth
    private val mstrokeWidth = 50f
    //定义圆心的坐标和半径
    private var cx = 0f
    private var cy = 0f
    private var radius = 0f
    //定义绘制圆的画笔
    private val progressBgPaint =Paint().apply {
        color = progressBgColor
        style = Paint.Style.STROKE
        strokeWidth = mstrokeWidth
    }

    private var progressFgPaint=Paint().apply {
        color = progressFgColor
        style = Paint.Style.STROKE
        strokeWidth = mstrokeWidth
    }

    private var textPaint=Paint().apply {
        color = textColor
        style = Paint.Style.FILL
        textSize=100f
        textAlign = Paint.Align.CENTER
    }

    //代码配置
    constructor(context: Context):super(context){}
    //xml配置
    constructor(context: Context,attrs:AttributeSet):super(context,attrs){
        //解析xml中配置的属性
        //将xml中传递过来的attrs解析出来
        //attrs  xml中为自定义的属性设置的值和属性名的集合
        //R.styleable.PercentLoading 依据哪个文件进行解析
        val typedArray :TypedArray=
          context.obtainStyledAttributes(attrs,R.styleable.PercentLoading)

        //取一个颜色
        //你要取自定义属性的哪个属性  类名——自定义属性名
        //R.styleable表示自定义属性
        progressBgColor=
            typedArray.getColor(R.styleable.PercentLoading_backgroundColor,Color.BLACK)

        //进度条颜色
      progressFgColor=
          typedArray.getColor(R.styleable.PercentLoading_foregroundColor,Color.MAGENTA)

        //文字颜色
      textColor=
         typedArray.getColor(R.styleable.PercentLoading_android_textColor,Color.BLACK)
        //使用完毕需要释放内存
        typedArray.recycle()
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        cx = width/2f
        cy = height/2f
        //半径等于最小边的一半
        //为了防止圆环超过定义的矩形，要减去画笔的宽度
        radius = (Math.min(width,height))/2f -mstrokeWidth
    }
    override fun onDraw(canvas: Canvas?) {
        //绘制背景圆圈
        canvas?.drawCircle(cx,cy,radius,progressBgPaint)
        //绘制进度的圆弧   useCenter 使不使用中心点，要不要和中心点进行链接
        canvas?.drawArc(mstrokeWidth,mstrokeWidth, width.toFloat()-mstrokeWidth,height.toFloat()-mstrokeWidth,-90f,360f*progress,false,progressFgPaint)

        //绘制文本
        val text = "${(progress*100).toInt()}%"
        //获取文字信息
       val metrics= textPaint.fontMetrics
        val space = (metrics.descent-metrics.ascent)/2-metrics.descent
        canvas?.drawText(text,cx,cy+space,textPaint)
    }
}