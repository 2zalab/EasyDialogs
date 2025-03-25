# EasyDialogs

EasyDialogs is a lightweight and intuitive Android library that simplifies the implementation of dialog boxes, menus, and interactive user interfaces. Designed to reduce boilerplate code and speed up development, this library offers a fluid and highly customizable API.

## Features

- **Simplified Dialogs**: Create alerts and custom dialogs in just a few lines of code
- **Context Menus**: Easily implement context and dropdown menus
- **Fluent API**: Method chaining for concise syntax
- **Material Design**: Modern appearance following Material Design guidelines
- **Highly Customizable**: Adapt components to your application's visual identity

## Installation

### Gradle

Add the Jitpack repository to your `settings.gradle` or project-level `build.gradle` file:

```gradle
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven { url 'https://jitpack.io' } // or maven { url = uri("https://jitpack.io") }
    }
}
```

Then add the dependency in your module's `build.gradle`:

```gradle
dependencies {
    implementation 'com.github.2zalab:EasyDialogs:1.0.1'
}
```

### Maven

```xml
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>

<dependency>
    <groupId>com.github.2zalab</groupId>
    <artifactId>EasyDialogs</artifactId>
    <version>1.0.0</version>
</dependency>
```

## Usage Guide

### Alert Dialogs

```kotlin
import com.touzalab.easydialogs.EasyUI

// Method 1: Builder pattern
EasyUI.alertDialog(context)
    .setTitle("Information")
    .setMessage("This is a dialog example.")
    .setPositiveButton("OK") {
        // Action when OK is clicked
    }
    .setNegativeButton("Cancel") {
        // Action when Cancel is clicked
    }
    .setCancelable(true)
    .show()

// Method 2: Direct creation
val dialog = EasyUI.createAlertDialog(context)
dialog.setTitle("Information")
dialog.setMessage("This is a dialog example.")
dialog.setPositiveButton("OK") { 
    // Action when OK is clicked
}
dialog.show()
```

### Custom Dialogs

```kotlin
// Creating a dialog with a custom layout
val dialog = EasyUI.createCustomDialog(context)
dialog.setTitle("Form")
dialog.setView(R.layout.dialog_form) // Your custom layout
dialog.setPositiveButton("Save") {
    // Access views from custom layout
    val editText = dialog.findViewById<TextInputEditText>(R.id.etName)
    val name = editText?.text.toString()
    // Process data
}
dialog.setNegativeButton("Cancel", null)
dialog.show()
```

### Dropdown Menus

```kotlin
// Creating a dropdown menu attached to a view
EasyUI.createMenu(context, anchorView)
    .addItem(1, "Edit", R.drawable.ic_edit) { id ->
        // Action when "Edit" is clicked
    }
    .addItem(2, "Share", R.drawable.ic_share) { id ->
        // Action when "Share" is clicked
    }
    .addItem(3, "Delete", R.drawable.ic_delete) { id ->
        // Action when "Delete" is clicked
    }
    .show()
```

### Context Menus

```kotlin
// Creating a context menu
val contextMenu = EasyUI.createContextMenu(context)
contextMenu.addItem(1, "Copy", R.drawable.ic_copy) { id ->
    // Action when "Copy" is clicked
}
contextMenu.addItem(2, "Cut", R.drawable.ic_cut) { id ->
    // Action when "Cut" is clicked
}
contextMenu.addItem(3, "Paste", R.drawable.ic_paste) { id ->
    // Action when "Paste" is clicked
}

// Show context menu on long press
myView.setOnLongClickListener { view ->
    contextMenu.show(view)
    true
}
```

### Dropdown Menus with TextInputLayout

```kotlin
// Setting up a dropdown menu with TextInputLayout
val tilDropdown = findViewById<TextInputLayout>(R.id.tilDropdown)
val dropdownMenu = EasyUI.createDropdownMenu<String>(context, tilDropdown)

val items = listOf("Option 1", "Option 2", "Option 3", "Option 4")
dropdownMenu.setItems(items)
dropdownMenu.setOnItemSelectedListener { item ->
    // Action when an item is selected
}
```

## Customization

### Styles and Themes

You can customize the appearance of components by modifying styles:

```xml
<!-- Dialog customization -->
<style name="AppTheme.Dialog" parent="Theme.MaterialComponents.Light.Dialog">
    <item name="colorPrimary">@color/your_primary_color</item>
    <item name="colorAccent">@color/your_accent_color</item>
</style>

<!-- In code -->
EasyUI.createAlertDialog(context, R.style.AppTheme.Dialog)
```

### Customizable Attributes

The library offers several attributes to adapt the appearance of components:

```xml
<declare-styleable name="EasyDialogTheme">
    <attr name="dialogCornerRadius" format="dimension" />
    <attr name="dialogBackgroundColor" format="color" />
    <attr name="dialogTitleColor" format="color" />
    <attr name="dialogMessageColor" format="color" />
    <attr name="dialogButtonTextColor" format="color" />
</declare-styleable>
```

## Complete Example

```kotlin
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputLayout
import com.touzalab.easydialogs.EasyUI

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        
        // Example 1: Alert dialog
        val btnShowAlertDialog = findViewById<Button>(R.id.btnShowAlertDialog)
        btnShowAlertDialog.setOnClickListener {
            EasyUI.alertDialog(this)
                .setTitle("Information")
                .setMessage("This is an alert dialog example.")
                .setPositiveButton("OK") {
                    Toast.makeText(this, "OK button clicked", Toast.LENGTH_SHORT).show()
                }
                .setNegativeButton("Cancel") {
                    Toast.makeText(this, "Cancel button clicked", Toast.LENGTH_SHORT).show()
                }
                .setCancelable(true)
                .show()
        }
        
        // Example 2: Context menu on long press
        val tvContextMenu = findViewById<View>(R.id.tvContextMenu)
        tvContextMenu.setOnLongClickListener { view ->
            EasyUI.createContextMenu(this)
                .addItem(1, "Copy", android.R.drawable.ic_menu_edit) { id ->
                    Toast.makeText(this, "Copy selected", Toast.LENGTH_SHORT).show()
                }
                .addItem(2, "Cut", android.R.drawable.ic_menu_share) { id ->
                    Toast.makeText(this, "Cut selected", Toast.LENGTH_SHORT).show()
                }
                .addItem(3, "Paste", android.R.drawable.ic_menu_delete) { id ->
                    Toast.makeText(this, "Paste selected", Toast.LENGTH_SHORT).show()
                }
                .show(view)
            true
        }

// Exemple 3: Menu déroulant
        val btnShowMenu = findViewById<Button>(R.id.btnShowMenu)
        btnShowMenu.setOnClickListener { view ->
            EasyUI.createMenu(this, view)
                .addItem(1, "Éditer", android.R.drawable.ic_menu_edit) { id ->
                    Toast.makeText(this, "Éditer sélectionné", Toast.LENGTH_SHORT).show()
                }
                .addItem(2, "Partager", android.R.drawable.ic_menu_share) { id ->
                    Toast.makeText(this, "Partager sélectionné", Toast.LENGTH_SHORT).show()
                }
                .addItem(3, "Supprimer", android.R.drawable.ic_menu_delete) { id ->
                    Toast.makeText(this, "Supprimer sélectionné", Toast.LENGTH_SHORT).show()
                }
                .show()
        }

        // Exemple 4: Menu contextuel
        val tvContextMenu = findViewById<View>(R.id.tvContextMenu)
        tvContextMenu.setOnLongClickListener { view ->
            EasyUI.createContextMenu(this)
                .addItem(1, "Copier", android.R.drawable.ic_menu_edit) { id ->
                    Toast.makeText(this, "Copier sélectionné", Toast.LENGTH_SHORT).show()
                }
                .addItem(2, "Couper", android.R.drawable.ic_menu_share) { id ->
                    Toast.makeText(this, "Couper sélectionné", Toast.LENGTH_SHORT).show()
                }
                .addItem(3, "Coller", android.R.drawable.ic_menu_delete) { id ->
                    Toast.makeText(this, "Coller sélectionné", Toast.LENGTH_SHORT).show()
                }
                .show(view)
            true
        }

        // Exemple 5: Menu déroulant avec TextInputLayout
        val tilDropdown = findViewById<TextInputLayout>(R.id.tilDropdown)
        val dropdownMenu: EasyDropdownMenu<String> = EasyUI.createDropdownMenu(this, tilDropdown)

        val items = listOf("Option 1", "Option 2", "Option 3", "Option 4")
        dropdownMenu.setItems(items)
        dropdownMenu.setOnItemSelectedListener { item ->
            Toast.makeText(this, "Sélectionné: $item", Toast.LENGTH_SHORT).show()
        }
    }
}
```

## Main Components

### Dialogs
- `EasyDialog`: Base class for all dialogs
- `EasyAlertDialog`: Dialog with title, message, and buttons
- `EasyCustomDialog`: Dialog with custom content

### Menus
- `EasyMenu`: Standard dropdown menu
- `EasyContextMenu`: Custom context menu
- `EasyDropdownMenu`: Dropdown menu based on TextInputLayout

### Entry Point
- `EasyUI`: Main facade for creating all types of components

## Compatibility

- Compatible with Android API 24 (Nougat) and higher
- Full integration with Material Design
- Compatible with AndroidX

## License

```
MIT License

Copyright (c) 2024 2zaLab

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```

## Contributions

Contributions are welcome! Feel free to open an issue or submit a pull request on the GitHub repository.

## Contact

For any questions or support, contact us via [Github Issues](https://github.com/2zalab/EasyDialogs/issues) or by email at support@2zalab.com.
