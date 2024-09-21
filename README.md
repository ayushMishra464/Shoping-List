
# Wishlist App

This is a simple Wishlist app built using Kotlin with the MVVM architecture and Room database for data persistence. The app allows users to create, update, and delete wishlist items while ensuring that the data is preserved even after the app is closed.

## Features
- **Add a Wish**: Users can add items to their wishlist.
- **Update a Wish**: Users can edit and update existing wishlist items.
- **Delete a Wish**: Users can remove unwanted items from their wishlist.
- **Data Persistence**: Room database is used to ensure that all wishes are stored and retrieved across app sessions.

## Screenshots

Below are some screenshots of the app to showcase the UI and functionality:


[Add Wish](https://github.com/user-attachments/assets/1b78a491-2efc-4653-8473-75ff4af56328)
 ![Home Screen](https://github.com/user-attachments/assets/53f3d178-c364-4129-9c2c-62a1260f06cf)
 ![Update Wish](https://github.com/user-attachments/assets/87eb6b79-b767-465c-8771-2e3f553fa900)

Make sure to place the screenshots in a `screenshots` folder in your repository, and adjust the image file paths as necessary.

## Tech Stack
- **Kotlin**: Programming language used for Android development.
- **MVVM Architecture**: Implements the Model-View-ViewModel architecture to separate concerns and make the app easier to maintain.
- **Room Database**: Provides local data persistence with minimal boilerplate code.
- **Coroutines**: Used for asynchronous operations to avoid blocking the main thread.
- **Flow**: Utilized to handle data streams asynchronously and to update UI components reactively.

## Repository
The repository class serves as a mediator between the data source (Room database) and the ViewModel. Here's an overview of the methods in the `Repository`:

```kotlin
class Repository(private val wishDao: WishDao) {

    suspend fun insertItem(wish: Wish) {
        wishDao.insertWish(wish)
    }

    suspend fun deleteWish(wish: Wish) {
        wishDao.DeleteWish(wish)
    }

    suspend fun updateWish(wish: Wish) {
        wishDao.updateWish(wish)
    }

    // Retrieves a wish by its ID using Flow to handle asynchronous data streaming
    fun wishById(id: Long) :  Flow<Wish> {
        return wishDao.queryWish(id)
    }

    // Retrieves all wishlist items using Flow for continuous updates
    fun getAllWish() : Flow<List<Wish>> {
        return wishDao.queryAllWish()
    }
}
```

## Setup Instructions
1. Clone this repository.
2. Open it in Android Studio.
3. Sync the project to download all required dependencies.
4. Run the project on an emulator or a physical device.

## Dependencies
Add the following dependencies to your `build.gradle` file:

```gradle
dependencies {
    // Room Database
    implementation "androidx.room:room-runtime:2.3.0"
    kapt "androidx.room:room-compiler:2.3.0"

    // Coroutines for asynchronous operations
    implementation 'org.jetbrains.kotlinx:kotlinx-coroutines-core:1.5.0'
    implementation 'org.jetbrains.kotlinx:kotlinx-coroutines-android:1.5.0'

    // Lifecycle components for ViewModel and LiveData
    implementation "androidx.lifecycle:lifecycle-viewmodel-ktx:2.3.1"
    implementation "androidx.lifecycle:lifecycle-livedata-ktx:2.3.1"
}
```
