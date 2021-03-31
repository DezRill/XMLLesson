package com.dezrill.xmllesson;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Xml;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import org.xmlpull.v1.XmlSerializer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringWriter;

public class AddNewFragment extends Fragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_new, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();

        EditText title=getView().findViewById(R.id.titleText);
        EditText description=getView().findViewById(R.id.descriptionText);

        getView().findViewById(R.id.addBtn).setOnClickListener(v -> {
            if (title.getText().toString().equals("") || description.getText().toString().equals(""))
                Toast.makeText(getContext(), "Fill all fields!", Toast.LENGTH_SHORT).show();
            else {
                try {
                    FileOutputStream stream=getContext().openFileOutput("items.xml", Context.MODE_APPEND);
                    XmlSerializer xmlSerializer= Xml.newSerializer();
                    StringWriter writer=new StringWriter();
                    xmlSerializer.setOutput(writer);
                    xmlSerializer.startTag(null, "item");
                    xmlSerializer.attribute(null, "title", title.getText().toString());
                    xmlSerializer.attribute(null,"description", description.getText().toString());
                    xmlSerializer.endTag(null, "item");
                    xmlSerializer.flush();
                    String dataWrite=writer.toString();
                    stream.write(dataWrite.getBytes());
                    stream.close();

                    MainActivity.controller.navigate(R.id.action_addNewFragment_to_listViewFragment);
                } catch (FileNotFoundException e) {
                    try {
                        FileOutputStream stream = getContext().openFileOutput("items.xml", Context.MODE_PRIVATE);
                        XmlSerializer xmlSerializer= Xml.newSerializer();
                        StringWriter writer=new StringWriter();
                        xmlSerializer.setOutput(writer);
                        xmlSerializer.startTag(null, "items");
                        xmlSerializer.startTag(null, "item");
                        xmlSerializer.attribute(null, "title", title.getText().toString());
                        xmlSerializer.attribute(null,"description", description.getText().toString());
                        xmlSerializer.endTag(null, "item");
                        xmlSerializer.endTag(null, "items");
                        xmlSerializer.flush();
                        String dataWrite=writer.toString();
                        stream.write(dataWrite.getBytes());
                        stream.close();
                    } catch (IOException fileNotFoundException) {
                        fileNotFoundException.printStackTrace();
                    }
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}