package com.pdmtaller2.O0085720_DiegoContreras

data class Dish(
    val id: Int,
    val name: String,
    val description: String,
    val imageUrl: String
)

data class Restaurant(
    val id: Int,
    val name: String,
    val description: String,
    val imageUrl: String,
    val categories: List<String>,
    val menu: List<Dish>
)