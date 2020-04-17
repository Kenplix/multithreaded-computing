#!venv/bin/python3
# -*- coding: UTF-8 -*-

import os
import time
import csv

import matplotlib.pyplot as plt
import numpy as np

BIN_DIR = r'bin'
FILE_PATH = r'src/Main.java'


def data_path(number: int) -> str:
    return f'data/AOT-{number}.csv'


def compiler() -> str:
    """
    Compiles a program written on Java and returns
    the class name to run the program
    """

    src_dir = os.path.dirname(FILE_PATH)
    compile_ = f'javac -sourcepath {src_dir} -d {BIN_DIR} {FILE_PATH}'
    os.system(compile_)
    return os.path.basename(FILE_PATH).replace('.java', '')


def tester(*thread_selection: int, elements: int, increment: int = 100, limit: int = 2000) -> None:
    """
    Executes a compiled program for certain
    streams in increments of elements
    """

    compiled_class = compiler()

    start_value = elements
    for threads in thread_selection:
        path = data_path(threads)
        if not os.path.isfile(path) or os.stat(path).st_size == 0:
            while elements <= limit:
                execute = f'java -classpath {BIN_DIR} {compiled_class} {elements} {threads}'
                start = time.time()
                os.system(execute)
                time_ = time.time() - start

                with open(path, 'a') as file:
                    writer = csv.writer(file)
                    writer.writerow([elements, time_])

                print(f'Amount of elements: {elements}\n'
                      f'Amount of threads: {threads}\n'
                      f'Lead time: {time_}\n')

                elements += increment
        else:
            elements = start_value


def drawer(*thread_selection: int) -> None:
    """
    Draws data recorded in CSV tables
    for given streams if such tables exist
    """
    fig, ax = plt.subplots()
    for threads in thread_selection:
        path = data_path(threads)
        if os.path.isfile(path) and os.stat(path).st_size != 0:
            elements, time_ = np.loadtxt(path, unpack=True, delimiter=',')
            ax.plot(elements, time_, label=path)

    ax.set_xlabel('Elements')
    ax.set_ylabel('Time')
    ax.legend()
    plt.show()


if __name__ == '__main__':
    threads = (1, 5, 10, 25, 50, 100)
    tester(*threads, elements=100)
    drawer(*threads)
