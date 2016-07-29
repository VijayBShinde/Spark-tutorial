
import org.apache.hadoop.conf._
import org.apache.hadoop.fs._
import java.net._
import org.apache.spark.SparkConf
import org.apache.spark.SparkContext
import org.apache.spark.rdd.RDD.rddToPairRDDFunctions

object ReadHDFS {
  def main(args: Array[String]) = {
    // HDFS Connection code
    var coreSitePath = new Path("/usr/local/hadoop/hadoop-2.7.2/etc/hadoop/core-site.xml")
    var hdfsSitePath = new Path("/usr/local/hadoop/hadoop-2.7.2/etc/hadoop/hdfs-site.xml")
    //create Configuration code
    var conf = new Configuration()
    conf.addResource(coreSitePath)
    conf.addResource(hdfsSitePath)
    print("Connected..")
    
    // Spark Connection
    val sparkConf = new SparkConf().setAppName("WordCount").setMaster("local")
    val sc = new SparkContext(sparkConf)
    println("Spark object created...")
    
    // Connect to FS
    var fs = FileSystem.get(new URI("/"),conf)
    var status = fs.listStatus(new Path("/EXDATA"))
    
    for(s <- status){
      //println(s.getPath)
      if(s.isDirectory()){
        println("Is directory !!!")
        var path = s.getPath()
        println(path)
        var status2 = fs.listStatus(path)
        var header = ""
        for(s2 <- status2){
          if(s2.isFile()){
            var file_Name = s2.getPath()
//            println("Is a file : "+s2.getPath().getName()+" : Path : "+file_Name)
            var df = sc.textFile(file_Name.toString())
            println(s2.getPath().getName()+" has "+df.count()+" number of line")
            header = df.first()
            println("First : "+df.first())
            //df.saveAsTextFile("/test.csv")
          }
          else{
            println("Is Directory !"+s2.getPath())
          }
          
        }
      }
      else{
        println("Its File !!!")
      }
    }
  }
}