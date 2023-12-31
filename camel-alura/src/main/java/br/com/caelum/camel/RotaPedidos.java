package br.com.caelum.camel;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.RoutesBuilder;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

public class RotaPedidos {

	public static void main(String[] args) throws Exception {

		CamelContext context = new DefaultCamelContext();
		context.addRoutes(new RouteBuilder() {
			@Override
			public void configure() throws Exception {
				//file(tipo de arquivo). :pedido nome arquivo | ?delay = observa a cada 5 segundos
				from("file:pedidos?delay=5s&noop=true")
						.split()
							.xpath("/pedido/itens/item")
							.log("${body}")
						.filter()
							.xpath("/item/formato[text()='EBOOK']")
						//marshal transforma tipos de dados
						.marshal()
							.xmljson()
						.log("${body}")
						//.setHeader("CamelFileName", simple("${file:name.noext}.json"))
						.setHeader(Exchange.FILE_NAME, simple("${file:name.noext}-${header.CamelSplitIndex}.json"))
						.to("file:saida");
			}
		});

		context.start();

		Thread.sleep(20000);
		context.start();


	}	
}
