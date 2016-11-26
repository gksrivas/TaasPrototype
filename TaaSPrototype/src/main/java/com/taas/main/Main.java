package com.taas.main;

import static com.taas.ws.Constants.PORT;

import com.taas.ws.ControllerBuilder;
import com.taas.ws.controller.TaaSController;
import com.taas.ws.services.impl.TaaSService;

import spark.Spark;

public class Main {

	public static void main(final String... args) {
		try {
			Spark.port(new Integer(PORT));
			
			// Building TaaS controller
			final ControllerBuilder<TaaSController, TaaSService> taasControllerBuilder = 
					new ControllerBuilder<TaaSController, TaaSService>(TaaSController.class, TaaSService.class);

			taasControllerBuilder.buildController().apply(taasControllerBuilder.buildService());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}