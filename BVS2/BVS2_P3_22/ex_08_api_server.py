from flask import Flask, render_template, request, redirect, session, jsonify
import requests
import os

app = Flask(__name__)
app.secret_key = 'supersecretkey'
saved_prompt = {"prompt": None}
ollama_url = os.getenv("OLLAMA_URL", "http://localhost:11434/api/generate")

@app.route('/set_prompt', methods=['GET'])
def prompt_page():
    prompt = request.args['prompt']
    saved_prompt['prompt'] = prompt
    return jsonify({"message": "Prompt saved", "prompt": prompt})


@app.route('/query', methods=['POST'])
def query_page():
    message = request.json.get('message')
    prompt = saved_prompt.get("prompt")
    
    kombiniert = f"{prompt} "+f" {message}"

    try:
        response = requests.post(ollama_url, json={
           "model": "phi",
           "prompt": kombiniert,
           "stream": False
        })
        response.raise_for_status()
    except requests.RequestException as e:
        return jsonify({"error": "Failed to contact model API", "details": str(e)})
    print("Response text:", response.text)
    
    data = response.json()
    answer_text = data.get("response", "")

    return jsonify({"answer": answer_text})


if __name__ == '__main__':
    app.run(debug=True,port = 5001, host='0.0.0.0')
