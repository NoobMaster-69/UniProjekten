from bs4 import BeautifulSoup
import requests
import sys

def main():
    api_key = "AIzaSyAsE6bvs1SvthRHym3849FmIsRD83fKzoI"
    try:
     page = sys.argv[1]
    except:
       print("please pass an additional argument")
    r = requests.get(f"https://en.wikipedia.org/api/rest_v1/page/html/{page}")
    soup = BeautifulSoup(r.content, 'html.parser')

    for element in soup.find_all(['script', 'style', 'sup']):
        element.decompose()

    text = soup.get_text() 


    summary = requests.post(f"https://generativelanguage.googleapis.com/v1beta/models/gemini-2.0-flash:generateContent?key={api_key}", data = """'{/
        "contents": [
        {
            "parts": [
            {
                "text": f"Summarize this text:"+" "+{text}
            }/
            ]/
        }/
        ]/
    }' """, headers={'Content-Type: application/json'})
    summary_json = summary.json()
    summary_text = summary_json['candidates'][0]['content']['parts'][0]['text']
    print(summary_text)


if __name__ == '__main__':
  main()