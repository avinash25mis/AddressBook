package com.avi.service;

import com.avi.constants.AppConstants;
import com.avi.constants.PropertiesConstant;
import com.avi.models.Contact;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang.StringUtils;

import java.io.*;
import java.lang.reflect.Field;
import java.util.*;

public class AppServiceImpl implements AppService{


    @Override
    public List<Contact> add(List<Contact> existingList, Contact contact, Map<String, Contact> existingMap) {
        if(existingMap.get(contact.getUniqueKey())!=null){
            System.out.println("An record with similar details exists :"+contact.getUniqueKey());
         }else {
            existingList.add(contact);
        }
        return existingList;
    }

    @Override
    public List<Contact> remove(List<Contact> existingList, Contact contact) {
        List<Contact> newList= new ArrayList<>();
        boolean found=false;
        for(Contact c:existingList){
            if(!c.getUniqueKey().equals(contact.getUniqueKey())){
                newList.add(c);
            }else{
                found=true;
            }
        }

        if(!found){
            System.out.println("Record not found to delete :-"+contact.getUniqueKey());
        }
        return newList;
    }

    @Override
    public List<Contact> display(List<Contact> existingList) {
        Collections.sort(existingList);
        for(Contact c:existingList){
            System.out.println(c.toString());
        }
        return null;
    }

    @Override
    public List<Contact> edit(List<Contact> existingList, Contact newContact, Map<String, Contact> existingMap) {

        Contact oldContact = existingMap.get(newContact.getUniqueKey());

        if(oldContact==null){
            System.out.println("Cannot edit, no contact found with key :"+newContact.getUniqueKey());
        }else {

            if (StringUtils.isNotEmpty(newContact.getTelephone())) {
                oldContact.setTelephone(newContact.getTelephone());
            }
            if (StringUtils.isNotEmpty(newContact.getEmail())) {
                oldContact.setEmail(newContact.getEmail());
            }

            if (newContact.getAge() != null) {
                oldContact.setAge(newContact.getAge());
            }

            if (StringUtils.isNotEmpty(newContact.getHairColor())) {
                oldContact.setHairColor(newContact.getHairColor());
            }
            if (StringUtils.isNotEmpty(newContact.getType())) {
                oldContact.setType(newContact.getType());
            }
            if (StringUtils.isNotEmpty(newContact.getRelationShip())) {
                oldContact.setRelationShip(newContact.getRelationShip());
            }
            if (newContact.getFriendshipYears() != null) {
                oldContact.setFriendshipYears(newContact.getFriendshipYears());
            }
        }

        return existingList;
    }

    @Override
    public void generateSampleFile() {
        StringBuilder sb=getHeadersAsCSV();
        StringBuilder sb1=getHeadersDescriptionCSV();

        List<StringBuilder> sbList= new ArrayList<>();
        sbList.add(sb);
        sbList.add(sb1);
        writeIntoTheFile(sbList,AppConstants.FILE_LOCATION+"/"+AppConstants.SAMPLE_FILE_NAME);
    }

    private StringBuilder getHeadersDescriptionCSV() {
        StringBuilder sb= new StringBuilder();
        for(String desc: PropertiesConstant.headerDescription.values()){
            sb.append(desc).append(AppConstants.DELIMETER);
        }
        sb.deleteCharAt(sb.length()-1);
        return sb;
    }



    private StringBuilder getHeadersAsCSV() {
        StringBuilder sb= new StringBuilder();
        for(String header: PropertiesConstant.headerToDto.keySet()){
            sb.append(header).append(AppConstants.DELIMETER);
        }
        sb.deleteCharAt(sb.length()-1);
        return sb;
    }


    public void writePojoInfile(List<Contact> contactList){
        List<StringBuilder> sbList= new ArrayList<>();
        StringBuilder sbHeaders = getHeadersAsCSV();
        sbList.add(sbHeaders);
        for(Contact contact:contactList){
            StringBuilder sb = getCsvFromPogo(contact);
            sbList.add(sb);
        }
        writeIntoTheFile(sbList,AppConstants.FILE_LOCATION+"/"+AppConstants.EXISTING_DATA_FILE);
    }


    private StringBuilder getCsvFromPogo(Contact contact){
        StringBuilder sb= new StringBuilder();
        sb.append(AppConstants.DELIMETER);
        sb.append(contact.getUniqueKey());
        sb.append(AppConstants.DELIMETER);
        sb.append(contact.getName());
        sb.append(AppConstants.DELIMETER);
        sb.append(contact.getSurname());
        sb.append(AppConstants.DELIMETER);
        sb.append(contact.getTelephone());
        sb.append(AppConstants.DELIMETER);
        sb.append(contact.getEmail());
        sb.append(AppConstants.DELIMETER);
        sb.append(contact.getAge());
        sb.append(AppConstants.DELIMETER);
        sb.append(contact.getHairColor());
        sb.append(AppConstants.DELIMETER);
        sb.append(contact.getType());
        sb.append(AppConstants.DELIMETER);
        sb.append(contact.getRelationShip());
        sb.append(AppConstants.DELIMETER);
        sb.append(contact.getFriendshipYears());
       return sb;
    }


    public  List<Contact> readDataUsingReading(String inputPath) {
        List<String[]> rowList = new ArrayList<>();
        List<Contact> contactList = new ArrayList<>();
        File file = new File(inputPath);
        if (file.exists()) {
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {

                if (br != null) {
                    String line;
                    while ((line = br.readLine()) != null) {
                        rowList.add(line.split(AppConstants.DELIMETER));
                    }
                }
                contactList = getPogoFromCsv(rowList);

            } catch(IOException e){
            e.printStackTrace();

           }catch(Exception e){
            e.printStackTrace();

        }
    }
        return contactList;
    }

    private List<Contact> getPogoFromCsv(List<String[]> rowList){
        String [] headers = rowList.get(0);
        List<Contact> contactList = new ArrayList<>();
        Collection<String> headerValues = PropertiesConstant.headerToDto.keySet();


        for (int i = 1; i < rowList.size(); i++) {
            String row[] = rowList.get(i);
            Contact contact = getContactDTo(row);
            if(contact!=null){
                contactList.add(contact);
            }
        }
        return contactList;
    }

    public Contact getContactDTo(String[] row) {
        Contact contact= null;
        Integer primaryIndex1 = PropertiesConstant.headerToIndex.get("name");
        Integer primaryIndex2 = PropertiesConstant.headerToIndex.get("surname");
        if (primaryIndex1!=null && primaryIndex2 !=null && row != null
                && row.length >= primaryIndex1 +1 && row.length >= primaryIndex2 +1 &&
                !StringUtils.isEmpty(row[primaryIndex1]) && !StringUtils.isEmpty(row[primaryIndex2])) {
            contact= new Contact();
            contact.setUniqueKey(row[primaryIndex1]+"-"+row[primaryIndex2]);

            for (int j = 0; j < row.length; j++) {
                if (!StringUtils.isEmpty(row[j]) && PropertiesConstant.indexToHeader.get(j) != null) {
                    Field field = null;
                    try {
                        field = contact.getClass().getDeclaredField(PropertiesConstant.indexToHeader.get(j));
                        field.setAccessible(true);
                        field.set(contact, row[j]);

                    } catch (NoSuchFieldException | IllegalAccessException e) {
                      e.printStackTrace();

                    }
                }
            }
        }
        contact=validateContact(contact);
        return contact;
    }

    private Contact validateContact(Contact contact) {
       if(contact!=null) {
           if (StringUtils.isNotEmpty(contact.getType())) {

               if (!PropertiesConstant.contactTypes.contains(contact.getType())) {
                   System.out.println("Invalid Contact Type :" + contact.getType()+":-"+contact.getUniqueKey());
                   System.out.println("Valid Contact Types are :" + PropertiesConstant.contactTypes);
                   return null;
               }

               if (contact.getType().equalsIgnoreCase(AppConstants.FAMILY)) {
                   if (StringUtils.isEmpty(contact.getRelationShip()) || !PropertiesConstant.relationShips.contains(contact.getRelationShip())) {
                       System.out.println("Invalid relationship :" + contact.getRelationShip()+":-"+contact.getUniqueKey());
                       System.out.println("Valid relationship are :" + PropertiesConstant.relationShips);
                       return null;
                   }
               }

               if (contact.getType().equalsIgnoreCase(AppConstants.FRIEND)) {
                   if (StringUtils.isEmpty(contact.getFriendshipYears())) {
                       System.out.println("Friendship in years missing :" + contact.getUniqueKey());
                       return null;
                   }
               }


           }
       }
       return contact;
    }


    private void writeIntoTheFile(List<StringBuilder> sbList,String filePath) {
        File csvOutputFile = new File(filePath);
        try (PrintWriter pw = new PrintWriter(csvOutputFile)) {
            for(StringBuilder sb:sbList) {
                pw.println(sb);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }



}
