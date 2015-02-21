package com.wendiesel.myapplication.data;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class JsonDataReader {
    private static String readResource(Context ctx, int res) {
        InputStream inputStream = ctx.getResources().openRawResource(res);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        int ctr;
        try {
            ctr = inputStream.read();
            while (ctr != -1) {
                byteArrayOutputStream.write(ctr);
                ctr = inputStream.read();
            }
            inputStream.close();
        } catch (IOException e) {
            return null;
        }
        return byteArrayOutputStream.toString();
    }

    public static JSONObject readObject(Context ctx, int res) {
        try {
            return new JSONObject(readResource(ctx, res));
        } catch (JSONException e) {
            return null;
        }
    }

    public static JSONArray readArray(Context ctx, int res) {
        try {
            return new JSONArray(readResource(ctx, res));
        } catch (JSONException e) {
            return null;
        }
    }
}
