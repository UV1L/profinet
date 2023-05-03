package anton.dev.profinet.presentation.customer_settings.ui

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.CheckedTextView
import androidx.appcompat.widget.AppCompatCheckedTextView
import androidx.collection.arraySetOf
import androidx.core.view.isGone
import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import anton.dev.profinet.databinding.LiQualificationBinding
import anton.dev.profinet.domain.models.Customer
import anton.dev.profinet.domain.models.Qualification
import anton.dev.profinet.domain.models.QualificationParams
import anton.dev.uikit.R as UiKitR

internal class CustomerQualificationAdapter(private val currentCustomer: Customer) :
    ListAdapter<QualificationParams, CustomerQualificationAdapter.QualificationViewHolder>(DiffCallback()) {

    val changedQualificationParams: MutableSet<QualificationParams> = arraySetOf()

    class QualificationViewHolder(
        val binding: LiQualificationBinding
    ) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QualificationViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        val binding: LiQualificationBinding =
            LiQualificationBinding.inflate(inflater!!, parent, false)

        return QualificationViewHolder(binding)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onBindViewHolder(holder: QualificationViewHolder, position: Int) = with(currentList[position]) {
        currentCustomer.qualifications.filter { it.qualification.name == this.qualification.name }
            .forEach {
                setSlider(holder.binding, it)
                setExperienceInput(holder.binding, it)
            }

        setCheckbox(
            holder.binding,
            currentCustomer.qualifications.getOrNull(position) ?: currentList[position]
        )

        holder.binding.liQualificationLevelSlider.addOnChangeListener { _, value, _ ->
            changedQualificationParams.find { params ->
                this.qualification.name == params.qualification.name
            }?.level = value.toInt()
        }
        holder.binding.liQualificationExperience.doOnTextChanged { text, _, _, _ ->
            text.toString().toIntOrNull()?.let {
                changedQualificationParams.find { params ->
                    this.qualification.name == params.qualification.name
                }?.experience = it
            }
        }

        Unit
    }

    fun clear() = changedQualificationParams.clear()

    private fun setSlider(binding: LiQualificationBinding, qualificationParams: QualificationParams) =
        with(binding.liQualificationLevelSlider) {
            value = qualificationParams.level.toFloat()
        }

    private fun setExperienceInput(binding: LiQualificationBinding, qualificationParams: QualificationParams) =
        with(binding.liQualificationExperience) {
            setText(qualificationParams.experience.toString())
        }

    private fun setCheckbox(binding: LiQualificationBinding, qualificationParams: QualificationParams) =
        with(binding.liQualificationCheckbox) {
            text = qualificationParams.qualification.displayName
            isChecked = currentCustomer.qualifications.any { params ->
                params.qualification == qualificationParams.qualification
            }
            binding.liQualificationLevelContainer.isGone = isChecked.not()
            when (isChecked) {
                true -> qualificationChecked(this, qualificationParams)
                false -> qualificationUnchecked(this, qualificationParams)
            }
            setOnClickListener {
                isChecked = (it as CheckedTextView).isChecked.not()
                binding.liQualificationLevelContainer.isGone = isChecked.not()
                when (isChecked) {
                    true -> qualificationChecked(this, qualificationParams)
                    false -> qualificationUnchecked(this, qualificationParams)
                }
            }
        }

    private fun qualificationChecked(checkbox: AppCompatCheckedTextView, qualificationParams: QualificationParams) {
        checkbox.setCheckMarkDrawable(
            UiKitR.drawable.baseline_check_box_filled_24
        )
        changedQualificationParams.add(qualificationParams)

    }

    private fun qualificationUnchecked(checkbox: AppCompatCheckedTextView, qualificationParams: QualificationParams) {
        checkbox.setCheckMarkDrawable(
            UiKitR.drawable.baseline_check_box_blank_24
        )
        changedQualificationParams.removeIf { it.qualification.name == qualificationParams.qualification.name }
    }

    private class DiffCallback : DiffUtil.ItemCallback<QualificationParams>() {

        override fun areItemsTheSame(oldItem: QualificationParams, newItem: QualificationParams) =
            oldItem.qualification.name == newItem.qualification.name

        override fun areContentsTheSame(oldItem: QualificationParams, newItem: QualificationParams) =
            oldItem == newItem
    }
}