package com.example.energydex.app

import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.example.energydex.presentation.energydrink.energydrink_add.EnergyDrinkAddAddScreenRoot
import com.example.energydex.presentation.energydrink.energydrink_add.EnergyDrinkAddViewModel
import com.example.energydex.presentation.main_screen.MainScreenRoot
import com.example.energydex.presentation.main_screen.MainScreenViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun App(){
    MaterialTheme {
        val navController = rememberNavController()
        NavHost(
            navController = navController,
            startDestination = Route.EnergyDexGraph
        ) {
            navigation<Route.EnergyDexGraph>(
                startDestination = Route.MainScreen
            ) {
                composable<Route.MainScreen>(
                    exitTransition = { slideOutHorizontally (  ) },
                    popEnterTransition = { slideInHorizontally () }
                ) {
                    val viewModel = koinViewModel<MainScreenViewModel>()

                    MainScreenRoot(
                        viewModel = viewModel,
                        onEnergyDrinkClick = {
                            navController.navigate(
                                Route.EnergyDrinkDetail
                            )
                        },
                        onAddEnergyDrinkButtonClick = {
                            navController.navigate(
                                Route.AddEnergyDrink
                            )
                        },
                        onTagClick = {
                            navController.navigate(
                                Route.TagDetail
                            )
                        }
                    )
                }

                composable<Route.AddEnergyDrink>(
                    //TODO
                ) {
                    val viewModel = koinViewModel<EnergyDrinkAddViewModel>()

                    EnergyDrinkAddAddScreenRoot(
                        viewModel = viewModel,
                        onSaveClick = {
                            navController.navigateUp()
                        },
                        onBackClick = {
                            navController.navigateUp()
                        }
                    )
                }

                composable<Route.CreateTag>(
                    //TODO slides
                ) {

                }

                composable<Route.EnergyDrinkDetail>(
                    //TODO slides
                ) {

                }

                composable<Route.TagDetail>(
                    //TODO slides
                ) {

                }

                composable<Route.UpdateEnergyDrink>(
                    //TODO slides
                ) {

                }

                composable<Route.UpdateTag>(
                    //TODO slides
                ) {

                }
            }
        }
    }
}