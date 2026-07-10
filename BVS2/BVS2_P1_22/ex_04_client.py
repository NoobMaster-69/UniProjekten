
import asyncio


HOST = 'localhost'  # localhost
PORT = 8888        # gleicher Port wie im client

async def send():
    writer = await asyncio.open_connection(HOST, PORT)

    # Nachricht senden
    print("Hier Naricht eingeben: ")
    writer.write(asyncio.to_thread(input, ""))  
    await writer.drain()
    
    # Verbindung schließen
    writer.close()
    await writer.wait_closed()
    

async def receive():

    reader = await asyncio.open_connection(HOST, PORT)
    antwort = await reader.readline()

    print(f"Empfangen vom Server: {antwort.decode().strip()}")


# Event Loop starten
async def main():

    await asyncio.gather(send(), receive())
    

main()