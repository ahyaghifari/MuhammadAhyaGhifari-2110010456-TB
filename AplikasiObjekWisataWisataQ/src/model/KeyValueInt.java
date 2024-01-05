/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Ahya Ghifari
 */

//  CLASS UNTUK MENAMPUNG KEY DAN VALUE COMBO BOX DENGAN KEY INTEGER
public class KeyValueInt {
    int key;
    String value;
    
    @Override
    public String toString(){
        return value;
    }
    
    public KeyValueInt(){
        key = 0;
        value = "";   
    }
    
    public KeyValueInt(int key, String value){
        this.key = key;
        this.value = value;
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
    
    
}
