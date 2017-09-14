package sooncode.mongodb;
import java.util.ArrayList;
import java.util.List;

import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoDatabase;  
  
public class MongoClientManager {  
	
	private String ip;
	private Integer port;
	private String username;
	private String password;
	private String databaseName;
	 
	
	
    public void setIp(String ip) {
		this.ip = ip;
	}
 
	public void setPort(Integer port) {
		this.port = port;
	}
 
	public void setUsername(String username) {
		this.username = username;
	}
 
	public void setPassword(String password) {
		this.password = password;
	}
 
	public void setDatabaseName(String databaseName) {
		this.databaseName = databaseName;
	}
  
	
 
	public <T> T mongoDatabaseCallBack( MongoDatabaseUse<T> MongoDatabaseUse ){  
		 MongoClient mongoClient = null ;
        try {  
            //连接到MongoDB服务 如果是远程连接可以替换“localhost”为服务器所在IP地址  
            //ServerAddress()两个参数分别为 服务器地址 和 端口  
            ServerAddress serverAddress = new ServerAddress(this.ip,this.port);  
            List<ServerAddress> addrs = new ArrayList<ServerAddress>();  
            addrs.add(serverAddress);  
              
            //MongoCredential.createScramSha1Credential()三个参数分别为 用户名 数据库名称 密码  
            MongoCredential credential = MongoCredential.createScramSha1Credential(this.username, this.databaseName, this.password.toCharArray());  
            List<MongoCredential> credentials = new ArrayList<MongoCredential>();  
            credentials.add(credential);  
              
            //通过连接认证获取MongoDB连接  
            mongoClient = new MongoClient(addrs,credentials);  
            MongoDatabase mongoDatabase = mongoClient.getDatabase(this.databaseName); 
            T t = MongoDatabaseUse.use(mongoDatabase);
            mongoClient.close();
            return t;
            
            
             
        } catch (Exception e) {  
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            if(mongoClient != null){
            	mongoClient.close();
            }
            return null;
             
        }  
    } 
	
	 
	
	 
} 