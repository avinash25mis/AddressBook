echo "Deploying Application....."


set JAVA_OPTS=-Xms512m -Xmx1024m

java %JAVA_OPTS%  -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=8998 -jar ./target/addressBook.jar -D &
echo "Deployment process complete...."