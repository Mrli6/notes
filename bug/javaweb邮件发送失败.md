# 问题

发送邮件时出现如下bug

```java
DEBUG: setDebug: JavaMail version 1.4.7
DEBUG: getProvider() returning javax.mail.Provider[TRANSPORT,smtp,com.sun.mail.smtp.SMTPTransport,Oracle]
DEBUG SMTP: useEhlo true, useAuth true
DEBUG SMTP: trying to connect to host "smtp.qq.com", port 465, isSSL true
Exception in thread "main" javax.mail.MessagingException: Could not connect to SMTP host: smtp.qq.com, port: 465;
  nested exception is:
	javax.net.ssl.SSLHandshakeException: No appropriate protocol (protocol is disabled or cipher suites are inappropriate)
	at com.sun.mail.smtp.SMTPTransport.openServer(SMTPTransport.java:1961)
	at com.sun.mail.smtp.SMTPTransport.protocolConnect(SMTPTransport.java:654)
	at javax.mail.Service.connect(Service.java:295)
	at javax.mail.Service.connect(Service.java:176)
	at com.kuang.servlet.MailDemo01.main(MailDemo01.java:41)
Caused by: javax.net.ssl.SSLHandshakeException: No appropriate protocol (protocol is disabled or cipher suites are inappropriate)
	at sun.security.ssl.HandshakeContext.<init>(HandshakeContext.java:171)
	at sun.security.ssl.ClientHandshakeContext.<init>(ClientHandshakeContext.java:101)
	at sun.security.ssl.TransportContext.kickstart(TransportContext.java:238)
	at sun.security.ssl.SSLSocketImpl.startHandshake(SSLSocketImpl.java:394)
	at sun.security.ssl.SSLSocketImpl.startHandshake(SSLSocketImpl.java:373)
	at com.sun.mail.util.SocketFetcher.configureSSLSocket(SocketFetcher.java:549)
	at com.sun.mail.util.SocketFetcher.createSocket(SocketFetcher.java:354)
	at com.sun.mail.util.SocketFetcher.getSocket(SocketFetcher.java:237)
	at com.sun.mail.smtp.SMTPTransport.openServer(SMTPTransport.java:1927)
	... 4 more
```





# 解决方案

jdk8版本，需要把jdk中的jre里面的java.security中文件中的如下代码删除。只有修改了jdk里面的代码才有效，删除外面的jre中的代码无效

```
SSLv3, TLSv1, TLSv1.1, 
```

