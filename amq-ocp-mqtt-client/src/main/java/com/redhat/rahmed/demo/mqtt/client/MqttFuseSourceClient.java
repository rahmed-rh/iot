package com.redhat.rahmed.demo.mqtt.client;

import javax.net.ssl.SSLException;
import java.util.concurrent.TimeUnit;

import org.apache.activemq.artemis.core.remoting.impl.ssl.SSLSupport;
import org.fusesource.mqtt.client.BlockingConnection;
import org.fusesource.mqtt.client.MQTT;
import org.fusesource.mqtt.client.Message;
import org.fusesource.mqtt.client.QoS;
import org.fusesource.mqtt.client.Topic;

public class MqttFuseSourceClient {
	static String broker = "ssl://broker-amq-headless-amq-demo6.apps.rahmed.lab.pnq2.cee.redhat.com:443";
	static String username = "amq-demo-user";
	static String password = "amqDemoPassword";
	static String clientId = "demoClient182457817321";
	static String topic = "demoTopic";
	static String keyStorePath = "/home/rahmed/workspace_amq/cert/amq-client.jks";
	static String keyStorePassword = "passw0rd";
	static String keyStoreType = "JKS";
	static String trustStorePath = "/home/rahmed/workspace_amq/cert/amq-client.jks";
	static String trustStorePassword = "passw0rd";
	static String trustStoreType = "JKS";

	public static void main(final String[] args) throws Exception {
		boolean exception = false;

		try {
			callBroker();
		} catch (SSLException e) {
			exception = true;
		}
		/*if (!exception) {
			throw new RuntimeException("The connection should be revoked");
		}*/
	}

	private static void callBroker() throws Exception {
		BlockingConnection connection = null;

		try {
			connection = retrieveMQTTConnection();
			// Subscribe to topics
			//Topic[] topics = { new Topic(topic, QoS.AT_MOST_ONCE) };
			//connection.subscribe(topics);
			
			System.out.println("Sending Message ...");

			// Publish Messages
			String payload = "This is message 1";

			connection.publish(topic, payload.getBytes(), QoS.AT_LEAST_ONCE, false);
			
			System.out.println("Message Sent !!!!");

			//Message message = connection.receive(5, TimeUnit.SECONDS);
			//System.out.println("Message received: " + new String(message.getPayload()));

		} catch (Exception e) {
			throw e;
		} finally {
			if (connection != null) {
				connection.disconnect();
			}
		}
	}

	private static BlockingConnection retrieveMQTTConnection() throws Exception {
		MQTT mqtt = new MQTT();
		mqtt.setUserName(username);
		mqtt.setPassword(password);
		mqtt.setClientId(clientId);
		mqtt.setConnectAttemptsMax(0);
		mqtt.setReconnectAttemptsMax(0);
		mqtt.setHost(broker);
		mqtt.setSslContext(SSLSupport.createContext(keyStoreType, keyStorePath, keyStorePassword, trustStoreType, trustStorePath,
				trustStorePassword));
		mqtt.setCleanSession(true);
		System.out.println("Connecting ....");
		BlockingConnection connection = mqtt.blockingConnection();
		connection.connect();
		System.out.println("Connected !!!!");
		return connection;
	}

}