package anton.dev.uikit.error

import android.animation.Animator

abstract class ViewPropertyAnimationListener : Animator.AnimatorListener {

    override fun onAnimationStart(animation: Animator) = Unit

    override fun onAnimationEnd(animation: Animator) = Unit

    override fun onAnimationCancel(animation: Animator) = Unit

    override fun onAnimationRepeat(animation: Animator) = Unit
}