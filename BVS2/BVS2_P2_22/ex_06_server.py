import rpyc, os
from rpyc.utils.server import ThreadedServer

class Expose(rpyc.Service):
    def exposed_print_directory(self, path):
        return os.listdir(path) 
    def exposed_read_file(self, filename):
        with open(filename, 'r') as f:
            return f.read()


if __name__ == "__main__":
    server = ThreadedServer(Expose, port=18865)
    print("Server working directory:", os.getcwd())
    server.start()