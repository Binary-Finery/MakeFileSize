package com.spencerstudios.makefilesize

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Environment
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_file_explorer.*
import kotlinx.android.synthetic.main.content_file_explorer.*
import java.io.File

class FileExplorerActivity : AppCompatActivity() {

    private lateinit var files: ArrayList<File>
    private lateinit var adapter: FileAdapter
    private lateinit var currentFile: File

    private var rootFile: File = File(Environment.getExternalStorageDirectory().toString())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_file_explorer)
        setSupportActionBar(toolbar)


        btnExplorerSelect.setOnClickListener {
            intent.putExtra(EXTRA_FILENAME, currentFile.toString())
            setResult(SELECT_DIRECTORY_RES_CODE, intent)
            finish()
        }

        currentFile = rootFile

        files = ArrayList()
        adapter = FileAdapter(this, files)
        lv.adapter = adapter

        listFiles()

        lv.setOnItemClickListener { parent, view, pos, id ->
            val file = files[pos]

            if (file.isDirectory) {
                currentFile = file
                listFiles()
            }
        }
    }


    private fun fileExists(file: File): Boolean {
        return file.exists()
    }

    private fun newDirectory(name: String) {
        val file = File(currentFile, name)
        if (file.mkdir()) {
            currentFile = file
            listFiles()
        } else msg("could not create new directory")
    }

    private fun msg(s: String) {
        Toast.makeText(this, s, Toast.LENGTH_LONG).show()
    }

    private fun listFiles() {
        files.clear()
        currentFile.listFiles().forEach { f -> files.add(f) }
        files.sort()
        adapter.notifyDataSetChanged()
        supportActionBar?.subtitle = "$currentFile"
    }

    @SuppressLint("InflateParams")
    private fun newDirDialog() {
        val dialog = AlertDialog.Builder(this).create()
        dialog.setTitle("Create Directory")
        val v = LayoutInflater.from(this).inflate(R.layout.new_dir_dialog, null)
        val etName: EditText = v.findViewById(R.id.etNewDir)
        dialog.setView(v)

        val btnCreate: Button = v.findViewById(R.id.btnCreateDir)
        val btnCancel: Button = v.findViewById(R.id.dialogCancel)

        btnCreate.setOnClickListener {
            val name = etName.text.toString().trim()
            when {
                name.isNotEmpty() -> when {
                    !fileExists(File(currentFile, name)) -> {
                        newDirectory(name)
                        dialog.dismiss()
                    }
                    else -> etName.error = "already exists"
                }
                else -> etName.error = "must not be empty"
            }
        }

        btnCancel.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }

    override fun onBackPressed() {
        when {
            currentFile != rootFile -> {
                currentFile = currentFile.parentFile
                listFiles()
                return
            }
            else -> super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val menuId = R.menu.menu_explorer
        menuInflater.inflate(menuId, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {

            R.id.action_add_folder -> {
                newDirDialog()
                true
            }

            R.id.action_home_dir -> {
                currentFile = rootFile
                listFiles()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}