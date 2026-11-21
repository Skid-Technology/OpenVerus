package me.levansj01.verus.util.hastebin;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.function.Function;

public class HTTPRequest {
    private final URL url;
    private HttpURLConnection httpURLConnection;

    public HTTPRequest outputStream(HTTPRequest.HTTPRequestConsumer<OutputStream> httpRequestConsumer) throws IOException {
        httpRequestConsumer.accept(this.httpURLConnection.getOutputStream());
        return this;
    }

    public HTTPRequest inputStream(HTTPRequest.HTTPRequestConsumer<InputStream> httpRequestConsumer) throws IOException {
        httpRequestConsumer.accept(this.httpURLConnection.getInputStream());
        return this;
    }

    public HTTPRequest setRequestProperty(String str, String str2) {
        this.httpURLConnection.setRequestProperty(str, str2);
        return this;
    }

    public HTTPRequest doOutput(boolean doOutput) {
        this.httpURLConnection.setDoOutput(doOutput);
        return this;
    }

    public HTTPRequest useCaches(boolean useCaches) {
        this.httpURLConnection.setUseCaches(useCaches);
        return this;
    }

    public HTTPRequest requestMethod(String requestMethod) throws ProtocolException {
        this.httpURLConnection.setRequestMethod(requestMethod);
        return this;
    }

    public HTTPRequest setReadTimeout(int readTimeout) {
        this.httpURLConnection.setReadTimeout(readTimeout);
        return this;
    }

    public HttpURLConnection getHttpURLConnection() {
        return this.httpURLConnection;
    }

    public HTTPRequest setConnectTimeout(int connectTimeout) {
        this.httpURLConnection.setConnectTimeout(connectTimeout);
        return this;
    }

    public <T> T disconnectAndReturn(HTTPRequest.HTTPRequestFunction<T> httpRequestFunction) throws IOException {
        return (T)httpRequestFunction.apply(this);
    }

    public HTTPRequest errorStream(Function<InputStream, ?> function) throws IOException {
        function.apply(this.httpURLConnection.getErrorStream());
        return this;
    }

    public HTTPRequest(String str) throws MalformedURLException {
        this.url = new URL(str);
    }

    public HTTPRequest open() throws IOException {
        this.httpURLConnection = (HttpURLConnection)this.url.openConnection();
        return this;
    }

    public HTTPRequest disconnect() {
        this.httpURLConnection.disconnect();
        return this;
    }

    public HTTPRequest doInput(boolean doInput) {
        this.httpURLConnection.setDoInput(doInput);
        return this;
    }

    @FunctionalInterface
    public interface HTTPRequestConsumer<T> {
        void accept(T value) throws IOException;
    }

    @FunctionalInterface
    public interface HTTPRequestFunction<T> {
        T apply(HTTPRequest httpRequest) throws IOException;
    }
}
