import os
import codecs
itemName = input("item name: ").strip()
text = """{
  "parent": "item/handheld",
  "textures": {
    "layer0": "kitchenparkour:item/""" + itemName + """"
  }
}
"""
with codecs.open(itemName + ".json", 'w+', encoding='utf8') as f:
    f.write(text)
