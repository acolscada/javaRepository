package cn.acol.scada.records.core.impl;


import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import cn.acol.scada.records.core.ConnectionException;
import cn.acol.scada.records.core.Response;
import cn.acol.scada.records.core.ScadaControl;
import cn.acol.scada.records.core.Session;

@Component
@Scope("prototype")
public class AcolResponse extends Response{
	
	@Autowired
	private ScadaControl scadaControl;
	
	public AcolResponse(Session session) {
		super(session);
	}
	public AcolResponse() {
		// TODO Auto-generated constructor stub
	}
	@Override
	public void setCollectionTime(Long ms) {
		// TODO Auto-generated method stub
		scadaControl.setCollectionTimes(ms);
	}
	@Override
	public void setUpTime(Long ms) {
		// TODO Auto-generated method stub
		scadaControl.setUpTimes(ms);
	}

	@Override
	public void flushTime() {
		// TODO Auto-generated method stub
		scadaControl.flushTimes();
	}

	
	@Override
	public void commit() throws ConnectionException{
		// TODO Auto-generated method stub
		byte[] controllerCommand = scadaControl.getControllerCommand();
		try {
			session.getOutputStream().write(controllerCommand);
		} catch (IOException e) {
			throw new ConnectionException(e.getMessage());
		}
	}
	private static final byte[] data = {0x18,0x01,0x00,0x00,0x00,0x02,0x00,0x00,0x1B,0x00,0x16};
	@Override
	public void absoluteUpSuccessful() throws ConnectionException {
		// TODO Auto-generated method stub
		try {
			session.getOutputStream().write(data);
		} catch (IOException e) {
			throw new ConnectionException(e.getMessage());
		}
	}
	@Override
	public void setPrice(Float price) {
		// TODO Auto-generated method stub
		if(price !=null) {
			scadaControl.setPrice(price);
		}
	}
	

	
	
}
