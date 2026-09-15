package dev.lcdii.energydex.di

import dev.lcdii.energydex.database.EnergyDexDatabase
import dev.lcdii.energydex.data.energydrink.database.DatabaseDriverFactory
import dev.lcdii.energydex.data.energydrink.repository.EnergyDrinkRepositoryImpl
import dev.lcdii.energydex.data.energydrink.repository.EnergyDrinkTagRepositoryImpl
import dev.lcdii.energydex.data.energydrink.repository.EnergyDrinkLocalMetadataRepositoryImpl
import dev.lcdii.energydex.data.tag.repository.TagRepositoryImpl
import dev.lcdii.energydex.domain.energydrink.repository.EnergyDrinkRepository
import dev.lcdii.energydex.domain.energydrink.repository.EnergyDrinkTagRepository
import dev.lcdii.energydex.domain.energydrink.repository.EnergyDrinkLocalMetadataRepository
import dev.lcdii.energydex.domain.energydrink.usecase.CreateEnergyDrinkUseCase
import dev.lcdii.energydex.domain.energydrink.usecase.AddTagsToEnergyDrinksUseCase
import dev.lcdii.energydex.domain.energydrink.usecase.DeleteEnergyDrinkUseCase
import dev.lcdii.energydex.domain.energydrink.usecase.GetEnergyDrinkByIdUseCase
import dev.lcdii.energydex.domain.energydrink.usecase.ObserveEnergyDrinksUseCase
import dev.lcdii.energydex.domain.energydrink.usecase.ObserveEnergyDrinksForTagUseCase
import dev.lcdii.energydex.domain.energydrink.usecase.UpdateEnergyDrinkUseCase
import dev.lcdii.energydex.domain.energydrink.usecase.UpdateEnergyDrinkTagRelationsUseCase
import dev.lcdii.energydex.domain.energydrink.usecase.ObserveEnergyDrinkTagRelationsUseCase
import dev.lcdii.energydex.domain.tag.repository.TagRepository
import dev.lcdii.energydex.domain.tag.usecase.CreateTagUseCase
import dev.lcdii.energydex.domain.tag.usecase.DeleteTagUseCase
import dev.lcdii.energydex.domain.tag.usecase.GetTagByIdUseCase
import dev.lcdii.energydex.domain.tag.usecase.UpdateTagUseCase
import dev.lcdii.energydex.domain.tag.usecase.ObserveTagsUseCase
import dev.lcdii.energydex.presentation.energydrink.energydrink_add.EnergyDrinkAddViewModel
import dev.lcdii.energydex.presentation.energydrink.energydrink_section.EnergyDrinkSectionViewModel
import dev.lcdii.energydex.presentation.energydrink.energydrink_detail.EnergyDrinkDetailViewModel
import dev.lcdii.energydex.presentation.energydrink.energydrink_update.EnergyDrinkUpdateViewModel
import dev.lcdii.energydex.presentation.tag.tag_section.TagSectionViewModel
import dev.lcdii.energydex.presentation.tag.tag_detail.TagDetailViewModel
import dev.lcdii.energydex.presentation.tag.tag_create_edit.TagCreateEditViewModel
import dev.lcdii.energydex.presentation.tag.tag_drink_selection.TagDrinkSelectionViewModel
import dev.lcdii.energydex.presentation.main_screen.MainScreenViewModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val useCaseModule = module {

    factoryOf(::CreateEnergyDrinkUseCase)
    factoryOf(::AddTagsToEnergyDrinksUseCase)
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


fun databaseModule(database: EnergyDexDatabase? = null) = module {

    single {
        database ?: EnergyDexDatabase(
            get<DatabaseDriverFactory>().createDriver()
        )
    }

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
        EnergyDrinkDetailViewModel(parameters.get(), get())
    }
    viewModel { parameters ->
        EnergyDrinkUpdateViewModel(parameters.get(), get(), get(), get(), get())
    }
    viewModel { parameters ->
        TagDetailViewModel(parameters.get(), get(), get(), get(), get())
    }
    viewModel { parameters ->
        TagCreateEditViewModel(parameters.getOrNull(), get(), get(), get())
    }
    viewModel { parameters ->
        TagDrinkSelectionViewModel(parameters.get(), get(), get(), get(), get())
    }
}
