package anton.dev.profinet.presentation.search_city.ui

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.AccelerateInterpolator
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import anton.dev.profinet.databinding.LiCityBinding
import anton.dev.profinet.presentation.search_city.vm.model.CityModel
import kotlinx.coroutines.flow.MutableStateFlow

internal class SearchCityAdapter :
    ListAdapter<CityModel, SearchCityAdapter.CityViewHolder>(DiffCallback()) {

    val selectedCity = MutableStateFlow<CityModel?>(null)

    class CityViewHolder(
        val binding: LiCityBinding
    ) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        val binding: LiCityBinding =
            LiCityBinding.inflate(inflater!!, parent, false)

        return CityViewHolder(binding)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onBindViewHolder(holder: CityViewHolder, position: Int) = with(currentList[position]) {


        holder.binding.root.setOnTouchListener { v, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> shrinkCard(v)
                else -> expandCard(v)
            }
            return@setOnTouchListener false
        }
        holder.binding.liCityName.text = name
        holder.binding.root.setOnClickListener {
            selectedCity.tryEmit(this)
        }
    }

    private fun shrinkCard(view: View) {
        view.alpha = 0.5f
        view.animate().apply {
            scaleX(0.94f)
            scaleY(0.94f)
            duration = 150L
            interpolator = AccelerateDecelerateInterpolator()
        }.start()
    }

    private fun expandCard(view: View) {
        view.alpha = 1f
        view.animate().apply {
            scaleX(1f)
            scaleY(1f)
            duration = 100L
            interpolator = AccelerateInterpolator()
        }.start()
    }

    private class DiffCallback : DiffUtil.ItemCallback<CityModel>() {

        override fun areItemsTheSame(oldItem: CityModel, newItem: CityModel) =
            oldItem.name == newItem.name

        override fun areContentsTheSame(oldItem: CityModel, newItem: CityModel) =
            oldItem == newItem
    }
}