package com.example.quizapp

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_question.*

class QuestionActivity : AppCompatActivity() {

    private var Name: String? = null
    private var score: Int = 0

    private var currentPosition: Int = 1
    private var questionList: ArrayList<QuestionData>? = null
    private var selectedOption: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question)

        Name = intent.getStringExtra(setData.name)

        questionList = setData.getQuestion().shuffled().take(10) as ArrayList<QuestionData>

        setQuestion()

        opt_1.setOnClickListener {
            selectedOptionStyle(opt_1, 1)
        }
        opt_2.setOnClickListener {
            selectedOptionStyle(opt_2, 2)
        }
        opt_3.setOnClickListener {
            selectedOptionStyle(opt_3, 3)
        }
        opt_4.setOnClickListener {
            selectedOptionStyle(opt_4, 4)
        }

        submit.setOnClickListener {
            if (selectedOption != 0) {
                val question = questionList!![currentPosition - 1]
                if (selectedOption != question.correct_ans) {
                    setColor(selectedOption, R.drawable.wrong_question_option)
                } else {
                    score++
                }
                setColor(question.correct_ans, R.drawable.correct_question_option)
                if (currentPosition == 10) {
                    submit.text = "SHOW RESULT"
                } else {
                    submit.text = "Go to Next"
                }
            } else {
                currentPosition++
                if (currentPosition <= 10) {
                    setQuestion()
                } else {
                    val intent = Intent(this, Result::class.java)
                    intent.putExtra(setData.name, Name.toString())
                    intent.putExtra(setData.score, score.toString())
                    intent.putExtra("total size", 10.toString())
                    startActivity(intent)
                    finish()
                }
            }
            selectedOption = 0
        }
    }

    private fun setColor(opt: Int, color: Int) {
        when (opt) {
            1 -> opt_1.background = ContextCompat.getDrawable(this, color)
            2 -> opt_2.background = ContextCompat.getDrawable(this, color)
            3 -> opt_3.background = ContextCompat.getDrawable(this, color)
            4 -> opt_4.background = ContextCompat.getDrawable(this, color)
        }
    }

    private fun setQuestion() {
        val question = questionList!![currentPosition - 1]
        setOptionStyle()

        progress_bar.progress = currentPosition
        progress_bar.max = 10
        progress_text.text = "$currentPosition/10"
        question_text.text = question.question
        opt_1.text = question.option_one
        opt_2.text = question.option_tw0
        opt_3.text = question.option_three
        opt_4.text = question.option_four
    }

    private fun setOptionStyle() {
        val optionList = arrayListOf(opt_1, opt_2, opt_3, opt_4)
        for (op in optionList) {
            op.setTextColor(Color.parseColor("#555151"))
            op.background = ContextCompat.getDrawable(this, R.drawable.question_option)
            op.typeface = Typeface.DEFAULT
        }
    }

    private fun selectedOptionStyle(view: TextView, opt: Int) {
        setOptionStyle()
        selectedOption = opt
        view.background = ContextCompat.getDrawable(this, R.drawable.selected_question_option)
        view.typeface = Typeface.DEFAULT_BOLD
        view.setTextColor(Color.parseColor("#000000"))
    }
}
