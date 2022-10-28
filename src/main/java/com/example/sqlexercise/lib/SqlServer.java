package com.example.sqlexercise.lib;

import lombok.extern.slf4j.Slf4j;

import java.net.ConnectException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;

@Slf4j(topic = "com.example.sqlexercise.lib.SqlServer")
public class SqlServer {

    String hostName;
    ArrayList<String> occupiedPort;

    public SqlServer() {
        this.hostName = "localhost";
        this.occupiedPort = new ArrayList<>();
    }

    public SqlServer(String hostName) {
        this.hostName = hostName;
        this.occupiedPort = new ArrayList<>();
        //TODO 自动获取远程服务器上已占用的端口列表
    }

    /**
     * 查找一个远端服务器上的可用端口（存在监听可以建立连接）
     * 当 autoRelease 为 false 时，查找到的端口不会自动解锁，需要手动释放
     * 检测范围为 portFrom 到 portTo
     *
     * @param {boolean} autoRelease
     * @param {int}     portFrom
     * @param {int}     [portTo]
     * @throws {Error} 如果在 portFrom 和 portTo 之间没有可用端口，则抛出异常
     * @returns {Promise<int>} 可用端口
     */
    public int detectServerPort(boolean autoRelease, int portFrom, int portTo) throws Exception {
        for (int port = portFrom; port <= portTo; port++) {
            if (!getServerOccupiedPort().contains(String.valueOf(port))) {
                occupyServerPort(port);
                boolean occupied = connectServerPort(port);
                if (autoRelease || occupied) {
                    releaseServerPort(port);
                }
                if (!occupied) {
                    return port;
                }
            }
        }
        throw new Exception("can not find available port in server");
    }

    /**
     * 获取服务器端口的操作锁
     * 一般在实际端口开始占用前进行
     *
     * @returns {number[]} 上锁端口
     */
    public ArrayList<String> getServerOccupiedPort() {
        return this.occupiedPort;
    }

    /**
     * 声明对服务器端口的操作上锁
     * 一般在实际端口开始占用前进行
     *
     * @param {number} port
     * @returns {void}
     */
    public void occupyServerPort(int port) {
        this.occupiedPort.add(String.valueOf(port));
    }

    /**
     * 尝试连接远端服务器上的端口
     *
     * @param {number} port
     * @returns {Promise<boolean>} 是否连接成功（端口被占用）
     */
    public boolean connectServerPort(int port) {
        try {
            InetAddress address = InetAddress.getByName(this.hostName);
            log.info(this.hostName + "'s IP address is " + address);
//            System.out.println(this.hostName + "'s IP address is " + address);
            Socket socket = new Socket(address, port);
            log.info("Port " + port + " is used...");
//            System.out.println("Port "+port+" is used...");
            socket.close();
            return true;
        } catch (ConnectException e) {
            log.info("Port " + port + " is not used...");
//            System.out.println("Port "+port+" is not used...");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 声明对服务端端口的操作解锁
     * 一般在实际端口取消占用或完成占用后进行
     *
     * @param {string} hostname
     * @param {number} port
     * @returns {void}
     */
    public void releaseServerPort(int port) {
        this.occupiedPort.remove(String.valueOf(port));
    }

    public static void main(String[] args) {
        try {
            InetAddress address = InetAddress.getByName("localhost");
            System.out.println(address);
            //InetAddress.getLocalHost();
            Socket socket = new Socket(address, 2375);
            System.out.println("Port is used...");
            socket.close();
        } catch (ConnectException e) {
            System.out.println("Port is not used...");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
