package anton.dev.profinet.presentation.main_screen.ui

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.AccelerateInterpolator
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import anton.dev.profinet.databinding.LiCustomerBinding
import anton.dev.profinet.presentation.main_screen.vm.model.CustomerListItem

internal class MainScreenCustomerAdapter :
    ListAdapter<CustomerListItem, MainScreenCustomerAdapter.CustomerViewHolder>(DiffCallback()) {

    class CustomerViewHolder(
        val binding: LiCustomerBinding
    ) : ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomerViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        val binding: LiCustomerBinding =
            LiCustomerBinding.inflate(inflater!!, parent, false)

        return CustomerViewHolder(binding)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onBindViewHolder(holder: CustomerViewHolder, position: Int) = with(currentList[position]) {
        holder.binding.liCustomerName.text = name
        holder.binding.liCustomerRatingCounter.text = "%.2f".format(rating)
        holder.binding.liCustomerAge.value = age.toString()
        holder.binding.liCustomerExperience.value = experience.toString()
        holder.binding.liCustomerSpeciality.value = speciality

        holder.binding.root.setOnTouchListener { v, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> shrinkCard(v)
                else -> expandCard(v)
            }
        }
    }

    private fun shrinkCard(view: View): Boolean {
        view.animate().apply {
            scaleX(0.94f)
            scaleY(0.94f)
            duration = 150L
            interpolator = AccelerateDecelerateInterpolator()
        }.start()

        return true
    }

    private fun expandCard(view: View): Boolean {
        view.animate().apply {
            scaleX(1f)
            scaleY(1f)
            duration = 100L
            interpolator = AccelerateInterpolator()
        }.start()

        return false
    }

    private class DiffCallback : DiffUtil.ItemCallback<CustomerListItem>() {

        override fun areItemsTheSame(oldItem: CustomerListItem, newItem: CustomerListItem) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: CustomerListItem, newItem: CustomerListItem) =
            oldItem == newItem
    }
}