package dev.lcdii.energydex.app

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.core.tween
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import dev.lcdii.energydex.presentation.energydrink.energydrink_add.EnergyDrinkAddScreenRoot
import dev.lcdii.energydex.presentation.energydrink.energydrink_add.EnergyDrinkAddViewModel
import dev.lcdii.energydex.presentation.main_screen.MainScreenRoot
import dev.lcdii.energydex.presentation.main_screen.MainScreenViewModel
import dev.lcdii.energydex.presentation.energydrink.energydrink_detail.EnergyDrinkDetailScreenRoot
import dev.lcdii.energydex.presentation.energydrink.energydrink_detail.EnergyDrinkDetailViewModel
import dev.lcdii.energydex.presentation.energydrink.energydrink_update.EnergyDrinkUpdateScreenRoot
import dev.lcdii.energydex.presentation.energydrink.energydrink_update.EnergyDrinkUpdateViewModel
import dev.lcdii.energydex.presentation.tag.tag_create_edit.TagCreateEditScreenRoot
import dev.lcdii.energydex.presentation.tag.tag_create_edit.TagCreateEditViewModel
import dev.lcdii.energydex.presentation.tag.tag_detail.TagDetailScreenRoot
import dev.lcdii.energydex.presentation.tag.tag_detail.TagDetailViewModel
import dev.lcdii.energydex.presentation.tag.tag_drink_selection.TagDrinkSelectionScreenRoot
import dev.lcdii.energydex.presentation.tag.tag_drink_selection.TagDrinkSelectionViewModel
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun App(){
    MaterialTheme {
        val navController = rememberNavController()
        NavHost(
            navController = navController,
            startDestination = Route.EnergyDexGraph,
            enterTransition = { EnterTransition.None },
            exitTransition = { ExitTransition.None },

        ) {
            navigation<Route.EnergyDexGraph>(
                startDestination = Route.MainScreen
            ) {
                composable<Route.MainScreen>(
                    exitTransition = { slideOutHorizontally(animationSpec = tween(150)) },
                    popEnterTransition = { slideInHorizontally(animationSpec = tween(150)) }
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
                                        onCreateTagClick = {
                                            navController.navigate(Route.CreateTag)
                                        },
                                        onTagClick = { tag ->
                            navController.navigate(Route.TagDetail(tag.id))
                        },
                    )
                }

                composable<Route.AddEnergyDrink>(
                    //TODO
                ) {
                    val viewModel = koinViewModel<EnergyDrinkAddViewModel>()

                    EnergyDrinkAddScreenRoot(
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
                    val viewModel = koinViewModel<TagCreateEditViewModel>(
                        parameters = { parametersOf(null as Long?) }
                    )
                    TagCreateEditScreenRoot(
                        viewModel = viewModel,
                        isEdit = false,
                        onSaved = { navController.navigateUp() },
                        onBack = { navController.navigateUp() }
                    )
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
                ) { backStackEntry ->
                    val route = backStackEntry.toRoute<Route.TagDetail>()
                    val viewModel = koinViewModel<TagDetailViewModel>(
                        parameters = { parametersOf(route.id) }
                    )
                    TagDetailScreenRoot(
                        viewModel = viewModel,
                        onBackClick = { navController.navigateUp()},
                        onEditClick = { navController.navigate(Route.UpdateTag(route.id)) },
                        onAddDrinksClick = {
                            navController.navigate(Route.TagDrinkSelection(route.id))
                        },
                        onDrinkClick = { drinkId ->
                            navController.navigate(Route.EnergyDrinkDetail(drinkId))
                        }
                    )
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
                ) { backStackEntry ->
                    val route = backStackEntry.toRoute<Route.UpdateTag>()
                    val viewModel = koinViewModel<TagCreateEditViewModel>(
                        parameters = { parametersOf(route.id) }
                    )
                    TagCreateEditScreenRoot(
                        viewModel = viewModel,
                        isEdit = true,
                        onSaved = {
                            navController.navigate(Route.TagDetail(route.id)) {
                                popUpTo<Route.TagDetail> { inclusive = true }
                                launchSingleTop = true
                            }
                        },
                        onBack = { navController.navigateUp()}
                    )
                }

                composable<Route.TagDrinkSelection> { backStackEntry ->
                    val route = backStackEntry.toRoute<Route.TagDrinkSelection>()
                    val viewModel = koinViewModel<TagDrinkSelectionViewModel>(
                        parameters = { parametersOf(route.tagId) }
                    )
                    TagDrinkSelectionScreenRoot(
                        viewModel = viewModel,
                        onSaved = {
                            navController.navigate(Route.TagDetail(route.tagId)) {
                                popUpTo<Route.TagDetail> { inclusive = true }
                                launchSingleTop = true
                            }
                        },
                        onBack = { navController.navigateUp() }
                    )
                }
            }
        }
    }
}
