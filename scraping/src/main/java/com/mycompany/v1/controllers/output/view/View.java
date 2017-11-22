package com.mycompany.v1.controllers.output.view;

import org.wicketstuff.rest.annotations.MethodMapping;
import org.wicketstuff.rest.utils.http.HttpMethod;

import com.mycompany.v1.controllers.RestAPI;
import com.mycompany.v1.models.dao.MachineDataDao;
import com.mycompany.v1.models.entity.DataEntity;

import java.util.List;


public class View extends RestAPI {
	private static final long serialVersionUID = 1L;

	public View() {
		super();
	}
	
	@MethodMapping("/list")
	public List<DataEntity> getList() {

	    MachineDataDao dao = new MachineDataDao();
    	List<DataEntity> list = dao.getList();
		   
		return list;
    }
	
	@MethodMapping(value = "/data/{machineId}", httpMethod = HttpMethod.GET)
	public DataEntity getData(Long machineId) {

    	MachineDataDao dao = new MachineDataDao();
		long machineNo = machineId;
    	DataEntity data = dao.search(machineNo);

    	if(data == null) {
    	    this.setResponseStatusCode(404);
    	}
    	
		return data;
	}
}
