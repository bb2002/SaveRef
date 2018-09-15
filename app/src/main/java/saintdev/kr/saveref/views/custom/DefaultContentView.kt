package saintdev.kr.saveref.views.custom

import android.content.Context
import android.graphics.Color
import android.support.v7.widget.CardView
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.RelativeLayout
import android.widget.TextView
import saintdev.kr.saveref.R

class DefaultContentView(context: Context, attrs: AttributeSet) : RelativeLayout(context, attrs) {
    private lateinit var titleView: TextView
    private lateinit var titleContainer: CardView

    init {
        initView()
        setViewData(attrs)
    }

    private fun initView() {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val v = inflater.inflate(R.layout.csv_default_content, this, false)
        addView(v)

        this.titleView = v.findViewById(R.id.csv_content_content)
        this.titleContainer = v.findViewById(R.id.csv_content_container)
    }

    private fun setViewData(attrs: AttributeSet) {
        val typedArr = context.obtainStyledAttributes(attrs, R.styleable.DefaultContentView)

        // text 를 적용한다.
        this.titleView.text = typedArr.getString(R.styleable.DefaultContentView_text)

        // text color 를 적용한다.
        this.titleView.setTextColor(typedArr.getColor(R.styleable.DefaultContentView_textColor, 0))

        // background 를 적용한다.
        this.titleContainer.setCardBackgroundColor(typedArr.getColor(R.styleable.DefaultContentView_bgColor, Color.WHITE))

        typedArr.recycle()
    }

    fun setText(text: String) {
        this.titleView.text = text
    }
}