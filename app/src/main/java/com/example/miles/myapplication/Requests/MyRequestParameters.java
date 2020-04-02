package com.example.miles.myapplication.Requests;

import com.alibaba.fastjson.JSON;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 用途：
 * 作者：Created by Miles Wang on 2018/12/26
 * 邮箱：icy-star@qq.com
 **/
public class MyRequestParameters {

    protected ConcurrentHashMap<String, String> urlParams;
    protected ConcurrentHashMap<String, StreamWrapper> streamParams;
    protected ConcurrentHashMap<String, FileWrapper> fileParams;
    protected ConcurrentHashMap<String, Object> urlParamsWithObjects;

    /**
     * Constructs a new empty {@code RequestParams} instance.
     */
    public MyRequestParameters() {
        this((Map<String, String>) null);
    }

    /**
     * Constructs a new RequestParams instance containing the key/value string params from the
     * specified map.
     *
     * @param source the source key/value string map to add.
     */
    public MyRequestParameters(Map<String, String> source) {
        init();
        if (source != null) {
            for (Map.Entry<String, String> entry : source.entrySet()) {
                put(entry.getKey(), entry.getValue());
            }
        }
    }

    /**
     * Constructs a new RequestParams instance and populate it with a single initial key/value
     * string param.
     *
     * @param key   the key name for the intial param.
     * @param value the value string for the initial param.
     */
    @SuppressWarnings("serial")
    public MyRequestParameters(final String key, final String value) {
        this(new HashMap<String, String>() {{
            put(key, value);
        }});
    }

    /**
     * Constructs a new RequestParams instance and populate it with multiple initial key/value
     * string param.
     *
     * @param keysAndValues a sequence of keys and values. Objects are automatically converted to
     *                      Strings (including the value {@code null}).
     * @throws IllegalArgumentException if the number of arguments isn't even.
     */
    public MyRequestParameters(Object... keysAndValues) {
        init();
        int len = keysAndValues.length;
        if (len % 2 != 0)
            throw new IllegalArgumentException("Supplied arguments must be even");
        for (int i = 0; i < len; i += 2) {
            String key = String.valueOf(keysAndValues[i]);
            String val = String.valueOf(keysAndValues[i + 1]);
            put(key, val);
        }
    }

    /**
     * Adds a key/value string pair to the request.
     *
     * @param key   the key name for the new param.
     * @param value the value string for the new param.
     */
    public void put(String key, String value) {
        if (key != null && value != null) {
            urlParams.put(key, value);
        }
    }

    /**
     * Adds a file to the request.
     *
     * @param key  the key name for the new param.
     * @param file the file to add.
     * @throws FileNotFoundException throws if wrong File argument was passed
     */
    public void put(String key, File file) throws FileNotFoundException {
        put(key, file, null);
    }

    /**
     * Adds a file to the request.
     *
     * @param key         the key name for the new param.
     * @param file        the file to add.
     * @param contentType the content type of the file, eg. application/json
     * @throws FileNotFoundException throws if wrong File argument was passed
     */
    public void put(String key, File file, String contentType) throws FileNotFoundException {
        if (key != null && file != null) {
            fileParams.put(key, new FileWrapper(file, contentType));
        }
    }

    /**
     * Adds an input stream to the request.
     *
     * @param key    the key name for the new param.
     * @param stream the input stream to add.
     */
    public void put(String key, InputStream stream) {
        put(key, stream, null);
    }

    /**
     * Adds an input stream to the request.
     *
     * @param key    the key name for the new param.
     * @param stream the input stream to add.
     * @param name   the name of the stream.
     */
    public void put(String key, InputStream stream, String name) {
        put(key, stream, name, null);
    }

    /**
     * Adds an input stream to the request.
     *
     * @param key         the key name for the new param.
     * @param stream      the input stream to add.
     * @param name        the name of the stream.
     * @param contentType the content type of the file, eg. application/json
     */
    public void put(String key, InputStream stream, String name, String contentType) {
        if (key != null && stream != null) {
            streamParams.put(key, new StreamWrapper(stream, name, contentType));
        }
    }

    /**
     * Adds param with non-string value (e.g. Map, List, Set).
     *
     * @param key   the key name for the new param.
     * @param value the non-string value object for the new param.
     */
    public void put(String key, Object value) {
        if (key != null && value != null) {
            urlParamsWithObjects.put(key, value);
        }
    }


    //Map转json  toJSONString
    public String MaptoJSONStringtest(){
        Map<String, Object> map = new HashMap<String, Object>();
        for (ConcurrentHashMap.Entry<String, String> entry : urlParams.entrySet()) {
            map.put(entry.getKey(), entry.getValue());
        }
//        com.alibaba.fastjson.JSONObject jsonObject = JSON.parseObject(result);
        String json = JSON.toJSONString(map);

        return json;
    }

    private static class StreamWrapper {
        public InputStream inputStream;
        public String name;
        public String contentType;

        public StreamWrapper(InputStream inputStream, String name, String contentType) {
            this.inputStream = inputStream;
            this.name = name;
            this.contentType = contentType;
        }
    }

    private static class FileWrapper {
        public File file;
        public String contentType;

        public FileWrapper(File file, String contentType) {
            this.file = file;
            this.contentType = contentType;
        }
    }

    private void init() {
        urlParams = new ConcurrentHashMap<String, String>();
        streamParams = new ConcurrentHashMap<String, StreamWrapper>();
        fileParams = new ConcurrentHashMap<String, FileWrapper>();
        urlParamsWithObjects = new ConcurrentHashMap<String, Object>();
    }

    /**
     * 获取参数字符串
     * @param isSimple 是否需要最原始的
     * @return
     */
    public String getParamString(boolean isSimple) {
        StringBuilder sb = new StringBuilder();
        if(isSimple){
            //sort
            List<String> keyList = new ArrayList<String>();
            for(ConcurrentHashMap.Entry<String, String> entry : urlParams.entrySet()) {
                keyList.add(entry.getKey());
            }
            Collections.sort(keyList);

            //order a-z sort add
            for(String key : keyList){
                for(ConcurrentHashMap.Entry<String, String> entry : urlParams.entrySet()) {
                    if(key.equals(entry.getKey())){
                        sb.append(entry.getKey()).append(entry.getValue());
                    }
                }
            }
        }
        return sb.toString();
    }
}
