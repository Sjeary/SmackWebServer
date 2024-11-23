package org.example.smackwebserver.config;

import org.bouncycastle.crypto.agreement.jpake.JPAKERound1Payload;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

public class MySmackConfiguration {
    public String hostname = "120.46.26.49";
    public int port = 5222;
    public String adminName = "admin";
    public String adminPassword = "BIT@1138";
    public String domainName = "localhost";
}
