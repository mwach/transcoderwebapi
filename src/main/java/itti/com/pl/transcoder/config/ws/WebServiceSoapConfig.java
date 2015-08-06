package itti.com.pl.transcoder.config.ws;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.server.EndpointInterceptor;
import org.springframework.ws.server.endpoint.interceptor.PayloadLoggingInterceptor;
import org.springframework.ws.soap.server.endpoint.SoapFaultMappingExceptionResolver;
import org.springframework.ws.soap.server.endpoint.interceptor.PayloadValidatingInterceptor;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.validation.ValidationErrorHandler;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

@Configuration
@EnableWs
@ComponentScan("itti.com.pl.transcoder.controller")
public class WebServiceSoapConfig extends WsConfigurerAdapter {

	@Bean(name = "configuration")
	public DefaultWsdl11Definition defaultWsdl11Definition(XsdSchema schema) {
		DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
		wsdl11Definition.setPortTypeName("ConfigurationPort");
		wsdl11Definition.setLocationUri("/ws");
		wsdl11Definition.setTargetNamespace("http://itti.com.pl/transcoderwebapi");
		wsdl11Definition.setSchema(schema);
		return wsdl11Definition;
	}

	@Override
	public void addInterceptors(List<EndpointInterceptor> interceptors) {
		interceptors.add(payloadValidatingInterceptor());
//		interceptors.add(new PayloadLoggingInterceptor());
	}

	private PayloadValidatingInterceptor payloadValidatingInterceptor(){
PayloadValidatingInterceptor payloadValidatingInterceptor = new PayloadValidatingInterceptor();
payloadValidatingInterceptor.setXsdSchema(schema());
payloadValidatingInterceptor.setValidateRequest(true);
payloadValidatingInterceptor.setValidateResponse(true);


return payloadValidatingInterceptor;
	}

	@Bean
	public XsdSchema schema() {
		return new SimpleXsdSchema(new ClassPathResource("transcoderwebapi.xsd"));
	}
}
