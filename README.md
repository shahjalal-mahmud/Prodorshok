# 🚀 Prodorshok – AI Career Companion

<p align="center">
  <img src="screenshots/icon.jfif" width="220" alt="Prodorshok App Icon"/>
</p>

<p align="center">
  <b>An AI-powered Android career guidance platform designed for Bangladeshi students.</b>
</p>

<p align="center">
  Built with Kotlin, Jetpack Compose, Firebase, and GPT-powered AI assistance.
</p>

---

## 📌 Project Status

> ⚠️ **Archived Portfolio Project**
>
> This project is no longer under active development and is being preserved as a portfolio showcase project for learning, technical demonstration, and career opportunities.
>
> The repository remains public to demonstrate:
>
> - Android development skills
> - Modern Jetpack Compose UI implementation
> - AI integration workflows
> - Firebase backend integration
> - MVP product architecture & product thinking

---

# 📱 About The Project

**Prodorshok** was developed as an AI-powered career guidance mobile application focused on helping Bangladeshi students navigate career confusion after SSC, HSC, or university.

The project was built as an investor-oriented MVP combining:

- 🎯 Career exploration
- 🤖 Conversational AI guidance
- 🧠 Skill-gap awareness
- 📍 Structured learning roadmaps
- 📚 Educational resources
- 💼 Future mentorship ecosystem planning

The goal was to create a single mobile platform where students could explore careers, ask AI-powered questions, and receive actionable guidance in a localized context.

---

# ✨ Key Highlights

- ✅ Built complete Android MVP using **Kotlin + Jetpack Compose**
- ✅ Integrated **GPT-3.5** using OpenRouter API
- ✅ Designed modern Material 3 responsive UI
- ✅ Implemented Firebase Authentication & Firestore
- ✅ Added roadmap generation & career recommendation flows
- ✅ Structured scalable UI modules for future expansion
- ✅ Engineered secure API key management using `BuildConfig`
- ✅ Developed investor-ready product presentation flow

---

# 📥 APK Download

If you are reviewing this project for hiring, technical evaluation, or portfolio purposes, you can directly download the APK below.

<p align="center">

[![Download APK](https://img.shields.io/badge/Download%20APK-Android-success?style=for-the-badge&logo=android&logoColor=white)](https://drive.google.com/file/d/1f74Z32Zrevh6W5p_Rp-MG6c1_3o33AVy/view?usp=sharing)

</p>

---

# 📸 Application Screenshots

## 🔐 Authentication & User Flow

<div align="center">

| Login                                           | Sign Up                                          | Dashboard                                           |
| ----------------------------------------------- | ------------------------------------------------ | --------------------------------------------------- |
| <img src="screenshots/login.jfif" width="250"/> | <img src="screenshots/singin.jfif" width="250"/> | <img src="screenshots/dashboard.jfif" width="250"/> |

| Profile                                           | Career Exploration                               | Career Roadmap                                    |
| ------------------------------------------------- | ------------------------------------------------ | ------------------------------------------------- |
| <img src="screenshots/profile.jfif" width="250"/> | <img src="screenshots/career.jfif" width="250"/> | <img src="screenshots/roadmap.jfif" width="250"/> |

</div>

---

## 🤖 AI Guidance & Community

<div align="center">

| AI Career Chat                                   | Feedback                                           | Community Reviews                                     |
| ------------------------------------------------ | -------------------------------------------------- | ----------------------------------------------------- |
| <img src="screenshots/chatAI.jfif" width="250"/> | <img src="screenshots/feedback.jfif" width="250"/> | <img src="screenshots/allFeedback.jfif" width="250"/> |

</div>

---

# 🧠 Core Features

## 🤖 AI Career Guidance

- GPT-powered conversational career assistant
- Career-related Q&A support
- AI-generated suggestions and guidance
- Personalized career direction exploration

### Example Questions

```text
What skills do I need to become a Data Analyst?

Should I learn Flutter or Web Development?

What can I do after HSC if I like programming?
```

---

## 🎯 Career Recommendation System

Users receive career suggestions based on:

- Interests
- Skills
- Academic background
- Preferred work style

Each career path includes:

- Career overview
- Required skills
- Opportunities in Bangladesh
- Salary insights
- SWOT analysis
- Learning recommendations

---

## 🛣️ Career Roadmap System

Visual step-by-step roadmap flow including:

- Learning path
- Skill development
- Degree suggestions
- Project building
- Internship preparation
- Job readiness guidance

---

## 🔐 Authentication & User Management

Implemented with Firebase Authentication:

- Google Sign-In
- Email & Password Authentication
- Firestore user profiles
- Persistent login session

---

## 🎨 Modern Android UI

- Jetpack Compose UI architecture
- Material 3 Design System
- Lottie animations
- Responsive layouts
- Smooth navigation transitions
- Markdown rendering support

---

# 🏗️ Tech Stack

## 📱 Android Development

- Kotlin
- Jetpack Compose
- Navigation Compose
- ViewModel
- Coroutines

---

## 🔥 Backend & Cloud

- Firebase Authentication
- Firebase Firestore
- Firebase Storage

---

## 🌐 Networking

- Retrofit
- OkHttp
- Gson Converter
- Logging Interceptor

---

## 🤖 AI Integration

- OpenRouter API
- GPT-3.5 Integration
- Prompt-engineered career guidance flow

---

## 🎨 UI & Media

- Material 3
- Lottie Animations
- Compose Markdown
- Coil / Picasso

---

# 📂 Project Architecture

## High-Level Structure

```text
com.example.prodorshok
│
├── auth/
│
├── data/
│   ├── local/
│   │   ├── db/
│   │   └── entities/
│   │
│   ├── mappers/
│   │
│   ├── remote/
│   │   ├── api/
│   │   └── dto/
│   │
│   └── repository/
│
├── ui/
│   ├── components/
│   │   ├── animation/
│   │   ├── auth/
│   │   └── common/
│   │
│   ├── navigation/
│   ├── screens/
│   │   ├── auth/
│   │   ├── career_guidance/
│   │   ├── dashboard/
│   │   ├── feedback/
│   │   ├── home/
│   │   ├── profile/
│   │   └── splash/
│   │
│   ├── theme/
│   └── utils/
│
├── viewmodel/
│   ├── auth/
│   └── dashboard/
│
└── utils/
```

---

# ⚙️ Environment Setup

## 1️⃣ Clone Repository

```bash
git clone https://github.com/shahjalal-mahmud/Prodorshok.git
```

---

## 2️⃣ Configure OpenRouter API

Add your API key to:

```properties
local.properties
```

Example:

```properties
OPENROUTER_API_KEY=your_api_key_here
```

BuildConfig injection:

```kotlin
buildConfigField(
    "String",
    "OPENROUTER_API_KEY",
    "\"${localProperties.getProperty("OPENROUTER_API_KEY")}\""
)
```

> ⚠️ Never expose or commit API keys publicly.

---

## 3️⃣ Firebase Configuration

1. Create Firebase project
2. Add Android application
3. Download `google-services.json`
4. Place file inside:

```text
app/google-services.json
```

Enable:

- Authentication
- Firestore Database
- Storage

---

# 📦 Build Configuration

| Config      | Value           |
| ----------- | --------------- |
| Compile SDK | 35              |
| Min SDK     | 24              |
| Target SDK  | 35              |
| JVM Target  | 11              |
| UI Toolkit  | Jetpack Compose |

---

# 🚀 Planned Features (Originally Intended)

Although the project is archived, the original product vision included:

## 💼 Mentorship Marketplace

- Mentor discovery
- Session booking
- Paid mentorship tiers
- Community interaction

---

## 📄 Resume & LinkedIn Builder

- AI-generated resume assistance
- LinkedIn optimization
- Career portfolio suggestions

---

## 🧠 Advanced AI Features

- Dynamic roadmap generation
- Skill-gap analysis
- Personalized growth tracking
- AI learning assistant

---

## ❤️ Student Mental Support

- Mental health guidance
- Emotional support system
- Parent-focused career education resources

---

# 🧪 Engineering Focus Areas

This project primarily focused on:

- Rapid MVP development
- Product validation
- UI/UX quality
- AI workflow integration
- Mobile-first experience
- Startup-oriented feature planning

This repository does **not** represent:

- Enterprise-scale architecture
- Full production optimization
- Advanced security hardening
- Complete clean architecture implementation

---

# 📚 Learning Outcomes

Through this project, key practical experience was gained in:

- Modern Android app development
- Compose UI architecture
- Firebase ecosystem integration
- AI API integration
- Product MVP planning
- Mobile UI/UX implementation
- Repository structuring
- Scalable feature planning

---

# 👨‍💻 Developer

## Md Shahajalal Mahmud

Android Developer • Kotlin Developer • AI Product Builder

### Areas of Interest

- Android Development
- Jetpack Compose
- Firebase Ecosystem
- AI-powered Applications
- Product-focused Engineering

---

# 📜 License

This repository is preserved for:

- Educational purposes
- Portfolio showcasing
- Technical evaluation
- Career opportunities

Commercial reuse or redistribution should be discussed with the author.

---

# ⭐ Final Note

Prodorshok was an ambitious student-built MVP attempting to solve a real-world problem using AI and modern Android technologies.

Even though development has stopped, the project remains an important milestone in demonstrating:

- Product thinking
- Technical implementation
- UI engineering capability
- AI integration experience
- End-to-end Android development workflow

If you are reviewing this repository for hiring or collaboration purposes, feedback is always appreciated.
