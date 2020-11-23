package com.example.cloudnotes;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class UniNoteActivity extends AppCompatActivity {
    TextView titleUniNote,contentUniNote;
    FloatingActionButton floatingActionButton;
    int size;
    Button pdfButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uni_note);
        floatingActionButton = findViewById(R.id.edit_floating_button);
        titleUniNote = findViewById(R.id.title_uninote);
        contentUniNote = findViewById(R.id.content_uninote);
        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        String content = intent.getStringExtra("content");
        String noteID = intent.getStringExtra("noteID");
        titleUniNote.setText(title);
        contentUniNote.setText(content);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(UniNoteActivity.this, EditNoteActivity.class);
                intent1.putExtra("title", title);
                intent1.putExtra("content", content);
                intent1.putExtra("noteId", noteID);
                startActivity(intent1);
            }
        });
        SeekBar seekBar=findViewById(R.id.seek_bar);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                size=progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if(size<8) contentUniNote.setTextSize(size*3);
                else contentUniNote.setTextSize(size*4);
            }
        });

        pdfButton=findViewById(R.id.pdf_button);
        pdfButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String path= getExternalFilesDir(null).toString()+"cloudNote.pdf";
                File file=new File(path);
                if(!file.exists()){
                    try {
                        file.createNewFile();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                Document document=new Document(PageSize.A4);
                try {
                    PdfWriter.getInstance(document, new FileOutputStream(file.getAbsoluteFile()));
                } catch (DocumentException e) {
                    e.printStackTrace();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                document.open();
                Font myfont=new Font(Font.FontFamily.HELVETICA,16,Font.NORMAL);
                Paragraph paragraph=new Paragraph();
                paragraph.add(new Paragraph(title,myfont));
                paragraph.add(new Paragraph(content,myfont));
                try {
                    document.add(paragraph);
                } catch (DocumentException e) {
                    e.printStackTrace();
                }
                Toast.makeText(UniNoteActivity.this, "Pdf created at "+path+" ", Toast.LENGTH_LONG).show();
                document.close();
            }
        });

    }
}