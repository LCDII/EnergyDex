package com.example.energydeks.di

import com.example.energydeks.domain.energydrink.usecase.AttachTagToEnergyDrinkUseCase
import com.example.energydeks.domain.energydrink.usecase.CreateEnergyDrinkUseCase
import com.example.energydeks.domain.energydrink.usecase.DeleteEnergyDrinkUseCase
import com.example.energydeks.domain.energydrink.usecase.DetachTagFromEnergyDrinkUseCase
import com.example.energydeks.domain.energydrink.usecase.EnergyDrinkUseCases
import com.example.energydeks.domain.energydrink.usecase.GetAllEnergyDrinksUseCase
import com.example.energydeks.domain.energydrink.usecase.GetEnergyDrinkByIdUSeCase
import com.example.energydeks.domain.energydrink.usecase.UpdateEnergyDrinkUseCase
import com.example.energydeks.domain.tag.usecase.CreateTagUseCase
import com.example.energydeks.domain.tag.usecase.DeleteTagUseCase
import com.example.energydeks.domain.tag.usecase.GetAllTagsUseCase
import com.example.energydeks.domain.tag.usecase.GetTagByIdUseCase
import com.example.energydeks.domain.tag.usecase.TagUseCases
import com.example.energydeks.domain.tag.usecase.UpdateTagUseCase
import org.koin.dsl.module

val useCaseModule = module {

    single { CreateEnergyDrinkUseCase(get()) }
    single { UpdateEnergyDrinkUseCase(get()) }
    single { DeleteEnergyDrinkUseCase(get()) }
    single { GetEnergyDrinkByIdUSeCase(get()) }
    single { GetAllEnergyDrinksUseCase(get()) }
    single { AttachTagToEnergyDrinkUseCase(get()) }
    single { DetachTagFromEnergyDrinkUseCase(get()) }

    single {
        EnergyDrinkUseCases(
            get(),
            get(),
            get(),
            get(),
            get(),
            get(),
            get()
        )
    }

    single { CreateTagUseCase(get()) }
    single { UpdateTagUseCase(get()) }
    single { DeleteTagUseCase(get()) }
    single { GetTagByIdUseCase(get()) }
    single { GetAllTagsUseCase(get()) }

    single{
        TagUseCases(
            get(),
            get(),
            get(),
            get(),
            get(),
        )
    }
}
//TODO repos di