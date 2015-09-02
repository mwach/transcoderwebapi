package itti.com.pl.transcoder;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration.Dynamic;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.ws.transport.http.MessageDispatcherServlet;

import itti.com.pl.transcoder.config.WebAppConfig;

public class Initializer implements WebApplicationInitializer {

	public void onStartup(ServletContext servletContext) throws ServletException {

		AnnotationConfigWebApplicationContext ctx = new AnnotationConfigWebApplicationContext();
		ctx.register(WebAppConfig.class);

		ctx.setServletContext(servletContext);
		ctx.refresh();
		DispatcherServlet servlet = new DispatcherServlet();
		servlet.setApplicationContext(ctx);
		Dynamic dynamic = servletContext.addServlet("dispatcher", servlet);
		dynamic.addMapping("/rest/*");
		dynamic.setLoadOnStartup(1);

		MessageDispatcherServlet mdServlet = new MessageDispatcherServlet();
		mdServlet.setTransformWsdlLocations(true);
		mdServlet.setApplicationContext(ctx);
		Dynamic soapDynamic = servletContext.addServlet("soapdispatcher", mdServlet);
		soapDynamic.addMapping("/ws/*");
		soapDynamic.setLoadOnStartup(1);

//		AnnotationConfigWebApplicationContext soapCtx = new AnnotationConfigWebApplicationContext();
//		soapCtx.register(WebServiceSoapConfig.class);
//		soapCtx.setServletContext(servletContext);
//		soapCtx.refresh();
//		MessageDispatcherServlet mdServlet = new MessageDispatcherServlet();
//		mdServlet.setTransformWsdlLocations(true);
//		mdServlet.setApplicationContext(soapCtx);
//		Dynamic soapDynamic = servletContext.addServlet("soapdispatcher", mdServlet);
//		soapDynamic.addMapping("/ws/*");
//		soapDynamic.setLoadOnStartup(1);

	
	}
}
