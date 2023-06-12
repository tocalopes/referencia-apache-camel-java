package br.com.caelum.camel;

import org.apache.camel.CamelContext;
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
						.log("${id}")
						//marshal transforma tipos de dados
						.marshal().xmljson()
						.log("${body}")
						.to("file:saida");
			}
		});

		context.start();

		Thread.sleep(20000);
		context.start();


	}	
}
