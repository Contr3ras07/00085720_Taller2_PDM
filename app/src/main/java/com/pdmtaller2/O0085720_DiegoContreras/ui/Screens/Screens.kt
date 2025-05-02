package com.pdmtaller2.O0085720_DiegoContreras.ui.Screens

sealed class Screens(val route: String) {
    object Home : Screens("home")
    object Search : Screens("search")
    object Orders : Screens("orders")
}
