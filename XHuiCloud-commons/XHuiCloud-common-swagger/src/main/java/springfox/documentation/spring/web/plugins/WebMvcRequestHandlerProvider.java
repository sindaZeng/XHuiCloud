/*
 * MIT License
 * Copyright <2021-2022>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
 * of the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A
 * PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF
 * CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE
 * OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 * @Author: Sinda
 * @Email:  xhuicloud@163.com
 */
package springfox.documentation.spring.web.plugins;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Conditional;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.RequestMappingInfoHandlerMapping;
import springfox.documentation.RequestHandler;
import springfox.documentation.spi.service.RequestHandlerProvider;
import springfox.documentation.spring.web.OnServletBasedWebApplication;
import springfox.documentation.spring.web.WebMvcRequestHandler;
import springfox.documentation.spring.web.readers.operation.HandlerMethodResolver;

import javax.servlet.ServletContext;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static java.util.stream.Collectors.toList;
import static springfox.documentation.builders.BuilderDefaults.nullToEmptyList;
import static springfox.documentation.spi.service.contexts.Orderings.byPatternsCondition;
import static springfox.documentation.spring.web.paths.Paths.ROOT;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
@Conditional(OnServletBasedWebApplication.class)
public class WebMvcRequestHandlerProvider implements RequestHandlerProvider {
  private final List<RequestMappingInfoHandlerMapping> handlerMappings;
  private final HandlerMethodResolver methodResolver;
  private final String contextPath;

  @Autowired
  public WebMvcRequestHandlerProvider(
      Optional<ServletContext> servletContext,
      HandlerMethodResolver methodResolver,
      List<RequestMappingInfoHandlerMapping> handlerMappings) {
    this.handlerMappings = handlerMappings.stream().filter(mapping -> mapping.getPatternParser() == null)
            .collect(Collectors.toList());
    this.methodResolver = methodResolver;
    this.contextPath = servletContext
        .map(ServletContext::getContextPath)
        .orElse(ROOT);
  }

  @Override
  public List<RequestHandler> requestHandlers() {
    return nullToEmptyList(handlerMappings).stream()
        .filter(requestMappingInfoHandlerMapping ->
            !("org.springframework.integration.http.inbound.IntegrationRequestMappingHandlerMapping"
                  .equals(requestMappingInfoHandlerMapping.getClass()
                      .getName())))
        .map(toMappingEntries())
        .flatMap((entries -> StreamSupport.stream(entries.spliterator(), false)))
        .map(toRequestHandler())
        .sorted(byPatternsCondition())
        .collect(toList());
  }

  private Function<RequestMappingInfoHandlerMapping,
      Iterable<Map.Entry<RequestMappingInfo, HandlerMethod>>> toMappingEntries() {
    return input -> input.getHandlerMethods()
        .entrySet();
  }

  private Function<Map.Entry<RequestMappingInfo, HandlerMethod>, RequestHandler> toRequestHandler() {
    return input -> new WebMvcRequestHandler(
        contextPath,
        methodResolver,
        input.getKey(),
        input.getValue());
  }
}
