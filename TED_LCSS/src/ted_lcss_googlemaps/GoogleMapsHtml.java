
package ted_lcss_googlemaps;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.Properties;


public class GoogleMapsHtml
{

    /**
     * http://stackoverflow.com/questions/2885173/java-how-to-create-and-write-to-a-file
     */
    
    public void HtmlCreate()
    {
        Properties property_file = new Properties();
        OutputStream out = null;
        
        try {
            /* Create Html file */
            File htmlFile = new File("maps.html");
            
            /* Save file path to property file */
            try{  
                /* Load property file from project folder */
                File f = new File("settings.properties");
                
                /* Set Value to path */
                property_file.setProperty("path_html_file", htmlFile.getAbsolutePath());
                
                /* Set property file value */
                out = new FileOutputStream( f );
                property_file.store(out, "Property File");
            } catch (IOException exception_IO) {  
                System.err.println("IO Exception occured while loading property file");  
            }
            
            FileOutputStream file_Output_Stream = new FileOutputStream(htmlFile);
            OutputStreamWriter output_Stream_Writer = new OutputStreamWriter(file_Output_Stream);  
            
            try ( Writer writer = new BufferedWriter(output_Stream_Writer)) {
                writer.write("<!DOCTYPE html>\n" +
                            "<html> \n" +
                            "<head> \n" +
                            "  <meta http-equiv=\"content-type\" content=\"text/html; charset=UTF-8\" /> \n" +
                            "  <title>Google Maps</title> \n" +
                            "  <style>\n" +
                            "      html, body, #map {\n" +
                            "        height: 100%;\n" +
                            "        margin: 0px;\n" +
                            "        padding: 0px\n" +
                            "      }\n" +
                            "    </style>\n" +
                            "  <script src=\"http://maps.google.com/maps/api/js?sensor=false\"></script>\n" +
                            "  <script src=\"http://ajax.aspnetcdn.com/ajax/jQuery/jquery-1.10.1.min.js\"></script>\n" +
                            "</head> \n" +
                            "<body>\n" +
                            "  <div id=\"map\"></div>\n" +
                            "\n" +
                            "  <script type=\"text/javascript\">\n" +
                            "  //http://chrisltd.com/blog/2013/08/google-map-random-color-pins/\n" +
                            "  \n" +
                            "    // Define your locations: HTML content for the info window, latitude, longitude\n" +
                            "    var locations_1 = [");
            }
        } catch (IOException exception_IO) {
            System.err.println("Problem writing to the file maps.html at Creation");
        }
        
    }
    
    
    public void HtmlAddMarker(String geo_x, String geo_y)
    {
        /* Open maps.html to append */
        try {
            try (PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("maps.html", true)))) {
                out.println("new google.maps.LatLng("+ geo_x +","+ geo_y +"),\n");
            }
        } catch (IOException exception_IO) {
            System.err.println("IO Exception occured while loading property file");
        }
    }
    
    public void HtmlCloseFiles()
    {
        /* Open maps.html to append */
        try {
            try (PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("maps.html", true)))) {
                out.println("];\n" +
                            "	\n" +
                            "	var locations_2 = [");
            }
        } catch (IOException exception_IO) {
            System.err.println("IO Exception occured while loading property file");
        }
    }
    
    public void HtmlFinallizer()
    {
        /* Open maps.html to append */
        try {
            try (PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("maps.html", true)))) {
                out.println("];\n" +
                            "\n" +
                            "	var flightPath_1 = new google.maps.Polyline({\n" +
                            "		path: locations_1,\n" +
                            "		geodesic: true,\n" +
                            "		strokeColor: '#00FF00',\n" +
                            "		strokeOpacity: 1.0,\n" +
                            "		strokeWeight: 2\n" +
                            "	  });\n" +
                            "	  \n" +
                            "	var flightPath_2 = new google.maps.Polyline({\n" +
                            "		path: locations_2,\n" +
                            "		geodesic: true,\n" +
                            "		strokeColor: '#FF0000',\n" +
                            "		strokeOpacity: 1.0,\n" +
                            "		strokeWeight: 2\n" +
                            "	  });\n" +
                            "	  \n" +
                            "	  \n" +
                            "    var map = new google.maps.Map(document.getElementById('map'), {\n" +
                            "		zoom: 6,\n" +
                            "		center: new google.maps.LatLng(39.93,116.39),\n" +
                            "		mapTypeId: google.maps.MapTypeId.ROADMAP,\n" +
                            "		mapTypeControl: true,\n" +
                            "		streetViewControl: true,\n" +
                            "		panControl: true,\n" +
                            "    });\n" +
                            "	\n" +
                            "	flightPath_1.setMap(map);\n" +
                            "	flightPath_2.setMap(map);\n" +
                            "\n" +
                            "  </script> \n" +
                            "</body>\n" +
                            "</html>");
            }
        } catch (IOException exception_IO) {
            System.err.println("IO Exception occured while loading property file");
        }
    }

}
