import os
import codecs
from os.path import isfile, join
files = [f for f in os.listdir() if isfile(f)]
files = list(filter(lambda x: x[-5:].lower() == ".json", files))
for file in files:
    text = ""
    with codecs.open(file, 'r', encoding='utf8') as f:
        text = f.read()
    # process Unicode text
    text = text.replace(
        """    "layer0":""",
        """    "layer0":"""
    ) #\
        #.replace("   \"parent\": \"item/handheld\"", "  \"parent\": \"item/handheld\"") \
        #.replace("   \"textures\": {", "  \"textures\": {") \
        #.replace("   }\n}", "  }\n}")
    with codecs.open(file, 'w', encoding='utf8') as f:
        f.write(text)
