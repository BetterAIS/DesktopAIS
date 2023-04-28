package com.bais.dais.baisclient;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.MediaType;
import okhttp3.Response;
import okhttp3.Request;

import com.bais.dais.baisclient.models.*;

public class BAISClient {
    private static Logger logger = LoggerFactory.getLogger(BAISClient.class);
    private static BAISClient _instance = null;
    private static final String BASE_URL = "http://0.0.0.0:8000/api/";
    private final OkHttpClient httpClient;
    private final ObjectMapper objectMapper;
    private BAISSession session = BAISSession.getInstance();

    private BAISClient() {
        httpClient = new OkHttpClient();
        objectMapper = new ObjectMapper();
        session.initDB();

        logger.debug("BAISClient initialized. Base URL: " + BASE_URL);
    }

    public static BAISClient getInstance() {
        if (_instance == null) {
            _instance = new BAISClient();
        }
        return _instance;
    }

    public void setTheme(String theme) {
        session.setTheme(theme);
    }

    public String getTheme(String or_theme) {
        if (!isSessionValid()) {
            return or_theme;
        }
        return session.getLastSession().getTheme();
    }

    public void setLang(String lang) {
        session.setLang(lang);
    }

    public String getLang(String or_lang) {
        if (!isSessionValid()) {
            return or_lang;
        }
        return session.getLastSession().getLang();
    }

    public String get(String url) throws IOException {
        Request request = new Request.Builder()
                .url(BASE_URL + url)
                .build();
        Response response = httpClient.newCall(request).execute();
        return response.body().string();
    }

    public String get(String url, Map<String, String> params) throws IOException {
        StringBuilder sb = new StringBuilder();
        sb.append(BASE_URL + url);
        sb.append("?");
        for (Map.Entry<String, String> entry : params.entrySet()) {
            sb.append(entry.getKey());
            sb.append("=");
            sb.append(entry.getValue());
            sb.append("&");
        }
        sb.deleteCharAt(sb.length() - 1);
        Request request = new Request.Builder()
                .url(sb.toString())
                .build();
        Response response = httpClient.newCall(request).execute();
        return response.body().string();
    }

    public String post(String url, String json) throws IOException {
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json);
        Request request = new Request.Builder()
                .url(BASE_URL + url)
                .post(body)
                .build();
        Response response = httpClient.newCall(request).execute();
        return response.body().string();
    }

    public boolean isLoggedIn() {
        return session.sessionExist() && isSessionValid();
    }

    public boolean isSessionValid() {
        try {
            if (!session.sessionExist()) {
                return false;
            }
            String access = session.getLastSession().getAccessToken();
            Map<String, String> params = Map.of("token", access);
            String response = get("auth/verify", params);
            return response.equals("true");
        } catch (IOException e) {
            logger.error("Error while verifying session: " + e.getMessage());
            return false;
        }
    }

    public void login(String username, String password) throws IOException {
        String json = objectMapper.writeValueAsString(new LoginRequest(username, password));
        String response = post("auth/login", json);
        System.out.println(response);
        Tokens tokens = objectMapper.readValue(response, Tokens.class);
        session.addSession(tokens.getAccess(), tokens.getRefresh(), 30*60);
    }

    public Accommodation getAccommodation() throws IOException {
        String access = session.getLastSession().getAccessToken();
        Map<String, String> params = Map.of("token", access);
        String response = get("accommodation/get_all", params);
        return objectMapper.readValue(response, Accommodation.class);
    }

    public LinkedList<Mail> getMails() throws IOException {
        String access = session.getLastSession().getAccessToken();
        Map<String, String> params = Map.of("token", access);
        get("ais/mails/new", params);
        String response = get("ais/mails", params);
        response = response.replaceAll("\\\\n", " ");
        LinkedList<Mail> result = new LinkedList<>();
        for (Object o : objectMapper.readValue(response, LinkedList.class)) {
//            change is_read to isRead
            o = objectMapper.convertValue(o, Map.class);
            ((Map) o).put("read", ((Map) o).get("is_read"));
            ((Map) o).remove("is_read");
            result.add(objectMapper.convertValue(o, Mail.class));
        }
        return result;
    }

    public LinkedList<Homework> getHomework() throws IOException {
        String access = session.getLastSession().getAccessToken();
        Map<String, String> params = Map.of("token", access);
        String response = get("ais/homeworks", params);
        response = response.replaceAll("\\\\n", " ");
        LinkedList<Homework> result = new LinkedList<>();
        for (Object o : objectMapper.readValue(response, LinkedList.class)) {
            result.add(objectMapper.convertValue(o, Homework.class));
        }
        return result;
    }

    public LinkedList<Timetable> getTimetable() throws IOException {
        String access = session.getLastSession().getAccessToken();
        Map<String, String> params = Map.of("token", access);
        String response = get("ais/time-table", params);
        LinkedList<Timetable> result = new LinkedList<>();
        for (Object o : objectMapper.readValue(response, LinkedList.class)) {
            result.add(objectMapper.convertValue(o, Timetable.class));
        }
        return result;
    }



    public void logout() {
        session.deleteLastSession();
    }

    protected void finalize() throws Throwable {
        try {
            session.closeDB();
        } finally {
            super.finalize();
        }
    }

}
