import os
import time

BIN_DIR = r'bin'
FILE_PATH = r'src/Main.java'

def tester():
    elements = 20
    threads = 2

    SRC_DIR = os.path.dirname(FILE_PATH)
    COMPILED_CLASS = os.path.basename(FILE_PATH).replace('.java', '')

    compile = f'javac -sourcepath {SRC_DIR} -d {BIN_DIR} {FILE_PATH}'
    execute = f'java -classpath {BIN_DIR} {COMPILED_CLASS} {elements} {threads}'
    os.system(compile)
    os.system(execute)

if __name__ == '__main__':
    tester()