/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sd.mqtt;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

/**
 *
 * @author rodrigo
 */
public class Subscriber {

    public static void main(String[] args) throws MqttException, InterruptedException {
        String clientId = "myClient2";
        String topic = "MQTT Examples";                  // confirme que é o tópico certo
        //String broker = "tcp://broker.mqttdashboard.com:1883";   // verifique se é o mesmo endereço
        String broker = "tcp://localhost:1883";   // verifique se é o mesmo endereço cassandana
        MqttClient sampleClient = new MqttClient(broker, clientId, new MemoryPersistence());
        MqttConnectOptions connOpts = new MqttConnectOptions();
        connOpts.setCleanSession(true);

        sampleClient.setCallback(new MqttCallback() {
            @Override
            public void connectionLost(Throwable cause) { //Called when the client lost the connection to the broker 
            }

            public void connectComplete(boolean reconnect, java.lang.String serverURI) {
            }

            @Override
            public void messageArrived(String topic, MqttMessage message) throws Exception {   // método acionado ao receber uma msg
                System.out.println("messageArrived:");
                System.out.println(topic + ": " + new String(message.getPayload()));
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {//Called when a outgoing publish is complete 
            }
        });

        System.out.println("Connecting to broker: " + broker);
        sampleClient.connect(connOpts);
        System.out.println("Connected");
        sampleClient.subscribe(topic);
        
        for (int i=1; i<30; i++) {
            System.out.println("\t ... "+i);
            Thread.sleep(1000);
        }
        
        sampleClient.disconnect();
        sampleClient.close();
    }

}
