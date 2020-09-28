package com.xhuicloud.common.core.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Bean属性
 *
 */
@Getter
@AllArgsConstructor
public class BeanProperty {
	private final String name;
	private final Class<?> type;
}
