package org.larry.socket.msn;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by Larry on 2017/4/17.
 */
public class ClientHandler implements Runnable {

    private Socket socket;

    private User user;

    private BufferedReader reader;

    private BufferedWriter writer;

    private List<ClientHandler> clients;

    public BufferedReader getReader() {
        return reader;
    }

    public void setReader(BufferedReader reader) {
        this.reader = reader;
    }

    public BufferedWriter getWriter() {
        return writer;
    }

    public void setWriter(BufferedWriter writer) {
        this.writer = writer;
    }

    public User getUser() {
        return user;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ClientHandler(Socket socket, List<ClientHandler> clients) {
        this.socket = socket;
        try {
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())) ;
            this.clients = clients;
            // 接收客户端的基本用户信息
            String inf = reader.readLine();

            System.out.println(inf);
            StringTokenizer st = new StringTokenizer(inf, "@");
            user = new User(st.nextToken(), st.nextToken());
            // 反馈连接成功信息
            writer.write(user.getName() + user.getIp() + "与服务器连接成功!\n");
            writer.flush();
            // 反馈当前在线用户信息
            if (clients.size() > 0) {
                String temp = "";
                for (int i = clients.size() - 1; i >= 0; i--) {
                    temp += (clients.get(i).getUser().getName() + "/" + clients
                            .get(i).getUser().getIp())
                            + "@";
                }
                writer.write("USERLIST@" + clients.size() + "@" + temp);
                writer.flush();
            }
            // 向所有在线用户发送该用户上线命令
            for (int i = clients.size() - 1; i >= 0; i--) {
                clients.get(i).getWriter().write(
                        "ADD@" + user.getName() + user.getIp());
                clients.get(i).getWriter().flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        // 获取在线用户列表，根据操作用户操作行为更新在线用户列表
        String readLine = null;
        while (true) {
            try {
                // 接收客户端信息
                readLine = reader.readLine();
                if ("CLOSE".equals(readLine)) {
                    reader.close();
                    writer.close();
                    socket.close();
                    if (!clients.isEmpty()) {
                        for (ClientHandler client : clients) {
                            client.getSocket().getOutputStream().write(("DELETE@" + user.getName()).getBytes());
                            client.getSocket().getOutputStream().flush();
                        }
                        for (int i = 0; i < clients.size(); i++) {
                            if (clients.get(i).getUser() == user) {
                                ClientHandler tem = clients.get(i);
                                clients.remove(clients.get(i));
                                Thread.currentThread().stop();
                                return;
                            }
                        }
                    }
                } else {
                    // 发送消息
                    dispatcherMessage(readLine) ;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public static void main(String[] args) {
        List<Integer> ints = new ArrayList<>();
        ints.add(1);
        ints.add(2);
        ints.add(3);
        ints.add(4);
        ints.add(5);

        for (int i = 0; i < ints.size(); i++) {
            System.out.println("--------------->i=" + i + ",value=" + ints.get(i));
            if (ints.get(i) == 3) {
                ints.remove(ints.get(i));
            }
            System.out.println("i=" + i + ",value=" + ints.get(i));

        }
        for (Integer a : ints) {
            System.out.println(a);
        }

    }
    // 转发消息
    public void dispatcherMessage(String message) throws IOException{
        StringTokenizer stringTokenizer = new StringTokenizer(message, "@");
        String source = stringTokenizer.nextToken();
        String owner = stringTokenizer.nextToken();
        String content = stringTokenizer.nextToken();
        message = source + "说：" + content;
        if (owner.equals("ALL")) {// 群发
            for (int i = clients.size() - 1; i >= 0; i--) {
                clients.get(i).getWriter().write(message + "(多人发送)");
                clients.get(i).getWriter().flush();
            }
        }
    }
}
