import random
import socket
import struct

INT = 4

if __name__ == '__main__':
    server_ip = 'localhost'
    server_port = 12345

    try:
        server = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
        random.seed()
        my_guess = random.randint(0, 1001)
        print('My guess: {} '.format(my_guess))
        server.sendto(struct.pack('!I', my_guess), (server_ip, server_port))

        greeting, address = server.recvfrom(1024)
        greeting = greeting.decode('utf-8')
        print(greeting + "\n")

        print('Waiting answer..')
        answer, address = server.recvfrom(1024)
        answer = answer.decode('utf-8')
        print(answer)
        server.close()
    except socket.error as msg:
        print("Error: ", msg.strerror)
        exit(-1)
