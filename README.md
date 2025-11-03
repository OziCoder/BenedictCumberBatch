# Benedict Cumberbatch Movies App

A simple Android app that fetches and displays a list of popular Benedict Cumberbatch movies from [TMDB](https://www.themoviedb.org/) using a clean MVVM architecture and modern Android libraries.  
It demonstrates separation of concerns, UI state handling, Compose UI, and unit testing best practices.

---

## Setup Instructions

1. Build and Run

   - Open the project in *Android Studio Giraffe or newer 
   - Choose a device/emulator (API 24+)  
   - Run

   Robolectric runs the Fragment and Compose UI tests locally on the JVM, no emulator required.

---
## Architecture Decisions and Reasoning

### Pattern: MVVM (Model–View–ViewModel)
- **Why:** provides clear separation between UI (View), presentation logic (ViewModel), and data (Repository).  
- **Benefit:** makes UI easy to test and maintain.

### Layers

| Layer | Responsibility | Example |
|-------|----------------|----------|
| **UI (Compose / Fragment)** | Renders data, collects state from ViewModel | HomeFragment, DetailScreen |
| **ViewModel** | Holds state, triggers repository calls, exposes StateFlow | HomeViewModel |
| **Repository** | Abstracts data source (network / DB) | MovieRepositoryImpl |
| **Remote API** | Uses Retrofit for TMDB endpoints | TmdbApi, ApiInterceptor |

### State Handling
- ViewModel exposes StateFlow<HomeUiState> → collected in UI using collectAsState().
- Prevents double emission on configuration changes.

### Testability
- ViewModel tested with MockK and runTest.
- Repository tested with mocked TmdbApi.
- UI verified via Robolectric (Fragments) and Compose JUnit4 (Composable screen).

---

## Libraries Used — and Why

| Library                       | Purpose                            | Reason for Choice |
|---------- ------------------- |------------------------------------|------------------|
| **Retrofit + OkHttp**         | REST client / interceptor          | Battle-tested networking; easy JSON mapping |
| **Gson**                      | JSON serialization                 | Simplicity for TMDB schemas |
| **Kotlin Coroutines + Flow**  | Async operations                   | Clean cancellation & state stream handling |
| **Jetpack Compose**           | UI framework                       | Modern, declarative UI |
| **Lifecycle / ViewModel KTX** | State management                   | Simplifies coroutine scopes |
| **MockK**                     | Unit-test mocking                  | Kotlin-friendly alternative to Mockito |
| **Robolectric**               | Run Fragments on JVM               | No emulator needed for UI tests |
| **Compose UI Test Junit4**    | Compose assertions                 | Verifies composable rendering |
| **JUnit4 + Coroutines Test**  | Core testing                       | Stable async testing |
| **InstantTaskExecutorRule**   | Synchronous LiveData / ListAdapter | Ensures deterministic adapter updates |

---

## 💡 What I Would Improve With More Time

1. **Offline Caching**
   - Add a Room database layer or Paging 3 to cache movies locally.
2. **Dependency Injection**
   - Replace manual AppContainer with **Hilt/Dagger** for easier test injection.
3. **UI Polish**
   - Animate list items, add placeholder/error images.
4. **Navigation**
   - Adopt Navigation Compose instead of fragment transactions.
5. **Modularization**
   - Split app into core, data, and feature modules for scalability.

---

## Challenges Encountered

| Challenge | Resolution |
|------------|-------------|
| **Async timing in tests** (RecyclerView ListAdapter not yet updated) | Used InstantTaskExecutorRule + ShadowLooper.runUiThreadTasksIncludingDelayedTasks() to flush UI threads. |
| **Compose test invisibility** | Wrapped screens in MaterialTheme and added scroll-aware assertions for nodes off-screen. |

---

## Summary

This project demonstrates a modern Android stack using:
- **Kotlin + Coroutines**
- **Jetpack Compose UI**
- **MVVM clean architecture**
- **Testable design** with MockK, Robolectric, Compose testing

It’s lightweight, fast to run, and easy to extend—ideal for showcasing clean Android architecture and practical testing patterns.
