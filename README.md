# PC-Movies
# PCMovies - Trending Movies & Details App 🎬

A clean and modern Android app that displays trending movies using The Movie Database (TMDb) API. Built with Kotlin, Jetpack components, and modern Android development practices.

## 🚀 Features

- 🔐 **User Selection & Creation**
  - Users can be created offline.
  - Local data is auto-synced with the server once network connectivity is available. 
- 🎞️ **Trending Movies Listing**
  - Movie Poster, Title, Release Date
  - Infinite scroll with Paging 3
  - Pull-to-refresh support
- 📄 **Movie Detail View**
  - Full movie description
  - Poster, title, and release date
- 🧭 **Navigation**
  - Navigate from movie list to detail screen seamlessly
- 🌐 **Retrofit with Hilt DI**
  - Clean and scalable network implementation
- 💾 **Repository Pattern**
  - Easy to extend and maintain
- 🌙 **Dark Mode Support**
- 🔄 **Loading Indicators**
  - Shimmer effect while loading movie data
- 🔌 **Offline Support**
  - User creation works without internet and syncs automatically when connected.
- 🌐 **Landscape Mode Supported**

---

## 🔧 Tech Stack

- Kotlin
- Android Jetpack (ViewModel, LiveData, Navigation)
- Retrofit2
- Paging 3
- Hilt (Dependency Injection)
- Glide (Image loading)
- Coroutine + Flow
- Material Design Components

---

## ✅ Assumptions

- A valid TMDb API key is required.
- No login or authentication is required for demo purposes.

---
