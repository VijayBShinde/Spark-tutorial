how to check how many cpu core in linux
	- nproc --all 
	- show how many cpu core have your machine
	lscpu | egrep 'Thread|Core|Socket|^CPU\('
	 
	mpstat -P ALL 
	
How to remove all file extension to different?

# Convert file extenstion
find . -name "*.COMPLETED" -exec sh -c 'mv "$1" "${1%.COMPLETED}"' _ {} \;

==========================================================================

Commands:
UNIX
----------------
<Mongo_DBPAth>/bin/mongodump --port 27071 --host IP -u user -p pass -d db

cmd: mysql -u root -p 
root
sudo chown -R username:ownname folder
scp -r folder user@ip:/Path

==========================================================================


• sed -i -- 's/pat1/pat2/g' *

===========================================================================

ERROR: org.apache.hadoop.hbase.PleaseHoldException: Master is initializing
Exact issue: This issue occurs when Region server getting stopped after some time

Exception in thread "main" org.apache.hadoop.hbase.client.RetriesExhaustedException: Can't get the locations

***     Note    ***
Check first all region server started or Not
Check masters log
You can check HDFS's health using:
hdfs fsck /apps/hbase

From <https://community.hortonworks.com/questions/39271/create-table-in-hbase-is-failing-with-the-pleaseho.html> 


**************

hbase-daemon.sh start regionserver

From <http://stackoverflow.com/questions/7309508/hbase-master-not-able-to-start-regionserver> 

This exception occurs because I removed the directory i.e /path/spark-sql-on-hbase-1.0.0.jar

Region servers logs
2017-02-10 20:10:17,164 ERROR [RS_OPEN_META-hadoop-VirtualBox:16201-0] coprocessor.CoprocessorHost: The coprocessor org.apache.spark.sql.hbase.CheckDirEndPointImpl threw java.lang.ClassNotFoundException: org.apache.spark.sql.hbase.CheckDirEndPointImpl
java.lang.ClassNotFoundException: org.apache.spark.sql.hbase.CheckDirEndPointImpl
	at java.net.URLClassLoader$1.run(URLClassLoader.java:366)
	at java.net.URLClassLoader$1.run(URLClassLoader.java:355)
	at java.security.AccessController.doPrivileged(Native Method)
	at java.net.URLClassLoader.findClass(URLClassLoader.java:354)
	at java.lang.ClassLoader.loadClass(ClassLoader.java:425)
	at sun.misc.Launcher$AppClassLoader.loadClass(Launcher.java:308)
	at java.lang.ClassLoader.loadClass(ClassLoader.java:358)
	at org.apache.hadoop.hbase.coprocessor.CoprocessorHost.loadSystemCoprocessors(CoprocessorHost.java:158)
	at org.apache.hadoop.hbase.regionserver.RegionCoprocessorHost.<init>(RegionCoprocessorHost.java:218)
	at org.apache.hadoop.hbase.regionserver.HRegion.<init>(HRegion.java:719)
	at org.apache.hadoop.hbase.regionserver.HRegion.<init>(HRegion.java:627)
	at sun.reflect.NativeConstructorAccessorImpl.newInstance0(Native Method)
	at sun.reflect.NativeConstructorAccessorImpl.newInstance(NativeConstructorAccessorImpl.java:57)
	at sun.reflect.DelegatingConstructorAccessorImpl.newInstance(DelegatingConstructorAccessorImpl.java:45)
	at java.lang.reflect.Constructor.newInstance(Constructor.java:526)
	at org.apache.hadoop.hbase.regionserver.HRegion.newHRegion(HRegion.java:6155)
	at org.apache.hadoop.hbase.regionserver.HRegion.openHRegion(HRegion.java:6459)
	at org.apache.hadoop.hbase.regionserver.HRegion.openHRegion(HRegion.java:6431)
	at org.apache.hadoop.hbase.regionserver.HRegion.openHRegion(HRegion.java:6387)
	at org.apache.hadoop.hbase.regionserver.HRegion.openHRegion(HRegion.java:6338)
	at org.apache.hadoop.hbase.regionserver.handler.OpenRegionHandler.openRegion(OpenRegionHandler.java:362)
	at org.apache.hadoop.hbase.regionserver.handler.OpenRegionHandler.process(OpenRegionHandler.java:129)
	at org.apache.hadoop.hbase.executor.EventHandler.run(EventHandler.java:129)
	at java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1145)
	at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:615)
	at java.lang.Thread.run(Thread.java:745)
2017-02-10 20:10:17,198 FATAL [RS_OPEN_META-hadoop-VirtualBox:16201-0] regionserver.HRegionServer: ABORTING region server hadoop-virtualbox,16201,1486737599539: The coprocessor org.apache.spark.sql.hbase.CheckDirEndPointImpl threw java.lang.ClassNotFoundException: org.apache.spark.sql.hbase.CheckDirEndPointImpl


This issue was came because of When I started to implement Spark-Hbase-SQL implementation, I added one properties in Hbase-env.sh Comment this line in sh file
# Extra Java CLASSPATH elements.  Optional.
 #export HBASE_CLASSPATH=$HBASE_CLASSPATH:/path/usr/Vijay/Code/Spark-SQL-on-HBase-master/target/spark-sql-on-hbase-1.0.0.jar



-- Changes

export HBASE_OPTS="-XX:+UseConcMarkSweepGC -Djava.net.preferIPv4Stack=true"

Getting Next Exception

2017-02-10 20:35:27,141 ERROR [RS_OPEN_META-hadoop-VirtualBox:16201-0] handler.OpenRegionHandler: Failed open of region=hbase:meta,,1.1588230740, starting to roll back the global memstore size.
java.io.InterruptedIOException
	at org.apache.hadoop.hbase.wal.WALKey.getWriteEntry(WALKey.java:108)
	at org.apache.hadoop.hbase.regionserver.wal.WALUtil.writeMarker(WALUtil.java:131)
	at org.apache.hadoop.hbase.regionserver.wal.WALUtil.writeRegionEventMarker(WALUtil.java:88)
	at org.apache.hadoop.hbase.regionserver.HRegion.writeRegionOpenMarker(HRegion.java:993)
	at org.apache.hadoop.hbase.regionserver.HRegion.openHRegion(HRegion.java:6506)
	at org.apache.hadoop.hbase.regionserver.HRegion.openHRegion(HRegion.java:6460)
	at org.apache.hadoop.hbase.regionserver.HRegion.openHRegion(HRegion.java:6431)
	at org.apache.hadoop.hbase.regionserver.HRegion.openHRegion(HRegion.java:6387)
	at org.apache.hadoop.hbase.regionserver.HRegion.openHRegion(HRegion.java:6338)
	at org.apache.hadoop.hbase.regionserver.handler.OpenRegionHandler.openRegion(OpenRegionHandler.java:362)
	at org.apache.hadoop.hbase.regionserver.handler.OpenRegionHandler.process(OpenRegionHandler.java:129)
	at org.apache.hadoop.hbase.executor.EventHandler.run(EventHandler.java:129)
	at java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1145)
	at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:615)
	at java.lang.Thread.run(Thread.java:745)
Caused by: java.lang.InterruptedException
	at java.util.concurrent.locks.AbstractQueuedSynchronizer.doAcquireSharedInterruptibly(AbstractQueuedSynchronizer.java:996)
	at java.util.concurrent.locks.AbstractQueuedSynchronizer.acquireSharedInterruptibly(AbstractQueuedSynchronizer.java:1303)
	at java.util.concurrent.CountDownLatch.await(CountDownLatch.java:236)
	at org.apache.hadoop.hbase.wal.WALKey.getWriteEntry(WALKey.java:98)
	... 14 more
2017-02-10 20:35:27,141 INFO  [RS_OPEN_META-hadoop-VirtualBox:16201-0] coordination.ZkOpenRegionCoordination: Opening of region {ENCODED => 1588230740, NAME => 'hbase:meta,,1', STARTKEY => '', ENDKEY => ''} failed, transitioning from OPENING to FAILED_OPEN in ZK, expecting version 7
2017-02-10 20:35:27,222 INFO  [regionserver/hadoop-VirtualBox/127.0.1.1:16201] ipc.RpcServer: Stopping server on 16201
2017-02-10 20:35:27,223 INFO  [RpcServer.listener,port=16201] ipc.RpcServer: RpcServer.listener,port=16201: stopping
2017-02-10 20:35:27,224 INFO  [RpcServer.responder] ipc.RpcServer: RpcServer.responder: stopped
2017-02-10 20:35:27,224 INFO  [RpcServer.responder] ipc.RpcServer: RpcServer.responder: stopping
2017-02-10 20:35:27,470 INFO  [regionserver/hadoop-VirtualBox/127.0.1.1:16201] zookeeper.ZooKeeper: Session: 0x15a288e42150001 closed
2017-02-10 20:35:27,470 INFO  [regionserver/hadoop-VirtualBox/127.0.1.1:16201] regionserver.HRegionServer: stopping server hadoop-virtualbox,16201,1486739103784; zookeeper connection closed.
2017-02-10 20:35:27,470 INFO  [regionserver/hadoop-VirtualBox/127.0.1.1:16201] regionserver.HRegionServer: regionserver/hadoop-VirtualBox/127.0.1.1:16201 exiting
2017-02-10 20:35:27,472 ERROR [main] regionserver.HRegionServerCommandLine: Region server exiting
java.lang.RuntimeException: HRegionServer Aborted
	at org.apache.hadoop.hbase.regionserver.HRegionServerCommandLine.start(HRegionServerCommandLine.java:68)
	at org.apache.hadoop.hbase.regionserver.HRegionServerCommandLine.run(HRegionServerCommandLine.java:87)
	at org.apache.hadoop.util.ToolRunner.run(ToolRunner.java:70)
	at org.apache.hadoop.hbase.util.ServerCommandLine.doMain(ServerCommandLine.java:126)
	at org.apache.hadoop.hbase.regionserver.HRegionServer.main(HRegionServer.java:2665)
2017-02-10 20:35:27,475 INFO  [main-EventThread] zookeeper.ClientCnxn: EventThread shut down
2017-02-10 20:35:27,488 INFO  [Thread-6] regionserver.ShutdownHook: Shutdown hook starting; hbase.shutdown.hook=true; fsShutdownHook=org.apache.hadoop.fs.FileSystem$Cache$ClientFinalizer@7f96313c
2017-02-10 20:35:27,491 INFO  [Thread-6] regionserver.ShutdownHook: Starting fs shutdown hook thread.
2017-02-10 20:35:27,522 INFO  [Thread-6] regionserver.ShutdownHook: Shutdown hook finished.



This issue is because of I added one property in Hbase-site.xml at the time of Spark-Hbase_SQL Integration i.e


<!--Comment at 10 Feb 2017 10:53PM
<property>
    <name>hbase.coprocessor.region.classes</name>
    <value>org.apache.spark.sql.hbase.CheckDirEndPointImpl</value>
</property>
--> 

I comment the property now Hbase is working fine. Feeling happy.

