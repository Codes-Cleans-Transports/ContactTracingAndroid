package com.example.gitaplication.firstScreen

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.size
import com.core.statefulview.SceneView
import com.example.gitaplication.R
import com.multiplatform.util.Diff
import kotlinx.android.synthetic.main.view_main.view.*


class MainView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : SceneView<MainState>(context, attrs, defStyleAttr) {


    override fun createView(inflater: LayoutInflater, parent: ViewGroup): View =
        inflater.inflate(R.layout.view_main, parent, false)

    override fun onViewCreated(view: View) {
        super.onViewCreated(view)

        iAmSick.setOnClickListener {
            dispatchAction(MainAction.ShowDialog(context))
        }

        url.setOnClickListener {
            dispatchAction(MainAction.GoToUrl(context))
        }

        safety.text = "Your personal risk level is: "
    }

    override fun render(state: MainState, diff: Diff<MainState>) {

        if (diff.by { it.status }) {
            status.text = state.status.status.capitalize()
        }

        if (diff.by { it.status }) {
            val risk = (1.00-state.status.safety.toDouble()).round(3)*100
            percent.text = "$risk%"
        }

        if (diff.by { it.status }) {
            if (state.status.status == "positive") {
                iAmSick.visibility = View.INVISIBLE
                message.visibility = View.VISIBLE
            }
        }

//        if (diff.by { it.isLoading }) {
//            progressIndicator.isVisible = state.isLoading
//        }
    }
    fun Double.round(decimals: Int = 2): Double = "%.${decimals}f".format(this).toDouble()
}
