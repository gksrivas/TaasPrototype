package com.taas.ws.controller;

import static com.taas.ws.util.JsonUtil.json;
import static com.taas.ws.util.JsonUtil.toJson;
import static spark.Spark.after;
import static spark.Spark.exception;
import static spark.Spark.get;

import com.taas.ws.error.ResponseError;
import com.taas.ws.services.IAbstractService;
import com.taas.ws.services.ITaaSService;

public class TaaSController  extends AbstractController {
	@Override
	public void apply(final IAbstractService iAbstractService) {
		final ITaaSService taasService = (ITaaSService) iAbstractService;

		get("/api/vm/start/:mac_address", (req, res) -> {	

			final String macAddress = req.params(":mac_address");

			return taasService.startVM(macAddress);
		} , json());

		get("/api/vm/stop/:mac_address", (req, res) -> {	

			final String macAddress = req.params(":mac_address");

			return taasService.stopVM(macAddress);
		} , json());

		get("/api/vm/status/:mac_address", (req, res) -> {	

			final String macAddress = req.params(":mac_address");

			return taasService.statusVM(macAddress);
		} , json());

		after((req, res) -> {
			res.header("Access-Control-Allow-Origin", "*");
			res.header("Access-Control-Allow-Headers", "X-Requested-With");
			res.type("application/json");
		});

		exception(IllegalArgumentException.class, (e, req, res) -> {
			res.status(400);
			res.body(toJson(new ResponseError(e)));
		});

	}
}
