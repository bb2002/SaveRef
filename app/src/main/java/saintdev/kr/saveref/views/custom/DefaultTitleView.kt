package saintdev.kr.saveref.views.custom

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.RelativeLayout
import android.widget.TextView
import saintdev.kr.saveref.R

class DefaultTitleView(context: Context, attrs: AttributeSet) : RelativeLayout(context, attrs) {
    private lateinit var titleView: TextView

    init {
        initView()
        setViewData(attrs)
    }

    private fun initView() {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val v = inflater.inflate(R.layout.csv_default_title, this, false)
        addView(v)

        this.titleView = v.findViewById(R.id.csv_title)


    }

    private fun setViewData(attrs: AttributeSet) {
        val typedArr = context.obtainStyledAttributes(attrs, R.styleable.DefaultTitleView)

        // text 를 적용한다.
        this.titleView.text = typedArr.getString(R.styleable.DefaultTitleView_text)

        // text color 를 적용한다.
        this.titleView.setTextColor(typedArr.getColor(R.styleable.DefaultTitleView_textColor, 0))

        typedArr.recycle()
    }
}