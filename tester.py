import os
import time

BIN_DIR = r'bin'
FILE_PATH = r'src/Main.java'

def tester():
    SRC_DIR = os.path.dirname(FILE_PATH)
    COMPILED_CLASS = os.path.basename(FILE_PATH).replace('.java', '')

    elements = 100
    thread_selection = (1, 10, 100)
    for threads in thread_selection:
        while elements != 3000:
            compile = f'javac -sourcepath {SRC_DIR} -d {BIN_DIR} {FILE_PATH}'
            execute = f'java -classpath {BIN_DIR} {COMPILED_CLASS} {elements} {threads}'
            os.system(compile)
            start = time.time()
            os.system(execute)
            print(f'Amount of elements: {elements}\n'
                  f'Amount of threads: {threads}\n'
                  f'Time execution: {time.time() - start}\n')
            elements += 10

if __name__ == '__main__':
    tester()