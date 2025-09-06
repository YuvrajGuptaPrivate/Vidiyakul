# 🎬 Vidiyakul - Android Video Player App

**Vidiyakul** is an Android video player app built with **Clean Architecture**.  
It allows users to **browse, play videos with ExoPlayer, and resume from the last playback position**.  

---

## 🚀 Features
- 📂 Browse and select from multiple videos  
- 🎥 Play videos using **ExoPlayer**  
- 💾 Save playback timestamps in **Room database**  
- ⏯ Resume playback using **ViewModel**  
- 📡 Handles **no-internet scenarios** gracefully  
- 🖼 Modular UI with **Jetpack Compose**  

---

## 🏗 Architecture
- **Data Layer**  
  - Room Database, DAO, Models, Repositories, Network Utils  

- **Presentation Layer**  
  - Jetpack Compose Screens: `Home`, `VideoPlayer`, `No Internet`  
  - ViewModels for state management  
  - Reusable composables and themes  

---

## 📱 Screens
1. **Home Screen**  
   - Displays video list  
   - Passes `Video ID` to Video Player screen  

2. **Video Player Screen**  
   - Fetches video info (description, URL, etc.)  
   - Plays video with **ExoPlayer**  
   - Saves playback position in Room  

3. **No Internet Screen**  
   - Shown when there’s no connectivity  

---

## ⚙️ Setup Instructions
1. **Clone the repository**
   ```bash
   git clone https://github.com/your-username/Vidiyakul.git
   cd Vidiyakul


## for Testing 

👉 **Important Note**

The main goal of this project was to:
1. Achieve **playback persistency** (saving and resuming video timestamps with Room)
2. Build a **Minimum Viable Product (MVP)** following clean architecture

Because of that:
- The app currently plays **one hardcoded demo video URL** with ExoPlayer  
- The `url` field in the `Video` model is present but **not used yet**  
- Each video item is identified by a **unique `videoId`**, along with its title and description  
- Playback progress is stored in Room **per `videoId`**  

 This ensures that even if every item plays the same demo file, each is treated as an **independent video with its own saved playback position**  

In the future, when multiple working video URLs are available, the app can immediately start using the `url` field — no architectural changes required.


## 📲 Download & Demo

- 📥 **APK Download**: [Click here to download our.apk]([https://your-link-here.com](https://drive.google.com/file/d/1mB8GAUxXdRwWuFfrNMp8NMl-vSQYB9YA/view?usp=sharing))  
- ▶️ **Demo Video**: [Watch the demo](https://your-demo-video-link.com)


