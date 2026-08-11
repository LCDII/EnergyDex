package com.example.energydex.di

import com.example.EnergyDexDatabase
import com.example.energydex.data.energydrink.database.DatabaseDriverFactory
import com.example.energydex.data.energydrink.repository.EnergyDrinkRepositoryImpl
import com.example.energydex.data.energydrink.repository.EnergyDrinkTagRepositoryImpl
import com.example.energydex.data.energydrink.repository.EnergyDrinkLocalMetadataRepositoryImpl
import com.example.energydex.data.tag.repository.TagRepositoryImpl
import com.example.energydex.domain.energydrink.repository.EnergyDrinkRepository
import com.example.energydex.domain.energydrink.repository.EnergyDrinkTagRepository
import com.example.energydex.domain.energydrink.repository.EnergyDrinkLocalMetadataRepository
import com.example.energydex.domain.energydrink.usecase.CreateEnergyDrinkUseCase
import com.example.energydex.domain.energydrink.usecase.DeleteEnergyDrinkUseCase
import com.example.energydex.domain.energydrink.usecase.GetEnergyDrinkByIdUseCase
import com.example.energydex.domain.energydrink.usecase.ObserveEnergyDrinksUseCase
import com.example.energydex.domain.energydrink.usecase.ObserveEnergyDrinksForTagUseCase
import com.example.energydex.domain.energydrink.usecase.UpdateEnergyDrinkUseCase
import com.example.energydex.domain.energydrink.usecase.UpdateEnergyDrinkTagRelationsUseCase
import com.example.energydex.domain.energydrink.usecase.ObserveEnergyDrinkTagRelationsUseCase
import com.example.energydex.domain.tag.repository.TagRepository
import com.example.energydex.domain.tag.usecase.CreateTagUseCase
import com.example.energydex.domain.tag.usecase.DeleteTagUseCase
import com.example.energydex.domain.tag.usecase.GetTagByIdUseCase
import com.example.energydex.domain.tag.usecase.UpdateTagUseCase
import com.example.energydex.domain.tag.usecase.ObserveTagsUseCase
import com.example.energydex.presentation.energydrink.energydrink_add.EnergyDrinkAddViewModel
import com.example.energydex.presentation.energydrink.energydrink_section.EnergyDrinkSectionViewModel
import com.example.energydex.presentation.energydrink.energydrink_detail.EnergyDrinkDetailViewModel
import com.example.energydex.presentation.energydrink.energydrink_update.EnergyDrinkUpdateViewModel
import com.example.energydex.presentation.tag.tag_section.TagSectionViewModel
import com.example.energydex.presentation.tag.tag_detail.TagDetailViewModel
import com.example.energydex.presentation.tag.tag_create_edit.TagCreateEditViewModel
import com.example.energydex.presentation.tag.tag_drink_selection.TagDrinkSelectionViewModel
import com.example.energydex.presentation.main_screen.MainScreenViewModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val useCaseModule = module {

    factoryOf(::CreateEnergyDrinkUseCase)
    factoryOf(::UpdateEnergyDrinkUseCase)
    factoryOf(::DeleteEnergyDrinkUseCase)
    factoryOf(::GetEnergyDrinkByIdUseCase)
    factoryOf(::ObserveEnergyDrinksUseCase)
    factoryOf(::ObserveEnergyDrinksForTagUseCase)

    factoryOf(::CreateTagUseCase)
    factoryOf(::UpdateTagUseCase)
    factoryOf(::DeleteTagUseCase)
    factoryOf(::GetTagByIdUseCase)
    factoryOf(::ObserveTagsUseCase)
    factoryOf(::UpdateEnergyDrinkTagRelationsUseCase)
    factoryOf(::ObserveEnergyDrinkTagRelationsUseCase)

}

expect val platformModule: Module


val databaseModule = module {

    single { EnergyDexDatabase(
        get<DatabaseDriverFactory>().createDriver()
    ) }

    single { get<EnergyDexDatabase>().energyDrinkEntityQueries }
    single { get<EnergyDexDatabase>().tagEntityQueries }
    single { get<EnergyDexDatabase>().energyDrinkTagQueries }
    single { get<EnergyDexDatabase>().energyDrinkLocalMetadataQueries }
}

val repositoryModule = module {
    single<EnergyDrinkRepository> {
        EnergyDrinkRepositoryImpl(
            get(),
            get(),
            get(),
        )
    }

    single<EnergyDrinkLocalMetadataRepository> {
        EnergyDrinkLocalMetadataRepositoryImpl(get())
    }

    single<TagRepository>{
        TagRepositoryImpl(
            get()
        )
    }

    single<EnergyDrinkTagRepository> {
        EnergyDrinkTagRepositoryImpl(get(), get())
    }
}


val viewModelModule = module {
    viewModelOf(::EnergyDrinkSectionViewModel)
    viewModelOf(::MainScreenViewModel)
    viewModelOf(::EnergyDrinkAddViewModel)
    viewModelOf(::TagSectionViewModel)
    viewModel { parameters ->
        EnergyDrinkDetailViewModel(parameters.get(), get(), get())
    }
    viewModel { parameters ->
        EnergyDrinkUpdateViewModel(parameters.get(), get(), get(), get())
    }
    viewModel { parameters ->
        TagDetailViewModel(parameters.get(), get(), get(), get())
    }
    viewModel { parameters ->
        TagCreateEditViewModel(parameters.getOrNull(), get(), get(), get())
    }
    viewModel { parameters ->
        TagDrinkSelectionViewModel(parameters.get(), get(), get(), get())
    }
}
