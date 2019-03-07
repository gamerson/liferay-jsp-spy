/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.liferay.jsp.spy;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ServiceScope;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Gregory Amerson
 */
@Component(
	property = {
		"dispatcher=INCLUDE", "dispatcher=INCLUDE",
		"osgi.http.whiteboard.filter.name=com.liferay.jsp.spy.JspSpyServletFilter",
		"osgi.http.whiteboard.filter.pattern=*.jsp", "osgi.http.whiteboard.filter.dispatcher=INCLUDE",
		"osgi.http.whiteboard.context.select=(osgi.http.whiteboard.context.name=*)", "servlet-context-name=",
		"servlet-filter-name=jsp-spi", "url-pattern=*.jsp"
	},
	scope = ServiceScope.PROTOTYPE, service = Filter.class
)
public class JspSpyServletFilter implements Filter {

	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
		throws IOException, ServletException {

		String spyBeginMarkup = _spy(request);

		_logger.debug(spyBeginMarkup);

		try {
			PrintWriter printWriter = response.getWriter();

			printWriter.write(spyBeginMarkup);
		}
		catch (IllegalStateException ise) {
			ServletOutputStream servletOutputStream = response.getOutputStream();

			servletOutputStream.write(spyBeginMarkup.getBytes());
		}

		filterChain.doFilter(request, response);
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		_logger = LoggerFactory.getLogger(JspSpyServletFilter.class);
	}

	private String _spy(ServletRequest request) {
		HttpServletRequest httpServletRequest = (HttpServletRequest)request;

		ServletContext servletContext = httpServletRequest.getServletContext();

		BundleContext bundleContext = (BundleContext)servletContext.getAttribute("osgi-bundlecontext");

		String bundleSymbolicName = "";

		if (bundleContext != null) {
			Bundle bundle = bundleContext.getBundle();

			bundleSymbolicName = bundle.getSymbolicName();
		}

		String spyInfo =
			bundleSymbolicName + httpServletRequest.getServletPath() + "->" +
				httpServletRequest.getAttribute("javax.servlet.include.servlet_path");

		StringBuilder sb = new StringBuilder();

		sb.append("\n<!--SPY \n");
		sb.append(spyInfo + " \n");
		sb.append(" -->\n");

		return sb.toString();
	}

	private Logger _logger;

}