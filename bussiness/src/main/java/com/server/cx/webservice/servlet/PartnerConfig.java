package com.server.cx.webservice.servlet;

public class PartnerConfig {

    // 合作商户ID。用签约支付宝账号登录ms.alipay.com后，在账户信息页面获取。
    public static final String PARTNER = "2088801486693070";

    // 商户收款的支付宝账号
    public static final String SELLER = "2088801486693070";

    // 商户（RSA）私钥
    public static final String RSA_PRIVATE = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAOKGPjE/iMDfxupDGwAcn6SGCJRMjF/m0Q5hBeU9QHiyZhXxt4CkfvsHkEXRlcaA+lKfvXfO/zFiGNI70sRYTjGZr/zwb/TsmTQfHiwMTU1kodXsIP9tcSfHQFZvIAkL8C0HMyntzY1eikW8V0FafiW/qFQWfm60ryAczasF0udXAgMBAAECgYBwSuv5re0077nD15BC9e2NuYP3QBs2T03DOwWZJkwXiRhiOHknJGRuPMmgUs1Uogt1egqKoeqPqHl1foJ1IvFfZmZcMlosZh4D7PwQJ9X3wzYvYyjpvlFn30e+s34hRtQhfa7VsSH24SbZnwcDeuMI4wAfzJgJm1jSLnq7YoFsQQJBAPaU715XCY7YIfDDE4k6f9mnxrUOe3O2LWpPY+yu1R//SQPGvJXT9eDHs0DSPhx9hL9V+lNYdy8XSnfxmuTYHGsCQQDrLTAMFwm/olrnb/vsvbQxKbI49gvklm4ImTag+lZdXjJ7clN/gL8eNhmnc9itFpmD4Bo+xLZgUXCZOMc3AVvFAkAdhjGvAw7i6qANut3Q9Y8vWXZiYWyIq0x+ySi2wyxDm4Z7jEUyT2HGW0LsRQAo2yKU4k+JwTLsEYbaG7SnJ/UnAkEAhftriSfC5QuBEKOK+hglxXCE2dDLe8J8E6f+KH4WoADfb9b1XQmjWHnn6a+lPQ9IDIqrSSFW6c7cAF8Jl/vNJQJAG/XoxkgSMt5mrhDdjFvtfmsSsyAKZ13T1yxGNCyQAG6hqdoizCSfpJ/vnEqLFJMON9vrYpSaMJR6Y0b9gm86QA==";

    // 支付宝（RSA）公钥 用签约支付宝账号登录ms.alipay.com后，在密钥管理页面获取。
    public static final String RSA_ALIPAY_PUBLIC = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCoIc0WsupmZnejJhON/P5CumOXX2rc6eKLpqmamgEymPVsgk3CsdQ4kqZgLoEohdr35J70s7Z+XkOWfIjQLU+EYQf4EtvaNsxieHPL221jYL0fASVaCDr74IqjPa4Qnl4DW7PdfozCJDW2179zTpJYMYMs9gdnBGQqf9TScy16RwIDAQAB";

    // 支付宝安全支付服务apk的名称，必须与assets目录下的apk名称一致
    public static final String ALIPAY_PLUGIN_NAME = "alipay_plugin_20120428msp.apk";

}
