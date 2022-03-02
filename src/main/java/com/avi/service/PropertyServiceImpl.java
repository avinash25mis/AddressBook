package com.avi.service;

import com.avi.constants.AppConstants;
import com.avi.constants.PropertiesConstant;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

public class PropertyServiceImpl implements PropertyService{


    @Override
    public void varifyIfFolderExist() {
        File file= new File(AppConstants.FILE_LOCATION);
        if(!file.exists()){
            file.mkdirs();
        }
    }

    @Override
    public void readJsonPropertiesforHeaders() throws IOException {

        ClassLoader classLoader = getClass().getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream("address.json");
        ObjectMapper obj=new ObjectMapper();
        Map<String, Map<String,String>> headerList= obj.readValue(inputStream, Map.class);
        PropertiesConstant.headerToDto = headerList.get("headersToDto");
        PropertiesConstant.headerDescription = headerList.get("headerDescription");
        for(Map.Entry<String, String> map:PropertiesConstant.headerToDto.entrySet()){
            PropertiesConstant.dtoToHeader.put(map.getValue(), map.getKey());
            PropertiesConstant.list.add(map.getValue());
        }
        initializeHeaderToIndexMap(PropertiesConstant.list);
        initializeIndexToHeaderMap(PropertiesConstant.list);


    }

    private void initializeIndexToHeaderMap(List<String> headers) {
        for(int i=0;i<headers.size();i++){
            if(headers.get(i)!=null) {
                PropertiesConstant.indexToHeader.put(i, headers.get(i));
            }
        }
    }

    private void initializeHeaderToIndexMap(List<String> headers) {
        for(int i=0;i<headers.size();i++){
            if(headers.get(i)!=null) {
                PropertiesConstant.headerToIndex.put(headers.get(i), i);
            }
        }
    }


}
