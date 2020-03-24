package com.spencerstudios.makefilesize

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.os.AsyncTask
import android.os.Build
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import java.io.File
import java.util.*


class MainActivity : AppCompatActivity(), TextWatcher {

    private var byteSize: Long = 0
    private var isProcessing = false
    private var path : String = ""

    private var PERMS_REQUEST_CODE = 123
    private lateinit var permissionsList : Array<String>

    private var totalChunks = 0

    private lateinit var etBytesArr: Array<EditText>
    private lateinit var progressDialog : AlertDialog
    private lateinit var tvProgress : TextView
    private lateinit var progressBar : ProgressBar
    private lateinit var wtfa : AsyncTask<String, String, String>

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        permissionsList = arrayOf(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
        if (hasPermission()) {
            Log.d("has permission", "permitted");
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                reqPermissions()
            }
        }

        initProgressDialog()

        etBytesArr = arrayOf(etMb, etKb, etB)
        tvBytes.text = "$byteSize bytes"

        (0 until etBytesArr.size).forEach { i ->
            etBytesArr[i].addTextChangedListener(this)
        }

        calcBytes()

        btnCreateFile.setOnClickListener {
            if (hasPermission()) {
                when {
                    !isProcessing -> {
                        val filename = etFilename.text.toString().trim()
                        when {
                            filename.isNotEmpty() && path.isNotEmpty() -> {
                                setChunks()
                                wtfa = WriteToFileAsync(filename, byteSize)
                                wtfa.execute("")
                            }
                            else -> Toast.makeText(
                                this@MainActivity,
                                "writing to file, please wait!",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                    else -> Toast.makeText(
                        this@MainActivity,
                        "file name and path must be specified",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }else{
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    reqPermissions()
                }
            }
        }

        tvPath.setOnClickListener {
            if(hasPermission()) {
                val i = Intent(this@MainActivity, FileExplorerActivity::class.java)
                startActivityForResult(i, SELECT_DIRECTORY_RES_CODE)
            }else{
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    reqPermissions()
                }
            }
        }
    }

    private fun setChunks() {
        totalChunks = (byteSize / TEN_MB).toInt()
        when{
            totalChunks * TEN_MB < byteSize -> totalChunks ++
        }
    }

    private fun calcBytes() {
        val arr = LongArray(etBytesArr.size)
        (0 until etBytesArr.size).forEach { i ->
            val n = etBytesArr[i].text.toString()
            arr[i] = when {
                n.isEmpty() -> 0
                else -> n.toLong()
            }
        }
        byteSize = ((arr[0] * MB) + (arr[1] * KB) + arr[2])
        formatBytes()
    }

    private fun formatBytes() {
        var unit = "B"
        var size: Double = byteSize.toDouble()
        when {
            byteSize >= MB -> {
                unit = "MB"
                size /= MB
            }
            byteSize >= KB -> {
                unit = "KB"
                size /= KB
            }
        }
        tvBytes.text = String.format(Locale.getDefault(), "%,d bytes", byteSize)
        tvFormattedBytes.text = when {
            size % 1 == .0 -> String.format(Locale.getDefault(), "%,d %s", size.toInt(), unit)
            else -> String.format(Locale.getDefault(), "%,.2f %s", size, unit)
        }
    }

    @SuppressLint("StaticFieldLeak")
    private inner class WriteToFileAsync(val filename: String, val bytes : Long) :
        AsyncTask<String, String, String>() {

        override fun doInBackground(vararg params: String): String {

            when {
                File(path, filename).exists() -> File(path, filename).delete()
            }

            val file = File(path, filename)

            try {
                var prog: Long = 0

                if (byteSize > TEN_MB) {
                    val chunks = bytes / TEN_MB
                    for (i in 0 until chunks) {
                        val ba = ByteArray(TEN_MB)
                        file.appendBytes(ba)
                        prog ++
                        publishProgress("$prog")
                    }

                    val remainder = bytes % (chunks * TEN_MB)

                    Log.d("rem", "$remainder")

                    if (remainder > 0) {
                        val ba = ByteArray(remainder.toInt())
                        file.appendBytes(ba)
                        prog ++
                        publishProgress("$prog")
                    }
                } else {
                    val ba = ByteArray(bytes.toInt())
                    file.writeBytes(ba)
                }
            }catch(e:Exception){
                runOnUiThread {
                    handledCancelledAsync(e.message.toString())
                }
                cancel(true)
            }

            return file.toString()
        }

        @SuppressLint("SetTextI18n")
        override fun onProgressUpdate(vararg values: String) {
            val b = values[0].toInt()
            val percent = (b * 100) / totalChunks
            tvProgress.text = String.format(Locale.getDefault(), "%d%%", percent)
            progressBar.progress = b
        }

        override fun onPostExecute(result: String) {
            progressDialog.dismiss()
            isProcessing = false

            fileCreated(result)
        }

        override fun onCancelled(result: String?) {
            super.onCancelled(result)

            Toast.makeText(this@MainActivity, "process cancelled",Toast.LENGTH_LONG).show()
            isProcessing = false
            progressDialog.dismiss()
        }

        @SuppressLint("SetTextI18n")
        override fun onPreExecute() {

            progressDialog.show()
            isProcessing = true

            progressBar.progress = 0
            progressBar.max = totalChunks
            tvProgress.text = "0%"

        }
    }

    private fun fileCreated(fn : String){
        val file = File(fn)
        val fileCreatedDialog = AlertDialog.Builder(this)
        if(file.exists()) {
            fileCreatedDialog.setTitle("File Created")
            fileCreatedDialog.setMessage("'${file.name}' was successfully created")
            fileCreatedDialog.setPositiveButton("close") { d, _ ->
                d.dismiss()
            }
        }
        fileCreatedDialog.create().show()
    }

    private fun handledCancelledAsync(s : String){
        val errDialog = AlertDialog.Builder(this)
        errDialog.setTitle("Error")
        errDialog.setMessage(s)
        errDialog.setPositiveButton("close"){d,_->
            d.dismiss()
        }
        errDialog.create().show()
    }

    override fun afterTextChanged(s: Editable?) {}
    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        calcBytes()
    }

    @SuppressLint("InflateParams")
    private fun initProgressDialog(){
        val v = LayoutInflater.from(this).inflate(R.layout.progress_dialog, null)
        progressDialog = AlertDialog.Builder(this).create()
        progressDialog.setTitle("Creating File...")
        tvProgress = v.findViewById(R.id.progressBytes)
        progressBar = v.findViewById(R.id.progBar)
        progressDialog.setCanceledOnTouchOutside(false)
        progressDialog.setView(v)
    }

    override fun onActivityResult(rc: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(rc, resultCode, data)
        when (rc) {
            SELECT_DIRECTORY_RES_CODE -> {
                val file = data?.getStringExtra(EXTRA_FILENAME)
                when {
                    file != null -> {
                        path = file
                        tvPath.text = path
                    }
                }
            }
        }
    }

    override fun onBackPressed() {

        when {
            isProcessing -> wtfa.cancel(true)
        }
        super.onBackPressed()
    }

    private fun hasPermission(): Boolean {
        for(perm in permissionsList){
            if(ActivityCompat.checkSelfPermission(this,perm)
                != PackageManager.PERMISSION_GRANTED){
                return false
            }
        }
        return true
    }

    private fun reqPermissions(){
        if (Build.VERSION.SDK_INT >= 23) {
            requestPermissions(permissionsList, PERMS_REQUEST_CODE)
        }
    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Log.d("PERMISSION", "granted")
        } else {
            if (!ActivityCompat.shouldShowRequestPermissionRationale(this, permissionsList[0]) || !ActivityCompat.shouldShowRequestPermissionRationale(this, permissionsList[1])) {
                Toast.makeText(this@MainActivity, "access denied :(", Toast.LENGTH_LONG).show()
            }
        }
    }
}
