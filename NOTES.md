# Latest update on 30/6/2023

## Update compose to comply prerequist on Android Studio Flamingo

- Upgrade each project to AGP (Android Gradle Plugin) 8.0. The thing to note is that if you use KAPT, you have to use Java 17.
- Implemented the [BOM (Bill of Materials)](https://developer.android.com/jetpack/compose/bom) for the Compose library version
- Migrate each project's theme from Material 2 to [Material 3](https://developer.android.com/jetpack/compose/designsystems/material2-material3).
- Change the colorScheme and typography according to Material 3.
- Uses the new [SearchBar](https://developer.android.com/reference/kotlin/androidx/compose/material3/package-summary#SearchBar(kotlin.String,kotlin.Function1,kotlin.Function1,kotlin.Boolean,kotlin.Function1,androidx.compose.ui.Modifier,kotlin.Boolean,kotlin.Function0,kotlin.Function0,kotlin.Function0,androidx.compose.ui.graphics.Shape,androidx.compose.material3.SearchBarColors,androidx.compose.ui.unit.Dp,androidx.compose.foundation.layout.WindowInsets,androidx.compose.foundation.interaction.MutableInteractionSource,kotlin.Function1)) component for the search feature.
- Changed the BottomNavigation component to a [NavigationBar](https://developer.android.com/reference/kotlin/androidx/compose/material3/package-summary#NavigationBar(androidx.compose.ui.Modifier,androidx.compose.ui.graphics.Color,androidx.compose.ui.graphics.Color,androidx.compose.ui.unit.Dp,androidx.compose.foundation.layout.WindowInsets,kotlin.Function1)).
- Added [project](https://github.com/dicodingacademy/a445-jetpack-compose-labs/tree/main/layout-pada-compose/TeoriScaffold) for Scaffold theory.
- Added a [monochrome](https://developer.android.com/develop/ui/views/launch/icon_design_adaptive#add_your_adaptive_icon_to_your_app) configuration for adaptive icons.
- Using [ModalNavigationDrawer](https://developer.android.com/reference/kotlin/androidx/compose/material3/package-summary#ModalNavigationDrawer(kotlin.Function0,androidx.compose.ui.Modifier,androidx.compose.material3.DrawerState,kotlin.Boolean,androidx.compose.ui.graphics.Color,kotlin.Function0)) to replace drawerContent on Scaffold.

References: [Link](https://arifaizin.notion.site/Cara-Migrasi-Project-Compose-dari-Material2-ke-Material3-3a607e2fbdb143ae9ca3cea2064a0ed4)