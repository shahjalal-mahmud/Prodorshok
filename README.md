# 🚀 Prodorshok App

**Your Smart AI-Powered Career Guide for Students in Bangladesh**

Prodorshok is an Android application built using **Kotlin + Jetpack Compose**, designed to help students explore careers, receive structured roadmaps, and interact with AI for career guidance.

This project was originally developed as a UI-first prototype focused on design quality, AI integration, and investor-ready MVP presentation.

---

## 📱 Overview

Many students in Bangladesh struggle with career confusion after SSC/HSC. Career advice is often scattered, inconsistent, or unavailable.

**Prodorshok solves this by providing:**

* 🎯 Structured career exploration
* 🧠 AI-powered Q&A guidance
* 📍 Career roadmaps
* 📊 SWOT & salary insights
* 📺 Learning resources
* 🚧 Clear premium roadmap for future scalability

---

## 📸 App Screenshots

<div align="center">
  <table>
    <tr>
      <td align="center" width="33%">
        <kbd><b>Login Screen</b></kbd><br><br>
        <img src="screenshots/login.jfif" width="720" alt="Login Screen"/>
      </td>
      <td align="center" width="33%">
        <kbd><b>Signin Screen</b></kbd><br><br>
        <img src="screenshots/singin.jfif" width="720" alt="Signin Screen"/>
      </td>
      <td align="center" width="33%">
        <kbd><b>Dashboard Screen</b></kbd><br><br>
        <img src="screenshots/dashboard.jfif" width="720" alt="Dashboard Screen"/>
      </td>
    </tr>
  </table>
</div>

---

## ✨ MVP Features (Implemented)

### 🔐 1. Authentication

* Firebase Authentication

  * Google Sign-In ✅
  * Email/Password ✅
  * Facebook (Coming Soon)
  * Phone (Coming Soon)

---

### 👤 2. User Profile Setup

Users can set:

* Full Name
* Study Level
* Skills
* Interests
* Career Goal (Optional)
* Location (Optional)

Stored securely in **Firebase Firestore**.

---

### 🏠 3. Dashboard

Main sections include:

* 🎯 Career Guidance
* 🧠 Skill Analysis (Coming Soon)
* 💼 Job Prep (Coming Soon)
* 🤝 Mentorship (Coming Soon)
* ❤️ Mental Support (Coming Soon)
* 📚 Learning Community (Coming Soon)

---

### 🎯 4. Career Guidance Flow

#### Step 1: Career Goal Question

* Do you have a fixed career goal?

  * ✅ Yes → Roadmap
  * ❌ No → Career Suggestions

#### Step 2: Suggestion Questions

* Subjects you enjoy
* Your skills
* Preferred work type

#### Step 3: Career Options

Displays 3–6 careers with:

* Short description
* Explore button

#### Step 4: Career Details

Each career includes:

* Requirements
* Opportunities in Bangladesh
* SWOT Analysis
* Average Salary Range
* FAQs
* Embedded Video (YouTube/mock)
* “Get Roadmap” button

#### Step 5: Career Roadmap

Visual vertical stepper:

* Learn skills
* Get degree
* Build projects
* Apply for jobs/internships

---

### 🤖 5. AI Chat (Lite Version)

* GPT-3.5 powered via OpenRouter API
* Prompt-limited implementation
* Example:

  > “What should I do after HSC if I want to become a data analyst?”

---

### 🚧 6. Coming Soon Screens

UI prepared for:

* Mentorship Tiers (Silver, Gold, Platinum)
* Resume Builder
* LinkedIn Builder
* Skill Gap Analysis
* Mental Health Support
* Community

Designed to show future roadmap for investors.

---

### 🎨 7. Modern UI

* Designed in Canva
* Implemented in Jetpack Compose
* Material 3
* Animations & smooth navigation
* Lottie support
* Markdown rendering support

---

### 🎨 8. ScreenShots


---


## 🛠️ Tech Stack

### 🧱 Android

* Kotlin
* Jetpack Compose
* Navigation Compose
* ViewModel
* Coroutines

### 🔥 Backend & Cloud

* Firebase Authentication
* Firebase Firestore
* Firebase Storage
* Firebase Analytics (optional upgrade)

### 🌐 Networking

* Retrofit
* OkHttp
* Logging Interceptor
* Gson Converter

### 🤖 AI Integration

* OpenRouter API (GPT-3.5)
* API key stored securely via `local.properties`

### 🎨 UI & Media

* Material 3
* Coil / Picasso
* Lottie Animations
* Compose Markdown

---

## 📂 Project Structure (High-Level)

```
com.example.prodorshok
│
├── ui/
│   ├── screens/
│   ├── components/
│   └── navigation/
│
├── data/
│   ├── model/
│   ├── repository/
│   └── remote/
│
├── viewmodel/
│
└── utils/
```

---

## 🔑 Environment Setup

### 1️⃣ Clone the project

```bash
git clone https://github.com/shahjalal-mahmud/Prodorshok
```

---

### 2️⃣ Add API Key

Add your OpenRouter API key to `local.properties`:

```
OPENROUTER_API_KEY=your_api_key_here
```

The project reads it securely via:

```kotlin
buildConfigField("String", "OPENROUTER_API_KEY", "\"${localProperties.getProperty("OPENROUTER_API_KEY")}\"")
```

⚠️ Never commit your API key.

---

### 3️⃣ Firebase Setup

1. Create Firebase Project
2. Add Android App
3. Download `google-services.json`
4. Place inside:

```
app/google-services.json
```

Enable:

* Authentication (Google + Email)
* Firestore Database
* Storage (Optional)

---

## 📦 Gradle Highlights

* Compile SDK: 35
* Min SDK: 24
* Target SDK: 35
* JVM Target: 11
* Compose enabled
* BuildConfig enabled for API key injection

---

## 🚧 Future Roadmap (Post-Funding)

### 💼 Mentorship Platform

* Tiered subscriptions
* Mentor browsing
* Session booking
* Group chat

### 🤖 Advanced AI Tools

* GPT-4 / DeepSeek server-hosted
* Dynamic roadmap tree generation
* Real-time skill gap analysis
* Resume & LinkedIn builder

### ❤️ Emotional & Parental Support

* Mental health chatbot
* Therapy content
* Parental guidance section

### ⚡ Backend Upgrades

* Blaze Plan Firebase
* Dedicated AI backend server
* Advanced analytics

---

## 🎯 Prototype Strategy (Original Plan)

### Month 1

* UI design in Canva
* Compose implementation
* Firebase Auth
* Static career data

### Month 2

* GPT-3.5 integration
* Roadmap system
* Coming Soon screens
* UX polish & testing

---

## ⚠️ Important Note

This project was built primarily as:

* A UI-focused prototype
* AI-integration demo
* Investor-ready MVP concept

It does **not yet focus on:**

* Advanced performance optimization
* Scalability architecture
* Clean architecture separation
* Production-level security hardening

Future versions will refactor the codebase with:

* Proper layered architecture
* Caching strategies
* Offline support
* Improved API management

---

## 👨‍💻 Author

**Shahajalal Mahmud**
Android Developer (Kotlin | Jetpack Compose | Firebase | AI Integration)

---

## 📜 License

This project is for educational and prototype purposes.
License can be updated based on future commercialization.

---
