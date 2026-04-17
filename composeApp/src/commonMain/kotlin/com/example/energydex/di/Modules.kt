package com.example.energydex.di

import com.example.EnergyDexDatabase
import com.example.energydex.data.energydrink.database.DatabaseDriverFactory
import com.example.energydex.data.energydrink.repository.EnergyDrinkRepositoryImpl
import com.example.energydex.data.energydrink.repository.EnergyDrinkTagRepositoryImpl
import com.example.energydex.data.tag.repository.TagRepositoryImpl
import com.example.energydex.domain.energydrink.repository.EnergyDrinkRepository
import com.example.energydex.domain.energydrink.repository.EnergyDrinkTagRepository
import com.example.energydex.domain.energydrink.usecase.AttachTagToEnergyDrinkUseCase
import com.example.energydex.domain.energydrink.usecase.CreateEnergyDrinkUseCase
import com.example.energydex.domain.energydrink.usecase.DeleteEnergyDrinkUseCase
import com.example.energydex.domain.energydrink.usecase.DetachTagFromEnergyDrinkUseCase
import com.example.energydex.domain.energydrink.usecase.GetAllEnergyDrinksUseCase
import com.example.energydex.domain.energydrink.usecase.GetEnergyDrinkByIdUseCase
import com.example.energydex.domain.energydrink.usecase.SearchEnergyDrinksUseCase
import com.example.energydex.domain.energydrink.usecase.UpdateEnergyDrinkUseCase
import com.example.energydex.domain.tag.repository.TagRepository
import com.example.energydex.domain.tag.usecase.CreateTagUseCase
import com.example.energydex.domain.tag.usecase.DeleteTagUseCase
import com.example.energydex.domain.tag.usecase.GetAllTagsUseCase
import com.example.energydex.domain.tag.usecase.GetTagByIdUseCase
import com.example.energydex.domain.tag.usecase.UpdateTagUseCase
import com.example.energydex.presentation.energydrink.energydrink_add.EnergyDrinkAddViewModel
import com.example.energydex.presentation.energydrink.energydrink_section.EnergyDrinkSectionViewModel
import com.example.energydex.presentation.main_screen.MainScreenViewModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val useCaseModule = module {

    factoryOf(::CreateEnergyDrinkUseCase)
    factoryOf(::UpdateEnergyDrinkUseCase)
    factoryOf(::DeleteEnergyDrinkUseCase)
    factoryOf(::GetEnergyDrinkByIdUseCase)
    factoryOf(::GetAllEnergyDrinksUseCase)
    factoryOf(::AttachTagToEnergyDrinkUseCase)
    factoryOf(::DetachTagFromEnergyDrinkUseCase)
    factoryOf(::SearchEnergyDrinksUseCase)

    factoryOf(::CreateTagUseCase)
    factoryOf(::UpdateTagUseCase)
    factoryOf(::DeleteTagUseCase)
    factoryOf(::GetTagByIdUseCase)
    factoryOf(::GetAllTagsUseCase)

}

expect val platformModule: Module


val databaseModule = module {

    single { EnergyDexDatabase(
        get<DatabaseDriverFactory>().createDriver()
    ) }

    single { get<EnergyDexDatabase>().energyDrinkEntityQueries }
    single { get<EnergyDexDatabase>().tagEntityQueries }
    single { get<EnergyDexDatabase>().energyDrinkTagQueries }
}

val repositoryModule = module {
    single<EnergyDrinkRepository> {
        EnergyDrinkRepositoryImpl(
            get(),
            get(),
        )
    }

    single<TagRepository>{
        TagRepositoryImpl(
            get()
        )
    }

    single<EnergyDrinkTagRepository> {
        EnergyDrinkTagRepositoryImpl(
            get()
        )
    }
}


val viewModelModule = module {
    viewModelOf(::EnergyDrinkSectionViewModel)
    viewModelOf(::MainScreenViewModel)
    viewModelOf(::EnergyDrinkAddViewModel)
}