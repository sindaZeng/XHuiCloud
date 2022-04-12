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

import static com.alibaba.nacos.sys.env.Constants.STANDALONE_MODE_PROPERTY_NAME;

@EnableScheduling
@SpringBootApplication
public class XHuiNacosApplication {

	public static void main(String[] args) {
		System.setProperty(STANDALONE_MODE_PROPERTY_NAME, "true");
		System.setProperty("server.port", "13000");
		SpringApplication.run(XHuiNacosApplication.class, args);
	}

}
