package com.taas.ws.controller;

import com.taas.ws.services.IAbstractService;

//Marker Interface
public abstract class AbstractController {

	protected abstract void apply(IAbstractService iAbstractService);

}