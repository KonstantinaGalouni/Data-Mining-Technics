package ted_lcss_d;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import static java.lang.Math.abs;
import ted_stopwatch.stopwatch;

/**
 *
 * @author Spiros Delviniotis , Konstantina Galouni
 */

public class LCSS_d {
    int best_match = 0;
    public static int line_counter_1;
    public static int line_counter_2;
    public static stopwatch clock_d = new stopwatch();
    
    public int lcss(String read_file_1, String read_file_2, double e, int d) throws FileNotFoundException, IOException {
       line_counter_1 = 1000;
       line_counter_2 = line_counter_1 + d;
       
       boolean flag = false;
        
        /* make tables of lat,lon in Double */
        double table_lat_1[] = new double[line_counter_1];
        double table_lon_1[] = new double[line_counter_1];
        double table_lat_2[] = new double[line_counter_2];
        double table_lon_2[] = new double[line_counter_2];
        int table_lcss[][] = new int[line_counter_1+1][line_counter_2+1];
        int offset = 0;
        String output_file_1 = "coordinates_1.txt";
        String output_file_2 = "coordinates_2.txt";
        
        /* Create output file */
        File DataFile_1 = new File(output_file_1);
        File DataFile_2 = new File(output_file_2);
        
        /* initialize table_lcss */
        for(int i=0; i<(line_counter_1 +1); i++)
        {
            for(int j=0; j<(line_counter_2 +1); j++)
            {
                table_lcss[i][j] = 0;
            }
        }
        
        try (BufferedReader reader = new BufferedReader(new FileReader(read_file_1));
            BufferedWriter writer = new BufferedWriter(new FileWriter(output_file_1))) {
            
            String line = null;
            int i=0;
            String temp_line[] = null;
            
            while ((line = reader.readLine()) != null) {
                temp_line = line.split(",");
                
                table_lat_1[i] = Double.parseDouble(temp_line[2]);
                table_lon_1[i] = Double.parseDouble(temp_line[3]);
                
                /* Write lat,lon in output file */
                writer.write(temp_line[2] + "," + temp_line[3]);
                writer.write("\n");
                
                i++;
            }
        }
        
        
        try (BufferedReader reader = new BufferedReader(new FileReader(read_file_2));
                BufferedWriter writer = new BufferedWriter(new FileWriter(output_file_2))) {
            String line = null;
            int i = 0;
            String temp_line[] = null;
            
            while ((line = reader.readLine()) != null)
            {
                temp_line = line.split(",");
                
                table_lat_2[i%line_counter_2] = Double.parseDouble(temp_line[2]);
                table_lon_2[i%line_counter_2] = Double.parseDouble(temp_line[3]);
                
                /* Write lat,lon in output file */
                writer.write(temp_line[2] + "," + temp_line[3]);
                writer.write("\n");
                
                if (i == line_counter_2-1)
                {
                    flag = true;
                    
                    /* Start Clock */
                    clock_d.start();
                }
                
                if (flag == true)
                {
                    /* Run LCSS */
                    for(int l=1; l<(line_counter_1 +1); l++)
                    {
                        
                        for(int j=1; j<(line_counter_2 +1); j++)
                        {
                            if (((abs(table_lat_1[l-1] - table_lat_2[((j-1+offset)%(line_counter_2))])) <= e) && ((abs(table_lon_1[l-1] - table_lon_2[((j-1+offset)%(line_counter_2))])) <= e))
                            {
                                table_lcss[l][j] = 1 + table_lcss[l-1][j-1];
                            }
                            else
                            {
                                if (table_lcss[l][j-1] > table_lcss[l-1][j])
                                    table_lcss[l][j] = table_lcss[l][j-1];
                                else
                                    table_lcss[l][j] = table_lcss[l-1][j];
                            }
                        }
                    }
                    offset++;
                }
                i++;
                if (best_match < table_lcss[line_counter_1][line_counter_2])
                {
                    best_match = table_lcss[line_counter_1][line_counter_2];
                }
            }
        }
        
        /* Stop Clock */
        clock_d.stop();
        
        return best_match;
    }
    
    
}
