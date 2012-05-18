Quick setup for developers
==========================

1) You will need to have the following on your machine:
    postgresql 8.4 +
	CouchDB 1.01 +
    ant 1.7.x
    jdk 1.6
    tomcat 6.0.xx (Might work for 5.5.20 version, did not get a chance to test on this, it is always better to use latest version.)

2) Setting up database:
    a. install Postgres 8.4
	b. use pgAdmin (GUI) to create cacure user (role)
	c. Create cacure database
	d. Set cacure database owner to cacure user
	e. run db creation script from source code (src/main/db/cacure.sql)
	f. modify cacure search path (this is needed for SQL not to have to use schema name)
		ALTER ROLE cacure SET search_path TO 'cacure','public';

3) Setting up data store:
    a. install CouchDB
	b. create cacure database
	c. in cacure database create a new design document names 'caCURE'
	d. update design document content with content from src/main/db/caCURE-CouchDB-App.json.txt
		
3) Configure your local build settings in ${workspace}/local-build.properties. The
   properties to pay attention to are:
    java.home=/System/Library/Frameworks/JavaVM.framework/Versions/1.5.0/Home
    tomcat.java.home=/System/Library/Frameworks/JavaVM.framework/Versions/1.5.0/Home
    tomcat.dir=../How2Tools/apache-tomcat-6.0.26
    ...
    if you had setup your databases differently than above modify src/main/resources/cacure.properties

4) Buillding and running the project. The following targets are useful from a
developer's perspective.
    Initially:
      ant -Dfast.build=1 -Dtarget.context=<your context> all
      ant tomcat-start
      You now should be able to see the web site at something like:
        http://localhost:8080/
    Modifying JSPs (no need to restart the server):
      ant copy-to-deploy
      reload the web page
    Modification to a controller:
      ant all
      ant tomcat-start

