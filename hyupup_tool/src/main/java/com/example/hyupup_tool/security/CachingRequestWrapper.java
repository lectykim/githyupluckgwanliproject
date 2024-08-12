package com.example.hyupup_tool.security;

import jakarta.servlet.ReadListener;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.util.StreamUtils;
import org.springframework.util.StringUtils;


import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class CachingRequestWrapper extends HttpServletRequestWrapper {
    private byte[] rawData;
    public CachingRequestWrapper(HttpServletRequest request) throws IOException {
        super(request);
        try(InputStream inputStream = request.getInputStream()){
            this.rawData = StreamUtils.copyToByteArray(inputStream);
        }



    }
    @Override
    public ServletInputStream getInputStream(){
        return new CachedBodyServletInputStream(this.rawData);
    }
    @Override
    public BufferedReader getReader(){
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(this.rawData);
        return new BufferedReader(new InputStreamReader(byteArrayInputStream));
    }

    private static class CachedBodyServletInputStream extends ServletInputStream{
        private final InputStream cachedBodyInputStream;

        public CachedBodyServletInputStream(byte[] cachedBody){
            this.cachedBodyInputStream = new ByteArrayInputStream(cachedBody);
        }

        @Override
        public boolean isFinished() {
            try {
                return cachedBodyInputStream.available() == 0;
            } catch (IOException e) {
                throw new RuntimeException("[CachedBodyServletInputStream] cachedBodyInputStream available fail", e);
            }
        }

        @Override
        public boolean isReady() {
            return true;
        }

        @Override
        public void setReadListener(ReadListener readListener) {
            throw new UnsupportedOperationException();
        }

        @Override
        public int read() throws IOException {
            return cachedBodyInputStream.read();
        }
    }

}
