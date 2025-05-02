package com.pdmtaller2.O0085720_DiegoContreras

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest

@Composable
fun homeScreen(onRestaurantClick: (Int) -> Unit) {

    val categories = listOf(
        "Comida Rápida", "Comida Mexicana", "Comida Italiana",
        "Comida Asiática", "Comida Saludable", "Postres y Dulces", "Bebidas"
    )

    val categoryEmojis = mapOf(
        "Comida Rápida" to "🍔", "Comida Mexicana" to "🌮", "Comida Italiana" to "🍕",
        "Comida Asiática" to "🍜", "Comida Saludable" to "🥗", "Postres y Dulces" to "🍰",
        "Bebidas" to "🍹"
    )

    val restaurantsByCategory = mapOf(
        "Comida Rápida" to listOf(
            Restaurant(
                1,
                "Burgers D'Diego",
                "Las mejores hamburguesas de la ciudad",
                "https://media.scoolinary.app/blog/images/2023/08/scoolinary-salsas-para-hamburguesas.jpg", // hamburguesa
                listOf("Comida Rápida"),
                listOf()
            ),
            Restaurant(
                2,
                "Pizza de Diego",
                "Pizza rápida y deliciosa",
                "https://s3.amazonaws.com/arc-wordpress-client-uploads/infobae-wp/wp-content/uploads/2018/05/09151048/comida-italiana-.jpg", // pizza
                listOf("Comida Rápida"),
                listOf()
            )
        ),
        "Comida Mexicana" to listOf(
            Restaurant(
                3,
                "Taquería JGL",
                "Auténtica comida mexicana",
                "https://uvn-brightspot.s3.amazonaws.com/assets/vixes/e/elote_preparado_comida_mexicana.jpg", // tacos
                listOf("Comida Mexicana"),
                listOf()
            )
        ),
        "Comida Italiana" to listOf(
            Restaurant(
                4,
                "Napolitana Di Maradona",
                "Auténtica pasta italiana",
                "https://i0.wp.com/www.buenossaborespanama.com/wp-content/uploads/2021/01/spaghetti-in-dish-on-wooden-background-scaled.jpg?fit=1200%2C800&ssl=1", // pasta
                listOf("Comida Italiana"),
                listOf()
            )
        )
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFE0F7FA)),
        contentPadding = PaddingValues(16.dp)
    ) {
        item {

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 32.dp, bottom = 16.dp)
                    .background(
                        Brush.horizontalGradient(listOf(
                            Color(0xFFFF4081),
                            Color(0xFF00E5FF)
                        )),
                        shape = RoundedCornerShape(bottomEnd = 32.dp, bottomStart = 32.dp)
                    )
            ) {
                Text(
                    text = "FoodSpot by Diego C.",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    modifier = Modifier
                        .padding(horizontal = 24.dp, vertical = 12.dp)
                        .align(Alignment.Center)
                )
            }
        }

        items(categories) { category ->
            val restaurantsInCategory = restaurantsByCategory[category] ?: emptyList()
            if (restaurantsInCategory.isNotEmpty()) {
                CategorySection(
                    category = category,
                    emoji = categoryEmojis[category] ?: "",
                    restaurants = restaurantsInCategory,
                    onRestaurantClick = onRestaurantClick
                )
                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}

@Composable
fun CategorySection(
    category: String,
    emoji: String,
    restaurants: List<Restaurant>,
    onRestaurantClick: (Int) -> Unit
) {
    Column(
        modifier = Modifier.padding(bottom = 24.dp)
    ) {
        Text(
            text = "$emoji $category",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF00796B),
            modifier = Modifier.padding(bottom = 12.dp)
        )

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(restaurants) { restaurant ->
                RestaurantCard(
                    restaurant = restaurant,
                    onClick = { onRestaurantClick(restaurant.id) }
                )
            }
        }
    }
}

@Composable
fun RestaurantCard(restaurant: Restaurant, onClick: () -> Unit) {
    val backgroundColors = listOf(
        Brush.horizontalGradient(listOf(Color(0xFF81C784), Color(0xFF66BB6A))),
        Brush.horizontalGradient(listOf(Color(0xFFFFEB3B), Color(0xFFFFC107))),
        Brush.horizontalGradient(listOf(Color(0xFFBBDEFB), Color(0xFF90CAF9))),
        Brush.horizontalGradient(listOf(Color(0xFFFF8A65), Color(0xFFFF7043)))
    )

    val bgColor = backgroundColors.random()

    Card(
        modifier = Modifier
            .width(160.dp)
            .height(200.dp)
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        border = BorderStroke(2.dp, Color.White)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(bgColor, shape = RoundedCornerShape(20.dp))
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(12.dp)
            ) {
                Image(
                    painter = rememberAsyncImagePainter(
                        ImageRequest.Builder(LocalContext.current).data(data = restaurant.imageUrl)
                            .apply(block = fun ImageRequest.Builder.() {
                                crossfade(true)
                            }).build()
                    ),
                    contentDescription = restaurant.name,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.size(80.dp)
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = restaurant.name,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = Color.White,
                    maxLines = 1,
                    modifier = Modifier.padding(horizontal = 8.dp)
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = "Envío desde $3.99",
                    fontSize = 14.sp,
                    color = Color(0xFFB2DFDB)
                )
            }
        }
    }
}
