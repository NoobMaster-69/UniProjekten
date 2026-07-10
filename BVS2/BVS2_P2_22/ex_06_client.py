import rpyc

def main():
    conn = rpyc.connect("localhost", 18865)

    while True:
        print("Options: \n list \n read \n quit")
        cmd = input("> ").strip().lower()
        if(cmd == "quit"):
            break
        elif(cmd == "read"):
            print("Please submit the file path.")
            f_path = input("> ").strip()
            print(conn.root.read_file(f_path))
            print("what command do you want to use next?")
        elif(cmd == "list"):
            print("Please submit the directory path")
            d_path = input("> ").strip().lower()
            print(conn.root.print_directory(d_path))
            print("what command do you want to use next?")
        else:
            print("bad input")

if __name__ == "__main__":
    main()