package com.zsq.netty;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * spring context 的配置文件
 */
@Configuration
@EnableWebMvc
@ImportResource({"/applicationContext.xml","/applicationContext-redis.xml"})
public class AppConfig {


}
