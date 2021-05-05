package com.ntu.graphs.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class shellUtil {
    public static void shellTestOnWin() {
        try {
            Process process = Runtime.getRuntime().exec(
                    "cmd.exe /c whoami");
            StringBuilder output = new StringBuilder();
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(process.getInputStream()));

            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
