package nev.com.dictionary.LongmanDictionary.Remote;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.SingleClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;

import java.io.InputStream;

/**
 * Created by Yeoh on 11/28/2016.
 */

public class HTTPSCall {
    private static final String HTTPS_SCHEME_NAME = "https";
    private String urlValue;

    public HTTPSCall (String urlValue){this.urlValue=urlValue;}

    public String doRemoteCall()throws Exception{
        HttpClient httpsClient = makeHTTPSClient();
        HttpGet httpsGet = new HttpGet(urlValue);
        HttpResponse response;
        String result = null;
        response = httpsClient.execute(httpsGet);
        HttpEntity entity = response.getEntity();
        if(entity !=null){
            InputStream inputStream = entity.getContent();
            result = InputStreamConverter.convertToString(inputStream);
            inputStream.close();
        }
        return result;

    }
    private HttpClient makeHTTPSClient(){
        SchemeRegistry schemeRegistry = new SchemeRegistry();
        schemeRegistry.register(new Scheme(HTTPS_SCHEME_NAME, org.apache.http.conn.ssl.SSLSocketFactory.getSocketFactory(), 443));
        HttpParams params = new BasicHttpParams();
        SingleClientConnManager mgr = new SingleClientConnManager(params,schemeRegistry);
        return new DefaultHttpClient(mgr,params);
    }
}
