package anton.dev.profinet.domain.di

import anton.dev.profinet.domain.get_customer.GetCurrentCustomerCase
import anton.dev.profinet.domain.get_customer.GetCurrentCustomerCaseImpl
import anton.dev.profinet.domain.update_city.UpdateCustomerAddressCase
import anton.dev.profinet.domain.update_city.UpdateCustomerAddressCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
internal abstract class CustomerModule {

    @Binds
    abstract fun bind1(impl: GetCurrentCustomerCaseImpl): GetCurrentCustomerCase

    @Binds
    abstract fun bind2(impl: UpdateCustomerAddressCaseImpl): UpdateCustomerAddressCase
}