package net.kosen10s.example;

import android.support.annotation.NonNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import static org.assertj.core.api.Fail.fail;

public class TestUtil {

    public String readJsonFromResources(@NonNull String fileName) {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(fileName);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder stringBuilder = new StringBuilder();
        try {
            String buffer;
            while ((buffer = bufferedReader.readLine()) != null) {
                stringBuilder.append(buffer);
            }
        } catch (IOException e) {
            fail(e.getMessage(), e);
        }
        return stringBuilder.toString();
    }
}
