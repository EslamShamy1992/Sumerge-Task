package Config;


import java.io.*;
import java.util.Properties;

public class PropertyUtils {


    public static Properties loadproperties(String filepath){


        File file = new File(filepath);

        try {

            InputStream inputstream = new FileInputStream(file);

            Properties properties = new Properties();

            try {

                properties.load(inputstream);


                inputstream.close();

                return properties;


            } catch (IOException e) {

                throw new RuntimeException("error while loading properties");

            }


        } catch (IOException e) {

            throw new RuntimeException("file is not found");

        }

    }

}
