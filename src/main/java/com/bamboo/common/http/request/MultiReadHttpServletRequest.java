package com.bamboo.common.http.request;



import com.bamboo.common.util.IoTools;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.*;

public class MultiReadHttpServletRequest  extends HttpServletRequestWrapper{
    private ByteArrayOutputStream cachedBytes;

    public MultiReadHttpServletRequest(HttpServletRequest request) {
        super(request);
    }

    public ServletInputStream getInputStream() throws IOException {
        if (this.cachedBytes == null) {
            this.cacheInputStream();
        }

        return new MultiReadHttpServletRequest.CachedServletInputStream();
    }

    public BufferedReader getReader() throws IOException {
        return new BufferedReader(new InputStreamReader(this.getInputStream()));
    }

    private void cacheInputStream() throws IOException {
        this.cachedBytes = new ByteArrayOutputStream();
        IoTools.copy(super.getInputStream(), this.cachedBytes);
    }

    public class CachedServletInputStream extends ServletInputStream {
        private ByteArrayInputStream input;

        public CachedServletInputStream() {
            this.input = new ByteArrayInputStream(MultiReadHttpServletRequest.this.cachedBytes.toByteArray());
        }

        public int read() throws IOException {
            return this.input.read();
        }

        public boolean isFinished() {
            return this.input.available() == 0;
        }

        public boolean isReady() {
            return true;
        }

        public void setReadListener(ReadListener listener) {
            throw new RuntimeException("Not implemented");
        }
    }
}