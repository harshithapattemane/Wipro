package com.contact;

import java.util.ArrayList;
import java.util.List;

public class ContactService {

    private static List<Contact> contacts = new ArrayList<>();

    public static void add(Contact contact) {
        contacts.add(contact);
    }

    public static List<Contact> getAll() {
        return contacts;
    }

    public static Contact get(int index) {
        return contacts.get(index);
    }

    public static void update(int index, Contact newContact) {
        contacts.set(index, newContact);
    }

    public static void delete(int index) {
        contacts.remove(index);
    }
}
