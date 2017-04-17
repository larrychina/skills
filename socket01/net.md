##java网络编程##

###1，知识扫盲###

> OSI模型：开放系统互连参考模型（open systeminterconnection reference model）
> 
> 为了解决网络之间的兼容性问题，帮助各个厂商生产出可兼容的网络设备，国际标准化组织ISO于1984年提出了OSI RM，OSI 参考模型很快成为计算机网络通信的基础模型。

OSI参考模型：
<table>
    <tr>
        <th>OSI分层</th>
		<th>OSI七层功能</th>
    </tr>
 	<tr>
        <td>应用层</td>
		<td>提供应用程序通信</td>
    </tr>
 	<tr>
        <td>表示层</td>
		<td>处理数据格式，数据加密等</td>
    </tr>
 	<tr>
        <td>会话层</td>
		<td>建立，维护和管理会话</td>
    </tr>
	<tr>
        <td>传输层</td>
		<td>建立主机端到端连接</td>
    </tr>
	<tr>
        <td>网络层</td>
		<td>寻址和路由选择</td>
    </tr>
	<tr>
        <td>数据链路层</td>
		<td>提供介质访问，数据链路管理</td>
    </tr>
	<tr>
        <td>物理层</td>
		<td>比特流传输</td>
    </tr>
</table>

>由于OSI模型和协议比较复杂，所以并没有得到广泛的应用。
而TCP/IP(transfer control protocol/internet protocol,传输控制协议/网际协议)模型因其开放性和易用性在实践中得到了广泛的应用，TCP/IP协议栈也成为互联网的主流协议

TCP/IP协议栈：
<table>
    <tr>
        <th>TCP/IP</th>
		<th>应用描述</th>
		<td>功能</th>
    </tr>
 	<tr>
        <td>应用层</td>
		<td>HTTP、Telnet、FTP、TFTP</td>
		<td>提供应用程序网络接口</td>
    </tr>
	<tr>
        <td>传输层</td>
		<td>TCP/UDP</td>
		<td>建立端到端连接</td>
    </tr>
	<tr>
        <td>网络层</td>
		<td>IP</td>
		<td>选址和路由选者</td>
    </tr>
	<tr>
        <td>数据链路层</td>
		<td>PPP、</td>
		<td>提供介质访问</td>
    </tr>

</table>

TCP的三次握手（建立连接）和四次挥手（断开连接）：

> 建立连接(这三个报文段完成TCP连接的建立)：
> 
*	 1、请求端（通常也称为客户端）发送一个SYN段表示客户期望连接服务器端口，初始序列号为a。
*    2、服务器发回序列号为b的SYN段作为响应。同时设置确认序号为客户端的序列号加1（a+1）作为对客户端的SYN报文		的确认。
*    3、客户端设置序列号为服务器端的序列号加1（b+1）作为对服务器端SYN报文段的确认。
___
    
> 断开连接(四次交互完成双方向的连接的关闭)：
> 
*	 1、请求端（通常也称为客户端）想终止连接则发送一个FIN段，序列号设置为a。
*    2、服务器回应一个确认序号为客户端的序列号加1（a+1）的ACK确认段，作为对客户端的FIN报文的确认。
*    3、服务器端向客户端发送一个FIN终止段（设置序列号为b，确认号为a+1）。
*    4、客户端返回一个确认报文（设置序列号为b+1）作为响应。

详细参考：[This link](http://wangdy.blog.51cto.com/3845563/1588379)

###2,java Socket通信##

Java Socket 可实现客户端--服务器间的双向实时通信。java.net包中定义的两个类socket和ServerSocket，分别用来实现双向连接的client和server端。

Socket通信实现方法（以下代码是简单的单线程实现）

-服务端代码：
	
	// server端
	public static void main(String[] args) {
        try {
            // 1,创建一个socket，指定绑定端口，并开启监听这个端口
            ServerSocket serverSocket = new ServerSocket(port) ;
            // 2,调用accept方法，等待客户端连接
            Socket socket = serverSocket.accept() ;
            // 3,获取输入流，读取客户端信息
            // 4，获取输出流，响应客户端信息
            new Thread(new ServerHandler(socket)).start();
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

	public class ServerHandler implements Runnable {

	    private Socket socket ;
	    public ServerHandler(Socket socket) {
	        this.socket = socket;
	    }
	    @Override
	    public void run() {
	        /**
	        * 代理服务端处理客户端请求的数据
	        */
	        try {
	            // 接收客户端发送的数据
	            InputStream is = socket.getInputStream() ;
	            BufferedReader br = new BufferedReader(new InputStreamReader(is) ) ;
	            String info = null ;
	            while ((info = br.readLine()) != null){
	                System.out.println("客户端发来信息："+info);
	            }
	
	            // 响应客户端请求
	            OutputStream os = socket.getOutputStream() ;
	            PrintWriter pw = new PrintWriter(os) ;
	            pw.println("我是小服，收到你的请求，我响应的数据是：你是个逗比，哈哈哈。。。");
	            pw.flush();
	            socket.shutdownOutput();
	            br.close();
	            is.close();
	            pw.close();
	            os.close();
	            //socket.close();
	        } catch (IOException e) {
	            e.printStackTrace();
	        } finally {
	            if(socket != null){
	                try {
	                   socket.close();
	                } catch (IOException e) {
	                    e.printStackTrace();
	                }
	            }
	            socket = null ;
	        }
	    }

-客户端代码实现：

	public static void main(String[] args) {
        Socket socket = null ;
        try {
            // 1、创建客户端Socket，指定服务器地址和端口
            socket = new Socket("127.0.0.1",port) ;
            // 2,获取输出流，向服务端发起信息
            OutputStream os = socket.getOutputStream() ;
            PrintWriter pw = new PrintWriter(os) ;
            pw.println("你好，我是小客："+Client.class.getPackage()+Client.class.toString());
            pw.flush();
            socket.shutdownOutput();

            // 3，获取输入流，读取服务端响应信息
            InputStream is = socket.getInputStream() ;
            BufferedReader br = new BufferedReader(new InputStreamReader(is)) ;
            String info  ;
            System.out.println(br.readLine());
            while ((info = br.readLine())!=null){
                System.out.println("接收到服务器端响应的消息是："+info);
                break;
            }

            pw.close();
            os.close();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if(socket != null){
                    socket.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

以上是一种比较古老的方式，也就是传统的BIO模式，很明显存在的问题是，当客户端数量较多时，随之也会造成服务器压力比较大，自己实现简单的通过伪异步的方式对其进行优化， 就是利用线程池，将任务放入缓冲队列中。

	// server端
	public static void main(String[] args) {
		ServerSocket server = null;
		BufferedReader in = null;
		PrintWriter out = null;
		try {
			server = new ServerSocket(PORT);
			System.out.println("server start");
			Socket socket = null;
			HandlerExecutorPool executorPool = new HandlerExecutorPool(50, 1000);
			while(true){
				socket = server.accept();
				System.out.println("server..................");
				executorPool.execute(new ServerHandler(socket));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(in != null){
				try {
					in.close();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
			if(out != null){
				try {
					out.close();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
			if(server != null){
				try {
					server.close();
				} catch (Exception e3) {
					e3.printStackTrace();
				}
			}
			server = null;				
		}
	}

	HandlerExecutorPool.java

	public class HandlerExecutorPool {

		private ExecutorService executor;
		public HandlerExecutorPool(int maxPoolSize, int queueSize){
			this.executor = new ThreadPoolExecutor(
					Runtime.getRuntime().availableProcessors(),
					maxPoolSize, 
					120L, 
					TimeUnit.SECONDS,
					new ArrayBlockingQueue<Runnable>(queueSize));
		}
		public void execute(Runnable task){
			this.executor.execute(task);
		}
	}



###**3，BIO,NIO,AIO原理分析及代码实现**###
----

**先来个例子理解一下概念，以银行取款为例：**

*	同步 ： 自己亲自出马持银行卡到银行取钱（使用同步IO时，Java自己处理IO读写）。

*	异步 ： 委托一小弟拿银行卡到银行取钱，然后给你（使用异步IO时，Java将IO读写委托给OS处理，需要将数据缓冲区地址和大小传给OS(银行卡和密码)，OS需要支持异步IO操作API）。

*	阻塞 ： ATM排队取款，你只能等待（使用阻塞IO时，Java调用会一直阻塞到读写完成才返回）。

*	非阻塞 ： 柜台取款，取个号，然后坐在椅子上做其它事，等号广播会通知你办理，没到号你就不能去，你可以不断问大堂经理排到了没有，大堂经理如果说还没到你就不能去（使用非阻塞IO时，如果不能读写Java调用会马上返回，当IO事件分发器会通知可读写时再继续进行读写，不断循环直到读写完成）。

**Java对BIO、NIO、AIO的支持：**

*	Java BIO ： 同步并阻塞，服务器实现模式为一个连接一个线程，即客户端有连接请求时服务器端就需要启动一个线程进行处理，如果这个连接不做任何事情会造成不必要的线程开销，当然可以通过线程池机制改善。

*	Java NIO ： 同步非阻塞，服务器实现模式为一个请求一个线程，即客户端发送的连接请求都会注册到多路复用器上，多路复用器轮询到连接有I/O请求时才启动一个线程进行处理。
	
*	Java AIO(NIO.2) ： 异步非阻塞，服务器实现模式为一个有效请求一个线程，客户端的I/O请求都是由OS先完成了再通知服务器应用去启动线程进行处理，

**BIO、NIO、AIO适用场景分析:**

*	BIO方式适用于连接数目比较小且固定的架构，这种方式对服务器资源要求比较高，并发局限于应用中，JDK1.4以前的唯一选择，但程序直观简单易理解。
	
*	NIO方式适用于连接数目多且连接比较短（轻操作）的架构，比如聊天服务器，并发局限于应用中，编程比较复杂，JDK1.4开始支持。
	
*	AIO方式使用于连接数目多且连接比较长（重操作）的架构，比如相册服务器，充分调用OS参与并发操作，编程比较复杂，JDK7开始支持。


> **在上面小节已经介绍过BIO这种阻塞通信模型了，存在的明显的问题是：**

     1，当客户端连接数较多时，服务端就会创建大量的线程，就会造成大量的内存开销和耗时
     2，频繁的上下文切换而且大多数切换都是无意义的。



>**而BIO这种阻塞通信模型（引入多路复用器的概念）：**   

	 1，开启一个线程，来处理所有的IO事件，并控制分发
	 2，事件到的时候触发，而不是阻塞去监听事件
	 3，线程之间是通过wait,notify进行通信，使每次上下文切换都有意义，减少不必要的线程切换


	