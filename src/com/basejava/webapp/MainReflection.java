package com.basejava.webapp;

import com.basejava.webapp.model.Resume;

import java.lang.reflect.Field;

public class MainReflection {
    public static void main(String[] args) throws IllegalAccessException {
        Resume r = new Resume("old_uuid");
        Field field = r.getClass().getDeclaredFields()[0];
        field.setAccessible(true);
        System.out.println(field.getName());
        System.out.println(field.get(r));
        field.set(r, "new_uuid");
        //print r as string via object method
        System.out.println(r);
        //print r as string via reflection
        System.out.println(field.get(r).toString());
    }
}
