package com.example.energydex.app

import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.energydex.presentation.energydrink.energydrink_add.EnergyDrinkAddAddScreenRoot
import com.example.energydex.presentation.energydrink.energydrink_add.EnergyDrinkAddViewModel
import com.example.energydex.presentation.main_screen.MainScreenRoot
import com.example.energydex.presentation.main_screen.MainScreenViewModel
import com.example.energydex.presentation.energydrink.energydrink_detail.EnergyDrinkDetailScreenRoot
import com.example.energydex.presentation.energydrink.energydrink_detail.EnergyDrinkDetailViewModel
import com.example.energydex.presentation.energydrink.energydrink_update.EnergyDrinkUpdateScreenRoot
import com.example.energydex.presentation.energydrink.energydrink_update.EnergyDrinkUpdateViewModel
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

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
                        onEnergyDrinkClick = { energyDrink ->
                            navController.navigate(
                                Route.EnergyDrinkDetail(energyDrink.id)
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
                ) { backStackEntry ->
                    val route = backStackEntry.toRoute<Route.EnergyDrinkDetail>()
                    val viewModel = koinViewModel<EnergyDrinkDetailViewModel>(
                        parameters = { parametersOf(route.id) }
                    )
                    EnergyDrinkDetailScreenRoot(
                        viewModel = viewModel,
                        onBackClick = { navController.navigateUp() },
                        onUpdateClick = {
                            navController.navigate(Route.UpdateEnergyDrink(route.id))
                        }
                    )
                }

                composable<Route.TagDetail>(
                    //TODO slides
                ) {

                }

                composable<Route.UpdateEnergyDrink>(
                    //TODO slides
                ) { backStackEntry ->
                    val route = backStackEntry.toRoute<Route.UpdateEnergyDrink>()
                    val viewModel = koinViewModel<EnergyDrinkUpdateViewModel>(
                        parameters = { parametersOf(route.id) }
                    )
                    EnergyDrinkUpdateScreenRoot(
                        viewModel = viewModel,
                        onSaveClick = {
                            navController.navigate(Route.EnergyDrinkDetail(route.id)) {
                                popUpTo<Route.EnergyDrinkDetail> { inclusive = true }
                                launchSingleTop = true
                            }
                        },
                        onBackClick = { navController.navigateUp() }
                    )
                }

                composable<Route.UpdateTag>(
                    //TODO slides
                ) {

                }
            }
        }
    }
}
