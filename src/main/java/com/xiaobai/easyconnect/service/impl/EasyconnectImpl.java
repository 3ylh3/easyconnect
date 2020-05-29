package com.xiaobai.easyconnect.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.xiaobai.easyconnect.common.Entity;
import com.xiaobai.easyconnect.common.MsgCdEnum;
import com.xiaobai.easyconnect.service.Easyconnect;
import com.xiaobai.easyconnect.utils.JsonUtils;
import com.xiaobai.easyconnect.utils.XmlUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.File;
import java.io.IOException;

/**
 * 发送逻辑处理
 *
 * @author yin_zhj
 * @date 2020/5/28
 */
public class EasyconnectImpl implements Easyconnect {
    private static final String JSON_SUFFIX = ".json";
    private static final String XML_SUFFIX = ".xml";
    private static final String DEFAULT_ENCODING = "UTF-8";
    private static final String JSON_TYPE = "application/json";
    private static final String XML_TYPE = "application/xml";

    private String url;
    private String encoding;

    public EasyconnectImpl(String url, String encoding) {
        this.url = url;
        this.encoding = encoding;
    }

    @Override
    public Entity postJson(String code, Entity message) {
        Entity rspEntity = new Entity();
        String fileName = code + JSON_SUFFIX;
        Resource resource = new ClassPathResource(fileName);
        try {
            File file = resource.getFile();
            String jsonStr = FileUtils.readFileToString(file);
            JSONObject json = JSONObject.parseObject(jsonStr);
            JSONObject req = json.getJSONObject("req");
            JSONObject rsp = json.getJSONObject("rsp");
            String msg = JsonUtils.parseReqJson(req, message);
            System.out.println(msg);
            CloseableHttpClient httpClient = HttpClients.createDefault();
            HttpPost httpPost = new HttpPost(url);
            if(StringUtils.isBlank(encoding)) {
                encoding = DEFAULT_ENCODING;
            }
            StringEntity entity = new StringEntity(msg, encoding);
            entity.setContentType(JSON_TYPE);
            httpPost.setEntity(entity);
            HttpResponse response = httpClient.execute(httpPost);
            if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                String result = EntityUtils.toString(response.getEntity());
                JsonUtils.parseRspStr(rsp, result, rspEntity);
                rspEntity.setMsgCd(MsgCdEnum.SUCC.getMsgCd());
                rspEntity.setMsgInf(MsgCdEnum.SUCC.getMsgInf());
                return rspEntity;
            } else {
                rspEntity.setMsgCd(MsgCdEnum.SEND_ERROR.getMsgCd());
                rspEntity.setMsgInf(MsgCdEnum.SEND_ERROR.getMsgInf());
                return rspEntity;
            }
        } catch (IOException e) {
            rspEntity.setMsgCd(MsgCdEnum.SYS_ERROR.getMsgCd());
            rspEntity.setMsgInf(e.toString());
            return rspEntity;
        }
    }

    @Override
    public Entity postXml(String code, Entity message) {
        Entity rspEntity = new Entity();
        String fileName = code + XML_SUFFIX;
        Resource resource = new ClassPathResource(fileName);
        try {
            File file = resource.getFile();
            SAXReader saxReader = new SAXReader();
            Document document = saxReader.read(file);
            Element root = document.getRootElement();
            Element req = root.element("req");
            Element rsp = root.element("rsp");
            String msg = XmlUtils.parseReqXml(req, message);
            CloseableHttpClient httpClient = HttpClients.createDefault();
            HttpPost httpPost = new HttpPost(url);
            if(StringUtils.isBlank(encoding)) {
                encoding = DEFAULT_ENCODING;
            }
            StringEntity entity = new StringEntity(msg, encoding);
            entity.setContentType(XML_TYPE);
            httpPost.setEntity(entity);
            HttpResponse response = httpClient.execute(httpPost);
            if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                String result = EntityUtils.toString(response.getEntity());
                XmlUtils.parseRspStr(rsp, result, rspEntity);
                rspEntity.setMsgCd(MsgCdEnum.SUCC.getMsgCd());
                rspEntity.setMsgInf(MsgCdEnum.SUCC.getMsgInf());
                return rspEntity;
            } else {
                rspEntity.setMsgCd(MsgCdEnum.SEND_ERROR.getMsgCd());
                rspEntity.setMsgInf(MsgCdEnum.SEND_ERROR.getMsgInf());
                return rspEntity;
            }
        } catch (IOException | DocumentException e) {
            rspEntity.setMsgCd(MsgCdEnum.SYS_ERROR.getMsgCd());
            rspEntity.setMsgInf(e.toString());
            return rspEntity;
        }
    }
}
