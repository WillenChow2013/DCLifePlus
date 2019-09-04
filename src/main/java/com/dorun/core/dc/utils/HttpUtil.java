package com.dorun.core.dc.utils;

import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.BasicHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

/**
 * http请求工具
 */
public class HttpUtil {

    public static final int CONNECT_TIMROUTMS = 6 * 1000;

    public static final int READ_TIMROUTMS = 8 * 1000;

    /**
     * post请求
     *
     * @param urlSuffix
     * @param data
     * @param connectTimeoutMs
     * @param readTimeoutMs
     * @return
     * @throws ClientProtocolException
     * @throws IOException
     */
    public static String post(String urlSuffix, String data, int connectTimeoutMs, int readTimeoutMs) throws ClientProtocolException, IOException {

        BasicHttpClientConnectionManager connManager;

        connManager = new BasicHttpClientConnectionManager(
                RegistryBuilder.<ConnectionSocketFactory>create()
                        .register("http", PlainConnectionSocketFactory.getSocketFactory())
                        .register("https", SSLConnectionSocketFactory.getSocketFactory())
                        .build(),
                null,
                null,
                null
        );
        HttpClient httpClient = HttpClientBuilder.create().setConnectionManager(connManager).build();

        HttpPost httpPost = new HttpPost(urlSuffix);

        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(readTimeoutMs).setConnectTimeout(connectTimeoutMs).build();

        httpPost.setConfig(requestConfig);

        StringEntity postEntity = new StringEntity(data, "UTF-8");
        postEntity.setContentEncoding("UTF-8");
        httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
        httpPost.addHeader("User-Agent", "wxpay sdk java v1.0 " + "1494064642");
        httpPost.setEntity(postEntity);
        HttpResponse httpResponse = httpClient.execute(httpPost);
        HttpEntity httpEntity = httpResponse.getEntity();
        return EntityUtils.toString(httpEntity, "UTF-8");
    }

    /**
     * get请求
     *
     * @param url
     * @param charset
     * @param readTimeoutMs
     * @param connectTimeoutMs
     * @return
     * @throws URISyntaxException
     * @throws ClientProtocolException
     * @throws IOException
     */
    public static String get(String url, String charset, int readTimeoutMs, int connectTimeoutMs) throws URISyntaxException, ClientProtocolException, IOException {
        String result = null;
        HttpGet httpGet = new HttpGet();

        httpGet.setURI(new URI(url));

        CloseableHttpClient client = HttpClients.createDefault();

        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(readTimeoutMs).setConnectionRequestTimeout(connectTimeoutMs).build();

        httpGet.setConfig(requestConfig);

        CloseableHttpResponse response = client.execute(httpGet);

        if (response.getStatusLine().getStatusCode() == org.apache.http.HttpStatus.SC_OK) {
            HttpEntity entity = response.getEntity();

            result = EntityUtils.toString(entity, charset);
        } else {

        }

        return result;
    }

    public static String post(String methodName, Map<String, String> params) {

        String result = null;
        org.apache.commons.httpclient.HttpClient httpClient = new org.apache.commons.httpclient.HttpClient();

        PostMethod postMethod = new PostMethod(methodName);

        for (Map.Entry<String, String> entry : params.entrySet())
            postMethod.addParameter(entry.getKey(), entry.getValue());
        try {
            int code = httpClient.executeMethod(postMethod);
            if (code != 200)
                throw new RuntimeException("请求失败");//暂时这么处理
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            result = postMethod.getResponseBodyAsString();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    public static String post(String urlSuffix, String data) throws ClientProtocolException, IOException {
        return post(urlSuffix, data, CONNECT_TIMROUTMS, READ_TIMROUTMS);
    }

    public static Map<String, String> xmlToMap(String strXML) {
        Map<String, String> data = new HashMap<String, String>();
        try {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            String FEATURE = null;
            try {
                FEATURE = "http://apache.org/xml/features/disallow-doctype-decl";
                documentBuilderFactory.setFeature(FEATURE, true);
                FEATURE = "http://xml.org/sax/features/external-general-entities";
                documentBuilderFactory.setFeature(FEATURE, false);
                FEATURE = "http://xml.org/sax/features/external-parameter-entities";
                documentBuilderFactory.setFeature(FEATURE, false);
                FEATURE = "http://apache.org/xml/features/nonvalidating/load-external-dtd";
                documentBuilderFactory.setFeature(FEATURE, false);
                documentBuilderFactory.setXIncludeAware(false);
                documentBuilderFactory.setExpandEntityReferences(false);
            } catch (ParserConfigurationException e) {
                System.out.println("ParserConfigurationException was thrown. The feature '" +
                        FEATURE + "' is probably not supported by your XML processor.");
            } catch (Exception se) {
                // TODO: handle exception
            }
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            InputStream stream = new ByteArrayInputStream(strXML.getBytes(Charset.forName("UTF-8")));
            Document doc = documentBuilder.parse(stream);
            NodeList nodeList = doc.getDocumentElement().getChildNodes();

            for (int idx = 0; idx < nodeList.getLength(); ++idx) {
                Node node = nodeList.item(idx);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    data.put(element.getNodeName(), element.getTextContent());
                } else {
                    data.put("result", node.getTextContent());
                }
            }

            try {
                stream.close();
            } catch (Exception e) {
                // TODO: handle exception
            }

        } catch (Exception e) {

            e.printStackTrace();
        }
        return data;
    }

}
