package com.xamdroid.attachfile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.core.content.FileProvider
import java.io.File
import java.io.FileOutputStream

class MainActivity : AppCompatActivity() {

    private lateinit var etFileName: EditText
    private lateinit var etFileContent: EditText
    private lateinit var btnAttachFile: Button

    private lateinit var fileName: String
    private lateinit var fileContent: String

    private lateinit var file: File

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        etFileName = findViewById(R.id.editTextName)
        etFileContent = findViewById(R.id.editTextContent)
        btnAttachFile = findViewById(R.id.buttonAttachFile)

        btnAttachFile.setOnClickListener {
            createFile()
            writeFile()
            attachFileToGmail()
        }
    }

    private fun createFile() {
        fileName = etFileName.text.toString()
        fileContent = etFileContent.text.toString()
        file = File(cacheDir, fileName)
        file.createNewFile()
    }

    private fun writeFile() {
        val fileOutputStream = FileOutputStream(file)
        fileOutputStream.write(fileContent.toByteArray())
        fileOutputStream.close()
    }

    private fun attachFileToGmail() {
        val uri = FileProvider.getUriForFile(this, "$packageName.fileProvider", file)
        val intent = Intent(Intent.ACTION_SEND)
        intent.setPackage("com.google.android.gm")
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_STREAM, uri)
        startActivity(intent)
    }
}