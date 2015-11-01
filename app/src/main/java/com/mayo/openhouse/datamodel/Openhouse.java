package com.mayo.openhouse.datamodel;

import com.parse.ParseClassName;
import com.parse.ParseObject;

import java.util.ArrayList;

/**
 * Created by mayo on 31/10/15.
 */
@ParseClassName("openhouse")
public class Openhouse extends ParseObject {
    public Openhouse(){

    }

    public ArrayList<String> getProperties(){
        return (ArrayList<String>) get("properties");
    }
}
