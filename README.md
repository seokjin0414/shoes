# shoes
프로젝트명:what shoes 
개발환경:Intellij, MySql, SpringBoot, JPA, Thymeleaf, Selenium 
OS 및 DB:macOS, maria DB 
사용언어:JAVA, Java Script, CSS, HTML, SQL, Python3 

shoes DB 구축, resale 내역 Selenium 이용하여 크롤링 Tensorflow, Keras 이용하여 이미지 모델 구축 shoes 이미지 판별

<img width="984" alt="스크린샷 2022-08-02 15 48 18" src="https://user-images.githubusercontent.com/80299204/182310365-8bea703c-f92f-4407-9fe0-b7b28b154ca3.png">



# 이미지 model
!pip install hickle matplotlib sklearn keras tensorflow

from tensorflow.python.client import device_lib
def get_available_gpus():
    return [x.name for x in device_lib.list_local_devices()]
get_available_gpus()

import os
import hickle as hkl
from numpy import load
import numpy as np
import matplotlib.pylab as plt
import itertools
import sklearn
from sklearn import model_selection
from sklearn.model_selection import train_test_split
from os import listdir
from numpy import asarray
from numpy import save
from keras.preprocessing.image import load_img
from keras.preprocessing.image import img_to_array
import keras
from keras import backend as K
from keras.callbacks import Callback, EarlyStopping, ReduceLROnPlateau, ModelCheckpoint
from keras.preprocessing.image import ImageDataGenerator
from keras.utils.np_utils import to_categorical
from keras.models import Sequential, model_from_json
from keras.optimizers import SGD, RMSprop, Adam, Adagrad, Adadelta
from keras.layers import Dense, Dropout, Activation, Flatten, BatchNormalization, Conv2D, MaxPool2D, MaxPooling2D
import tensorflow as tf
from tensorflow.keras.layers import Dense, Conv2D, MaxPooling2D, Flatten, Dropout
from tensorflow.keras.models import Sequential
from tensorflow.keras.callbacks import ModelCheckpoint
from tensorflow.keras.applications import VGG16, Xception, ResNet152V2, InceptionResNetV2, DenseNet201, NASNetLarge
%matplotlib inline

x_train=hkl.load('x_train1.hkl')
x_test=load('x_test1.npy')
y_train=load('y_train1.npy')
y_test=load('y_test1.npy')

import gc
import warnings
from keras.callbacks import EarlyStopping, ReduceLROnPlateau
from keras import backend as K

warnings.filterwarnings('ignore')
earlystop=EarlyStopping(patience=5)
learning_rate_reduction=ReduceLROnPlateau(monitor='val_accuracy',
                                          patience=2,
                                          verbose=0,
                                          factor=0.5,
                                          min_lr=0.00001)
callbacks=[earlystop, learning_rate_reduction]
BATCH_SIZE = 50
epochs=1
validation_steps= int(len(list(x_test))) // BATCH_SIZE

collected = gc.collect()

x_train0=x_train[0:100]
y_train0=y_train[0:100].copy()
x_train1=x_train[0:500]
y_train1=y_train[0:500].copy()
x_train2=x_train[0:1000]
y_train2=y_train[0:1000].copy()
x_train3=x_train[0:2000]
y_train3=y_train[0:2000].copy()
x_train4=x_train[0:3000]
y_train4=y_train[0:3000].copy()
x_train5=x_train[0:5000]
y_train5=y_train[0:5000].copy()
x_train6=x_train[0:7000]
y_train6=y_train[0:7000].copy()
x_train7=x_train[0:10000]
y_train7=y_train[0:10000].copy()
x_train8=x_train[0:13000]
y_train8=y_train[0:13000].copy()
x_train9=x_train[0:15000]
y_train9=y_train[0:15000].copy()

for i in range(150):
    for j in range(10):
        collected=gc.collect()
        model=NASNetLarge(weights='imagenet', include_top=False, input_shape=(331,331,3))
        model.trainable=False
        model1=Sequential([model,Flatten(),Dense(64, activation='relu'), Dense(1, 'sigmoid')])
        model1.compile(optimizer='adam', loss='binary_crossentropy', metrics=['accuracy'])
        exec('transfer_history=model1.fit(x_train'+str(j)+',y_train'+str(j)+',validation_data=(x_test,y_test), steps_per_epoch=int(len(list(x_train'+str(j)+'))) // BATCH_SIZE, validation_steps=validation_steps,epochs=epochs, callbacks=callbacks, verbose=0)')
        exec('np.savetxt(\'NNL'+str(j+1)+'_0_'+str(i+1)+'vacc.txt\',transfer_history.history[\'val_accuracy\'])')
        del model
        del model1
        K.clear_session()
        
for i in range(150):
    for j in range(10):
        collected=gc.collect()
        model=DenseNet201(weights='imagenet', include_top=False, input_shape=(331,331,3))
        model.trainable=False
        model1=Sequential([model,Flatten(),Dense(64, activation='relu'), Dense(1, 'sigmoid')])
        model1.compile(optimizer='adam', loss='binary_crossentropy', metrics=['accuracy'])
        exec('transfer_history=model1.fit(x_train'+str(j)+',y_train'+str(j)+',validation_data=(x_test,y_test), steps_per_epoch=int(len(list(x_train'+str(j)+'))) // BATCH_SIZE, validation_steps=validation_steps,epochs=epochs, callbacks=callbacks, verbose=0)')
        exec('np.savetxt(\'DN'+str(j+1)+'_0_'+str(i+1)+'vacc.txt\',transfer_history.history[\'val_accuracy\'])')
        del model
        del model1
        K.clear_session()
        
for i in range(150):
    for j in range(10):
        collected=gc.collect()
        model=Xception(weights='imagenet', include_top=False, input_shape=(331,331,3))
        model.trainable=False
        model1=Sequential([model,Flatten(),Dense(64, activation='relu'), Dense(1, 'sigmoid')])
        model1.compile(optimizer='adam', loss='binary_crossentropy', metrics=['accuracy'])
        exec('transfer_history=model1.fit(x_train'+str(j)+',y_train'+str(j)+',validation_data=(x_test,y_test), steps_per_epoch=int(len(list(x_train'+str(j)+'))) // BATCH_SIZE, validation_steps=validation_steps,epochs=epochs, callbacks=callbacks, verbose=0)')
        exec('np.savetxt(\'XC'+str(j+1)+'_0_'+str(i+1)+'vacc.txt\',transfer_history.history[\'val_accuracy\'])')
        del model
        del model1
        K.clear_session()
        
