import socket

HOST = '0.0.0.0' # Listen on all available interfaces
PORT = 62423 # Port to listen on

def start_server():
    with socket.socket(socket.AF_INET, socket.SOCK_STREAM) as server_socket:
        server_socket.bind((HOST, PORT))
        server_socket.listen()
        print(f"Server listening on port {PORT}...")
        
        while True:
            conn, addr = server_socket.accept()
            with conn:
                print(f"Connected by {addr}")
                while True:
                    data = conn.recv(1024)
                    if not data:
                        break
                    sorted_data = ''.join(sorted(data.decode()))
                    response = f"Sorted message: {sorted_data}"
                    conn.sendall(response.encode())
def main():
    start_server()

main()