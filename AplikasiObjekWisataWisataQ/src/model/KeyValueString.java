/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Ahya Ghifari
 */
//  CLASS UNTUK MENAMPUNG KEY DAN VALUE COMBO BOX DENGAN KEY STRING
public class KeyValueString {
    String key;
    String value;
    
    @Override
    public String toString(){
        return value;
    }
    
    public KeyValueString(){
        key = "";
        value = "";   
    }
    
    public KeyValueString(String key, String value){
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
    
    
}
