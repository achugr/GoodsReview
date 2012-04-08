# coding=utf-8
from pymorphy import get_morph
import sys


__author__ = 'artemii'

morph = get_morph('.')

word = raw_input()

word = word.decode("utf-8").upper()

info = morph.normalize(word)

print info.pop().encode('utf-8')

