
import asyncio





HOST = 'localhost'  # localhost
PORT = 65432        # gleicher Port wie im client
# Create the Unix socket server


async def handle_client(reader, writer):
    daten = await reader.readline()
    nachricht = daten.decode()
    print(f"Erhaltene Nachricht: {nachricht}")

    writer.write(daten)
    await writer.drain()
    print("Zurueck gesendet")
    
    
    writer.close()
    await writer.wait_closed()
    print("Verbindung geschlossen")



async def main():

    server = await asyncio.start_server(handle_client, HOST, PORT)
    async with server:
        await server.serve_forever()

asyncio.run(main())



