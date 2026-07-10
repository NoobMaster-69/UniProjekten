from flask import Flask, request, render_template
import requests

app = Flask(__name__)
app.secret_key = '123'
server_url = "http://api:5001"

@app.route("/", methods=['GET', 'POST'])
def request_page():
    answer = None
    if request.method == 'POST':
        message = request.form['message']
        if message:
            try:
                requests.get(f"{server_url}/set_prompt", params = {"prompt": "Answer in rhymes"})
                response = requests.post(f"{server_url}/query", json={"message": message})
                answer = response.json().get("answer", "No answer returned.")
            except Exception as e:
                answer = f"Error:  {e}"
    return render_template('request_page.html', response = answer)
        
    




if __name__ == '__main__':
    app.run(debug=True,port = 5002, host='0.0.0.0')