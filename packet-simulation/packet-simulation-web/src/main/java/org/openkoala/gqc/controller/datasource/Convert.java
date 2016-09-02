package org.openkoala.gqc.controller.datasource;

public class Convert {
	
	    private static String ascii2native ( String asciicode )
	    {
	        String[] asciis = asciicode.split ("\\\\u");
	        String nativeValue = asciis[0];
	        try
	        {
	            for ( int i = 1; i < asciis.length; i++ )
	            {
	                String code = asciis[i];
	                nativeValue += (char) Integer.parseInt (code.substring (0, 4), 16);
	                if (code.length () > 4)
	                {
	                    nativeValue += code.substring (4, code.length ());
	                }
	            }
	        }
	        catch (NumberFormatException e)
	        {
	            return asciicode;
	        }
	        return nativeValue;
	    }
	 
	    public static void main ( String[] args )
	    {
//	        String str = "\"JWHQK_JWQC\":\"\u6c5f\u5b89\u793e\u533a\u5c45\u6c11\u59d4\u5458\u4f1a\"";
	        String str = "{\"一直\":\"\u4e00啊\u81f4\"}";
	        String result = ascii2native (str);
	        System.out.println(result);
	    }
}
