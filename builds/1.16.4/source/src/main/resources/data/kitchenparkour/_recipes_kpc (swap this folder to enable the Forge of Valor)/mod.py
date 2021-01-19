import os
import codecs

path = os.getcwd()

for root, directories, files in os.walk(path):
	for name in files:
		if "mod.py" in name:
			continue
		text = ''
		with codecs.open(name, 'r+', encoding='utf8') as f:
			text = f.read()
			text = text.replace('kitchenparkour:kpc_crafting_shaped', 'minecraft:crafting_shaped').replace('kitchenparkour:kpc_crafting_shapeless', 'minecraft:crafting_shapeless')
			print(text)
		with codecs.open("new/" + name[:-5] + "_mc.json", 'w+', encoding='utf8') as f:
		    f.write(text)
