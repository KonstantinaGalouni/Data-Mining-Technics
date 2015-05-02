package ted_lcss;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import static java.lang.Math.abs;
import java.util.ArrayList;
import ted_stopwatch.stopwatch;

/**
 *
 * @author Spiros Delviniotis , Konstantina Galouni
 */

public class LCSS {
    public static int line_counter_1;
    public static int line_counter_2;
    public static stopwatch clock = new stopwatch();
    
    public static ArrayList<Double> lat = new ArrayList<Double>();
    public static ArrayList<Double> lon = new ArrayList<Double>();
    
    private final String read_file_1;
    private final String read_file_2;
    private final double e;

    public LCSS(String file_1, String file_2, double input_e) {
        read_file_1 = file_1;
        read_file_2 = file_2;
        e = input_e;
    }
    
    
    
    public int lcss() throws FileNotFoundException, IOException {
        /* Open files */
        String output_file_1 = "coordinates_1.txt";
        String output_file_2 = "coordinates_2.txt";
        String output_file_3 = "lcs.txt";
        
        /* Create output file */
        File DataFile_1 = new File(output_file_1);
        File DataFile_2 = new File(output_file_2);
        File DataFile_3 = new File(output_file_3);
        
        /* find counter lines */
        line_counter_1 = 0;
        line_counter_2 = 0;
        
        try (BufferedReader reader = new BufferedReader(new FileReader(read_file_1)); 
                BufferedWriter writer = new BufferedWriter(new FileWriter(output_file_1))) {
            String line = null;
            
            String temp_line[] = null;
            
            while ((line = reader.readLine()) != null) {
                temp_line = line.split(",");
                
                /* Write lat,lon in output file */
                writer.write(temp_line[2] + "," + temp_line[3]);
                writer.write("\n");
                
                line_counter_1++;
            }
        }
        
        
        try (BufferedReader reader = new BufferedReader(new FileReader(read_file_2)); 
                BufferedWriter writer = new BufferedWriter(new FileWriter(output_file_2))) {
            String line = null;
            
            String temp_line[] = null;
            
            while ((line = reader.readLine()) != null) {
                temp_line = line.split(",");
                
                /* Write lat,lon in output file */
                writer.write(temp_line[2] + "," + temp_line[3]);
                writer.write("\n");
                
                line_counter_2++;
            }
        }
        
        //System.out.println("    lines of file 1: "+line_counter_1);
        //System.out.println("    lines of file 2: "+line_counter_2);
        
        /* make tables of lat,lon in Double */
        double table_lat_1[] = new double[line_counter_1];
        double table_lon_1[] = new double[line_counter_1];
        
        try (BufferedReader reader = new BufferedReader(new FileReader(output_file_1))) 
        {
            String line = null;
            int i = 0;
            String temp_line[] = null;
            
            while ((line = reader.readLine()) != null) {
                temp_line = line.split(",");
                
                table_lat_1[i] = Double.parseDouble(temp_line[0]);
                table_lon_1[i] = Double.parseDouble(temp_line[1]);
                
                i++;
            }
        }
        
        double table_lat_2[] = new double[line_counter_2];
        double table_lon_2[] = new double[line_counter_2];
        
        try (BufferedReader reader = new BufferedReader(new FileReader(output_file_2))) 
        {
            String line = null;
            int i = 0;
            String temp_line[] = null;
            
            while ((line = reader.readLine()) != null) {
                temp_line = line.split(",");
                
                table_lat_2[i] = Double.parseDouble(temp_line[0]);
                table_lon_2[i] = Double.parseDouble(temp_line[1]);
                
                i++;
            }
        }
        
        int table_lcss[][] = new int[line_counter_1+1][line_counter_2+1];
        
        /* Start Clock */
        clock.start();
        
        /* Lcss algorithm */
        for(int i=0; i<(line_counter_1 +1); i++)
        {
            for(int j=0; j<(line_counter_2 +1); j++)
            {
                table_lcss[i][j] = 0;
            }
        }
        
        for(int i=1; i<(line_counter_1 +1); i++)
        {
            
            for(int j=1; j<(line_counter_2 +1); j++)
            {
                if (((abs(table_lat_1[i-1] - table_lat_2[j-1])) <= e) && ((abs(table_lon_1[i-1] - table_lon_2[j-1])) <= e))
                {
                    table_lcss[i][j] = 1 + table_lcss[i-1][j-1];
                }
                else
                {
                    if (table_lcss[i][j-1] > table_lcss[i-1][j])
                        table_lcss[i][j] = table_lcss[i][j-1];
                    else
                        table_lcss[i][j] = table_lcss[i-1][j];
                }
            }
        }
        
        /* Stop Clock */
        clock.stop();
        
        /* Find longest Common Subsequence */
        int i = line_counter_1;
        int j = line_counter_2;
        while(i>0 && j>0)
        {
            if (table_lcss[i][j] == table_lcss[i-1][j])
            {
                i--;
            }
            else if (table_lcss[i][j] == table_lcss[i][j-1])
            {
                j--;
            }
            else if ((table_lcss[i][j] > table_lcss[i][j-1]) && (table_lcss[i][j] > table_lcss[i-1][j]))
            {
                lat.add(table_lat_1[i-1]);
                lon.add(table_lon_1[j-1]);
                    
                i = i-1;
                j = j-1;
            }
        }
        
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(output_file_3))) 
        {
            /* Write lat,lon in output file */
            for (i=lat.size()-1; i>=0; i--)
            {
                writer.write(lat.get(i)+" , "+lon.get(i));
                writer.write("\n");
            }
        }
        
        //System.out.println(" LCSS DETALES: Clock : "+clock.toString());
        //System.out.println(" LCSS DETALES: lat.size(): "+lat.size());
        //System.out.println(" LCSS DETALES: lon.size(): "+lon.size());
        //System.out.println(" LCSS DETALES: e: "+e);
        //System.out.println(" LCSS DETALES: line_counter_1: "+line_counter_1);
        //System.out.println(" LCSS DETALES: line_counter_2: "+line_counter_2);
        
        return table_lcss[line_counter_1][line_counter_2];
    }
    
    
}
