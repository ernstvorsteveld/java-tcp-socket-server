import os
import socket
import threading
import time

HOST = "127.0.0.1"
PORT = 80

class SocketManager:
    def __init__(self, host, port):
        self.s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        self.s.connect((host,port))

    def start(self, prefix, max):
        i = 0
        while i < max:
            message = prefix + " Hello, world" + str(i) + "\r\n"
            self.s.sendall(bytes(message, 'utf-8'))
            data = self.s.recv(50)
            print(f"Received {data!r}")
            i = i + 1

def doIt(s, prefix, max):
    s.start(prefix, max)

if __name__ =="__main__":

    s = SocketManager(HOST, PORT)
    ts = []
    for i in range(100):
        ts.append(threading.Thread(target=doIt, args=(s, str(i), 1000,)))

    for t in ts:
        t.start()

    for t in ts:
        t.join()
 
    time.sleep(10)
    print("Done!")

    