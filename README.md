# 👽 Rick and Morty App

A modern Android application built with **Jetpack Compose** that displays a list of characters from the popular show **"Rick and Morty"** using the [Rick and Morty API](https://rickandmortyapi.com/).

This project serves as a practical example of **modern Android development**, showcasing **Clean Architecture**, **modularization**, and the **latest Android libraries** recommended by Google.

---

## ✨ Features

- 🧍‍♂️ **Browse Characters** – Displays an infinite-scrolling list of characters.
- 🔄 **Pagination** – Automatically loads more characters as the user scrolls.
- 📜 **Character Details (Planned)** – Tap on a character to view detailed information.
- 🧩 **Clean Architecture** – The project follows a layered approach: data, domain, and presentation.
- ⚠️ **Error Handling** – Non-intrusive error messages (e.g., via Toast) for network issues.
- ⏳ **Loading States** – Shows loading indicators for both initial and paginated data fetches.

---

## 🛠 Tech Stack & Libraries

This project is built with **100% Kotlin** and leverages modern Android development tools and libraries:

### 🖼 UI
- **Jetpack Compose** – Declarative UI toolkit.
- **Material 3** – Implements Material Design components.
- **Compose Navigation** – Simplified navigation between screens.

### 🏗 Architecture
- **Clean Architecture**
  - **Presentation Layer** – Jetpack Compose, StateFlow, and ViewModels.
  - **Domain Layer** – Business logic, models, and use cases.
  - **Data Layer** – Repository pattern for data management.

### 🧰 Core Libraries
- **Dependency Injection:** [Hilt](https://dagger.dev/hilt/)
- **Asynchronous Programming:** Kotlin Coroutines
- **Networking:** Retrofit – Type-safe HTTP
- **Testing:** MockK – Mocking library for Kotlin

---

## 🧱 Project Structure
The project follows a modular Clean Architecture structure:

### RickAndMortyApp/
#### ├── app/
#### ├── presentation/
#### ├── domain/
#### ├── data/
- **:app:** Main application module
- **:presentation:** UI Layer (Jetpack Compose, ViewModels, Navigation)
- **:domain:** Business Logic Layer (Use Cases, Models)
- **:data:**  Data Layer (Repositories, API Services)

### Module Responsibilities
- **:presentation:** Handles all UI, state management, and navigation. Depends on the `:domain` module.
- **:domain:** Pure Kotlin module — defines business logic, models, and repository interfaces. No Android dependencies.
- **:data:** Implements repositories and manages API/network communication (and future database integration).

---

## 🚀 Getting Started

### Prerequisites
- 🧰 **Android Studio** – Latest stable version recommended.
- 🌐 **Internet Connection** – Required to download Gradle dependencies and fetch API data.

### Steps
1. Clone this repository:
   ```bash
   git clone https://github.com/ZahraHeydari/RickAndMortyApp.git
    ```
2. Open the project in Android Studio.
3. Sync the project with Gradle (File > Sync Project with Gradle Files).
4. Build and run the app on an emulator or a physical device.
