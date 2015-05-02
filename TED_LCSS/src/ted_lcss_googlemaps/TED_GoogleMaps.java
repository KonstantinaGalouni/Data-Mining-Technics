
package ted_lcss_googlemaps;

import java.io.IOException;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.LineNumberReader;

/**
 *
 * @author Spiros Delviniotis
 */

public class TED_GoogleMaps {
    //String f_1;
    //String f_2;
    
    public void browser() throws FileNotFoundException{
        /* Create Html File */
        GoogleMapsHtml write = new GoogleMapsHtml();
        write.HtmlCreate();
        
        /* Read txt file_1 to get lat,lon */
        try{
            File file_1 =new File("coordinates_1.txt");
 
            if (file_1.exists())
            {
                int linenumber = 0;
                int i;
                FileReader fr = new FileReader(file_1);
                try (LineNumberReader lnr = new LineNumberReader(fr)) {
                    while (lnr.readLine() != null){
                        linenumber++;
                    }
                }
                    
                System.out.println("Number of lines in file: " + linenumber);
                try (BufferedReader reader = new BufferedReader(new FileReader("coordinates_1.txt"))) {
                    String line = null;
                    String[] textData = new String[2];
                    int flag = 1;  //none line red
                    while ((line = reader.readLine()) != null) {
                        if (flag == 0)
                        {
                            textData = line.split(",",2);
                            
                            /* Add marker to Html File */
                            write.HtmlAddMarker(textData[0], textData[1]);
                        }
                        else
                        {
                            flag = 0;
                        }
                    }
                }
            }
            else
            {
                System.out.println("File does not exists!");
            }
 
    	}catch(IOException e){
            
    	}
        
        write.HtmlCloseFiles();
        
        /* Read txt file_2 to get lat,lon */
        try{
            File file_2 =new File("coordinates_2.txt");
 
            if (file_2.exists())
            {
                int linenumber = 0;
                int i;
                FileReader fr_2 = new FileReader(file_2);
                try (LineNumberReader lnr = new LineNumberReader(fr_2)) {
                    while (lnr.readLine() != null){
                        linenumber++;
                    }
                }
                    
                System.out.println("Number of lines in file: " + linenumber);
                try (BufferedReader reader = new BufferedReader(new FileReader("coordinates_2.txt"))) {
                    String line = null;
                    String[] textData = new String[2];
                    int flag = 1;  //none line red
                    
                    while ((line = reader.readLine()) != null) {
                        if (flag == 0)
                        {
                            textData = line.split(",",2);
                            
                            /* Add marker to Html File */
                            write.HtmlAddMarker(textData[0], textData[1]);
                        }
                        else
                        {
                            flag = 0;
                        }
                    }
                }
            }
            else
            {
                System.out.println("File does not exists!");
            }
 
    	}catch(IOException e){
            
    	}
        
        /* Finallize Html File */
        write.HtmlFinallizer();
        
        /* Launch browser */
        Google_maps embeded_browser = new Google_maps();
        embeded_browser.Browser();
    }

    /*public TED_GoogleMaps(String file_1, String file_2) {
        f_1 = file_1;
        f_2 = file_2;
    }*/
}
