package com.avi.constants;

import java.util.*;

public class PropertiesConstant {

    public static Map<String,String> headerToDto = new HashMap<>();
    public static Map<String,String> dtoToHeader = new HashMap<>();
    public static List<String> list = new ArrayList<>();
    public static Map<String, String> headerDescription= new HashMap<>();

    public static Map<String,Integer> headerToIndex = new HashMap<>();
    public static Map<Integer,String> indexToHeader = new HashMap<>();

    public static List<String> contactTypes = new ArrayList<>(Arrays.asList(AppConstants.FAMILY,AppConstants.FRIEND,AppConstants.ACQUAINTANCE));
    public static List<String> relationShips = new ArrayList<>(Arrays.asList(AppConstants.PARENTS,AppConstants.GRANDPARENTS,AppConstants.SON,AppConstants.UNCLE,AppConstants.AUNT));

}
