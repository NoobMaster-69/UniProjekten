
import asyncio


HOST = 'localhost'  # localhost
PORT = 65432        # gleicher Port wie im client

async def main():
    reader, writer = await asyncio.open_connection(HOST, PORT)

    # Nachricht senden
    writer.write(b"Hallo Server!\n")  
    await writer.drain()

    # Echo empfangen
    antwort = await reader.readline()
    print(f"Empfangen vom Server: {antwort.decode().strip()}")

    # Verbindung schließen
    writer.close()
    await writer.wait_closed()

# Event Loop starten
asyncio.run(main())