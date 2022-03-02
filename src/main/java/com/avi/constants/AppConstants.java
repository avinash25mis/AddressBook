package com.avi.constants;

public class AppConstants {

   public static final String FILE_LOCATION="C:/ADDRESS_BOOK";
   public static final String SAMPLE_FILE_NAME="sample.csv";
   public static final String EXISTING_DATA_FILE="contact_db.csv";


   public static final String OPERATION_TYPE="OPERATION_TYPE";
   public static final String ADD="A";
   public static final String REMOVE="R";
   public static final String DISPLAY="D";
   public static final String EDIT="E";

   public static final String DELIMETER=",";

   public static final String FRIEND="FRIEND";
   public static final String FAMILY="FAMILY";
   public static final String ACQUAINTANCE="ACQUAINTANCE";

   public static final String PARENTS="PARENTS";
   public static final String GRANDPARENTS="GRANDPARENTS";
   public static final String SON="SON";
   public static final String AUNT="AUNT";
   public static final String UNCLE="UNCLE";





   public  static String getOperationTypes() {
      return "A to ADD, R to REMOVE, D to Display, E to Edit";
   }
}
