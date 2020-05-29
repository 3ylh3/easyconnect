package com.xiaobai.easyconnect.common;

/**
 * 返回码枚举
 *
 * @author yin_zhj
 * @date 2020/5/29
 */
public enum MsgCdEnum {
    SUCC("00000", "success"),
    SYS_ERROR("00001", "system error"),
    SEND_ERROR("00002", "send error");


    private String msgCd;
    private String msgInf;
    MsgCdEnum(String msgCd, String msgInf) {
        this.msgCd = msgCd;
        this.msgInf = msgInf;
    }

    public String getMsgCd() {
        return msgCd;
    }

    public String getMsgInf() {
        return msgInf;
    }
}
