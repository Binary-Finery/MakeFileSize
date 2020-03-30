package com.spencerstudios.makefilesize

import android.annotation.SuppressLint
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import kotlinx.android.synthetic.main.activity_blank_message_generator.*
import kotlinx.android.synthetic.main.content_blank_message_generator.*

class LoremIpsumGeneratorActivity : AppCompatActivity() {

    private val sb = StringBuilder()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_blank_message_generator)
        setSupportActionBar(toolbar)

        toolbar.visibility = View.GONE

        getText().forEach { s ->
            sb.append(s.plus("\n\n"))
        }

        tvLoremIpsum.text = sb.toString().trimEnd()

        fabOpenSettings.setOnClickListener {
            dispDialog()
        }

        icMenu.setOnClickListener {v->
            dispMenu(v)
        }
    }

    @SuppressLint("InflateParams")
    fun dispDialog() {
        val v = LayoutInflater.from(this).inflate(R.layout.lorem_ipsum_dialog, null)
        val dialog = AlertDialog.Builder(this)
        dialog.setTitle("Settings")
        dialog.setView(v)

        val qty: EditText = v.findViewById(R.id.etQty)
        val grp: RadioGroup = v.findViewById(R.id.radGrp)

        val rbPara : RadioButton = v.findViewById(R.id.rbParagraphs)
        rbPara.isChecked = true

        dialog.setPositiveButton("okay") { _, _ ->
            val rb = grp.findViewById<RadioButton>(grp.checkedRadioButtonId)
            val s = qty.text.toString()
            var n = 0
            when {
                s.isNotEmpty() -> n = s.toInt()
            }
            proc(grp.indexOfChild(rb), n)
        }

        dialog.setNegativeButton("cancel") { d, _ ->
            d.dismiss()
        }

        dialog.create().show()
    }

    private fun proc(i: Int, qty: Int) {
        when (i) {
            0 -> {
                paras(qty)
            }
            1 -> {
                sentences(qty)
            }
            2-> bulletList(qty)
        }
    }

    private fun bulletList(qty: Int) {
        sb.setLength(0)

        val sen = sentence()
        var idx = 0
        for (i in 0 until qty) {
            val p = sen[idx]
            sb.append("•  ".plus(p).plus("\n\n"))
            idx++
            if (idx == sen.size) {
                idx = 0
            }
        }

        val txt = sb.toString().replace("..", ". ").trimEnd()
        tvLoremIpsum.text = txt

    }

    private fun paras(qty: Int) {
        sb.setLength(0)

        val paras = getText()
        val parasLen = paras.size
        var idx = 0

        for (i in 0 until qty) {
            sb.append(paras[idx].plus("\n\n"))
            idx++
            when (idx) {
                parasLen -> idx = 0
            }
        }
        tvLoremIpsum.text = sb.toString().trimEnd()
        sb.setLength(0)
    }

    private fun sentences(qty: Int) {
        sb.setLength(0)

        val sen = sentence()
        var idx = 0
        for (i in 0 until qty) {
            val p = sen[idx]
            sb.append(p.plus(" "))
            idx++
            if (idx == sen.size) {
                idx = 0
            }
        }

        val txt = sb.toString().replace("..", ". ").trimEnd()
        tvLoremIpsum.text = txt
    }

    private fun dispMenu(v: View) {
        val pum = PopupMenu(this, v)
        pum.apply {

            menu.add("Copy")
            menu.add("Share")

            setOnMenuItemClickListener {
                when(it.title){
                    "Copy"-> copy()
                    "Share" -> share()
                }
                false
            }
            show()
        }
    }

    private fun copy(){
        val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText("text", tvLoremIpsum.text.toString())
        clipboard.primaryClip = clip
        Toast.makeText(this, "copied to clipboard", Toast.LENGTH_SHORT).show()
    }

    private fun share(){

        val content = tvLoremIpsum.text.toString()

        if (!content.isEmpty()) {
            val i = Intent(android.content.Intent.ACTION_SEND)
            i.type = "text/plain"
            i.putExtra(android.content.Intent.EXTRA_SUBJECT, "lorem ipsum")
            i.putExtra(android.content.Intent.EXTRA_TEXT, content)
            startActivity(Intent.createChooser(i, "share..."))
        } else {
            Toast.makeText(this, "nothing to share", Toast.LENGTH_SHORT).show()
        }
    }
}
