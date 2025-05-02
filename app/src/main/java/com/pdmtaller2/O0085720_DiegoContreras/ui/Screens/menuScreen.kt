package com.pdmtaller2.O0085720_DiegoContreras

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun menuScreen(
    restaurantId: Int,
    onBackClick: () -> Unit
) {
    val restaurant = getSampleRestaurant(restaurantId)
    var searchText by remember { mutableStateOf("") }

    val dishCategories = remember(restaurant.menu) {
        mapOf(
            "Platos Principales" to restaurant.menu.filter { it.id <= 3 },
            "Acompañamientos" to restaurant.menu.filter { it.id == 2 || it.id == 4 },
            "Bebidas" to restaurant.menu.filter { it.id == 4 }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Menú de ${restaurant.name}") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Volver"
                        )
                    }
                }
            )
        }

    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            OutlinedTextField(
                value = searchText,
                onValueChange = { searchText = it },
                label = { Text("Buscar platillo...") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp)
            ) {
                dishCategories.forEach { (category, dishes) ->
                    val filteredDishes = dishes.filter {
                        it.name.contains(searchText, ignoreCase = true)
                    }

                    if (filteredDishes.isNotEmpty()) {
                        item {
                            Text(
                                text = category,
                                fontSize = 22.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(vertical = 16.dp)
                            )
                        }

                        items(filteredDishes) { dish ->
                            MenuDishItem(dish = dish)
                            Spacer(modifier = Modifier.height(12.dp))
                        }
                    }
                }

                item {
                    Spacer(modifier = Modifier.height(24.dp))
                }
            }
        }
    }
}

@Composable
fun MenuDishItem(dish: Dish) {
    val context = LocalContext.current
    val backgroundColors = listOf(
        Color(0xFFFFF59D),
        Color(0xFFFFCDD2),
        Color(0xFFB2EBF2),
        Color(0xFFFFCCBC)
    )
    val bgColor = remember { backgroundColors.random() }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(140.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = bgColor),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = rememberAsyncImagePainter(model = dish.imageUrl),
                contentDescription = dish.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(80.dp)
                    .aspectRatio(1f)
                    .padding(4.dp)
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp)
            ) {
                Text(
                    text = dish.name,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    maxLines = 1
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = dish.description,
                    fontSize = 13.sp,
                    maxLines = 2
                )

                Spacer(modifier = Modifier.height(8.dp))

                AssistChip(
                    onClick = {
                        Toast.makeText(
                            context,
                            "\"${dish.name}\" agregado al carrito",
                            Toast.LENGTH_SHORT
                        ).show()
                    },
                    label = { Text("Agregar al carrito") }
                )
            }
        }
    }
}


fun getSampleRestaurant(id: Int): Restaurant {
    return when (id) {
        1 -> Restaurant(
            id = 1,
            name = "Burgers D'Diego",
            description = "Burgues con autentica carne argentina",
            imageUrl = "https://media.scoolinary.app/blog/images/2023/08/scoolinary-salsas-para-hamburguesas.jpg",
            categories = listOf("Comida Rápida"),
            menu = listOf(
                Dish(1, "Hamburguesa Clásica", "Carne de res 100% natural, queso cheddar, lechuga, tomate y nuestra salsa especial", "https://www.tqma.com.ec/images/com_yoorecipe/banner_superior/15685_1.jpg"),
                Dish(2, "Papas Fritas", "Papas fritas crujientes con sal de mar", "https://okdiario.com/img/2023/04/12/el-truco-definitivo-para-que-las-patatas-fritas-te-queden-mas-crujientes.jpg"),
                Dish(3, "Hamburguesa Especial", "Doble carne, doble queso cheddar, bacon crujiente, cebolla caramelizada y salsa BBQ", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQLPbMhdiK6KIrx52IJcNNOcCTzX6C_nSVbTQ&s"),
                Dish(4, "Refresco", "Bebida refrescante de cola, naranja o limón", "https://media.istockphoto.com/id/458464735/es/foto/coca-cola.jpg?s=612x612&w=0&k=20&c=SnB7NqAiTxs3PQzWpSpwOiOncP1hbYHEP9zaDurvLwU="),
                Dish(5, "Hamburguesa Vegetariana", "Hamburguesa a base de plantas con queso, lechuga, tomate y salsa vegana", "https://www.pequerecetas.com/wp-content/uploads/2009/04/hamburguesa-de-garbanzos-casera-receta.jpg"),
                Dish(6, "Aros de Cebolla", "Aros de cebolla fritos con salsa ranch", "https://www.recetasnestle.com.mx/sites/default/files/srh_recipes/0239e322448197a2a283d9a1b2837c53.jpg")
            )
        )
        2 -> Restaurant(
            id = 2,
            name = "Pizza de Diego",
            description = "Pizza autentica de Napoles",
            imageUrl = "https://s3.amazonaws.com/arc-wordpress-client-uploads/infobae-wp/wp-content/uploads/2018/05/09151048/comida-italiana-.jpg",
            categories = listOf("Comida Rápida", "Comida Italiana"),
            menu = listOf(
                Dish(1, "Pizza Pepperoni", "Pizza con abundante pepperoni y queso mozzarella", "https://www.novachef.es/media/images/pizza-pepperoni.jpg"),
                Dish(2, "Pizza Margarita", "Pizza tradicional con tomate, queso mozzarella y albahaca fresca", "https://www.laespanolaaceites.com/wp-content/uploads/2019/06/pizza-margarita-1080x671.jpg"),
                Dish(3, "Pizza Hawaiana", "Pizza con jamón, piña y queso mozzarella", "https://cloudfront-us-east-1.images.arcpublishing.com/infobae/BZWVMJ2EA5HGPH7IY2S2AJ3NEI.jpg"),
                Dish(4, "Pizza 4 Quesos", "Pizza con mezcla de quesos mozzarella, parmesano, gorgonzola y provolone", "https://valledearas.com/wordpress/wp-content/uploads/2023/01/pizza-4-quesos.jpg"),
                Dish(5, "Pasta Alfredo", "Pasta fettuccine con salsa cremosa y pollo", "https://images.aws.nestle.recipes/resized/cc72869fabfc2bdfa036fd1fe0e2bad8_creamy_alfredo_pasta_long_left_1080_850.jpg"),
                Dish(6, "Lasaña", "Lasaña tradicional con carne, tomate y queso", "https://www.recetasnestle.cl/sites/default/files/srh_recipes/57d2453074b608263f3a814302cc7864.jpg")
            )
        )
        3 -> Restaurant(
            id = 3,
            name = "Taquería JGL",
            description = "Cocina al ritmo de Joaquin Guzman Loera",
            imageUrl = "https://uvn-brightspot.s3.amazonaws.com/assets/vixes/e/elote_preparado_comida_mexicana.jpg",
            categories = listOf("Comida Mexicana"),
            menu = listOf(
                Dish(1, "Tacos al Pastor", "Tacos de cerdo marinado con piña, cilantro y cebolla", "https://s3.amazonaws.com/static.realcaliforniamilk.com/media/recipes_2/tacos-al-pastor.jpg"),
                Dish(2, "Guacamole", "Guacamole fresco con totopos de maíz", "https://californiaavocado.com/wp-content/uploads/2020/07/Guacamole-Autentico-1.jpeg"),
                Dish(3, "Enchiladas Verdes", "Tortillas rellenas de pollo con salsa verde, crema y queso", "https://editorialtelevisa.brightspotcdn.com/dims4/default/4a5dbdb/2147483647/strip/true/crop/996x560+2+0/resize/1440x810!/quality/90/?url=https%3A%2F%2Fk2-prod-editorial-televisa.s3.us-east-1.amazonaws.com%2Fbrightspot%2Fwp-content%2Fuploads%2F2019%2F05%2Fenchiladas-verdes.png"),
                Dish(4, "Quesadillas", "Tortillas de maíz con queso derretido y su elección de carne", "https://upload.wikimedia.org/wikipedia/commons/7/75/Quesadilla_2.jpg"),
                Dish(5, "Burritos", "Tortilla de harina rellena de frijoles, arroz, carne y guacamole", "https://static01.nyt.com/images/2024/01/10/multimedia/AS-Burrito-vzhk/AS-Burrito-vzhk-superJumbo.jpg"),
                Dish(6, "Agua de Horchata", "Bebida refrescante de arroz con canela", "https://familiakitchen.com/wp-content/uploads/2022/09/iStock-1217713217-Horchata-2.jpg")
            )
        )
        4 -> Restaurant(
            id = 4,
            name = "Napolitana Di Maradona",
            description = "Auténtica pasta italiana",
            imageUrl = "https://i0.wp.com/www.buenossaborespanama.com/wp-content/uploads/2021/01/spaghetti-in-dish-on-wooden-background-scaled.jpg?fit=1200%2C800&ssl=1",
            categories = listOf("Comida Italiana"),
            menu = listOf(
                Dish(1, "Lasaña", "Lasaña tradicional con carne, salsa de tomate y queso", "https://www.recetasnestle.com.ec/sites/default/files/srh_recipes/6594a07290c4cc5ed88f682560cc2e49.jpg"),
                Dish(2, "Espagueti Carbonara", "Espagueti con salsa cremosa, queso parmesano y panceta", "https://www.laragazzacolmattarello.com/wp-content/uploads/2025/01/pasta-a-la-carbonara.jpg"),
                Dish(3, "Ravioles", "Ravioles rellenos de queso ricotta y espinacas", "https://resizer.glanacion.com/resizer/v2/ravioles-de-atun-con-berenjena-y-pasas-de-SDMHHOBB2NFG5KMWQQPA527CF4.jpg?auth=4fd6b2526fdbd8677a150b56caaac07a00217dd7cdfc164072895e19fa30328c&width=1280&height=854&quality=70&smart=true"),
                Dish(4, "Vino Tinto", "Copa de vino tinto de la casa", "https://licoreriasunidas.pe/cdn/shop/articles/tipos-de-vinos.jpg?v=1693249338"),
                Dish(5, "Tiramisú", "Postre italiano con café, queso mascarpone y cacao", "https://www.recetasnestle.com.ec/sites/default/files/srh_recipes/7f45d6f8807ebc775928651a3398dce9.png"),
                Dish(6, "Pan de Ajo", "Pan crujiente con mantequilla de ajo y hierbas", "https://imag.bonviveur.com/pan-de-ajo-foto-cerca.jpg")
            )
        )
        else -> Restaurant(
            id = 0,
            name = "Restaurante",
            description = "Información no disponible",
            imageUrl = "",
            categories = emptyList(),
            menu = emptyList()
        )
    }
}