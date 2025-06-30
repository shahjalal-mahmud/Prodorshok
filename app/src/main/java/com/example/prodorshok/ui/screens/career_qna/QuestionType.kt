package com.example.prodorshok.ui.screens.career_qna

data class QnAItem(
    val question: String,
    val answer: String
)

sealed class QuestionType {
    data class YesNo(val question: String) : QuestionType()
    data class MultipleChoice(val question: String, val options: List<String>) : QuestionType()
    data class Checkbox(val question: String, val options: List<String>) : QuestionType()
    data class ShortAnswer(val question: String) : QuestionType()
}

