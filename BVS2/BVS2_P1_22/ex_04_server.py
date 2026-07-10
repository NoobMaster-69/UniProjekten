
import asyncio
import socket


HOST = 'localhost'  # localhost
PORT = 8888        # gleicher Port wie im client
server_socket = socket.socket()
# Create the Unix socket server


async def handle_client(reader, writer):
    conn, address = server_socket.accept()
    print("Connection from: " +str(address))
    client_set = set(address)
    daten = await reader.readline()
    nachricht = daten.decode()
    print(f"Erhaltene Nachricht: {nachricht}")

    writer.write(daten)
    await writer.drain()
    print("Zurueck gesendet")
    
    
    writer.close()
    await writer.wait_closed()
    print("Verbindung geschlossen")
    conn.close()
    client_set.remove(address)
    print("Connection from "+ str(address)+ "closed")


async def main():

    server = await asyncio.start_server(handle_client, HOST, PORT)
    async with server:
        await server.serve_forever()

asyncio.run(main())



