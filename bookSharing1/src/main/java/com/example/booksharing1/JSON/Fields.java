package com.example.booksharing1.JSON;

import com.example.booksharing1.JSON.Field;

import java.util.ArrayList;
import java.util.List;
public class Fields {
    private List<Field> fields;

    public List<Field> getFields() {
        if (fields == null){
            fields = new ArrayList<Field>();
        }
        return fields;
    }

    public void setFields(List<Field> fields) {
        this.fields = fields;
    }
    public Fields(List<Field> fields) {
        this.fields = fields;
    }
}