# Monitor - Camunda

This example contains 2 parts:

1. Exposed Webservice to be invoked from camunda process
2. Invocation Camunda Rest API to start a new process. 

# Overview

## How to run it
1. Download camunda-bpm-tomcat-7.4.0
2. Download grails-2.4.4 (grails+groovy)
3. Checkout the project with Git
4. create a war (grails war) and copy it on C:\camunda-bpm-tomcat-7.4.0\server\apache-tomcat-8.0.24\webapps
5. Run start-camunda.bat
6. Deploy validateAuthor.bpmn on Camunda using a RestClient (http://localhost:8080/engine-rest/deployment/create)
7. http://localhost:8080/validate-author-0.1/
8. click on --> org.monitor.StartProcessController - It will start a new process which invoke validateAuthor webservice 
Check it on Camunda Tasklist



## How it works

### Using Grails REST Client Builder Plugin

StartProcessController create a new process of a workflow previously uploaded to Camunda:

			def resp = client.post('http://localhost:8080/engine-rest/process-definition/key/validateAuthor/start'){
				contentType "application/json"
				json '{"variables": {"businessKey":{"value":"myBusinessKey","type":"String"},
											"url":{"value":"http://localhost:8080/validate-author-0.1/author/","type":"String"},
											"idAuthor":{"value":"1","type":"String"}}}'				
			}



### Using a Connector Service Task for REST requests

The task *Valid Corresponding Author* makes use of Connect's HTTP connector. It does the following things:

1. Invoke a REST web service using the HTTP connector provided by camunda Connect.
2. Extract a property indicating whether the author is validated from the service's JSON response using Javascript and camunda Spin. This variable is used on the follow-up exclusive gateway.

In detail, the task is declared as follows (see workflow):

    <bpmn2:serviceTask id="ServiceTask_1" name="Valid Corresponding Author">
      <bpmn2:extensionElements>
        <camunda:connector>
          <camunda:inputOutput>               
            <camunda:inputParameter name="url">${url}${idAuthor}</camunda:inputParameter>
            <camunda:inputParameter name="method">GET</camunda:inputParameter>
            <camunda:inputParameter name="headers">
              <camunda:map>
                <camunda:entry key="Accept">application/json</camunda:entry>
              </camunda:map>
            </camunda:inputParameter>
            <camunda:outputParameter name="isValidated">
              <![CDATA[
                ${S(response).jsonPath("$..validated[0]").boolValue()}
              ]]>
            </camunda:outputParameter>
          </camunda:inputOutput>
          <camunda:connectorId>http-connector</camunda:connectorId>
        </camunda:connector>
      </bpmn2:extensionElements>
      <bpmn2:incoming>SequenceFlow_1</bpmn2:incoming>
      <bpmn2:outgoing>SequenceFlow_2</bpmn2:outgoing>
    </bpmn2:serviceTask>

The task uses a `camunda:connector` extension element. It means that a connector should be invoked when the service task is executed. 

The `${url}${idAuthor}` variables are received as parameters when the start-process is invoked.

The `outputParameter` element maps the response obtained by the connector to a variable `isValidated`. The mapping extract an element from the returned JSON.

