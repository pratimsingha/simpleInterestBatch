package com.batch.simpleInterestBatch.utils;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;

public class FileUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(FileUtils.class);
    public static String getFilePath(Environment environment){
        String filePath = environment.getProperty("file.path");
        if (filePath.isEmpty()){
            LOGGER.error("The file path value should be added along the key file.path");
        }else{
//            if ((filePath.indexOf('/') >= 0) && (filePath.charAt(filePath.length()-1) != '/')){
//                filePath = filePath + '/';
                LOGGER.info("The file path: {}",filePath);
        }

        return filePath;

    }

    public static String getUri(Environment environment) {
        String uri = environment.getProperty("rest.url");
        return uri;
    }
}
