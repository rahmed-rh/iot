package com.redhat.rahmed.demo.mqtt.client;

import java.util.Properties;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

public class MQTTPahoClient {
	public MQTTPahoClient() {
	}

	public static void main(String[] args) {

		String broker = "ssl://broker-amq-headless-amq-demo6.apps.rahmed.lab.pnq2.cee.redhat.com:443";
		String username = "amq-demo-user";
		String password = "amqDemoPassword";
		String clientId = "demoClient182457817321";
		String topic = "demoTopic";
		String keyStorePath = "/home/rahmed/workspace_amq/cert/amq-client.p12";
		String keyStorePassword = "passw0rd";
		String keyStoreType="PKCS12";
		String trustStorePath="/home/rahmed/workspace_amq/cert/amq-client.p12";
		String trustStorePassword="passw0rd";
		String trustStoreType="PKCS12";
				
		String content = "This is message";
		int qos = 2;

		MemoryPersistence persistence = new MemoryPersistence();
		try {
			Properties sslProps = new Properties();
			
			sslProps.put("com.ibm.ssl.protocol", "SSLv3");
			sslProps.put("com.ibm.ssl.contextProvider", "SunJSSE");
			sslProps.put("com.ibm.ssl.keyStore", keyStorePath);
			sslProps.put("com.ibm.ssl.keyStorePassword", keyStorePassword);
			sslProps.put("com.ibm.ssl.keyStoreType", keyStoreType);
			sslProps.put("com.ibm.ssl.trustStore", trustStorePath);
			sslProps.put("com.ibm.ssl.trustStorePassword", trustStorePassword);
			sslProps.put("com.ibm.ssl.trustStoreType", trustStoreType);
			
			
			
			
			MqttClient sampleClient = new MqttClient(broker, clientId, persistence);
			MqttConnectOptions connOpts = new MqttConnectOptions();
			connOpts.setUserName(username);
			connOpts.setPassword(password.toCharArray());
			connOpts.setSSLProperties(sslProps);

			connOpts.setCleanSession(true);
			System.out.println("Connecting to broker: " + broker);
			sampleClient.connect(connOpts);
			System.out.println("Connected");
			System.out.println("Publishing message: " + content);
			MqttMessage message = new MqttMessage(content.getBytes());
			message.setQos(qos);
			sampleClient.publish(topic, message);
			System.out.println("Message published");
			sampleClient.disconnect();
			System.out.println("Disconnected");
			System.exit(0);
		} catch (MqttException me) {
			System.out.println("reason " + me.getReasonCode());
			System.out.println("msg " + me.getMessage());
			System.out.println("loc " + me.getLocalizedMessage());
			System.out.println("cause " + me.getCause());
			System.out.println("excep " + me);
			me.printStackTrace();
		}
	}
}