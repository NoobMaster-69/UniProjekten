import socket

HOST = 'nebsy.nt.fh-koeln.de' # Server's IP address (use localhost for local testing)3
PORT = 62423 # Server's port4

def send_message(message):
    with socket.socket(socket.AF_INET, socket.SOCK_STREAM) as client_socket:
        client_socket.connect((HOST, PORT))
        client_socket.sendall(message.encode())
        response = client_socket.recv(1024)
        dec_response = response.decode()
        print("Server response:", dec_response)
        with open ("ex_01.txt", "w") as text_file:
            text_file.write(dec_response)

if __name__ == "__main__":
        msg = input("Enter a message to send to the server: ")
        send_message(msg)