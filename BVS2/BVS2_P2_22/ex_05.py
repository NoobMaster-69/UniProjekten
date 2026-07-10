from flask import Flask, jsonify, request, render_template, redirect, session


app = Flask(__name__)
app.secret_key = '123'

@app.route('/', methods=['GET', 'POST'])
def login():
    if request.method == 'POST':
        username = request.form['username'].strip()
        if username:
            session['username'] = username
            return redirect('/chat' if username.lower() != 'admin' else '/admin')
    return render_template('login.html')

@app.route('/chat', methods=['GET', 'POST'])
def send_message():
    username = session.get('username')

    if not username or username.lower() == 'admin':
        return redirect('/')
    
    if request.method == 'POST':
        message = request.form['message']
        if message:
            datei = open("chat.txt", "a", encoding='utf8')
            datei.write(username+': '+message + '\n')

    return render_template('chat.html', username = username)

@app.route('/messages', methods = ['GET', 'POST'])
def show_messages():
    try:
        with open('chat.txt','r', encoding='utf-8') as reader:
            lines = reader.readlines()
            return jsonify(lines)
    except FileNotFoundError:
            return jsonify([])
           

if __name__ == '__main__':
    app.run(debug=True)


