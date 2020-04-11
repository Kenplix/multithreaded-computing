import os
import time
import csv

import matplotlib.pyplot as plt
import numpy as np

BIN_DIR = r'bin'
FILE_PATH = r'src/Main.java'


def data_path(number):
    return f'data/AOT-{number}.csv'


def tester(*thread_selection: int, elements: int, increment: int = 100, limit: int = 2000):
    src_dir = os.path.dirname(FILE_PATH)
    compiled_class = os.path.basename(FILE_PATH).replace('.java', '')
    compile_ = f'javac -sourcepath {src_dir} -d {BIN_DIR} {FILE_PATH}'
    os.system(compile_)

    start_value = elements
    for threads in thread_selection:
        path = data_path(threads)
        execute = f'java -classpath {BIN_DIR} {compiled_class} {elements} {threads}'

        if not os.path.isfile(path) or os.stat(path).st_size == 0:
            while elements <= limit:
                start = time.time()
                os.system(execute)
                time_ = time.time() - start

                with open(path, 'a') as file:
                    writer = csv.writer(file)
                    writer.writerow([elements, time_])

                elements += increment
        else:
            elements = start_value


def drawer(*thread_selection):
    elements = [[] for _ in range(len(thread_selection))]
    times = [[] for _ in range(len(thread_selection))]

    for index, threads in enumerate(thread_selection):
        with open(data_path(threads), 'r') as f:
            reader = csv.reader(f)
            for elem, time_ in reader:
                elements[index].append(elem)
                times[index].append(time_)

    print(times)
    print(elements)


if __name__ == '__main__':
    thread_selection = (1, 10, 100)
    tester(*thread_selection, elements=100)
    drawer(*thread_selection)
