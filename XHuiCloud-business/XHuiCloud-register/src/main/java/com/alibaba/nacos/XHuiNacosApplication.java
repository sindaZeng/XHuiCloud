/*
 * Copyright 1999-2018 Alibaba Group Holding Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.alibaba.nacos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class XHuiNacosApplication {

	public static String STANDALONE_MODE = "nacos.standalone";

	public static String AUTH_ENABLED = "nacos.core.auth.enabled";

	public static String LOG_BASEDIR = "server.tomcat.basedir";

	public static String LOG_ENABLED = "server.tomcat.accesslog.enabled";

	public static void main(String[] args) {
		// 注入环境 以防止被覆盖
		System.setProperty(STANDALONE_MODE, "true");
		System.setProperty(AUTH_ENABLED, "false");
		System.setProperty(LOG_BASEDIR, "logs");
		System.setProperty(LOG_ENABLED, "false");
		System.setProperty("server.port", "13000");

		SpringApplication.run(XHuiNacosApplication.class, args);
	}

}
