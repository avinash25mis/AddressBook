package com.avi.service;

import com.avi.models.Contact;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface AppService {

    List<Contact> add(List<Contact> existingList, Contact contact, Map<String, Contact> existingMap);
    List<Contact>  remove(List<Contact> existingList, Contact contact);
    List<Contact>  display(List<Contact> existingList);
    List<Contact>  edit(List<Contact> existingList, Contact contact, Map<String, Contact> existingMap);
    void generateSampleFile();

    List<Contact> readDataUsingReading(String inputPath);
    void writePojoInfile(List<Contact> contactList);
    Contact getContactDTo(String[] row);

}
