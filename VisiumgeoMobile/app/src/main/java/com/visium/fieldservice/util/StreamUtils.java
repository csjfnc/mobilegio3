package com.visium.fieldservice.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @author Andrew Willard (andrewillard@gmail.com)
 */
public class StreamUtils {

    private StreamUtils(){}

    public static String read(InputStream inputStream) {

        StringBuilder sb = new StringBuilder();

        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        String line;

        try {

            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

        } catch (IOException e) {
            LogUtils.error(StreamUtils.class, e);
        }

        return sb.toString();

    }
}
