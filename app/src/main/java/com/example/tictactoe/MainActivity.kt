package com.example.myapplication

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var buttons: Array<Button>
    private lateinit var tvStatus: TextView

    private var currentPlayer = "X"
    private var board = Array(9) { "" }
    private var isGameActive = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvStatus = findViewById(R.id.tvStatus)

        buttons = arrayOf(
            findViewById(R.id.btn0), findViewById(R.id.btn1), findViewById(R.id.btn2),
            findViewById(R.id.btn3), findViewById(R.id.btn4), findViewById(R.id.btn5),
            findViewById(R.id.btn6), findViewById(R.id.btn7), findViewById(R.id.btn8)
        )

        for (button in buttons) {
            button.setOnClickListener(this)
        }
    }

    override fun onClick(view: View?) {
        if (!isGameActive) return

        val button = view as Button
        val index = buttons.indexOf(button)

        if (board[index].isNotEmpty()) return

        board[index] = currentPlayer
        button.text = currentPlayer

        if (checkWin()) {
            tvStatus.text = "Player $currentPlayer Wins!"
            isGameActive = false
        } else if (board.none { it.isEmpty() }) {
            tvStatus.text = "It's a Draw!"
            isGameActive = false
        } else {
            currentPlayer = if (currentPlayer == "X") "O" else "X"
            tvStatus.text = "Player $currentPlayer's Turn"
        }
    }

    private fun checkWin(): Boolean {
        val winPositions = arrayOf(
            intArrayOf(0, 1, 2), intArrayOf(3, 4, 5), intArrayOf(6, 7, 8), // Rows
            intArrayOf(0, 3, 6), intArrayOf(1, 4, 7), intArrayOf(2, 5, 8), // Columns
            intArrayOf(0, 4, 8), intArrayOf(2, 4, 6)  // Diagonals
        )

        for (pos in winPositions) {
            if (board[pos[0]] == currentPlayer &&
                board[pos[1]] == currentPlayer &&
                board[pos[2]] == currentPlayer
            ) {
                return true
            }
        }
        return false
    }
}
