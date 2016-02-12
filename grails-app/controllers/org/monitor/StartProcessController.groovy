package org.monitor

import grails.plugins.rest.client.*

class StartProcessController {

    def index() {
		try {
			render '************StartProcessController**************'
			
			def client = new RestBuilder()
			
			def resp = client.post('http://localhost:8080/engine-rest/process-definition/key/validateAuthor/start'){
				contentType "application/json"
				json '{"variables": {"businessKey": {"value":"myBusinessKey","type":"String"},"url":{"value":"http://localhost:8080/validate-author-0.1/author/","type":"String"},"idAuthor":{"value":"1","type":"String"}}}'				
			}

			render '   STATUS: ' + resp.getStatus();

		}catch (Exception e) {
			e.printStackTrace()
		}
						
	  
	}

	
}
