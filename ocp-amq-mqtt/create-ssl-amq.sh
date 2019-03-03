keytool -genkey -noprompt -keyalg RSA -alias amq-broker -dname "CN=broker-amq-headless-demo.apps.rahmed.lab.pnq2.cee.redhat.com" -keystore amq-broker.jks -storepass passw0rd -keypass passw0rd -deststoretype pkcs12
keytool -genkey -noprompt -keyalg RSA -alias amq-client -dname "CN=broker-amq-headless-demo.apps.rahmed.lab.pnq2.cee.redhat.com" -keystore amq-client.jks -storepass passw0rd -keypass passw0rd -deststoretype pkcs12


keytool -export -alias amq-broker -keystore amq-broker.jks -storepass passw0rd -file amq-broker_cert.der
openssl x509 -inform DER -in amq-broker_cert.der -out amq-broker_cert.crt

keytool -export -alias amq-client -keystore amq-client.jks -storepass passw0rd -file amq-client_cert.der
openssl x509 -inform DER -in amq-client_cert.der -out amq-client_cert.crt


keytool -import -trustcacerts -noprompt -alias amq-broker -keystore amq-client.jks -storepass passw0rd -file amq-broker_cert.der
keytool -import -trustcacerts -noprompt -alias amq-client -keystore amq-broker.jks -storepass passw0rd -file amq-client_cert.der


openssl pkcs12 -in amq-broker.jks -nocerts -nodes  -out amq-broker.key 
openssl pkcs12 -in amq-client.jks -nocerts -nodes  -out amq-client.key 

