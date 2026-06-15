# ⚡ Circuit Design Q&A Tutor

An AI-powered interactive tutoring application where students can describe circuit problems and get detailed explanations, step-by-step calculations, and design improvement suggestions.

![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.1.0-brightgreen)
![Java](https://img.shields.io/badge/Java-17-orange)
![MySQL](https://img.shields.io/badge/MySQL-8.0-blue)
![Groq AI](https://img.shields.io/badge/Groq-AI-purple)

---

## 🚀 Features

### Phase 1 — Core AI Tutor + History
- Ask any circuit design question in natural language
- AI explains **Theory**, walks through **Calculations**, and gives **Design Tips**
- All questions and answers saved to MySQL database
- Full question history with view and delete

### Phase 2 — Dashboard + Saved Notes + PDF Export
- 📊 Dashboard with total questions, saved notes, topics covered
- 📌 Save important answers as notes (localStorage)
- 📄 Export any answer as a styled PDF
- 🕐 Recent questions on dashboard

### Phase 3 — Circuit Image Analysis
- 📸 Upload a circuit image
- AI identifies components, explains topology, suggests improvements
- Markdown-rendered responses for clean formatting

---

## 🛠️ Tech Stack

| Layer | Technology |
|-------|-----------|
| Backend | Java 17, Spring Boot 4.1 |
| AI API | Groq API (llama-3.3-70b-versatile) |
| Database | MySQL 8.0 + Spring Data JPA + Hibernate |
| Frontend | HTML, CSS, Vanilla JavaScript |
| PDF Export | jsPDF |
| Markdown | Marked.js |

---

## 📁 Project Structure
