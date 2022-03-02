package com;

import com.avi.constants.AppConstants;
import com.avi.models.Contact;
import com.avi.service.AppService;
import com.avi.service.AppServiceImpl;
import com.avi.service.PropertyService;
import com.avi.service.PropertyServiceImpl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class DriverMain {

    private AppService appService= new AppServiceImpl();
    private PropertyService propertyService= new PropertyServiceImpl();



    public static void main(String[] args) throws IOException {
       DriverMain main= new DriverMain();
        main.extracteArguments(args, main);

    }

    private  void extracteArguments(String[] args, DriverMain main) throws IOException {
        propertyService.varifyIfFolderExist();
        propertyService.readJsonPropertiesforHeaders();
        List<Contact> existingList = appService.readDataUsingReading(AppConstants.FILE_LOCATION + "/" + AppConstants.EXISTING_DATA_FILE);
        Map<String, Contact> existingMap = existingList.stream().collect(Collectors.toMap(e -> e.getUniqueKey(), Function.identity()));
        if(args.length==0){
            main.generateSampleFile();
        }
        else {
            List<String[]> rowList=new ArrayList<>();
            for(String st: args){
                String[] row=st.split(AppConstants.DELIMETER);
                rowList.add(row);
            }

            main.eveluateAndPerformAction(rowList,existingList,existingMap);
        }
    }



    private  void eveluateAndPerformAction(List<String[]> rowList, List<Contact> existingList, Map<String, Contact> existingMap) throws IOException {

        List<Contact> newList= new ArrayList<>();
        boolean change=false;

        for(String[] row:rowList) {
            String operationType = row[0];
            Contact contact = appService.getContactDTo(row);
            if (contact == null) {
                System.out.println("Please follow the" + AppConstants.SAMPLE_FILE_NAME + " .in the location :" + AppConstants.FILE_LOCATION);
            }
            if (operationType.equalsIgnoreCase(AppConstants.ADD) && contact!=null) {
                newList=appService.add(existingList, contact, existingMap);
                change=true;
            } else if (operationType.equalsIgnoreCase(AppConstants.REMOVE) && contact!=null) {
                newList = appService.remove(existingList, contact);
                change=true;
            } else if (operationType.equalsIgnoreCase(AppConstants.EDIT) && contact!=null) {
                newList = appService.edit(existingList, contact, existingMap);
                change=true;
            }else if(operationType.equalsIgnoreCase(AppConstants.DISPLAY)){
                appService.display(existingList);
            }
        }
        if(newList!=null && !newList.isEmpty() && change) {
            appService.writePojoInfile(newList);
        }
        if(change) {
            System.out.println("Changes done");
        }else {
            System.out.println("No change done");
        }
    }




    private void generateSampleFile() throws IOException {

        appService.generateSampleFile();
        System.out.println("Choose operation from:"+AppConstants.getOperationTypes());
        System.out.println("Get the sample file downloaded at :"+ AppConstants.FILE_LOCATION+"/"+AppConstants.SAMPLE_FILE_NAME);
    }
}
