# coding=utf-8

__author__ = 'artemii'
from pymorphy import get_morph
from pymorphy.contrib import tokenizers

f = open('negative_words.txt', 'r')
resultFile = open('negative_words_normalized.txt', 'a')
morph = get_morph('.')

#normalized = morph.normalize('‘Œ“Œ¿œœ¿–¿“¿'.decode("utf-8"))
#print normalized.pop().lower().encode("utf-8")

for line in f :
#    word = raw_input()
    words = tokenizers.extract_words(line.decode("utf-8"))
    word = words.next()
    normalized = morph.normalize(word.upper())
    resultFile.write(normalized.pop().lower().encode("utf-8") + '\n')
#    print normalized.pop().lower()

# for word pairs
#for line in f :
##    word = raw_input()
#    words = tokenizers.extract_words(line.decode("utf-8"))
#    normalized_fst = morph.normalize(words.next().upper())
#    normalized_snd = morph.normalize(words.next().upper())
#    resultFile.write(normalized_fst.pop().lower().encode("utf-8") + ' ' + normalized_snd.pop().lower().encode("utf-8") + '\n')