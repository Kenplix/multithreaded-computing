import os
import time
import csv

BIN_DIR = r'bin'
FILE_PATH = r'src/Main.java'


def tester(*thread_selection: int, elements: int):
    src_dir = os.path.dirname(FILE_PATH)
    compiled_class = os.path.basename(FILE_PATH).replace('.java', '')
    compile_ = f'javac -sourcepath {src_dir} -d {BIN_DIR} {FILE_PATH}'
    os.system(compile_)

    for threads in thread_selection:
        execute = f'java -classpath {BIN_DIR} {compiled_class} {elements} {threads}'
        while elements <= 2000:
            data_path = f'data/AOT-{threads}.csv'

            if not os.path.isfile(data_path):
                with open(data_path, 'a') as file:
                    start = time.time()
                    os.system(execute)
                    time_ = time.time() - start

                    writer = csv.DictWriter(file, fieldnames=['Elements', 'Time'])
                    writer.writerow({
                        'Elements': elements,
                        'Time': time_,
                    })
                    elements += 100
            else:
                break
        elements = 100


if __name__ == '__main__':
    tester(1, 10, 100, elements=100)
