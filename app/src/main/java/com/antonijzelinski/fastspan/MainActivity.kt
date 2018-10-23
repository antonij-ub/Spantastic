package com.antonijzelinski.fastspan

import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textView.movementMethod = LinkMovementMethod.getInstance();

        textView.text = Spantastic.make(this)
            .add("Anyone who reads").space()
            .add("Old").color(R.color.color3).space()
            .add("and").relSize(0.7f).space()
            .add("Middle").color(R.color.color3).space()
            .add(" English literary texts ").background(R.color.color1).space()
            .add("will be familiar with the mid-brown volumes of the").space()
            .add(R.string.eets).underline().relSize(1.3f).listener {
                Toast.makeText(this, "Click", Toast.LENGTH_SHORT).show()
            }.space()
            .add(" with the symbol of ").colorInt(Color.WHITE).backgroundInt(Color.GRAY).space()
            .add("Alfred's").color(R.color.color3).font(R.font.charmonman_bold).absSize(20).space()
            .add("jewel embossed on").space()
            .add("the front").underline().space()
            .add("cover.").style(Style.BOLD_ITALIC)
            .apply()

    }
}
