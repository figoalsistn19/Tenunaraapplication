import numpy as np
from PIL import Image
from tensorflow import keras
from keras_preprocessing import image
from keras.applications.xception import preprocess_input
from flask import Flask, jsonify, request

app = Flask(__name__)

@app.route('/scanner', methods=['POST'])
def predict_tenun():
    uploaded_files = request.files['file']
    labels = (['Gringsing','Palembang','Tenun Ikat Dayak Sintang','Tenun Rangrang NTB','Tenun Sasak NTB','Ulos Ragihotang'])
    model = keras.models.load_model('model/best_model.h5')
 
    # predicting images
    img = Image.open(uploaded_files).resize((300, 300))
    x = image.img_to_array(img)
    x = np.expand_dims(x, axis=0)
    x = preprocess_input(x)

    images = np.vstack([x])
    proba = model.predict(images)[0]
    kain_tenun = {"Nama_tenun":"", "Probabilitas":0}
    
    for (label, p) in zip(labels, proba):
        print("{}: {:.2f}%".format(label, p * 100))
        if((p*100)>kain_tenun['Probabilitas']):
            kain_tenun = {"Nama_tenun": label, "Probabilitas": p*100}
    
    if(kain_tenun['Probabilitas']<=60):
            kain_tenun = {"Nama_tenun": None, "Probabilitas": 0}
            
    img.close()
    return kain_tenun
        
        
if __name__ == '__main__':
    app.run(debug=True)